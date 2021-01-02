package com.gxk.jvm.instruction.stack;

import com.gxk.jvm.instruction.Instruction;


import com.gxk.jvm.rtda.Frame;

public class Pop2Inst implements Instruction {

  @Override
  public void execute(Frame frame) {
    frame.popRef();
    frame.popRef();
  }

  public int getOpCode() { return 0x58; }
}
