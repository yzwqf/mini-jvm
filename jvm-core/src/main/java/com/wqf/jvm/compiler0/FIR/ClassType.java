package com.wqf.jvm.compiler0.FIR;

public class ClassType extends Type {
    C0Class cls;

    public ClassType(C0Class cls) {
        super(cls.getName(), TypeKind.ClassTy);
        this.cls = cls;
    }
}
