package com.gxk.jvm.instruction.stack;

import com.gxk.jvm.instruction.Instruction;
import com.gxk.jvm.rtda.Frame;

public class Dup2X2Inst implements Instruction {

  @Override
  public void execute(Frame frame) {
    throw new UnsupportedOperationException();
  }

  public int getOpCode() { return 0x5e; }
}
