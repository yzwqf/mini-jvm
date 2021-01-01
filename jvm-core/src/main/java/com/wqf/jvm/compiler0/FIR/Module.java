package com.wqf.jvm.compiler0.FIR;

import java.util.ArrayList;

public class Module {
    String name;
    C0Class[] classes;


    public Module(String name) {
        this.name = name;
    }

    public void setClasses(C0Class[] classes) {
        this.classes = classes;
    }
}
