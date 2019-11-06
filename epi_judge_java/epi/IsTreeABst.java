package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.Stack;

public class IsTreeABst {
  //for tracking increasing value of inOrder traversal
  static int prevData = Integer.MIN_VALUE;
  /**
   * In order w/out recursion. Use heap to store stack.
   */
  @EpiTest(testDataFile = "is_tree_a_bst.tsv")
  public static boolean isBinaryTreeBST(BinaryTreeNode<Integer> tree) {
    if(tree == null){ return true;}
    return isBinaryTreeBST(tree.left, tree.data, Integer.MIN_VALUE) &&
            isBinaryTreeBST(tree.right, Integer.MAX_VALUE, tree.data);
  }

  public static boolean isBinaryTreeBST(BinaryTreeNode<Integer> tree, int upper, int lower) {
    if(tree == null) return true;
    if(tree.data >= lower && tree.data < upper){
      return isBinaryTreeBST(tree.left, tree.data, lower) &&
              isBinaryTreeBST(tree.right, upper, tree.data);
    }else{
      return false;
    }
  }
  public static boolean isBinaryTreeBST4(BinaryTreeNode<Integer> root) {
    Stack<BinaryTreeNode> stack = new Stack();
    double inorder = -Double.MAX_VALUE;

    while (!stack.isEmpty() || root != null) {
      while (root != null) {
        stack.push(root);
        root = root.left;
      }
      root = stack.pop();
      // If next element in inorder traversal
      // is smaller than the previous one
      // that's not BST.
      if (root.data <= inorder) return false;
      inorder = root.data;
      root = root.right;
    }
    return true;
  }

  /**
   * In order recursive
   * @param tree
   * @return
   */
  public static boolean isBinaryTreeBST2(BinaryTreeNode<Integer> tree) {
    //check for leaf or null root
    if(tree == null){
      return true;
    }
    //in order traversal. left, self, right
    if(!isBinaryTreeBST(tree.left)){
      return false;
    }

    //visit
//    System.out.print(tree.data + ", ");
    if(prevData > tree.data){
      //violated in order travseral. prevData should be <= curData
      return false;
    }else{
      //passed BST. Update this to be prevData for next operation.
      prevData = tree.data;
    }
    if(!isBinaryTreeBST(tree.right)){
      return false;
    }
    return true;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "IsTreeABst.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
