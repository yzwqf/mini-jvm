package com.gxk.jvm.instruction.math;

import com.gxk.jvm.instruction.Instruction;
import com.gxk.jvm.rtda.Frame;

public class DNegInst implements Instruction {

  @Override
  public void execute(Frame frame) {
    double tmp = frame.popDouble();
    frame.pushDouble(-tmp);
  }

  public int getOpCode() { return 0x77; }
}