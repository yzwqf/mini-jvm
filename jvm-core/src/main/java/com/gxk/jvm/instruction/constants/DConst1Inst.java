package com.gxk.jvm.instruction.constants;

import com.gxk.jvm.instruction.Instruction;
import com.gxk.jvm.rtda.Frame;

public class DConst1Inst implements Instruction {

  @Override
  public void execute(Frame frame) {
    frame.pushDouble(1.0d);
  }

  public int getOpCode() { return 0xf; }
}
