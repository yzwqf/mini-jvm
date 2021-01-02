package com.gxk.jvm.instruction.stores;

import com.gxk.jvm.instruction.Instruction;
import com.gxk.jvm.rtda.Frame;
import com.gxk.jvm.rtda.heap.Instance;

public class AStore0Inst implements Instruction {
  @Override
  public void execute(Frame frame) {
    Object tmp = frame.popRef();
    frame.setRef(0, (Instance) tmp);
  }

  @Override
  public String format() {
    return "astore_0";
  }

  public int getOpCode() { return 0x4b; }
}
