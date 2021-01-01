package com.wqf.jvm.compiler0.FIR;

public class Variable extends Value {
    enum VariableKind {
        Local, Field
    }

    VariableKind kind;

    protected Variable(String name, Type type, VariableKind kind) {
        super(name);
        setType(type);
        this.kind = kind;
    }

    String dump() { return getName(); }
}