package se.liu.thebo717_petbjo980.schooltool.calendar;

/**
 * Represent a timespan in a day, between to TimePoints.
 */
public class TimeSpan
{
    private TimePoint start;
    private TimePoint end;

    public TimeSpan(final TimePoint start, final TimePoint end) {
	this.start = start;
	this.end = end;
    }

    public TimePoint getStart() {
	return start;
    }

    public TimePoint getEnd() {
	return end;
    }

    @Override public String toString() {
	return start + " - " + end;
    }

    //getDuration?

}
