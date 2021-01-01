package com.wqf.jvm.compiler0.FIR;

import com.gxk.jvm.rtda.heap.Class;

public class C0Class extends Value {
    Class rtCls;
    C0Class superCls;
    C0Class[] interfaces;

    FieldVar[] staticVars;
    FieldVar[] objVars;

    Method classInit;
    Method[] nativeMethods;
    Method[] staticMethods;
    Method[] objInit;
    Method[] objMethods;

    public C0Class(Class rtCls) {
        super(rtCls.name);
        this.rtCls = rtCls;
    }

    public C0Class getSuperCls() {
        return superCls;
    }

    public Class getRtClass() {
        return rtCls;
    }

    boolean hasSuper() { return superCls != null; }

    public void setClassInit(Method classInit) {
        this.classInit = classInit;
    }

    public void setObjInit(Method[] objInit) {
        this.objInit = objInit;
    }

    public void setNativeMethods(Method[] nativeMethods) {
        this.nativeMethods = nativeMethods;
    }

    public void setObjMethods(Method[] objMethods) {
        this.objMethods = objMethods;
    }

    public void setStaticMethods(Method[] staticMethods) {
        this.staticMethods = staticMethods;
    }

    public void setStaticVars(FieldVar[] staticVars) {
        this.staticVars = staticVars;
    }

    public void setInterfaces(C0Class[] interfaces) {
        this.interfaces = interfaces;
    }

    public void setObjVars(FieldVar[] objVars) {
        this.objVars = objVars;
    }

    public void setSuperCls(C0Class superCls) {
        this.superCls = superCls;
    }

    String dump() {
        StringBuilder builder = new StringBuilder();
        builder.append("class ").append(getName()).append("{\n");
        return builder.toString();
    }
}
