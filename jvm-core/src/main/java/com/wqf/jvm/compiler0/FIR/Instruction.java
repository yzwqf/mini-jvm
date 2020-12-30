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

        // Branch
        Branch("Branch");

        private String name;
        private OpCode(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    OpCode opCode;
    Value[] operands;

    Instruction(OpCode opCode, String name) {
        super(name);
        this.opCode = opCode;
    }

    int getOperandNum() { return operands.length; }
    Value getOperand(int i) { return operands[i]; }
    Value setOperand(int i, Value operand) { operands[i] = operand; }
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
        super(opCode, getBinaryName(opCode));
        assert isLegalBinaryOp(opCode) : "Not a legal binary Operator.";
        operands = new Value[2];
        setOperand(0, lhs);
        setOperand(1, rhs);
    }

    Value getLHS() { return getOperand(0); }
    Value getRHS() { return getOperand(1); }
}

class UnaryOp extends Instruction {
    private static int[] idxs = new int[2];
    private static String getUnaryName(OpCode opCode) {
        if (opCode.compareTo(OpCode.Neg) == 0)
            return "Neg" + idxs[0]++;
        else if (opCode.compareTo(OpCode.Inc) == 0)
            return "Inc" + idxs[1]++;
        return "BAD";
    }

    private static boolean isLegalUnaryOp(OpCode opCode) {
        return opCode.compareTo(OpCode.Neg) == 0 ||
                opCode.compareTo(OpCode.Inc) == 0;
    }

    public UnaryOp(OpCode opCode, Value operand) {
        super(opCode, getUnaryName(opCode));
        assert isLegalUnaryOp(opCode) : "Not a legal Unary Operator.";
        operands = new Value[1];
        operands[0] = operand;
    }

    Value getOperand() { return getOperand(0); }
}

class BranchInst extends Instruction {
    private static int idx = 0;
    private static String getBranchName() {
        return "Branch" + idx++;
    }

    public BranchInst(Value succ) {
        super(OpCode.Branch, getBranchName());
        operands = new Value[1];
        setOperand(0, succ);
    }

    public BranchInst(Value predicate, BasicBlock tb, BasicBlock fb) {
        super(OpCode.Branch, getBranchName());
        operands = new Value[3];
        setOperand(0, predicate);
        setOperand(1, tb);
        setOperand(2, fb);
    }

    boolean isConditional() { return getOperandNum() > 1; }
    Value getPredicate() { return getOperand(0); }
    BasicBlock getTrue() { return (BasicBlock) getOperand(1); }
    BasicBlock getFalse() { return (BasicBlock) getOperand(2); }
}
