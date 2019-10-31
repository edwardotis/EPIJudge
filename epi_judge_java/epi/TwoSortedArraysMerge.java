package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
import java.util.List;
public class TwoSortedArraysMerge {

  public static void mergeTwoSortedArrays(List<Integer> A, int m,
                                          List<Integer> B, int n) {
    //ok, start from high end of A, where empty slots would be.
    //3 pointers. empty slot, and then iterators for A and B
    //TODO is empty size of A exactly the number? Or could be way too big? hmmm
    //ok, let's make i into m + n to be safe. YES, example shows this is the case.
    int i = m + n - 1;//A.size()-1;
    int a = m-1;//we could derive this easily in O(m) time anyway, but we'll take it free from var.
    int b = n-1;//B.size-1;//or n? n b/c B could also not be full array

    System.out.println("Start value of i: " + i);
    System.out.println("Start value of m: " + m);
    System.out.println("Start value of n: " + n);

    //ok, now compare and move
    //O(m + n) where m is
    while(a != -1 && b != -1){//of while(a >=0 && b >= 0) may be better
      if(B.get(b) >= A.get(a)){
        A.set(i, B.get(b) );//do we need to clear B? no.
        b--;
      }else{
        A.set(i, A.get(a));
        a--;
      }
      i--;
    }
    //ok, when array is empty, so no more compaisons to be made. Just fill in the other array's
    //remaining numbers, which are already correctly sorted.

    // finish off A
    while(a != -1){
      A.set(i, A.get(a));
      a--; i--;
    }
    //finish B
    while(b != -1){
      A.set(i, B.get(b));
      b--; i--;
    }
    //
    System.out.println("End value of i: " + i);
  }
  @EpiTest(testDataFile = "two_sorted_arrays_merge.tsv")
  public static List<Integer>
  mergeTwoSortedArraysWrapper(List<Integer> A, int m, List<Integer> B, int n) {
    mergeTwoSortedArrays(A, m, B, n);
    return A;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "TwoSortedArraysMerge.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
