package com.wqf.jvm.compiler0.FIR;

import java.util.ArrayList;

public class Value {
    private ArrayList<Value> users;

    public void addUser(Value user) { users.add(user); }
}

class NamedValue extends Value {
    String name;

    public NamedValue(String name) {
        super();
        this.name = name;
    }
}
