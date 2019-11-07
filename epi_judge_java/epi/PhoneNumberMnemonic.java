package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.EpiTestComparator;
import epi.test_framework.GenericTest;

import java.util.*;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

public class PhoneNumberMnemonic {

//  static Map<Character,String> phoneMap = new HashMap<>();
  private  static final String[] phoneMap = {"0", "1", "ABC", "DEF", "GHI", "JKL", "MNO", "PQRS", "TUV", "WXYZ"};
  //TODO load this up. and pass it down.

  /**
   ok, base case.
   return all permutations of a single number.
   example. 2 [a,b,c] -> [a][b][c]
   2,3 = [a,b,c][d,e,f] -> [a,d][a,e][a,f],[b,d][b,e][b,f],[c,a][c,b][c,f]

   So, when you add another group of 3 letters (skip 4 issue for moment)
   You make 2 new copies of each existing list element, for 3 total.
   Then add each letter to each group.
   So, let's do it for one.

   Time: n recursiions with a loop of 3 * n strings each time. So, n^2? I feel like it should be bigger. Maybe if I think of iterative, solution. yeah, 3^n in final loop, so
   3^n + 3^n-1....(3^n)^2 ?  eh, book says (3^n)*n, which yeah, that's right way to look at loops.


   Space: 3^n, n is numbers not including 1,0,*, #. And the 9isn't bigOish
   1 number ==  3*1 combinations.
   2 numbers == 3*3=9, 3^2
   3 num ===    3*9, 27, 3^3


   */
  @EpiTest(testDataFile = "phone_number_mnemonic.tsv")
  public static List<String> phoneMnemonic(String phoneNumber) {
    // Character[] arr = phoneNumber.toCharArray();//or charsequence might be better
//    phoneNumber = "23";
    List<Character> chars = phoneNumber.chars()
            .mapToObj(e->(char)e).collect(Collectors.toList());
    //remove one, and call self, until base caes. new method so we can pass list? or no?
    return phoneMnemonic(chars);
  }

  //public static List<String> phoneMnemonic(List<Character> phoneNumber, List<String> resp) {
  public static List<String> phoneMnemonic(List<Character> phoneNumber) {
    List<String> oldStrs;
    //remove one, and call self, until base caes. new method so we can pass list? or no?
    if(phoneNumber.size() > 1 ){
      oldStrs = phoneMnemonic(phoneNumber.subList(0, phoneNumber.size()-1));
    }else{
      //base case of single character
      //load up oldStrs with the initial char
      String[] chars = phoneMap[(phoneNumber.get(phoneNumber.size()-1))-'0' ].split("");
      oldStrs = Arrays.asList(chars);
      return oldStrs;
    }
    //ok, I feel like iterative would be better, and recursion has my head turned upside down,
    //but that's the point. Get your head to grok recursion
    /**
     so, if we get down to a single char in list, let's put that into response.
     */
    //TODO make it's own method to handle 1,0, #, * and 9
    String[] chars = phoneMap[phoneNumber.get(phoneNumber.size()-1)-'0'].split("");
    List<String> newStrings = new ArrayList<>();
    for(String newChar : chars){
      for(String old: oldStrs){
        //just get it working first, then optimize.
        //TODO make more efficient use of same list, use StringBuilders in this list, not string..
        newStrings.add(old + newChar);
//        for(String n : newStrings){
//          System.out.print(n + ", ");
//        }
      }
    }
    return newStrings;
  }

  @EpiTestComparator
  public static BiPredicate<List<String>, List<String>> comp =
      (expected, result) -> {
    if (result == null) {
      return false;
    }
    Collections.sort(expected);
    Collections.sort(result);
    return expected.equals(result);
  };

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "PhoneNumberMnemonic.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
