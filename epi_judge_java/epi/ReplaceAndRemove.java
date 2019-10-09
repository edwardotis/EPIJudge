package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
import epi.test_framework.TimedExecutor;
import java.util.ArrayList;
import java.util.List;
public class ReplaceAndRemove {

  /**
   *       //move i forward//argh, fuck. if it's all b's, this algorithm breaks b/c will leave shit unchanged.
   *       //<sigh> guess, we really do need to remove b's in first pass
   * @param size
   * @param s
   * @return
   */
  public static int replaceAndRemove(int size, char[] s) {
     //calculate counts
    int aLow = 0, bLow = 0, aTotal = 0, bTotal = 0, total = 0;
    int i = 0;
    //count a's and b's lte size
    //so, we have to know what the empty char characters are for my shit to work. b/c otherwise, my trailer pointer
    //from reverse might be. Wait, just assume that the array they pass in has *exactly* enough empty slots.
    // yeah, bu tI use total, eh think I used it as a short cut. Let's just trailer ptr at length-1. end of array
    while(i < s.length) {
      if (s[i] == 'a') {
        aTotal++;
        if (aLow + bLow < size) {
          aLow++;
        }
      } else if (s[i] == 'b') {
        bTotal++;
        if (aLow + bLow < size) {
          bLow++;
        }
      }
    }
    //now iterate through reverse to fix it
    int j = s.length-1;//trailer
    //leading a|b  ptr
    int abDelta;
    if(bLow > aLow){
      //move j forward. array is strhinking
    }else{
      //move i forward//argh, fuck. if it's all b's, this algorithm breaks b/c will leave shit unchanged.
      //<sigh> guess, we really do need to remove b's in first pass
    }
    i = s.length - (int)Math.abs(aLow + bLow);//We want it forward in array by the number of
    while(i > -1){

    }

    return 0;
  }
  @EpiTest(testDataFile = "replace_and_remove.tsv")
  public static List<String>
  replaceAndRemoveWrapper(TimedExecutor executor, Integer size, List<String> s)
      throws Exception {
    char[] sCopy = new char[s.size()];
    for (int i = 0; i < size; ++i) {
      if (!s.get(i).isEmpty()) {
        sCopy[i] = s.get(i).charAt(0);
      }
    }

    Integer resSize = executor.run(() -> replaceAndRemove(size, sCopy));

    List<String> result = new ArrayList<>();
    for (int i = 0; i < resSize; ++i) {
      result.add(Character.toString(sCopy[i]));
    }
    return result;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "ReplaceAndRemove.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
