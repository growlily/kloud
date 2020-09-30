package orderspeed;

public class TimeCoord implements Comparable<TimeCoord> {
    public Long time;
    public double jing;
    public double wei;

    @Override
    public int compareTo(TimeCoord timeCoord) {
        return time.compareTo(timeCoord.time);
    }
}
