package com.gxk.jvm.instruction.stores;

import com.gxk.jvm.instruction.Instruction;
import com.gxk.jvm.rtda.Frame;

public class DStore2Inst implements Instruction {

  @Override
  public void execute(Frame frame) {
    double tmp = frame.popDouble();
    frame.setDouble(2, tmp);
  }

  public int getOpCode() { return 0x49; }
}
