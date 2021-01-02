package com.gxk.jvm.instruction.conversions;

import com.gxk.jvm.instruction.Instruction;
import com.gxk.jvm.rtda.Frame;

public class D2iInst implements Instruction {

  @Override
  public int offset() {
    return 1;
  }

  @Override
  public void execute(Frame frame) {
    double tmp = frame.popDouble();
    frame.pushInt(((int) tmp));
  }

  public int getOpCode() { return 0x8e; }
}