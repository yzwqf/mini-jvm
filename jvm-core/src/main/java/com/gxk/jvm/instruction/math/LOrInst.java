package com.gxk.jvm.instruction.math;

import com.gxk.jvm.instruction.Instruction;


import com.gxk.jvm.rtda.Frame;

public class LOrInst implements Instruction {

  @Override
  public void execute(Frame frame) {
    Long v2 = frame.popLong();
    Long v1 = frame.popLong();
    frame.pushLong(v1 | v2);
  }

  public int getOpCode() { return 0x81; }
}