package com.gxk.jvm.instruction.loads;

import com.gxk.jvm.instruction.Instruction;
import com.gxk.jvm.rtda.Frame;
import com.gxk.jvm.rtda.heap.Instance;

public class ALoadInst implements Instruction {
  public final int index;

  public ALoadInst(int index) {
    this.index = index;
  }

  @Override
  public int offset() {
    return 2;
  }

  @Override
  public void execute(Frame frame) {
    Object tmp = frame.getRef(index);
    frame.pushRef((Instance) tmp);
  }

  @Override
  public String format() {
    return "aload " + index;
  }

  public int getOpCode() { return 0x19; }
}