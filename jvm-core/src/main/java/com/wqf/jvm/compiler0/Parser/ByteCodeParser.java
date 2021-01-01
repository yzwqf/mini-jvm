package com.wqf.jvm.compiler0.Parser;

import com.gxk.jvm.instruction.Instruction;
import com.gxk.jvm.rtda.heap.Class;
import com.gxk.jvm.rtda.heap.Field;
import com.gxk.jvm.rtda.heap.Heap;
import com.gxk.jvm.util.Utils;
import com.wqf.jvm.compiler0.FIR.*;
import com.wqf.jvm.compiler0.FIR.Module;

import java.util.HashMap;
import java.util.List;

public class ByteCodeParser {
    private static int moduleCnt = 0;

    Module module;
    HashMap<Class, C0Class> classMap;
    HashMap<String, Type> parsedTypes;

    ByteCodeParser() {
        this.module = new Module("Module" + moduleCnt++);
        this.classMap = new HashMap<>();
        this.parsedTypes = new HashMap<String, Type>() {
            {
                put("Z", BuiltinType.BooleanTy);
                put("C", BuiltinType.CharTy);
                put("B", BuiltinType.ByteTy);
                put("S", BuiltinType.ShortTy);
                put("I", BuiltinType.IntTy);
                put("J", BuiltinType.LongTy);
                put("F", BuiltinType.FloatTy);
                put("D", BuiltinType.DoubleTy);
                put("[Z", ArrayType.BooleanArrayTy);
                put("[C", ArrayType.CharArrayTy);
                put("[B", ArrayType.ByteArrayTy);
                put("[S", ArrayType.ShortArrayTy);
                put("[I", ArrayType.IntArrayTy);
                put("[J", ArrayType.LongArrayTy);
                put("[F", ArrayType.FloatArrayTy);
                put("[D", ArrayType.DoubleArrayTy);
            }
        };
    }

    Type parseType(String name) {
        Type type = parsedTypes.get(name);
        if (type != null) return type;
        switch (name.charAt(0)) {
            case '[': type = new ArrayType(parseType(name.substring(1))); break;
            case '(':
                List<String> args = Utils.parseMethodDescriptor(name);
                Type[] argTypes = args.stream().map(this::parseType).toArray(Type[]::new);
                if (argTypes.length == 0)
                    argTypes = new Type[] { BuiltinType.VoidTy };
                type = new MethodType(parseType(name.substring(name.indexOf(')')+1)), argTypes);
                break;
            default: // L, object,
                Class cls = Heap.findClass(name.substring(1));
                assert cls != null : "Class is not register!";
                type = new ClassType(parseClass(cls));
        }
        parsedTypes.put(name, type);
        return type;
    }

    Method parseMethod(com.gxk.jvm.rtda.heap.Method rtMethod) {
        C0Class cls = classMap.get(rtMethod.clazz);
        MethodType methodType = (MethodType) parseType(rtMethod.descriptor);
        Method method = new Method(cls, rtMethod, methodType);

        int numParams = methodType.getNumParam();
        LocalVar[] localVars = numParams > 0 ? new LocalVar[numParams] : null;
        for (int i = 0; i < numParams; ++i)
            localVars[i] = new LocalVar(method.getName() + "." + i, methodType.getArgType(i), method);
        method.setParamVars(localVars);

        int localSize = rtMethod.maxLocals - numParams;
        LocalVar[] realLocals = localSize > 0 ? new LocalVar[localSize] : null;
        for (int i = 0; i < localSize; ++i)
            realLocals[i] = new LocalVar(method.getName() + "." + i, Type.unknownType, method);
        method.setLocalVars(realLocals);

        method.setBasicBlocks(new FIRBuilder(method).build().getBasicBlocks());
        return method;
    }

    FieldVar parseVariable(Field field) {
        return new FieldVar(field, parseType(field.descriptor));
    }

    boolean isObjectInit(com.gxk.jvm.rtda.heap.Method method) {
        return method.name.equals("<init>");
    }

    boolean isClassInit(com.gxk.jvm.rtda.heap.Method method) {
        return method.name.equals("<cinit>");
    }

    C0Class parseClass(Class cls) {
        C0Class c0Class = classMap.getOrDefault(cls, null);
        if (c0Class != null)
            return c0Class;
        c0Class = new C0Class(cls);
        classMap.put(cls, c0Class);

        Class superCls = cls.getSuperClass();
        C0Class superC0Cls = superCls != null ? parseClass(superCls) : null;
        c0Class.setSuperCls(superC0Cls);

        // Interfaces
        c0Class.setInterfaces(cls.getInterfaces().stream().map(this::parseClass).toArray(C0Class[]::new));
        // static methods
        c0Class.setStaticMethods(cls.methods.stream().filter(method -> method.isStatic() && !method.isNative() && !isClassInit(method)).
                map(this::parseMethod).toArray(Method[]::new));
        // object methods
        c0Class.setObjMethods(cls.methods.stream().filter(method -> !method.isStatic() && !method.isNative() && !isObjectInit(method)).
                map(this::parseMethod).toArray(Method[]::new));
        // native methods
        c0Class.setNativeMethods(cls.methods.stream().filter(com.gxk.jvm.rtda.heap.Method::isNative).
                map(this::parseMethod).toArray(Method[]::new));
        // <cinit>
        com.gxk.jvm.rtda.heap.Method cinit = cls.methods.stream().filter(this::isClassInit).findFirst().orElse(null);
        if (cinit != null)
            c0Class.setClassInit(parseMethod(cinit));
        // <init>
        c0Class.setObjInit(cls.methods.stream().filter(this::isObjectInit).map(this::parseMethod).toArray(Method[]::new));

        // static variables
        c0Class.setStaticVars(cls.fields.stream().filter(Field::isStatic).map(this::parseVariable).toArray(FieldVar[]::new));
        // object variables
        c0Class.setObjVars(cls.fields.stream().filter(field -> !field.isStatic()).map(this::parseVariable).toArray(FieldVar[]::new));
        return c0Class;
    }
}
