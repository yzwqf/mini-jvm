package com.gxk.jvm.instruction.constants;

import com.gxk.jvm.instruction.Instruction;
import com.gxk.jvm.rtda.Frame;

public class BiPushInst implements Instruction {

  public final byte val;

  public BiPushInst(byte val) {
    this.val = val;
  }

  @Override
  public int offset() {
    return 2;
  }

  @Override
  public void execute(Frame frame) {
    frame.pushInt((int) (this.val));
  }

  @Override
  public String format() {
    return "bipush " + val;
  }

  public int getOpCode() { return 0x10; }
}
