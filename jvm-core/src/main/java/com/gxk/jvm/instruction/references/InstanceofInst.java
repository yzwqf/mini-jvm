package com.gxk.jvm.instruction.references;

import com.gxk.jvm.instruction.Instruction;


import com.gxk.jvm.rtda.Frame;
import com.gxk.jvm.rtda.heap.Instance;

public class InstanceofInst implements Instruction {

  public final String clazz;

  public InstanceofInst(String clazz) {
    this.clazz = clazz;
  }

  @Override
  public int offset() {
    return 3;
  }

  @Override
  public void execute(Frame frame) {
    Instance obj = (Instance) frame.popRef();
    if (obj == null) {
      frame.pushInt(0);
      return;
    }
    boolean ret = obj.clazz.is(clazz);
    if (!ret) {
      frame.pushInt(0);
      return;
    }
    frame.pushInt(1);
  }

  public int getOpCode() { return 0xc1; }
}