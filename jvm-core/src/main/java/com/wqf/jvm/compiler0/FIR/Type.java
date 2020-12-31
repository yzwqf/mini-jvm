package com.wqf.jvm.compiler0.FIR;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Type {
    String name;

    Type(String name) {
        this.name = name;
    }

    String getName() { return name; }
}

class BuiltinType extends Type {
    public static final BuiltinType ByteTy = new BuiltinType("byte");
    public static final BuiltinType ShortTy = new BuiltinType("short");
    public static final BuiltinType IntTy = new BuiltinType("int");
    public static final BuiltinType LongTy = new BuiltinType("long");
    public static final BuiltinType FloatTy = new BuiltinType("float");
    public static final BuiltinType DoubleTy = new BuiltinType("double");
    public static final BuiltinType CharTy = new BuiltinType("char");
    public static final BuiltinType BooleanTy = new BuiltinType("boolean");
    public static final BuiltinType VoidTy = new BuiltinType("void");

    public BuiltinType(String name) {
        super(name);
    }
}

class MethodType extends Type {
    static String getMethodName(Type retTy, Type... argTys) {
        String args = "void";
        if (argTys.length > 0)
            args = String.join(",",
                    Arrays.stream(argTys).map(type -> type.getName()).collect(Collectors.toList()));
        return args + "->" + retTy.getName();
    }

    Type retTy;
    Type[] argTys;

    public MethodType(Type retTy, Type... argTys) {
        super(getMethodName(retTy, argTys));
        this.retTy = retTy;
        if (argTys.length > 0)
            this.argTys = argTys;
    }

    int getNumParam() {
        if (argTys == null) return 0;
        return argTys.length;
    }
}

class ClassType extends Type {
    C0Class cls;

    public ClassType(C0Class cls) {
        super(cls.getName());
        this.cls = cls;
    }
}

class ArrayType extends Type {
    Type elementTy;

    public ArrayType(Type elementTy) {
        super(elementTy.getName() + "[]");
        this.elementTy = elementTy;
    }

    public Type getElementTy() {
        return elementTy;
    }
}