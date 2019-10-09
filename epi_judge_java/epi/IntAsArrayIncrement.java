package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
import java.util.List;
public class IntAsArrayIncrement {
  @EpiTest(testDataFile = "int_as_array_increment.tsv")
  public static List<Integer> plusOne(List<Integer> A) {
    int i = A.size()-1;
    boolean isCarryOne = false;
    if(A.get(i) != 9){
      A.set(i, A.get(i) + 1);
    }else{//carry over case
      while(i >= 0 && A.get(i) == 9){
        A.set(i, 0);
        i--;
        isCarryOne = true;
        //TODO if valid to have lots of preceding zero's, then we need to alter this while loop logic
        //perhaps with a boolean for isCarryingOne. And then set if not at -1 to 1 after loop ends, as opposed
        //to this logic below that prepends a new digit to the list.

        //Handle prepending a new entry to number like 99 -> 100
        if( i == -1 ){
          //prepend a 1 to array in this case. O(n) operation
          A.add(0, 1);
          isCarryOne = false;// unnecessary, but being safe
        }
      }
      //handle most significant digit in number like: 39 -> 40
      if(i != -1 && isCarryOne){
        A.set(i, A.get(i) + 1);
      }

    }
    return A;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "IntAsArrayIncrement.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
