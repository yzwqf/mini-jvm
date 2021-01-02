package com.gxk.jvm.instruction.control;

import com.gxk.jvm.instruction.Instruction;


import com.gxk.jvm.rtda.Frame;
import com.gxk.jvm.util.Utils;

public class LReturnInst implements Instruction {

  @Override
  public void execute(Frame frame) {
//    Long tmp = frame.popLong();
//    frame.thread.popFrame();
//    if (!frame.thread.empty()) {
//      frame.thread.currentFrame().pushLong(tmp);
//    }
//    System.out.println("do ret " + tmp);

    Utils.doReturn2();
  }

  public int getOpCode() { return 0xad; }
}