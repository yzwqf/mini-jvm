package com.gxk.jvm.instruction.conversions;

import com.gxk.jvm.instruction.Instruction;
import com.gxk.jvm.rtda.Frame;

public class F2lInst implements Instruction {

  @Override
  public int offset() {
    return 1;
  }

  @Override
  public void execute(Frame frame) {
    float tmp = frame.popFloat();
    frame.pushLong((long) tmp);
  }

  public int getOpCode() { return 0x8c; }
}