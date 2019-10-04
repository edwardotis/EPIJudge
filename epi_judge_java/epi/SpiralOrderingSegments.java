package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.ArrayList;
import java.util.List;
public class SpiralOrderingSegments {
  @EpiTest(testDataFile = "spiral_ordering_segments.tsv")

  public static List<Integer>
  matrixInSpiralOrder(List<List<Integer>> squareMatrix) {
//    squareMatrix = new ArrayList<List<Integer>>();
//    squareMatrix.add(new ArrayList<Integer>());
//    squareMatrix.add(new ArrayList<Integer>());
//    squareMatrix.add(new ArrayList<Integer>());
//
//    squareMatrix.get(0).add(1);
//    squareMatrix.get(0).add(4);
//    squareMatrix.get(0).add(7);
//
//    squareMatrix.get(1).add(9);
//    squareMatrix.get(1).add(8);
//    squareMatrix.get(1).add(2);
//
//    squareMatrix.get(2).add(3);
//    squareMatrix.get(2).add(5);
//    squareMatrix.get(2).add(6);

    List<Integer> resp = new ArrayList<>(squareMatrix.size() * squareMatrix.size());
    int cStart = 0, rStart = 0;
    int cEnd = squareMatrix.size(), rEnd = squareMatrix.size();
    while(true) {

      //right
      //pre
      if (!(cStart < cEnd)) {
        break;
      }
      //goRight
      for (int c = cStart; c < cEnd; c++) {
        int val = squareMatrix.get(rStart).get(c);
        System.out.print(val + ",");
        resp.add(val);
      }
      //post
      rStart++;

      //down
      //pre
      if (!(rStart < rEnd)) {
        break;
      }
      //goDown
      for (int r = rStart; r < rEnd; r++) {
        int val = squareMatrix.get(r).get(cEnd-1);
        System.out.print(val + ",");
        resp.add(val);
      }
      //post
      cEnd--;

      //go left
      //pre
      if (!(cEnd > cStart)) {
        break;
      }
      //goLeft
      for (int c = cEnd-1; c >= cStart; c--) {
        int val = squareMatrix.get(rEnd-1).get(c);
        System.out.print(val + ",");
        resp.add(val);
      }
      //post
      rEnd--;


      //up
      //pre
      if (!(rStart < rEnd)) {
        break;
      }
      //goUp TODO. Check for bug here.
      for (int r = rEnd-1; r >= rStart; r--) {
        int val = squareMatrix.get(r).get(cStart);
        System.out.print(val + ",");
        resp.add(val);
      }
      //post
      cStart++;

    }
    System.out.println();
    return resp;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "SpiralOrderingSegments.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
