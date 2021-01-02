package com.gxk.jvm.instruction.conversions;

import com.gxk.jvm.instruction.Instruction;
import com.gxk.jvm.rtda.Frame;

public class I2lInst implements Instruction {

  @Override
  public int offset() {
    return 1;
  }

  @Override
  public void execute(Frame frame) {
    Integer tmp = frame.popInt();
    frame.pushLong(tmp.longValue());
  }

  public int getOpCode() { return 0x85; }
}