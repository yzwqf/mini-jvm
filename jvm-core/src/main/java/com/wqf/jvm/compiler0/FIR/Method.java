package com.wqf.jvm.compiler0.FIR;

import java.util.ArrayList;

public class Method extends Value {
    private static String getMethodName(String name, String desc) {
        return String.join("_", name, desc);
    }

    final C0Class cls;
    final com.gxk.jvm.rtda.heap.Method rtMethod;
    ArrayList<BasicBlock> basicBlocks;

    public Method(C0Class cls, com.gxk.jvm.rtda.heap.Method rtMethod, MethodType type) {
        super(getMethodName(rtMethod.name, rtMethod.descriptor));
        this.cls = cls;
        this.rtMethod = rtMethod;
        this.basicBlocks = new ArrayList<>();
        setType(type);
    }

    BasicBlock getEntry() { return basicBlocks.get(0); }

    public com.gxk.jvm.rtda.heap.Method getRtMethod() {
        return rtMethod;
    }

    public ArrayList<BasicBlock> getBasicBlocks() {
        return basicBlocks;
    }

    String dump() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("method ").append(getName()).append(getType().getName()).append("{\n");
        for (BasicBlock basicBlock : basicBlocks)
            stringBuilder.append(basicBlock.dump()).append('\n');
        stringBuilder.append("}\n");
        return stringBuilder.toString();
    }
}
