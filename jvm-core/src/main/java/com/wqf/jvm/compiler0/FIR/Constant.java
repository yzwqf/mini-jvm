package com.wqf.jvm.compiler0.FIR;

public class Constant extends Value {
    public Constant(String name, Type type) {
        super(name);
        setType(type);
    }

    String dump() { return getName(); }
}

class IntConstant extends Constant {
    private static int idx = 0;
    private static String getConstName() {
        return "intConst" + idx++;
    }

    long val;

    public IntConstant(long val, BuiltinType type) {
        super(getConstName(), type);
        this.val = val;
    }

    public boolean getBooleanVal() {
        return this.val != 0;
    }

    public byte getByteVal() {
        return (byte) val;
    }

    public short getShortVal() {
        return (short) val;
    }

    public char getCharVal() {
        return (char) val;
    }

    public int getIntVal() {
        return (int) val;
    }

    public long getLongVal() {
        return val;
    }

    boolean isBoolean() { return getType() == BuiltinType.BooleanTy; }
    boolean isByte() { return getType() == BuiltinType.ByteTy; }
    boolean isShort() { return getType() == BuiltinType.ShortTy; }
    boolean isChar() { return getType() == BuiltinType.CharTy; }
    boolean isInt() { return getType() == BuiltinType.IntTy; }
    boolean isLong() { return getType() == BuiltinType.LongTy; }
}

class FloatConstant extends Constant {
    private static int idx = 0;
    private static String getConstName() {
        return "floatConst" + idx++;
    }

    float val;

    public FloatConstant(float val) {
        super(getConstName(), BuiltinType.FloatTy);
        this.val = val;
    }

    public float getVal() {
        return val;
    }
}

class DoubleConstant extends Constant {
    private static int idx = 0;
    private static String getConstName() {
        return "doubleConst" + idx++;
    }

    double val;

    public DoubleConstant(double val) {
        super(getConstName(), BuiltinType.FloatTy);
        this.val = val;
    }

    public double getVal() {
        return val;
    }
}
