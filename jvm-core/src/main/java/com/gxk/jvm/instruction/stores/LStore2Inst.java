package com.gxk.jvm.instruction.stores;

import com.gxk.jvm.instruction.Instruction;


import com.gxk.jvm.rtda.Frame;

public class LStore2Inst implements Instruction {

  @Override
  public void execute(Frame frame) {
    Long tmp = frame.popLong();
    frame.setLong(2, tmp);
  }

  @Override
  public String format() {
    return "lstore_2";
  }

  public int getOpCode() { return 0x41; }
}