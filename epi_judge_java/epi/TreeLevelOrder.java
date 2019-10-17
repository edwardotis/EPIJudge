package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
public class TreeLevelOrder {
  @EpiTest(testDataFile = "tree_level_order.tsv")
  public static List<List<Integer>>  binaryTreeDepthOrder(BinaryTreeNode<Integer> tree) {
    List<List<Integer>> resp = new ArrayList<List<Integer>>();
    if (tree == null) {
      return resp;
    }
    int curDepth = 0;

    // List<Integer> dList = new ArrayList<>();
    List<BinaryTreeNode<Integer>> levelQ = new ArrayList<>();
    // Deque<Integer> depthQ = new ArrayDeque<>();
    levelQ.add(tree);
    // depthQ.addLast(0);
    while (!levelQ.isEmpty()) {
      //level list to go into resp
      List<Integer> dList = new ArrayList<>();

      // curDepth = depthQ.pollFirst();
      for (BinaryTreeNode<Integer> n : levelQ) {
        dList.add(n.data);
      }
      //Now add that to resp
      resp.add(dList);

      //Now add all children children in level to a new levelQ
      List<BinaryTreeNode<Integer>> nextLevelQ = new ArrayList<>();
      for (BinaryTreeNode<Integer> n : levelQ) {
        if (n.left != null) {
          nextLevelQ.add(n.left);
          // depthQ.add(curDepth+1);
        }
        if (n.right != null) {
          nextLevelQ.add(n.right);
          // depthQ.add(curDepth+1);
        }
      }
      // and now we are through with current levelQ list.
      // replace it with the next level one, and old one is eligible for GC
      levelQ = nextLevelQ;
    }
    return resp;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "TreeLevelOrder.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
