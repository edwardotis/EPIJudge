package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.EpiTestComparator;
import epi.test_framework.GenericTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BiPredicate;
public class KLargestValuesInBst {

  /*
ok, so, this is like the headset, tailset of a TreeSet.
So, increasing order, we'd just do an in order traversal. left, self, right.
So, a reverse of that should be right, self, self? No, it's reversing the
comparator for sure to be opposite of default.
And then we also, do need to go right, self, left.

space 1
time. worst O(n-k). For instance, k==1, and tree goes totally down to right each time.
If well balanced big tree, then O(h) to find largest element, and then k-1 more steps from there
as each unwinding of stack would be added to resp. and then done.
O(h + k). If k is close to n, then k dominates the big O. Else, the height dominates the big O

tree may be less than k also
 */
  @EpiTest(testDataFile = "k_largest_values_in_bst.tsv")
  public static List<Integer> findKLargestInBst(BstNode<Integer> tree, int k) {
    List<Integer> resp = new ArrayList<>();
    findKLargestInBst(tree, k, resp);
    return resp;
  }

  public static void findKLargestInBst(BstNode<Integer> tree, int k, List<Integer> resp) {
    //base case
    //tree size < k
    //resp.size == k || resp.size == tree.size
    //oh, we don't have size in this Node.
    //TODO, have to detect end of iteration instead as a possibility.
    //how? just letting tree == null check do it's job I believe.
    if(tree != null && resp.size() < k){
//      if(tree.right != null){
        findKLargestInBst(tree.right, k, resp);
//      }
      //ok, we've gone as far right as possible to biggest element
      if(resp.size() < k){
        resp.add(tree.data);
        findKLargestInBst(tree.left, k, resp);
      }
      //now go left
//      if(tree.left != null){
      //TODO actually, if we've hit size k, we never want to take this branch.
//        findKLargestInBst(tree.left, k, resp);
//      }
    }
  }
  @EpiTestComparator
  public static BiPredicate<List<Integer>, List<Integer>> comp =
      (expected, result) -> {
    if (result == null) {
      return false;
    }
    Collections.sort(expected);
    Collections.sort(result);
    return expected.equals(result);
  };

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "KLargestValuesInBst.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
