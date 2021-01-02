package com.gxk.jvm.instruction.stack;

import com.gxk.jvm.instruction.Instruction;
import com.gxk.jvm.rtda.Frame;
import com.gxk.jvm.rtda.Slot;

public class DupX1Inst implements Instruction {

  @Override
  public void execute(Frame frame) {
    Slot s1 = frame.popSlot();
    Slot s2 = frame.popSlot();
    frame.pushSlot(s1);
    frame.pushSlot(s2);
    frame.pushSlot(s1);
  }

  @Override
  public String format() {
    return "dupx1";
  }

  public int getOpCode() { return 0x5a; }
}