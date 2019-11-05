package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.EpiUserType;
import epi.test_framework.GenericTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
public class CalendarRendering {
  @EpiUserType(ctorParams = {int.class, int.class})

  public static class Event {
    public int start, finish;

    public Event(int start, int finish) {
      this.start = start;
      this.finish = finish;
    }
  }

  private static class Endpoint {
    public int time;
    public boolean isStart;

    Endpoint(int time, boolean isStart) {
      this.time = time;
      this.isStart = isStart;
    }
  }

  @EpiTest(testDataFile = "calendar_rendering.tsv")

  public static int findMaxSimultaneousEvents(List<Event> A) {
    /**
     *
     O(n) n is # of events
     Space (n) 2n  sorted events by start and end.
     Convert Event into start times and end times.
     2 ptrs
     A new start time always means a new meeting is starting.
     inc curMeetings

     also
     if a next meeting end time is < cur start time.
     Then at least one meeting has ended.
     decr curMeetings

     Now, what if 6 meetings ended before the  latest one started?
     How is that accounted for?
     */
    if(A == null || A.isEmpty()){
      return 0;
    }
    int concurrent = 0;
    int max = 0;
    List<Integer> startTimes = new ArrayList<>();
    List<Integer> endTimes = new ArrayList<>();

    for(Event e : A){
      startTimes.add(e.start);
      endTimes.add(e.finish);
    }

    Collections.sort(startTimes);
    Collections.sort(endTimes);

    Iterator<Integer> sIt = startTimes.iterator();
    Iterator<Integer> eIt = endTimes.iterator();
    Integer end = eIt.next();
    while(sIt.hasNext()){
      //a new meeting has started
      Integer start = sIt.next();
      concurrent++;
      if(start > end){
        concurrent--;//a meeting has ended
        end = eIt.next();
      }

      //
      max = Math.max(max, concurrent);
    }


    return max;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "CalendarRendering.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
