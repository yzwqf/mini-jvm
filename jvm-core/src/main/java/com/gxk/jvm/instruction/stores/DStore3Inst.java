package com.gxk.jvm.instruction.stores;

import com.gxk.jvm.instruction.Instruction;
import com.gxk.jvm.rtda.Frame;

public class DStore3Inst implements Instruction {

  @Override
  public void execute(Frame frame) {
    double tmp = frame.popDouble();
    frame.setDouble(3, tmp);
  }

  public int getOpCode() { return 0x4a; }
}
