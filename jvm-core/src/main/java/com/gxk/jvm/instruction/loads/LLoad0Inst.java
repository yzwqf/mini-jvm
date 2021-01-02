package com.gxk.jvm.instruction.loads;

import com.gxk.jvm.instruction.Instruction;


import com.gxk.jvm.rtda.Frame;

public class LLoad0Inst implements Instruction {

  @Override
  public void execute(Frame frame) {
    Long tmp = frame.getLong(0);
    frame.pushLong(tmp);
  }

  public int getOpCode() { return 0x1e; }
}
