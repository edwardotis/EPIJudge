package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NearestRepeatedEntries {
  @EpiTest(testDataFile = "nearest_repeated_entries.tsv")

  /**
   brute force
   n^2
   space 0
   int outerpos
   for each word
   int innerpos = 0;
   for each word
   if words are equal
   take abs value of difference in positions, and store that in a min value

   ok, how can we make it faster?
   We're doing duplicate work, and we're comparing words that aren't equal to each other.
   What if we stored position of each word in hashtable.
   As we iterate through all words.
   We we find a word that is in the hashtable, that last position must be the most recently seen version
   of the word. So, take lastPosition - curPosition and see if that a min.
   Then store curPosition for the word and continue on.

   Time: O(n) n is total words
   Space O(distinct words)

   */
  public static int findNearestRepetition(List<String> paragraph) {
    Map<String, Integer> lastWordToPos = new HashMap<>();
    // String[] wordArray = words.toArray;
    // for(int i=0; i<words.size(); i++){
    int minDistance = Integer.MAX_VALUE;
    int curPos = 0;
    //O(words)
    //space O(distance words)
    for(String word: paragraph){
      // Integer lastPos = lastWordToPos.getOrDefault(word, Integer.MAX_VALUE);
      //TODO or should we just check for null? What if word never duplicated?
      Integer lastPos = lastWordToPos.get(word);
      if(lastPos != null){
        //calculate distance
        int distance = Math.abs(curPos - lastPos);
        minDistance = Math.min(minDistance, distance);
      }
      //regardless put curPos into map
      lastWordToPos.put(word, curPos);
      curPos++;
    }
    return minDistance != Integer.MAX_VALUE ? minDistance : -1;//returns MAX_VALUE if no dupes in list of words.
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "NearestRepeatedEntries.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
