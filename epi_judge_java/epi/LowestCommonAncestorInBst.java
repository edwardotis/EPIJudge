package epi;
import epi.test_framework.BinaryTreeUtils;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
import epi.test_framework.TestFailure;
import epi.test_framework.TimedExecutor;
public class LowestCommonAncestorInBst {

  /*
ok, all distinct.
2 nodes. find LCA
So, in normal btree, we might detect heights first, and work up to common height before moving in
tandem.
So, bst has global property of left right.
Does that help us do this more efficiently?
hmmm, no parent though.

Can we navigate from tree, keeping up with whether current node is same for both paths.
As soon as it's not same, we just keep that last one LCA.
Then, we either find both, or else we return null if either target node is missing from tree.
So, basically O(h) and space of 1. 2h time. That's a BCR, I thin

space 1, no call stack.
Time:Worst case O(h) if not in tree. Else, O(Max(sHeight, bHeight)
 */

  // Input nodes are nonempty and the key at s is less than or equal to that at b.
  public static BstNode<Integer> findLCA(BstNode<Integer> tree, BstNode<Integer> s, BstNode<Integer> b) {
    if(tree == null) return null;

    BstNode<Integer> lca = tree, sIt = tree, bIt = tree;

    while( sIt != s && bIt != b){
      //move first, since we guaranteed a root exists
      if(s.data > sIt.data){
        sIt = sIt.right;
      }else{
        sIt = sIt.left;
      }

      if(b.data > bIt.data){
        bIt = bIt.right;
      }else{
        bIt = bIt.left;
      }

      //update lca
      if(sIt == bIt){
        lca = sIt;//this is a common ancestor
      }

      //if either iterator are null, then one or more target nodes is not in tree, which means no lca
      if(sIt == null || bIt  == null){
        lca = null;
        break;
      }
    }
    //If we found both targets, then lca is set to that value.
    //Else, it's set to null;
    return lca;
  }
  @EpiTest(testDataFile = "lowest_common_ancestor_in_bst.tsv")
  public static int lcaWrapper(TimedExecutor executor, BstNode<Integer> tree,
                               Integer key0, Integer key1) throws Exception {
    BstNode<Integer> node0 = BinaryTreeUtils.mustFindNode(tree, key0);
    BstNode<Integer> node1 = BinaryTreeUtils.mustFindNode(tree, key1);

    BstNode<Integer> result = executor.run(() -> findLCA(tree, node0, node1));

    if (result == null) {
      throw new TestFailure("Result can't be null");
    }
    return result.data;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "LowestCommonAncestorInBst.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
