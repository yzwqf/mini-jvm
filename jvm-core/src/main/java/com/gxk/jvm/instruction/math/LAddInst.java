package com.gxk.jvm.instruction.math;

import com.gxk.jvm.instruction.Instruction;


import com.gxk.jvm.rtda.Frame;

public class LAddInst implements Instruction {

  @Override
  public void execute(Frame frame) {
    Long a1 = frame.popLong();
    Long a2 = frame.popLong();
    frame.pushLong(a1 + a2);
  }

  public int getOpCode() { return 0x61; }
}
