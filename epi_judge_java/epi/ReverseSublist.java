package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
public class ReverseSublist {
  @EpiTest(testDataFile = "reverse_sublist.tsv")

  public static ListNode<Integer> reverseSublist(ListNode<Integer> L, int start,
                                                 int finish) {
    //takes 3 pointers total
    //2 permanent

    ListNode<Integer> dummyHead = new ListNode<>(Integer.MIN_VALUE, null);
    //handle edge cases
    if(L == null || L.size() <= 1  || start == finish){
      return L;
    }
    dummyHead.next = L;
    //advance to head of sublist
    //TODO issue with dummyHead if reversing entire list
    ListNode<Integer> cur = L;
    ListNode<Integer> prev = dummyHead;
    int i = 1;//dummyhead is 0
    while(i < start){
      prev = cur;
      cur = cur.next;
      i++;
    }
    ListNode<Integer> subListHead = start == 1 ? dummyHead : cur;//this will point to final node in sublist
    ListNode<Integer> subListTail = subListHead.next;//this will point to rest of list or null
    //now reverse
    // i=start;
    while(i <= finish){
      ListNode<Integer> tmpnext = cur.next;
      cur.next = prev;
      prev = cur;
      cur = tmpnext;
      //cur = tmpnext == null ? cur : tmpnext;//prevent cur being null if sublist is end of list
      i++;
    }
    ////now wire sublist start and end into existing list
    subListHead.next = prev;// 2 -> 5
    if(subListTail != null) {
       subListTail.next = cur;// 3 -> 6
    }
    return dummyHead.next;
  }

    public static ListNode<Integer> reverseSublistBad(ListNode<Integer> L, int start,
                                                 int finish) {

    ListNode<Integer> dummyHead = new ListNode<>(0, L);
    ListNode<Integer> sublistHead = dummyHead;
    int k = 1;
    while (k++ < start) {
      sublistHead = sublistHead.next;
    }

    // Reverse sublist.
    ListNode<Integer> sublistIter = sublistHead.next;
    while (start++ < finish) {
      ListNode<Integer> temp = sublistIter.next;
      sublistIter.next = temp.next;
      temp.next = sublistHead.next;
      sublistHead.next = temp;
    }
    return dummyHead.next;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "ReverseSublist.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
