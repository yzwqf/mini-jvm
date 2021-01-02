package com.gxk.jvm.instruction.comparisons;

import com.gxk.jvm.instruction.Instruction;
import com.gxk.jvm.rtda.Frame;

public class IfICmpLeInst implements Instruction {
  public final int offset;

  public IfICmpLeInst(int offset) {
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
    if (val1 <= val2) {
      frame.nextPc = frame.getPc() + offset;
    }
  }

  @Override
  public String format() {
    return "if_icmple " + offset;
  }

  public int getOpCode() { return 0xa4; }

}
