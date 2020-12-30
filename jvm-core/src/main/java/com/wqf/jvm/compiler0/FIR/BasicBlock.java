package com.wqf.jvm.compiler0.FIR;

import java.util.ArrayList;

public class BasicBlock extends NamedValue {
    Method method;
    ArrayList<Instruction> instructions;

    public BasicBlock(String name, Method method) {
        super(name);
        this.method = method;
        this.instructions = new ArrayList<>();
    }

    public Method getMethod() {
        return method;
    }

    public ArrayList<Instruction> getInstructions() {
        return instructions;
    }

    void addInstruction(Instruction inst) {
        instructions.add(inst);
    }
}
