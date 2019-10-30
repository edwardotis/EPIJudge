package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
import java.util.List;
public class KthLargestInArray {
  // The numbering starts from one, i.e., if A = [3,1,-1,2] then
  // findKthLargest(A, 1) returns 3, findKthLargest(A, 2) returns 2,
  // findKthLargest(A, 3) returns 1, and findKthLargest(A, 4) returns -1.
  @EpiTest(testDataFile = "kth_largest_in_array.tsv")
  public static int findKthLargest(int k, List<Integer> A) {
    /**
     ok, kth largest in array.
     Brute Force
     Sort nlgn  Space n
     count from one end, handling duplicates (which count as the same k) O(n)
     ----
     So, if we can stream this problem, then a minheap, might be a good way to reduce space to k,
     and O(nlgk).

     And O(k) to get to the kth value at the end.

     Now, dupes can really fuck this up. If the array is all same value, then a search for k becomes
     O(nlgn) and Space(n)

     So, need a counter besides k, like uniqueK t keep track of size of heap.

     So, find 5th largest value in array.
     Well, so, if we use a maxHeap. And we put 5 unique in to start. Then if next is > heap peek, we add it.
     But if first values contains the largest in heap, we'll never add the next largest.
     um.
     Use a minHeap. Add first including largest and smallest.
     If next is bigger than min, remove min and add next. Yes, this will get us there.
     Also, if next is equal to min, we dont' add it.
     So, we do have to keep up with dupes when initially filling the array, so a kUnique counter is still required.
     oh, fuck, how do we know we are adding a dupe if it's not a comparison with the min value?
     ok. heap.contains() is O(n), so we can't use that.
     Keep a side hashtable of size k. key -> number of heap insertions. upsert(k, +1), upsert(k, -1) if popping from heap.

     If k > size()/2, we probably want to work from opposite direction, so that the space and time is never greater than
     O(n(lg(1/2)n), eh, that's still nlgn, but may be useful to point out.
     ----
     ok, book says there's a better solution than this one.
     Divide and conquer plus randomness.

     Split unsorted array into k partitions.
     Search each parition and present largest in each. O(n)
     No, this doesn't  work.

     ah, quicksort and pivot
     nlgn, or does it becomes nlgk without the need for extra memory like the heap?

     How does this work.
     Pick a pivot.
     Do I make pivot at the kth spot from end, and put all bigger than some rando value to one side.
     How does that help?
    --
     ok, work through it on paper picking the pivot as the kth index from the end, and swapping all the smaller to on the right
     to the left side of pivot. ugh, but no, that's not right.
     I have a note to read leetcode solution instead.
     https://leetcode.com/problems/kth-largest-element-in-an-array/

     Now leetcode has dupes. hmmm.
     ok.
     Leetcode quickselect solution:
     ! Please notice that this algorithm works well even for arrays with duplicates.

     Which I think means leetcode heap is incorrect for dupes. Man everyone fucks this up, huh

     */

    return 0;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "KthLargestInArray.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
