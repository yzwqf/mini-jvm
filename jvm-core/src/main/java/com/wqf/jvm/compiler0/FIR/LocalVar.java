package com.wqf.jvm.compiler0.FIR;

public class LocalVar extends Variable {
    Method method;

    public LocalVar(String name, Type type, Method method) {
        super(name, type, VariableKind.Local);
        this.method = method;
    }

    public Method getMethod() {
        return method;
    }
}
