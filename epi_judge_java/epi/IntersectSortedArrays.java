package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.*;

public class IntersectSortedArrays {
  @EpiTest(testDataFile = "intersect_sorted_arrays.tsv")

  public static List<Integer> intersectTwoSortedArrays(List<Integer> A,
                                                       List<Integer> B) {
    List<Integer> resp = new ArrayList<>();
    //user sorted property
    //TODO consider picking smallest to be out loop, if significant different in sizes
    //TODO listIterator.previous would eliminate need for the Prev integers although that's still more confusing to
    //deal with than just usind indexes into the lists
    Iterator<Integer> ait = A.iterator();
    Iterator<Integer> bit = B.iterator();
    Integer aPrev = null, bPrev = null;
    Integer a = ait.next();
    Integer b = bit.next();
    //if either array ends, then there can be no more matches in remaining values
    //TODO ok, iterators suck for this problem. Better to use A.get(i), B.get(j)
    //because when you advance to get last int in either list, the hasnext fails, but you haven't had an opportunity to
    //test the value yet. So, just doesn't work for this kind of dual iterator logic at different paces.
    //Just check solution in book. Same logic as below but able to test final value(s) properly.
    while(ait.hasNext() && bit.hasNext()){
      //3 cases:
      //equal non dupe
      //equal and dupe
      //unequal, smaller is not in both sets

      //check for either list to have dupe in it's list
      boolean isDupe = false;
      //TODO So, better to put the dupe logic *after* detecting match b/c then you only have to
      // check for one of the prev values to be a dupe, not both. simpler.
      if(aPrev != null && aPrev == a){
        //dupe detected  in A. advance A
        a = ait.next();
        isDupe = true;
      }
      if(bPrev != null && bPrev == b){
        //dupe detected  in B. advance B
        b = bit.next();
        isDupe = true;
      }
      if(isDupe){
        //TODO or we could just advance on first dupe detected and avoid isDupe logic
        continue;
      }

      //neither a nor b is a dupe of it's prior list if we got here
      //check inequal first
      if(a < b){
        //no match, a cannot ever be in B. advance A
        aPrev = a;
        a = ait.next();
      }else if(b < a){
        //no match, b cannot ever be in A.  advance B
        bPrev = b;
        b = bit.next();
      }else{//match
        resp.add(a);
        //advance both
        aPrev = a;
        bPrev = b;
        a = ait.next();
        b = bit.next();
      }
    }
    return resp;


    //v1 too slow
//    HashSet<Integer> ASet = new HashSet<>(A);// O(A)
//    HashSet<Integer> BSet = new HashSet<>(B); //O(B)
//    ASet.retainAll(BSet);  //O(A) Could get min size of A and B to make this most efficient.
//    List<Integer> resp = new ArrayList<>(ASet);
//    Collections.sort(resp);//O nlgn where n is size of resp
//    return resp;
//
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "IntersectSortedArrays.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
