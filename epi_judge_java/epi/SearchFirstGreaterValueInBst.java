package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
public class SearchFirstGreaterValueInBst {

  public static BstNode<Integer> findFirstGreaterThanK(BstNode<Integer> tree,
                                                       Integer k) {
    BstNode<Integer>  candidate = null, subtree = tree;
    while(subtree != null){
      if(k < subtree.data ){//duh, don't use <= b/c equals should end the loop not traverse further
//      if(subtree.data > k ){
        candidate = subtree;
        subtree = subtree.left;
      }else{
        subtree = subtree.right;
      }
    }
    return candidate;

    //    return findFirstGreaterThanK(tree, k, null);
  }

  /**
   * ugh, messy. as hell. book answer so much more elegant.
   *
   * @param tree
   * @param k
   * @param higher
   * @return
   */
  public static BstNode<Integer> findFirstGreaterThanK(BstNode<Integer> tree,
                                                       Integer k, BstNode<Integer> higher) {

    if(tree == null){
      return null;
    }

    if(tree.data == k ){//OR if we didn't find key.
      //handle no right child case.
      if(tree.right == null){
        //parent must be next of fuck
        //No. This doesn't work.
        //maybe we keep up with smallest value bigger than k that we encounter during the search?
        //rather than immediate parent.
        // if(parent > tree.data){
        //   return parent.data;
        // }else{
        //   return null;//value for not found
        // }
        return higher;//this is next highest value we've been tracking from parent tree
      }else{
        //there is a right child
        //make a special left child only recursive method for this case?
        //or a loop
        tree = tree.right;
        while(tree.left != null){
          tree = tree.left;
        }
        //now return left most value of the target key's right subtree
        return tree;
      }
    }else{

      //calculate if there is a new higher value, closer to k
      //higher == updateHigher(tree, k, higher);
//      if(higher == null){//handle root case
//        higher = tree;
//      }else if(tree.data > k){
//        //So, this could be O(n) worst case. maybe O(lgn + h the right child subtree)
//        if(tree.data < higher.data){
//          higher = tree;
//        }
//      }

      //normal bin search
      if(tree.left != null && k <= tree.data){
        higher = tree;
        return findFirstGreaterThanK(tree.left, k, higher);
      }else{
        if(tree.right != null && k > tree.data){
          return findFirstGreaterThanK(tree.right, k, higher);
        }
      }
      //key not in tree. Return closest in order node as IF it were in the tree. oh.
//      return higher;
      if(tree.right == null){
        //parent must be next of fuck
        //No. This doesn't work.
        //maybe we keep up with smallest value bigger than k that we encounter during the search?
        //rather than immediate parent.
        // if(parent > tree.data){
        //   return parent.data;
        // }else{
        //   return null;//value for not found
        // }
        return higher;//this is next highest value we've been tracking from parent tree
      }else{
        //there is a right child
        //make a special left child only recursive method for this case?
        //or a loop
        tree = tree.right;
        while(tree.left != null){
          tree = tree.left;
        }
        //now return left most value of the target key's right subtree
        return tree;
      }
    }
  }

  @EpiTest(testDataFile = "search_first_greater_value_in_bst.tsv")
  public static int findFirstGreaterThanKWrapper(BstNode<Integer> tree,
                                                 Integer k) {
    BstNode<Integer> result = findFirstGreaterThanK(tree, k);
    return result != null ? result.data : -1;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "SearchFirstGreaterValueInBst.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
