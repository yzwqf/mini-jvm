package com.gxk.jvm.instruction.math;

import com.gxk.jvm.instruction.Instruction;


import com.gxk.jvm.rtda.Frame;

public class IRemInst implements Instruction {

  @Override
  public void execute(Frame frame) {
    Integer v2 = frame.popInt();
    Integer v1 = frame.popInt();
    frame.pushInt(v1 % v2);
  }

  public int getOpCode() { return 0x70; }
}