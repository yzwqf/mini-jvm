package com.gxk.jvm.interpret;

import com.gxk.jvm.instruction.Instruction;
import com.gxk.jvm.rtda.Frame;
import com.gxk.jvm.rtda.MetaSpace;
import com.gxk.jvm.rtda.Thread;
import com.gxk.jvm.rtda.heap.Heap;
import com.gxk.jvm.rtda.heap.InstanceArray;
import com.gxk.jvm.rtda.heap.Class;
import com.gxk.jvm.rtda.heap.Method;
import com.gxk.jvm.rtda.heap.Instance;
import com.gxk.jvm.util.Const;
import com.gxk.jvm.util.DebugContextHolder;
import com.gxk.jvm.util.EnvHolder;
import com.gxk.jvm.util.Logger;
import com.gxk.jvm.util.Utils;

import java.util.Scanner;

public class Interpreter {

  /**
   * 同步执行指定方法
   */
  public static void execute(Method method) {
    final Thread env = MetaSpace.getMainEnv();
    Frame newFrame = new Frame(method);
    // 传参
    final int slots = method.getArgSlotSize();
    if (slots > 0) {
      final Frame old = env.topFrame();
      for (int i = slots - 1; i >= 0; i--) {
        newFrame.set(i, old.pop());
      }
    }
    execute(newFrame);
  }

  /**
   * 同步执行栈帧
   */
  public static void execute(Frame newFrame) {
    final Thread env = MetaSpace.getMainEnv();
    env.pushFrame(newFrame);

    newFrame.stat = Const.FAKE_FRAME;
    do {
      Frame frame = env.topFrame();
      Instruction instruction = frame.getInst();
      frame.nextPc += instruction.offset();
      traceBefore(instruction, frame);
      instruction.execute(frame);
    } while (newFrame.stat == Const.FAKE_FRAME);
  }

  public static void runMain(Method method, String[] args) {
    Frame frame = new Frame(method);

    Instance[] kargs = new Instance[args.length];
    for (int i = 0; i < args.length; i++) {
      kargs[i] = Utils.str2Obj(args[i], frame.method.clazz.classLoader);
    }
    Class arrClazz = Heap.findClass("[Ljava/lang/String;");
    if (arrClazz == null) {
      arrClazz = new Class(1, "[Ljava/lang/String;", method.clazz.classLoader, null);
      Heap.registerClass(arrClazz.name, arrClazz);
    }
    InstanceArray array = new InstanceArray(arrClazz, kargs);
    frame.setRef(0, array);

    execute(frame);
  }

  public static void loop(Thread thread) {
    if (EnvHolder.debug) {
      try {
        System.out.println("正在初始化jdb...");
        DebugContextHolder.scanner = new Scanner(System.in);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    do {
      Frame frame = thread.topFrame();
      int pc = frame.nextPc;

      Instruction inst = frame.getInst();
      if (inst == null) {
        StringBuilder sb = new StringBuilder();
        sb.append(pc).append("\n");
        sb.append("class: ").append(frame.method.clazz.name).append("\n");
        sb.append("method: ").append(frame.method.name).append("\n");
        sb.append("methodDescriptor: ").append(frame.method.descriptor).append("\n");
        frame.method.instructionMap.forEach((key, val) -> {
          sb.append(key).append(" ").append(val.format()).append("\n");
        });
        String str = sb.toString();
        System.err.println(str);
        throw new IllegalStateException();
      }
      traceBefore(inst, frame);

      frame.nextPc += inst.offset();
      try {
        inst.execute(frame);
      } catch (Exception e) {
        e.printStackTrace();

        String name = frame.getCurrentMethodFullName();
        String msg = name + "(" + frame.getCurrentSource() + ":" + frame.getCurrentLine() + ")";
        System.out.println(msg);
        throw new IllegalStateException();
      }

    } while (!thread.empty());
  }

  private static void traceBefore(Instruction inst, Frame frame) {
    if (EnvHolder.verboseDebug) {
      debugBefore(inst, frame);
    }
    // verboseTrace
    if (EnvHolder.verboseTrace) {
      trace(inst, frame);
    }
    // verboseCall
    if (EnvHolder.verboseCall) {
      call(inst, frame);
    }
  }

  private static void call(Instruction inst, Frame frame) {
    if (!inst.format().startsWith("invoke")) {
      return;
    }
    String space = genSpace((frame.thread.size() - 1) * 2);
    Logger.trace(space + frame.getPc() + " " + inst.format());
  }

  private static void trace(Instruction inst, Frame frame) {
    String space = genSpace((frame.thread.size() - 1) * 2);
    Logger.trace(space + frame.getPc() + " " + inst.format());
  }

  static void debugBefore(Instruction inst, Frame frame) {
    String space = genSpace(frame.thread.size() * 2);
    Logger.debug(
        space + frame.thread.size() + " <> " + frame.method.name + "_" + frame.method.descriptor
            + " ==============================" + "\n");
    Logger.debug(inst.debug(space + frame.getPc() + " "));
    Logger.debug(frame.debugNextPc(space));
    Logger.debug(frame.debugLocalVars(space));
    Logger.debug(frame.debugOperandStack(space));
    Logger.debug(space + "---------------------");
    Logger.debug(space + "\n");
  }

  public static String genSpace(int size) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < size; i++) {
      sb.append(" ");
    }
    return sb.toString();
  }
}
