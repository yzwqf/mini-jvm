package com.wqf.jvm.compiler0.FIR;

public class ArrayType extends Type {
    public static final ArrayType ByteArrayTy =  new ArrayType(BuiltinType.ByteTy);
    public static final ArrayType ShortArrayTy = new ArrayType(BuiltinType.ShortTy);
    public static final ArrayType IntArrayTy = new ArrayType(BuiltinType.IntTy);
    public static final ArrayType LongArrayTy = new ArrayType(BuiltinType.LongTy);
    public static final ArrayType FloatArrayTy = new ArrayType(BuiltinType.FloatTy);
    public static final ArrayType DoubleArrayTy = new ArrayType(BuiltinType.DoubleTy);
    public static final ArrayType CharArrayTy = new ArrayType(BuiltinType.CharTy);
    public static final ArrayType BooleanArrayTy = new ArrayType(BuiltinType.BooleanTy);

    Type elementTy;

    public ArrayType(Type elementTy) {
        super(elementTy.getName() + "[]", TypeKind.ArrayTy);
        this.elementTy = elementTy;
    }

    public Type getElementTy() {
        return elementTy;
    }
}
