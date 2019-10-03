package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
import java.util.List;
public class BuyAndSellStock {
  @EpiTest(testDataFile = "buy_and_sell_stock.tsv")
  public static double computeMaxProfit(List<Double> prices) {
    double maxProfit = 0.0;
    double curMin = Double.MAX_VALUE;
    double curProfit = 0.0;
    for(Double price : prices){
      curMin = Math.min(curMin, price);
      curProfit = price - curMin;
      maxProfit = Math.max(curProfit, maxProfit);
    }
    return maxProfit;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "BuyAndSellStock.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
