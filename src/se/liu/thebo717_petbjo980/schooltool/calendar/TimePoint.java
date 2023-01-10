package se.liu.thebo717_petbjo980.schooltool.calendar;

import java.util.Map;

/**
 * Represents a timepoint in a day, hour and minute.
 */
public class TimePoint
{
    private int hour;
    private int minute;

    public TimePoint(int hour, int minute) {
	this.hour = hour;
	this.minute = minute;
    }

    public int getHour() {
	return hour;
    }

    public int getMinute() {
	return minute;
    }


    public int compareTo(TimePoint other){
	Map<String, Integer> time = Map.of( "same", 0, "earlier", 1, "later", -1);
	if (other.hour == getHour() && other.minute == getMinute()){
	    return time.get("same"); //same timepoint
	}
	else if (other.hour <= getHour() && other.minute <= getMinute()){
	    return time.get("earlier"); //"other" starts earlier
	}
	else{
	    return time.get("later"); //"other" starts later
	}
    }

    public String forceTwoDigit(int i){
	if(i < 0){
	    return "00";
	}
	else if(i <= 9){
	    //nedan vill vi inte ha mellanslag och ingen variabel, 9 är största ensiffriga talet. Samma resonemang för 99.
	    return "0" + i;
	} else if(i > 99){
	    return "99";
	}
	return Integer.toString(i);
    }

    @Override public String toString() {
	return  forceTwoDigit(hour) + ":" + forceTwoDigit(minute) ;
    }
}
