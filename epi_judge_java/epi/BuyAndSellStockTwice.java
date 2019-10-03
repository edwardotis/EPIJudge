package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
import java.util.List;
public class BuyAndSellStockTwice {
  @EpiTest(testDataFile = "buy_and_sell_stock_twice.tsv")
  public static double buyAndSellStockTwice(List<Double> prices) {
    double maxProfit = 0.0;
    double maxProfit2nd = 0.0;
    int maxProfitBuy = -1;
    int maxProfitSell = -1;
    double curMin = Double.MAX_VALUE;
    double curProfit = 0.0;
    int curProfitBuy = -1;
    //pass 1
    for(int i=0; i<prices.size(); i++){
//    for(Double price : prices){
      double price = prices.get(i);
      //Damn, how do we handle first window with duplicate buy prices spread across dates?
      /**
       * For single max, we stick with first one.
       * But to optimize a second txn, we might need to move that buy date to a smaller window to give 2nd max more
       * windows to work with. However, we don't know if moving this curMin up will oh, no it should be totally fine
       * to advance it in this first loop b/c we've tracked the max to this point already.
       * Maybe it's doing that to second loop too. to avoid being caught in the first max window unnecessarily.
       * ok. That fixed a bunch more tests.
       *
       * But this algo fails for this:
       * 	arg0:        [0.9, 0.8, 0.8, 0.8, 0.7, 0.2, 1.0, 0.5, 0.6, 1.1, 0.7, 0.3]
       *
       * Failure info
       * 	explanation: First transaction buy at 0.2 and sell first at 1.0, and second transaction buy at 0.5 and sell at 1.1
       * 	expected:    1.4
       * 	result:      0.9000000000000001
       *
       * So, the primary solution kept up with all n maxProfits in first pass.
       * Then went in reverse on 2nd pass.
       * And was able to use the maxProfits first pass O(n) space to calculate the optimal.
       *
       * And they even have an optimal with constant space, which is kind of more like this algo, but
       * really hard to grok the part about minPrices updating. And they failed to put in any comments. Which is super lame.
       *
       */
      if(price <= curMin){
        curMin = price;
        curProfitBuy = i;
      }
      curProfit = price - curMin;
      if(curProfit > maxProfit){
        maxProfit = Math.max(curProfit, maxProfit);
        maxProfitBuy = curProfitBuy;
        maxProfitSell = i;//today's price is sell date
      }
    }
    // pass 2
    //reset
    curMin = Double.MAX_VALUE;
    curProfit = 0.0;
    curProfitBuy = -1;
    for(int i=0; i<prices.size(); i++) {
      double price = prices.get(i);
      //lt or equal to.
      if (price <= curMin) {
        curMin = price;
        curProfitBuy = i;
      }
      curProfit = price - curMin;
      if (curProfit > maxProfit2nd) {
        //check that this doesn't overlap existing max profit buy/sell window
        int curProfitSell = i;
        //cur txn began after max txn sold, or cur txn sell date happened before max tnx bought date
        //otherwise, this conflicts, and we need to move on to evaluate next price in list
        if (curProfitBuy > maxProfitSell ||
                curProfitSell < maxProfitBuy) {
          maxProfit2nd = Math.max(curProfit, maxProfit2nd);
        }else{
          //we've crossed into conflict. Advance i to max sell date and
          i = maxProfitSell;
          //reset curMin variable
          curMin = Double.MAX_VALUE;
        }
      }
    }
      return maxProfit + maxProfit2nd;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "BuyAndSellStockTwice.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
