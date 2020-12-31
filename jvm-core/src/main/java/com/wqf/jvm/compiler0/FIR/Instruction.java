package com.wqf.jvm.compiler0.FIR;

import com.gxk.jvm.rtda.heap.Class;
import com.sun.org.apache.bcel.internal.generic.RETURN;
import com.sun.tools.javac.comp.Check;

import java.util.Arrays;

public abstract class Instruction extends Value {
    enum OpCode {
        // Binary Op
        Add("Add"), Sub("Sub"), Mul("Mul"),
        Div("Div"), Rem("Rem"), Shr("Shr"),
        Shl("Shl"), UShr("UShr"), Or("Or"),
        Xor("Xor"), And("And"), Cmp("Cmp"),

        // Unary Op
        Neg("Neg"), Inc("Inc"), Cast("cast"),

        // control flow
        Branch("Br"), Return("Ret"),

        // reference
        New("new"), NewArray("newArray"),
        ArrayLength("arrayLength"),
        CheckCast("objCast"), InstanceOf("instanceOf");

        private String name;
        OpCode(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    OpCode opCode;
    BasicBlock basicBlock;
    Value[] operands;

    Instruction(OpCode opCode, String name, Value... operands) {
        super(name);
        this.opCode = opCode;
        if (operands.length > 0)
            this.operands = operands;
        Arrays.stream(operands).forEach(operand -> operand.addUser(this));
    }

    int getOperandNum() { return operands != null ? operands.length : 0; }
    Value getOperand(int i) { return operands[i]; }
    public BasicBlock getBasicBlock() {
        return basicBlock;
    }

    public Method getMethod() {
        return getBasicBlock().getMethod();
    }

    void setOperand(int i, Value operand) { operands[i] = operand; }
    public void setBasicBlock(BasicBlock basicBlock) {
        this.basicBlock = basicBlock;
    }
}

class BinaryOp extends Instruction {
    private static int[] idxs = new int[12];
    private static String getInstName(OpCode opCode) {
        int idx = opCode.compareTo(OpCode.Add);
        if (idx >= 0 && opCode.compareTo(OpCode.Cmp) <= 0)
            return opCode.getName() + idxs[idx]++;
        return "BAD";
    }

    private static boolean isLegalBinaryOp(OpCode opCode) {
        return opCode.compareTo(OpCode.Add) >= 0 &&
                opCode.compareTo(OpCode.Cmp) <= 0;
    }

    public BinaryOp(OpCode opCode, Value lhs, Value rhs) {
        super(opCode, getInstName(opCode), lhs, rhs);
        assert isLegalBinaryOp(opCode) : "Not a legal binary Operator.";
        if (opCode.equals(OpCode.Cmp))
            setType(lhs.getType());
        else
            setType(BuiltinType.BooleanTy);

    }

    Value getLHS() { return getOperand(0); }
    Value getRHS() { return getOperand(1); }

    String dump() {
        StringBuilder builder = new StringBuilder();
        builder.append(getName()).append(" = ").append(getLHS().getType().getName()).append(" ").
                append(getLHS().getName()).append(" ").append(opCode.getName()).append(" ").
                append(getRHS().getType().getName()).append(" ").append(getRHS().getName());
        return builder.toString();
    }
}

class UnaryOp extends Instruction {
    private static int[] idxs = new int[2];
    private static String getInstName(OpCode opCode) {
        if (opCode.compareTo(OpCode.Neg) == 0)
            return opCode.getName() + idxs[0]++;
        else if (opCode.compareTo(OpCode.Inc) == 0)
            return opCode.getName() + idxs[1]++;
        return "BAD";
    }

    private static boolean isLegalUnaryOp(OpCode opCode) {
        return opCode.compareTo(OpCode.Neg) == 0 ||
                opCode.compareTo(OpCode.Inc) == 0;
    }

    public UnaryOp(OpCode opCode, Value operand) {
        super(opCode, getInstName(opCode), operand);
        assert isLegalUnaryOp(opCode) : "Not a legal Unary Operator.";
        setType(operand.getType());
    }

    Value getOperand() { return getOperand(0); }

    String dump() {
        StringBuilder builder = new StringBuilder();
        builder.append(getName()).append(" = ").append(opCode.getName()).append(" ").
                append(getOperand().getType()).append(" ").append(getOperand().getName());
        return builder.toString();
    }
}

class CastInst extends Instruction {
    private static int idx = 0;
    private static String getInstName(BuiltinType ty0, BuiltinType ty1) {
        return ty0.getName() + "2" + ty1.getName() + idx++;
    }

    CastInst(BuiltinType toType, Value operand) {
        super(OpCode.Cast, getInstName(toType, (BuiltinType) operand.getType()), operand);
        setType(toType);
    }

    Value getOperand() { return getOperand(0); }

