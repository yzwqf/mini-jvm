package com.wqf.jvm.compiler0.FIR;

import com.gxk.jvm.rtda.heap.Field;

public class FieldVar extends Variable {
    Field rtField;

    public FieldVar(Field field, Type type) {
        super(field.name, type, VariableKind.Field);
        this.rtField = field;
    }

    public Field getRtField() {
        return rtField;
    }
}
