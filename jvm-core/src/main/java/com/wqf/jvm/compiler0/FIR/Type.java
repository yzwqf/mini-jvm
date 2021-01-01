package com.wqf.jvm.compiler0.FIR;

public class Type {
    public enum TypeKind {
        BuiltinTy, MethodTy, ClassTy, ArrayTy, UnknowTy
    }

    public static Type unknownType = new Type("unknown", TypeKind.UnknowTy);

    String name;
    TypeKind kind;

    Type(String name, TypeKind kind) {
        this.name = name;
        this.kind = kind;
    }

    public String getName() { return name; }

    public TypeKind getKind() {
        return kind;
    }
}

