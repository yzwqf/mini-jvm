package com.wqf.jvm.compiler0.FIR;

public class C0Class extends NamedValue {
    Method clsInit;
    C0Class parent;

    public C0Class(String name) {
        super(name);
    }

    public C0Class(String name, C0Class parent) {
        super(name);
        this.parent = parent;
    }

    boolean hasParent() { return parent != null; }

    public C0Class getParent() {
        return parent;
    }
}
