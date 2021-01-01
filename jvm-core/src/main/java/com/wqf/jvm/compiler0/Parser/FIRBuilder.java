package com.wqf.jvm.compiler0.Parser;

import com.gxk.jvm.instruction.Instruction;
import com.wqf.jvm.compiler0.FIR.BasicBlock;
import com.wqf.jvm.compiler0.FIR.LocalVar;
import com.wqf.jvm.compiler0.FIR.Method;
import com.wqf.jvm.compiler0.FIR.Value;

import java.util.*;

public class FIRBuilder {
    int pc;
    Method method;
    com.gxk.jvm.rtda.heap.Method rtMethod;
    ArrayList<BasicBlock> basicBlocks;

    Stack<Value> stk;
    LocalVar[] localVars;

    public FIRBuilder(Method method) {
        this.method = method;
        this.rtMethod = method.getRtMethod();
        this.basicBlocks = new ArrayList<>();
    }

    LocalVar[] prepareLocalValrs(Method method) {
        LocalVar[] locals = null;
        int localSize = method.getNumParamVar() + method.getNumLocalVar();
        if (localSize > 0) {
            locals = new LocalVar[localSize];
            for (int i = 0; i < method.getNumParamVar(); ++i)
                locals[i] = method.getParamVar(i);
            for (int j = 0; j < method.getNumLocalVar(); ++j)
                locals[j + method.getNumParamVar()] = method.getLocalVar(j);
        }
        return locals;
    }

    FIRBuilder build() {
        if (rtMethod.isNative())
            return this;

        Stack<Value> stk = new Stack<>();
        this.stk = stk;
        LocalVar[] localVars = prepareLocalValrs(method);
        this.localVars = localVars;
        int i = 0, pc = 0, bcSize = rtMethod.instructionMap.size();
        while (i < bcSize) {
            Instruction inst = rtMethod.instructionMap.get(pc);
            switch (inst.getOpCode()) {

            }

            ++i;
        }

        return this;
    }

    public ArrayList<BasicBlock> getBasicBlocks() {
        return basicBlocks;
    }
}
