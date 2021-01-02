package com.gxk.jvm.instruction.comparisons;

import com.gxk.jvm.instruction.Instruction;
import com.gxk.jvm.rtda.Frame;

public class IfICmpGeInst implements Instruction {
  public final int offset;

  public IfICmpGeInst(int offset) {
    this.offset = offset;
  }

  @Override
  public int offset() {
    return 3;
  }

  @Override
  public void execute(Frame frame) {
    Integer val2= frame.popInt();
    Integer val1= frame.popInt();
    if (val1 >= val2) {
      frame.nextPc = frame.getPc() + offset;
    }
  }

  @Override
  public String format() {
    return "if_icmp_ge";
  }

  public int getOpCode() { return 0xa2; }
}
