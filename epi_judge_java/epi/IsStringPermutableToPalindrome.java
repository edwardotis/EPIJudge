package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.HashMap;
import java.util.Map;

public class IsStringPermutableToPalindrome {
  @EpiTest(testDataFile = "is_string_permutable_to_palindrome.tsv")

  /**

   Not creating the permutation, just answering question.
   Can it created into one.

   So, characteristics are:
   Every character count is an even number, except optionally one.

   So, iterate all chars int hashmap N of char -> count
   iterate over all entries in hashmap.
   Keep track of non-even counts.
   If more than one non-even count, return false. else true.
   */

  public static boolean canFormPalindrome(String s) {
    Map<Character, Integer> charCount = new HashMap<>();
    //load up counts
    //Space O(distinct char count)
    for(Character c : s.toCharArray()){//O(c), c is char count in input string
      int count = charCount.getOrDefault(c, 0);
      charCount.put(c, ++count);
    }
    //now test counts for odds
    int oddCount = 0;
    for(Integer i : charCount.values()){//O(c)
      if(i % 2 != 0){
        oddCount++;
        if(oddCount > 1){
          return false;
        }
      }
    }
    return true;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "IsStringPermutableToPalindrome.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
