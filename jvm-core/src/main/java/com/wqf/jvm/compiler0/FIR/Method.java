package com.wqf.jvm.compiler0.FIR;

import java.util.ArrayList;

public class Method extends Value {
    String getMethodName(String cls, String name, String desc) {
        return String.join("_", cls, name, desc);
    }

    C0Class cls;
    ArrayList<BasicBlock> basicBlocks;

    public Method(String name, C0Class cls) {
        super(name);
        this.cls = cls;
        this.basicBlocks = new ArrayList<>();
    }

    BasicBlock getEntry() { return basicBlocks.get(0); }

    public ArrayList<BasicBlock> getBasicBlocks() {
        return basicBlocks;
    }
}
