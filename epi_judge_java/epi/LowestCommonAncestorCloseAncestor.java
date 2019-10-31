package epi;
import epi.test_framework.BinaryTreeUtils;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
import epi.test_framework.TestFailure;
import epi.test_framework.TimedExecutor;

import java.util.HashSet;

public class LowestCommonAncestorCloseAncestor {

  /**
   Space tradeoff for time.
   Keep a map of Node references to int 1 or 2.
   Start traversing both up parents.
   First time that Map already contains the parentNode, we've found the LCA.
   Space O(aDepth from lca + bDepth from lca)
   Time O(max depth) worst case, but O(distance between nodes) is the real one, which could be a max depth in worst case.
   really, a hashset might do it too. Don't even need to know who put it there. yes.

   So, worst case. a is close to root, and quickly becomes null.
   b is at bottom of tree and and traverses all the way up to root.
   */
  public static BinaryTree<Integer> LCA(BinaryTree<Integer> a,
                                        BinaryTree<Integer> b) {
    HashSet<BinaryTree<Integer>> visited = new HashSet<>();
    BinaryTree<Integer> lca = null;
    while(a != null || b != null){
      if(a != null){
        if(visited.contains(a) ){
          lca = a;
          break;
        }else{
          visited.add(a);
        }
        a = a.parent;
      }

      if(b != null){
        if(visited.contains(b) ){
          lca = b;
          break;
        }else{
          visited.add(b);
        }
        b = b.parent;
      }
    }

    return lca;
  }
  @EpiTest(testDataFile = "lowest_common_ancestor.tsv")
  public static int lcaWrapper(TimedExecutor executor, BinaryTree<Integer> tree,
                               Integer key0, Integer key1) throws Exception {
    BinaryTree<Integer> node0 = BinaryTreeUtils.mustFindNode(tree, key0);
    BinaryTree<Integer> node1 = BinaryTreeUtils.mustFindNode(tree, key1);

    BinaryTree<Integer> result = executor.run(() -> LCA(node0, node1));

    if (result == null) {
      throw new TestFailure("Result can not be null");
    }
    return result.data;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "LowestCommonAncestorCloseAncestor.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
