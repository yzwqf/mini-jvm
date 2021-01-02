package com.gxk.jvm.instruction.stores;

import com.gxk.jvm.instruction.Instruction;


import com.gxk.jvm.rtda.Frame;

public class IStore1Inst implements Instruction {

  @Override
  public void execute(Frame frame) {
    Integer tmp = frame.popInt();
    frame.setInt(1, tmp);
  }

  @Override
  public String format() {
    return "istore_1";
  }

  public int getOpCode() { return 0x3c; }
}
