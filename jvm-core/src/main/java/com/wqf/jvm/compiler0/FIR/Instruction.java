package com.wqf.jvm.compiler0.FIR;

public class Instruction extends NamedValue {
    enum OpCode {
        // Binary Op
        Add("Add"), Sub("Sub"), Mul("Mul"),
        Div("Div"), Rem("Rem"), Shr("Shr"),
        Shl("Shl"), UShr("UShr"), Or("Or"),
        Xor("Xor"), And("And"), Cmp("Cmp"),

        // Unary Op
        Neg("Neg"), Inc("Inc"),

        // control flow
        Branch("Br"), Return("Ret");

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

        int opNum = operands.length;
        if (opNum > 0) {
            int idx = 0;
            this.operands = new Value[opNum];
            for (Value operand : operands) {
                operand.addUser(this);
                this.operands[idx++] = operand;
            }
        }
    }

    int getOperandNum() { return operands.length; }
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
    private static String getBinaryName(OpCode opCode) {
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
        super(opCode, getBinaryName(opCode), lhs, rhs);
        assert isLegalBinaryOp(opCode) : "Not a legal binary Operator.";
        if (opCode.equals(OpCode.Cmp))
            setType(lhs.getType());
        else
            setType(BuiltinType.BooleanTy);

    }

    Value getLHS() { return getOperand(0); }
    Value getRHS() { return getOperand(1); }
}

class UnaryOp extends Instruction {
    private static int[] idxs = new int[2];
    private static String getUnaryName(OpCode opCode) {
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
        super(opCode, getUnaryName(opCode), operand);
        assert isLegalUnaryOp(opCode) : "Not a legal Unary Operator.";
        setType(operand.getType());
    }

    Value getOperand() { return getOperand(0); }
}

class BranchInst extends Instruction {
    private static int idx = 0;
    private static String getBranchName() {
        return OpCode.Branch.getName() + idx++;
    }

    public BranchInst(Value succ) {
        super(OpCode.Branch, getBranchName(), succ);
        setType(BuiltinType.VoidTy);
    }

    public BranchInst(Value predicate, BasicBlock tb, BasicBlock fb) {
        super(OpCode.Branch, getBranchName(), predicate, tb, fb);
        setType(BuiltinType.VoidTy);
    }

    boolean isConditional() { return getOperandNum() > 1; }
    Value getPredicate() { return getOperand(0); }
    BasicBlock getTrue() { return (BasicBlock) getOperand(1); }
    BasicBlock getFalse() { return (BasicBlock) getOperand(2); }
}

class ReturnInst extends Instruction {
    private static int idx = 0;
    private static String getReturnName() { return OpCode.Return.getName() + idx++; }

    public ReturnInst() {
        super(OpCode.Return, getReturnName());
        setType(BuiltinType.VoidTy);
    }

    public ReturnInst(Value retVal) {
        super(OpCode.Return, getReturnName(), retVal);
        setType(retVal.getType());
    }

    boolean hasRetVal() { return getOperandNum() != 0; }
    Value getRetVal() { return getOperand(0); }
}


