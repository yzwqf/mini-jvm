package com.wqf.jvm.compiler0.FIR;

public class BuiltinType extends Type {
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
        super(name, TypeKind.BuiltinTy);
    }
}
