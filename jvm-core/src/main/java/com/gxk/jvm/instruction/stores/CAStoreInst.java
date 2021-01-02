package com.gxk.jvm.instruction.stores;

import com.gxk.jvm.instruction.Instruction;
import com.gxk.jvm.rtda.Frame;
import com.gxk.jvm.rtda.heap.PrimitiveArray;

public class CAStoreInst implements Instruction {

  @Override
  public void execute(Frame frame) {
    int val = frame.popInt();
    int index = frame.popInt();
    PrimitiveArray array = (PrimitiveArray) frame.popRef();
    array.ints[index] = val;
  }

  @Override
  public String format() {
    return "castore";
  }

  public int getOpCode() { return 0x55; }
}