package com.gxk.jvm.instruction.loads;

import com.gxk.jvm.instruction.Instruction;
import com.gxk.jvm.rtda.Frame;

public class DLoad3Inst implements Instruction {

  @Override
  public void execute(Frame frame) {
    double tmp = frame.getDouble(3);
    frame.pushDouble(tmp);
  }

  public int getOpCode() { return 0x29; }
}
