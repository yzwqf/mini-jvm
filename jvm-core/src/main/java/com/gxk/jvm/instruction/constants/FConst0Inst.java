package com.gxk.jvm.instruction.constants;

import com.gxk.jvm.instruction.Instruction;


import com.gxk.jvm.rtda.Frame;

public class FConst0Inst implements Instruction {

  @Override
  public void execute(Frame frame) {
    frame.pushFloat(0.0f);
  }

  public int getOpCode() { return 0xb; }
}
