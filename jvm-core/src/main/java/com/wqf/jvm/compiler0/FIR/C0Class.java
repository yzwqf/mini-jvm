package com.wqf.jvm.compiler0.FIR;

import com.gxk.jvm.rtda.heap.Class;

public class C0Class extends Value {
    Class rtCls;
    Method classInit;
    C0Class parent;
    Method[] methods;

    public C0Class(String name) {
        super(name);
    }

    public C0Class(Class rtCls, C0Class parent) {
        super(rtCls.name);
        this.rtCls = rtCls;
        this.parent = parent;
    }

    boolean hasParent() { return parent != null; }

    public C0Class getParent() {
        return parent;
    }

    public void setClassInit(Method classInit) {
        this.classInit = classInit;
    }

    public void setMethods(Method[] methods) {
        this.methods = methods;
    }

    String dump() {
        StringBuilder builder = new StringBuilder();
        builder.append("class ").append(getName()).append("{\n");
        return builder.toString();
    }
}
