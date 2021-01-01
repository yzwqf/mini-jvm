package com.wqf.jvm.compiler0.FIR;

import java.util.ArrayList;
import java.util.Arrays;

public class Method extends Value {
    private static String getMethodName(String name, String desc) {
        return String.join(".", name, desc);
    }

    final C0Class cls;
    final com.gxk.jvm.rtda.heap.Method rtMethod;

    LocalVar[] paramVars;
    LocalVar[] localVars;
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

    public LocalVar getParamVar(int i) { return paramVars[i]; }

    public LocalVar getLocalVar(int i) { return localVars[i]; }

    public LocalVar[] getParamVars() {
        return paramVars;
    }

    public int getNumParamVar() { return paramVars != null ? paramVars.length : 0; }

    public int getNumLocalVar() { return localVars != null ? localVars.length : 0; }

    public ArrayList<BasicBlock> getBasicBlocks() {
        return basicBlocks;
    }

    public void setParamVars(LocalVar[] paramVars) {
        this.paramVars = paramVars;
    }

    public void setLocalVars(LocalVar[] localVars) {
        this.localVars = localVars;
    }

    public void setBasicBlocks(ArrayList<BasicBlock> basicBlocks) {
        this.basicBlocks = basicBlocks;
    }

    String dump() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("method ").append(getName()).append('(').append(
                String.join(", ", Arrays.stream(paramVars).map(
                        x -> x.getType().getName() + " " + x.getName()).toArray(String[]::new))).append(") {\n");
        for (BasicBlock basicBlock : basicBlocks)
            stringBuilder.append(basicBlock.dump()).append('\n');
        stringBuilder.append("}\n");
        return stringBuilder.toString();
    }
}
