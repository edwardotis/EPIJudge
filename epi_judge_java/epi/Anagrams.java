package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.EpiTestComparator;
import epi.test_framework.LexicographicalListComparator;
import epi.test_framework.GenericTest;

import java.util.*;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Anagrams {
  @EpiTest(testDataFile = "anagrams.tsv")

  public static List<List<String>> findAnagrams(List<String> dictionary) {
    Map<String, Set<String>> map = new HashMap<>();
    for(String s : dictionary){
      String sorted =
              Stream.of(s.split(""))
                      .sorted()
                      .collect(Collectors.joining());
      Set<String> set = map.getOrDefault(sorted, new HashSet<>());

      set.add(s);
      map.putIfAbsent(sorted, set);
    }
    List<List<String>> resp = new ArrayList<>();
    for(Map.Entry<String, Set<String>> entry : map.entrySet()) {
      Set<String> anagrams = entry.getValue();
      if(anagrams.size() > 1){
        resp.add(new ArrayList<>(anagrams));
      }
    }
    return resp;
  }
  @EpiTestComparator
  public static BiPredicate<List<List<String>>, List<List<String>>> comp =
      (expected, result) -> {
    if (result == null) {
      return false;
    }
    for (List<String> l : expected) {
      Collections.sort(l);
    }
    expected.sort(new LexicographicalListComparator<>());
    for (List<String> l : result) {
      Collections.sort(l);
    }
    result.sort(new LexicographicalListComparator<>());
    return expected.equals(result);
  };

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "Anagrams.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
