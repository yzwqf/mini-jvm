package com.gxk.jvm.instruction.math;

import com.gxk.jvm.instruction.Instruction;


import com.gxk.jvm.rtda.Frame;

public class FAddInst implements Instruction {

  @Override
  public void execute(Frame frame) {
    float a1 = frame.popFloat();
    float a2 = frame.popFloat();
    frame.pushFloat(a1 + a2);
  }

  public int getOpCode() { return 0x62; }
}
