package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.HashMap;
import java.util.Map;

public class IsAnonymousLetterConstructible {
  @EpiTest(testDataFile = "is_anonymous_letter_constructible.tsv")

  /**
   magazine to anonymous letter.

   Brute.
   Search magazine letters as type anonymous letter, and remove it from magazine collection.
   O(anon chars * magazine chars)

   Or sort magazine chars in mlgm.
   Then O(anon chars * lg(m)) for searching with bin search. whichever is greater.

   Or
   Get charFrequency of magazine. O(m) where m is letters in magazine
   for each char in anon letter O(a)
   //if char not in map or charFrequency is 0, return false O(2)

   Time: Max of O(max(m, a)
   Space: O(distinct chars in magazine. <50 if ascii)
   */
  public static boolean isLetterConstructibleFromMagazine(String letterText,
                                                          String magazineText) {
    Map<Character, Integer> charFreq = new HashMap<>();
    //O(m chars)
    //Space (distinct m chars)
    for(Character c : magazineText.toCharArray()){
      int count = charFreq.getOrDefault(c,0);
      charFreq.put(c, ++count);
    }
    //O(l chars)
    for(Character c : letterText.toCharArray()){
      int count = charFreq.getOrDefault(c,0);
      if(count < 1){
        return false;//magazine doesn't have this char left for the letter
      }else{
        //reduce mag count
        charFreq.put(c, --count);
      }
    }
    return true;
  }


  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "IsAnonymousLetterConstructible.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
