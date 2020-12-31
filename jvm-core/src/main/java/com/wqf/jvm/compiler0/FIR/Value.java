package com.wqf.jvm.compiler0.FIR;

import java.util.ArrayList;

public class Value {
    Type type;
    ArrayList<Value> users;

    public Type getType() {
        return type;
    }

    public ArrayList<Value> getUsers() {
        return users;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void addUser(Value user) { users.add(user); }
}

class NamedValue extends Value {
    String name;

    public NamedValue(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}