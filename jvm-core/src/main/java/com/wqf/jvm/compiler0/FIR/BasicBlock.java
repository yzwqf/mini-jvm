package com.wqf.jvm.compiler0.FIR;

import java.util.ArrayList;

public class BasicBlock extends NamedValue {
    private static int idx = 0;
    private static String getBBName() {
        return "BB" + idx++;
    }

    ArrayList<Instruction> instructions;

    public BasicBlock() {
        super(getBBName());
        instructions = new ArrayList<>();
    }

    void addInstruction(Instruction inst) {
        instructions.add(inst);
    }
}
