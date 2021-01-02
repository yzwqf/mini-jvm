package com.gxk.jvm.instruction.loads;

import com.gxk.jvm.instruction.Instruction;


import com.gxk.jvm.rtda.Frame;

public class FLoad1Inst implements Instruction {

  @Override
  public void execute(Frame frame) {
    float tmp = frame.getFloat(1);
    frame.pushFloat(tmp);
  }

  public int getOpCode() { return 0x23; }
}