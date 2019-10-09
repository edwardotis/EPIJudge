package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
import epi.test_framework.TestFailure;
public class StringIntegerInterconversion {

  public static String intToString(int x) {
    //or, we could use a stack (deque or linkedlist), and then feed that into a stringbuilder that would pop as it built
    // so space either way is O(n) extra, but probably cleaner with stack? Remove the .reverse call, but no change to big O.
    // N calls to fill stack or to create reverse stringbuilder
    // N more calls to empty the stack or reverse the stringbuilder.
//    x = -2147483648;
    if(x == 0){
      return "0";
    }
    StringBuilder resp = new StringBuilder();
    boolean isNeg = false;
    if(x < 0){
     isNeg = true;
//     x *= -1; //This breaks at MIN_VALUE
    }
    while(x != 0){
      int digit = x % 10;
      if(isNeg){
        digit *= -1;//or could use Math.abs
      }
      resp.append(digit);
      x /= 10;
    }
    if(isNeg){
      resp.append('-');
    }
    String r =  resp.reverse().toString();
    return r;
  }
  public static int stringToInt(String s) {
      int resp = 0;
      if(s.isEmpty()){
        return resp;
      }
      int multiplier = 1;
      boolean isNeg = s.charAt(0) == '-';
      int firstIdx = isNeg ? 1 : 0;
      for(int i = s.length()-1; i >=firstIdx ; i--){
        char c = s.charAt(i);
        int digit = c - (int)'0'; //trick to convert a 0-9 char to it's int form.
        resp += digit * multiplier;
//        resp = resp * multiplier + digit;
        multiplier *= 10;
      }
      if(isNeg){
        //Technically, this is broken for string representing INT.MIN_VALUE
        resp *= -1;
      }
    return resp;
  }
  @EpiTest(testDataFile = "string_integer_interconversion.tsv")
  public static void wrapper(int x, String s) throws TestFailure {
    if (!intToString(x).equals(s)) {
      throw new TestFailure("Int to string conversion failed");
    }
    if (stringToInt(s) != x) {
      throw new TestFailure("String to int conversion failed");
    }
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "StringIntegerInterconversion.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
