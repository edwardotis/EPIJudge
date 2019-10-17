package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.EpiUserType;
import epi.test_framework.GenericTest;
import epi.test_framework.TestFailure;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.NoSuchElementException;
public class StackWithMax {

  public static class Stack {
    public boolean empty() {
      // TODO - you fill in here.
      return true;
    }
    public Integer max() {
      // TODO - you fill in here.
      return 0;
    }
    public Integer pop() {
      // TODO - you fill in here.
      return 0;
    }
    public void push(Integer x) {
      // TODO - you fill in here.
      return;
    }

    private static class MinMaxStack {

      private Deque<Integer> stack = new ArrayDeque<>();
      private Deque<Integer> minStack = new ArrayDeque<>();
      private Deque<Integer> maxStack = new ArrayDeque<>();

      public void push(Integer i){
        if( i >= max() ){
          maxStack.push(i);
        }
        // if( i.intValue() <= min() ){
        //   minStack.push(i);
        // }
        stack.push(i);
      }

//     public Integer pop(){
//       int resp = stack.pop();
//       if(resp == max()){
//         maxStack.pop();
//       }
//       if(resp == min()){
//         minStack.pop();
//       }
//       return resp;
//     }

      public Integer min(){
        return minStack.peek();
      }

      public Integer max(){
        return maxStack.peek();
      }

    }
  }
  @EpiUserType(ctorParams = {String.class, int.class})
  public static class StackOp {
    public String op;
    public int arg;

    public StackOp(String op, int arg) {
      this.op = op;
      this.arg = arg;
    }
  }

  @EpiTest(testDataFile = "stack_with_max.tsv")
  public static void stackTest(List<StackOp> ops) throws TestFailure {
    try {
      Stack s = new Stack();
      int result;
      for (StackOp op : ops) {
        switch (op.op) {
        case "Stack":
          s = new Stack();
          break;
        case "push":
          s.push(op.arg);
          break;
        case "pop":
          result = s.pop();
          if (result != op.arg) {
            throw new TestFailure("Pop: expected " + String.valueOf(op.arg) +
                                  ", got " + String.valueOf(result));
          }
          break;
        case "max":
          result = s.max();
          if (result != op.arg) {
            throw new TestFailure("Max: expected " + String.valueOf(op.arg) +
                                  ", got " + String.valueOf(result));
          }
          break;
        case "empty":
          result = s.empty() ? 1 : 0;
          if (result != op.arg) {
            throw new TestFailure("Empty: expected " + String.valueOf(op.arg) +
                                  ", got " + String.valueOf(s));
          }
          break;
        default:
          throw new RuntimeException("Unsupported stack operation: " + op.op);
        }
      }
    } catch (NoSuchElementException e) {
      throw new TestFailure("Unexpected NoSuchElement exception");
    }
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "StackWithMax.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
