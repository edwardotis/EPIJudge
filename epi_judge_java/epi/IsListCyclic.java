package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
import epi.test_framework.TestFailure;
import epi.test_framework.TimedExecutor;
public class IsListCyclic {

  /**
   * Brute. Use N space. Keep up with Node visit count in map. No, just use a Set, and first exists
   * Or save space for N^2
   * Use outer and inner loop.
   * uh, I don't understand it. draw it out.
   *
   * @param head
   * @return
   */
  public static ListNode<Integer> hasCycle(ListNode<Integer> head) {
    //guarantee size of 1 at least
    if(head == null){
      return null;
    }

    ListNode<Integer> slowPtr = head, fastPtr = head.next;
    //fast moves 2x
    //if slow and fast ever point to same node, then you must be in a cycle b/c
    //otherwise, the slow would never catch up to the fast before it becomes null at end of list
    while(fastPtr != null && fastPtr.next != null ){
      if(slowPtr == fastPtr){
        //how do we know the start of the cycle node?
        //keep up with visited node count and a single ptr
        //TODO book has a clever solution to this problem.
        //count cycle length (Just leave one fastPtr sitting there, and move slowPtr around until it hits again. counting
        //Then start at beginning with 2 ptrs.
        //Advance one by C
        // Then move them both 1 until they hit each other again. That will be first spot
        // Bonus. To find Node *before* the cycle. Use a 3rd trailer ptr that follows by one in previous solution.
        return null;
      }
      slowPtr = slowPtr.next;
      //protected npe here by while statement
      fastPtr = fastPtr.next.next;
    }
    return null;
  }
  @EpiTest(testDataFile = "is_list_cyclic.tsv")
  public static void HasCycleWrapper(TimedExecutor executor,
                                     ListNode<Integer> head, int cycleIdx)
      throws Exception {
    int cycleLength = 0;
    if (cycleIdx != -1) {
      if (head == null) {
        throw new RuntimeException("Can't cycle empty list");
      }
      ListNode<Integer> cycleStart = null, cursor = head;
      while (cursor.next != null) {
        if (cursor.data == cycleIdx) {
          cycleStart = cursor;
        }
        cursor = cursor.next;
        if (cycleStart != null) {
          cycleLength++;
        }
      }
      if (cursor.data == cycleIdx) {
        cycleStart = cursor;
      }
      if (cycleStart == null) {
        throw new RuntimeException("Can't find a cycle start");
      }
      cursor.next = cycleStart;
      cycleLength++;
    }

    ListNode<Integer> result = executor.run(() -> hasCycle(head));

    if (cycleIdx == -1) {
      if (result != null) {
        throw new TestFailure("Found a non-existing cycle");
      }
    } else {
      if (result == null) {
        throw new TestFailure("Existing cycle was not found");
      }

      ListNode<Integer> cursor = result;
      do {
        cursor = cursor.next;
        cycleLength--;
        if (cursor == null || cycleLength < 0) {
          throw new TestFailure(
              "Returned node does not belong to the cycle or is not the closest node to the head");
        }
      } while (cursor != result);

      if (cycleLength != 0) {
        throw new TestFailure(
            "Returned node does not belong to the cycle or is not the closest node to the head");
      }
    }
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "IsListCyclic.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
