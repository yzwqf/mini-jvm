package com.wqf.jvm.compiler0.FIR;

public class Type {
}

class BuiltinType extends Type {
    public static final BuiltinType ByteTy = new BuiltinType();
    public static final BuiltinType ShortTy = new BuiltinType();
    public static final BuiltinType IntTy = new BuiltinType();
    public static final BuiltinType LongTy = new BuiltinType();
    public static final BuiltinType FloatTy = new BuiltinType();
    public static final BuiltinType DoubleTy = new BuiltinType();
    public static final BuiltinType CharTy = new BuiltinType();
    public static final BuiltinType BooleanTy = new BuiltinType();
    public static final BuiltinType VoidTy = new BuiltinType();
}

class ClassType extends Type {
    C0Class cls;

    ClassType(C0Class cls) {
        this.cls = cls;
    }
}