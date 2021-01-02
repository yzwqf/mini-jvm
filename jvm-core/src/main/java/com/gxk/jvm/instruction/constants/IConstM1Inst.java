package com.gxk.jvm.instruction.constants;

import com.gxk.jvm.instruction.Instruction;


import com.gxk.jvm.rtda.Frame;

public class IConstM1Inst implements Instruction {

  @Override
  public void execute(Frame frame) {
    frame.pushInt(-1);
  }

  @Override
  public String format() {
    return "iconst_m1";
  }

  public int getOpCode() { return 0x2; }
}