    String dump() {
        StringBuilder builder = new StringBuilder();
        builder.append(getName()).append(" = ").append(opCode.getName()).append(" ").
                append(getOperand().getType()).append(" ").append(getOperand().getName());
        return builder.toString();
    }
}

class BranchInst extends Instruction {
    private static int idx = 0;
    private static String getInstName() {
        return OpCode.Branch.getName() + idx++;
    }

    public BranchInst(Value succ) {
        super(OpCode.Branch, getInstName(), succ);
        setType(BuiltinType.VoidTy);
    }

    public BranchInst(Value predicate, BasicBlock tb, BasicBlock fb) {
        super(OpCode.Branch, getInstName(), predicate, tb, fb);
        setType(BuiltinType.VoidTy);
    }

    boolean isConditional() { return getOperandNum() > 1; }
    Value getSingleSuccessor() { return getOperand(0); }
    Value getPredicate() { return getOperand(0); }
    BasicBlock getTrue() { return (BasicBlock) getOperand(1); }
    BasicBlock getFalse() { return (BasicBlock) getOperand(2); }

    String dump() {
        StringBuilder builder = new StringBuilder();
        builder.append("branch ");
        if (isConditional())
            builder.append(getPredicate().getName()).append(" ").append(getTrue().getName()).
                append(", ").append(getFalse().getName());
        else
            builder.append(getSingleSuccessor().getName());
        return builder.toString();
    }
}

class ReturnInst extends Instruction {
    private static int idx = 0;
    private static String getInstName() { return OpCode.Return.getName() + idx++; }

    public ReturnInst() {
        super(OpCode.Return, getInstName());
        setType(BuiltinType.VoidTy);
    }

    public ReturnInst(Value retVal) {
        super(OpCode.Return, getInstName(), retVal);
        setType(retVal.getType());
    }

    boolean hasRetVal() { return getOperandNum() != 0; }
    Value getRetVal() { return getOperand(0); }

    String dump() {
        StringBuilder builder = new StringBuilder();
        builder.append("return");
        if (hasRetVal())
            builder.append(getRetVal().getName());
        return builder.toString();
    }
}

class NewInst extends Instruction {
    private static int idx = 0;
    private static String getInstName(ClassType classType) {
        return String.join("_", OpCode.New.getName(),
                classType.getName(), String.valueOf(idx++));
    }

    public NewInst(ClassType clsTy) {
        super(OpCode.Neg, getInstName(clsTy));
        setType(clsTy);
    }

    String dump() {
        StringBuilder builder = new StringBuilder();
        builder.append(getName()).append(" = new ").append(getType().getName());
        return builder.toString();
    }
}

class NewArrayInst extends Instruction {
    private static int idx = 0;
    private static String getInstName(ArrayType arrayType) {
        return String.join("_", OpCode.NewArray.getName(),
                arrayType.getElementTy().getName(), String.valueOf(idx++));
    }

    public NewArrayInst(ArrayType arrayType, Value length) {
        super(OpCode.Neg, getInstName(arrayType), length);
        setType(arrayType);
    }

    String dump() {
        StringBuilder builder = new StringBuilder();
        builder.append(getName()).append(" = newarray ").append(getType().getName());
        return builder.toString();
    }
}

class ArrayLengthInst extends Instruction {
    private static int idx = 0;
    private static String getInstName() { return OpCode.ArrayLength.getName() + idx++; }

    public ArrayLengthInst(Value array) {
        super(OpCode.ArrayLength, getInstName(), array);
        assert array.getType().getKind() == Type.TypeKind.ArrayTy : "Not a Array Type!";
        setType(BuiltinType.IntTy);
    }

    Value getArray() { return getOperand(0); }

    String dump() {
        StringBuilder builder = new StringBuilder();
        builder.append(getName()).append(" = lengthOf ").append(getArray().getName());
        return builder.toString();
    }
}

class CheckCastInst extends Instruction {
    private static int idx = 0;
    private static String getInstName() { return OpCode.CheckCast.getName() + idx++; }

    public CheckCastInst(ClassType classType, Value operand) {
        super(OpCode.CheckCast, getInstName(), operand);
        setType(classType);
    }

    Value getOperand() { return getOperand(0); }

    String dump() {
        StringBuilder builder = new StringBuilder();
        builder.append(getName()).append(" = checkcast ").append(getOperand().getName());
        return builder.toString();
    }
}

class InstanceOfInst extends Instruction {
    private static int idx = 0;
    private static String getInstName() { return OpCode.InstanceOf.getName() + idx++; }

    public InstanceOfInst(Value operand) {
        super(OpCode.InstanceOf, getInstName(), operand);
        setType(BuiltinType.BooleanTy);
    }

    Value getOperand() { return getOperand(0); }

    String dump() {
        StringBuilder builder = new StringBuilder();
        builder.append(getName()).append(" = instanceOf ").append(getOperand().getName());
        return builder.toString();
    }
}






