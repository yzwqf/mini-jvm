package com.gxk.jvm.instruction.constants;

import com.gxk.jvm.instruction.Instruction;


import com.gxk.jvm.rtda.Frame;

public class IConst4Inst implements Instruction {

  @Override
  public void execute(Frame frame) {
    frame.pushInt(4);
  }

  @Override
  public String format() {
    return "iconst_4";
  }

  public int getOpCode() { return 0x7; }
}
