package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
import epi.test_framework.TestFailure;
import epi.test_framework.TimedExecutor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
public class DutchNationalFlag {
  public enum Color { RED, WHITE, BLUE }

  /**
   * ok, my version basic insight is good. Imagine 3 partitions and swap in place.
   * But bounds checking and off by ones are a pita.
   * And I've still got a bug in there that's causing inf loop. Probably an off by one.
   *
   * Also, just not intuitive that lp partition will always be done when rp and ep cross each other.
   * hmmm, I guess if any 2 of 3 partitions are correct, then transitively the 3rd is.
   * But...
   *
   * And really lame that EPI doesn't put variant solutions
   *
   * ok. next problem.
   *
   * @param pivotIndex
   * @param A
   */
  public static void dutchFlagPartition(int pivotIndex, List<Color> A) {
    Color pv = A.get(pivotIndex);
    //find each partition start by counting O(n)
    Counter counter = new Counter(A, pv).invoke();
    int ltCount = counter.getLtCount();
    int eqCount = counter.getEqCount();
    //set partition starts for pointers
    int lp = 0;
    int ep = ltCount;
    int rp = ltCount + eqCount;
    //base case of loop. 3 partitions have not all been completed
    while(ep < ltCount + eqCount && lp < ltCount && rp < A.size()){
      //advance pointers to an invalid value for their partition or outside their partition
      while(ep < ltCount + eqCount &&  A.get(ep).ordinal() == pv.ordinal()){
        ep++;
      }
      while(lp < ltCount &&  A.get(lp).ordinal() < pv.ordinal()){
        lp++;
      }
      while(rp < A.size() && A.get(rp).ordinal() > pv.ordinal()){
        rp++;
      }
      //now look for a valid swap
      // I think ordering of these is off.
      //partitions 1 and 3
      //TODO add the bounds checks for both partition pointers
      // TODO ugh. I bet this is too explicit for swaps, and not letting any ptrs progress
      // and instead of trying to be so explicit, look for how a rearranged ordering would
      // create a logic that guarantees a ptr always moves. i.e. if all 3 of your blocks are else if's
      // you probably have a bug. Work through and think if can guarantee a ptr movement
      if(lp < ltCount && rp < A.size() &&
      A.get(lp).ordinal() > pv.ordinal() && A.get(rp).ordinal() > pv.ordinal() ){
        Collections.swap(A, lp, rp);
        lp++;rp++;
      }else if( lp < ltCount && ep < ltCount + eqCount &&
              A.get(lp).ordinal() == pv.ordinal() && A.get(ep).ordinal() < pv.ordinal()){
        //swap small and equals partitions
        Collections.swap(A, lp, ep);
        lp++;ep++;
      }else if( rp < A.size() && ep < ltCount + eqCount &&
      A.get(rp).ordinal() == pv.ordinal() && A.get(ep).ordinal() > pv.ordinal()){
        Collections.swap(A, rp, ep);
        rp++;ep++;
      }
    }
  }

  public static void dutchFlagPartition_optimal(int pivotIndex, List<Color> A) {
    // 1st the optimial eip solution
    Color pv = A.get(pivotIndex);
    int lp = 0, ep = 0;//
    int rp = A.size();
    while(ep < rp){
      //small
      if(A.get(ep).ordinal() < pv.ordinal()){
        Collections.swap(A, ep, lp);
        ep++; lp++;
      }else if(A.get(ep).ordinal() == pv.ordinal()){////equal
        ep++;
      }else{ //A[ep] larger than pv
        Collections.swap(A, ep, --rp);
        //ep doesn't move. becase A[ep] may now contain either smaller or equal value that must be dealt with
        // in next iteration. However, the old A[rp] is guaranteed to be in final spot (> pv)
      }
    }
    return;
  }

  @EpiTest(testDataFile = "dutch_national_flag.tsv")
  public static void dutchFlagPartitionWrapper(TimedExecutor executor,
                                               List<Integer> A, int pivotIdx)
      throws Exception {
    List<Color> colors = new ArrayList<>();
    int[] count = new int[3];

    Color[] C = Color.values();
    for (int i = 0; i < A.size(); i++) {
      count[A.get(i)]++;
      colors.add(C[A.get(i)]);
    }

    Color pivot = colors.get(pivotIdx);
    executor.run(() -> dutchFlagPartition(pivotIdx, colors));

    int i = 0;
    while (i < colors.size() && colors.get(i).ordinal() < pivot.ordinal()) {
      count[colors.get(i).ordinal()]--;
      ++i;
    }

    while (i < colors.size() && colors.get(i).ordinal() == pivot.ordinal()) {
      count[colors.get(i).ordinal()]--;
      ++i;
    }

    while (i < colors.size() && colors.get(i).ordinal() > pivot.ordinal()) {
      count[colors.get(i).ordinal()]--;
      ++i;
    }

    if (i != colors.size()) {
      throw new TestFailure("Not partitioned after " + Integer.toString(i) +
                            "th element");
    } else if (count[0] != 0 || count[1] != 0 || count[2] != 0) {
      throw new TestFailure("Some elements are missing from original array");
    }
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "DutchNationalFlag.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }

  private static class Counter {
    private List<Color> a;
    private Color pv;
    private int ltCount;
    private int eqCount;

    public Counter(List<Color> A, Color pv) {
      a = A;
      this.pv = pv;
    }

    public int getLtCount() {
      return ltCount;
    }

    public int getEqCount() {
      return eqCount;
    }

    public Counter invoke() {
      ltCount = 0;
      eqCount = 0;
      int gtCount = 0;
      for(Color c : a){
        if(c.ordinal() < pv.ordinal()){
          ltCount++;
        }else if(c.ordinal() == pv.ordinal()){
          eqCount++;
        }else{
          gtCount++;
        }
      }
      return this;
    }
  }
}
