package com.wqf.jvm.compiler0.FIR;

import java.util.Arrays;
import java.util.stream.Collectors;

public class MethodType extends Type {
    static String getMethodName(Type retTy, Type[] argTys) {
        if (argTys.length > 0) {
            StringBuilder builder = new StringBuilder();
            builder.append('(').append(String.join(",",
                    Arrays.stream(argTys).map(type -> type.getName()).collect(Collectors.toList()))).
                    append(") -> ").append(retTy.getName());
            return builder.toString();
        }
        return "() -> " + retTy.getName();
    }

    Type retTy;
    Type[] argTys;

    public MethodType(Type retTy, Type[] argTys) {
        super(getMethodName(retTy, argTys), TypeKind.MethodTy);
        this.retTy = retTy;
        this.argTys = argTys;
    }

    public int getNumParam() {
        if (argTys == null) return 0;
        return argTys.length;
    }

    public Type[] getArgTys() {
        return argTys;
    }

    public Type getArgType(int idx) {
        return argTys[idx];
    }
}
