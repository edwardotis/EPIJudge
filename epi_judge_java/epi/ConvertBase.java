package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
public class ConvertBase {
  @EpiTest(testDataFile = "convert_base.tsv")

  public static String convertBase(String numAsString, int b1, int b2) {
    //b1 str to base10 int
//    numAsString = "1196028";
//    b1 = 10;
//    b2 = 12;
    //TODO A-F
    boolean isNeg = numAsString.charAt(0) == '-';
    int firstIdx = isNeg ? 1 : 0;
    int x = 0;
    int b1Pow = 0;
    int hexA_FConst = 52; // aka (int)'A' + 10
    for(int i=numAsString.length()-1; i >= firstIdx; i--){
      char c = numAsString.charAt(i);
      int digit;
      if(c >= '1' && c <= '9') {
        digit = c - (int) '0';
      }else{
        //switch for A-F b/c 2 >= base <= 16
        digit = c - 'A' + 10;// + 10;
//        digit = c - hexA_FConst + 10;
      }
      double addition = Math.abs(digit) * Math.pow(b1, b1Pow++);
      x += (int)addition;
//TODO I don't get the book's solution looks like this instead of using power of the base 16^0, 16^1, 2^0, 2^1
      // Do I not understand something about what java stream reduce is doing?
      //      x = (x * b1) + Math.abs(digit);// (x * 16) + 13 ??? Just can't be this.
      // My base 10 solution in StringIntegerInterconversion passes all their tests. and converting it lik this fails.
      // so read up on reduce.
      //eh, 9:30pm. lost internet.
    }
    StringBuilder resp = new StringBuilder();
    //now go b10 int -> b2 str
    while(x != 0){
      int digit = x % b2;
      char c;
      if(digit >= 0 && digit <= 9){
        c = (char)(digit + (int)'0');
      }else{
        //A-F
        c = (char)(digit + 'A' - 10);
      }
      resp.append(c);
      x /= b2;
    }
    if(isNeg){
      resp.append('-');
    }
    String r =  resp.reverse().toString();
    return r;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "ConvertBase.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
