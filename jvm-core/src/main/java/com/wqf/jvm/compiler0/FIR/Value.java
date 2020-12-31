package com.wqf.jvm.compiler0.FIR;

import java.util.ArrayList;

public abstract class Value {
    Type type;
    String name;
    ArrayList<Value> users;

    Value(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

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

    abstract String dump();
}