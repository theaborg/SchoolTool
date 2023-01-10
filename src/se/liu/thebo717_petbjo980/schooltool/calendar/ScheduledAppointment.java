package se.liu.thebo717_petbjo980.schooltool.calendar;
import se.liu.thebo717_petbjo980.schooltool.Course;

/**
 * Represents an appointment for a recurring class.
 * Is used to view Schedule.
 * Courses have ScheduledAppointments, and Users combine the appointments
 * of their courses to create a personalised schedule.
 */
public class ScheduledAppointment
{
    private Weekday weekday;
    private Course course;
    private TimeSpan timeSpan;
    private String description;

    public ScheduledAppointment(final Course course, final TimeSpan timeSpan, Weekday weekday, String description)
    {
	this.weekday = weekday;
	this.course = course;
	this.timeSpan = timeSpan;
	this.description = description;
    }

    public Weekday getWeekday() {
	return weekday;
    }

    public Course getCourse() {
	return course;
    }

    public TimeSpan getTimeSpan() {
	return timeSpan;
    }

    public String getDescription() {
	return description;
    }

    @Override public String toString() {
	final String appointments = getCourse().getTitle() + "\n" + getDescription()
			    + "\n" + getTimeSpan() + "\n" + "--------------------\n";
	return appointments;
    }
}
