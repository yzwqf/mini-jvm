package com.gxk.jvm.instruction.math;

import com.gxk.jvm.instruction.Instruction;
import com.gxk.jvm.rtda.Frame;

public class IIncInst implements Instruction {

  public final int index;
  public final int val;

  public IIncInst(int index, int val) {
    this.index = index;
    this.val = val;
  }

  @Override
  public int offset() {
    return 3;
  }

  @Override
  public void execute(Frame frame) {
    Integer tmp = frame.getInt(index);
    tmp += val;
    frame.setInt(index, tmp);
  }

  @Override
  public String format() {
    return "iinc " + index + " " + val;
  }

  public int getOpCode() { return 0x84; }
}
