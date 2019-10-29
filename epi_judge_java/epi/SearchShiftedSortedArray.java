package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
import java.util.List;
public class SearchShiftedSortedArray {
  @EpiTest(testDataFile = "search_shifted_sorted_array.tsv")

  public static int searchSmallest(List<Integer> A) {
    /**
     Collections.rotate

     Brute.
     Keep up with minIdx and traverse. O(n)
     We don't have to fix it.

     ok, we don't need to actually rotate it.
     We just need to find smallest array. That means i-1 will be biggest value.
     So, modify bin search

     int min;
     So, calc mid
     if A[mid] < min
     go left
     No.

     Trick is comparing A[mid] to A[size-1] (or whatever your moving right boundary is.
     If Amid is > than right boundary, then the cycle is happening in the right partition.
     if it's less than right boundary, then right partition is perfectly sorted, so the rotation idx
     must be to the left.
     And always check current value by checking Amid < A[mid-1], which is how we'd detect we are the smallest value.
     um, do this first.

     And check for 0 idx and size-1 index. 0th, you have to modify the mid-1 check to go around.
     size-1. eh, that might not require special check beyond bin search. oh, well, yes, b/c your right will equal the thing.
     ok, trazadone kicking in.
     */
    //ok, modified binary search

    int high = A.size()-1;
    int low = 0;

    return binSearchIterative(high, low, A);
  }

  /**
   * ok, the recursive version would potentially hold log(n) calls on callstack, while iterative
   * would not grow space with array size.
   * @param high
   * @param low
   * @param A
   * @return
   */
  static int binSearchIterative(int high, int low, List<Integer> A) {
    //base case
    //when searching for max or min in this case, we will narrow down to the extreme in bin search and always have the answer
    //kk
    while(high > low){
      int mid = low + ((high-low)/2);
      //TODO handle mid = 0
      if(A.get(high) > A.get(mid)) {
        // The cycle must occur to left of mid index b/c everything is in order to right of mid
        // but confusing that high = mid-1 will fail the algorithm for [3, 1, 2]
        //oh, look. low is 1 in [5,2,4] and first mid index is 1. We have to include that mid value in next evaluation
        //And more clearly. We know A[mid] is < A[high], so no reason that mid can't be the smallest value in array.
        //thus it cannot be excluded in next iteration. While the else statement does necessarily exclude the mid value
        //from being the smallest. ok.
        high = mid;
      }else{ //A[high] <= A[low] TODO hmmm, maybe putting <= in the first part of bin search will remind me...ah, the normal
        //binSearch. oh, distinct array only in this problem. So never can be ==, unlike normal binSearch
        //So really, A[high] < A[mid]  [1,3,2], since we want smallest value in array, and A[high] < A[mid], obviously
        //A[mid] is not the lowest value!

        //cycle must occur to right of mid index b/c A[high] is < A[mid]. Thus out of order somewhere in right side.
        //ditto confusing that low = mid will also fail for [3, 1, 2]
        //I guess as array gets small, the off by one issue is greatly magnified. So, good to test these small examples
        //in real interview, I think identifying the bin search will get 90% of credit. It would from me. off by one on
        //interview white board would not be biggest concern for this problem.
        low = mid+1;
      }
    }
   return low;
  }

    static int binSearch(int high, int low, List<Integer> A){
    //base cases
//    if(low > high){
//      return -1;//key not found TODO NO! we are looking for smallest value in distinct array. There will always
      // be a smallest value in that case. This is not searching for a key that may not be there.
//    }
      if(low == high){
      return low;//key was found? TODO No, looking for lowest value. BinSearch will always converge to smallest in this case.
    }
    int mid = low + ((high-low)/2);
    //TODO handle mid = 0
    if(A.get(high) > A.get(mid)) {
      // The cycle must occur to left of mid index b/c everything is in order to right of mid
      // but confusing that high = mid-1 will fail the algorithm for [3, 1, 2]
      //oh, look. low is 1 in [5,2,4] and first mid index is 1. We have to include that mid value in next evaluation
      high = mid;
    }else{
      //cycle must occur to right of mid index b/c A[high] is <= A[Amid]
      //ditto confusing that low = mid will also fail for [3, 1, 2]
      //I guess as array gets small, the off by one issue is greatly magnified. So, good to test these small examples
      //in real interview, I think identifying the bin search will get 90% of credit. It would from me. off by one on
      //interview white board would not be biggest concern for this problem.
      low = mid+1;
    }
    return binSearch(high, low, A);
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "SearchShiftedSortedArray.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
