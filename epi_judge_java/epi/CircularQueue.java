package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.EpiUserType;
import epi.test_framework.GenericTest;
import epi.test_framework.TestFailure;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
public class CircularQueue {

  public static class Queue {

    //int [] or ArrayList?
    //I think instructions were int [] only
    int initCapacity = 3;
    int capacity = initCapacity;
    int size = 0;
    int head = 0, tail = 0;
    Integer[] q = new Integer[initCapacity];

    public Queue(int capacity) {
      this.capacity = capacity;
      List strList = Arrays.asList("abcd".toCharArray());
      Collections.swap(strList, 0,1);
      System.out.println(strList.toString());
    }

    //need thread safety during this operation.
    private void expandCapacity(){
      /**
       ok.
       Create new array of double size.
       Copy existing full array into first half of that array.

       But now that breaks my logic checks for head or tail being at end of array. the circle logic
       is broken. How to repair that?
       Example
       Why don't we start at head of old array, and walk it through circle while starting the new array head
       at 0th index.
       Yes, same O time as Arrays.copy()

       */
      System.out.println("expanding capacity");

      Integer[] newQ = new Integer[capacity * 2];
      //now copy over
      int newI = 0;//, newHead = 0, newTail = 0;
      int oldI = head;
      while(oldI != tail){
        //copy over to beginning of new array
        newQ[newI++] = q[oldI];
        //handle circle
        if(oldI == q.length-1){
          oldI = 0;
        }else{
          oldI++;
        }
      }
      //final copy of old tail
      newQ[newI] = q[oldI];

      ///now reset internal member variables
      q = newQ;
      tail = newI;
      head = 0;
      capacity *= 2;
      //and size is still correct
    }

    public void enqueue(Integer x) {
      if(size == capacity){
        //TODO double array size
        // throw new RuntimeException("queue full");
        expandCapacity();
      }
      //check for tail at end of array
      if(size == 0){
        //empty queue. enqueue without moving tail ptr (or head)
        q[tail] = x;
      }else if(tail != q.length-1){
        tail++;
        q[tail] = x;
      }else{
        //wrap tail to first spot
        tail = 0;//wrap around
        q[tail] = x;
      }
      size++;
      return;
    }

    public Integer dequeue() {
      if(size == 0){
        //or return special value or null
        return null;
        // throw new RuntimeException("queue empty");
      }
      Integer resp = q[head];
      //Q. Should we null out the values or leave old values in? Probably less confusing to null it out.
      q[head] = null;
      if(size == 1){
        //do nothing. head and tail are already in correct spot to enqueue
        // head = tail = 0;//reset empty queue to front. Any reason not to do this?
      }else if(head == q.length-1){
        //queue > 1, and head was at end of array. Tail is not at end of array b/c that would be size 1 array
        head = 0;
      }else{ //typical case
        head++;
      }
      size--;
      return resp;
    }


    public int size() {
      return size;
    }

    @Override
    public String toString() {
      // TODO - you fill in here.
      return super.toString();
    }
  }
  @EpiUserType(ctorParams = {String.class, int.class})
  public static class QueueOp {
    public String op;
    public int arg;

    public QueueOp(String op, int arg) {
      this.op = op;
      this.arg = arg;
    }

    @Override
    public String toString() {
      return op;
    }
  }

  @EpiTest(testDataFile = "circular_queue.tsv")
  public static void queueTest(List<QueueOp> ops) throws TestFailure {
    Queue q = new Queue(1);
    int opIdx = 0;
    for (QueueOp op : ops) {
      switch (op.op) {
      case "Queue":
        q = new Queue(op.arg);
        break;
      case "enqueue":
        q.enqueue(op.arg);
        break;
      case "dequeue":
        int result = q.dequeue();
        if (result != op.arg) {
          throw new TestFailure()
              .withProperty(TestFailure.PropertyName.STATE, q)
              .withProperty(TestFailure.PropertyName.COMMAND, op)
              .withMismatchInfo(opIdx, op.arg, result);
        }
        break;
      case "size":
        int s = q.size();
        if (s != op.arg) {
          throw new TestFailure()
              .withProperty(TestFailure.PropertyName.STATE, q)
              .withProperty(TestFailure.PropertyName.COMMAND, op)
              .withMismatchInfo(opIdx, op.arg, s);
        }
        break;
      }
      opIdx++;
    }
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "CircularQueue.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
