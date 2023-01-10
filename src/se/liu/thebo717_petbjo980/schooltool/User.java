package se.liu.thebo717_petbjo980.schooltool;

import se.liu.thebo717_petbjo980.schooltool.calendar.ScheduledAppointment;

import java.util.ArrayList;
import java.util.List;

/**
 * Base class for users.
 * Student and Employee derive from this.
 */
public class User
{
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private CourseList courses;
    protected UserType type = UserType.UNDEFINED;

    public User(final String username, final String password, final String firstName, final String lastName) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;

        courses = new CourseList();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getName() {
        return firstName +" " + lastName;
    }

    public UserType getType() {
        return type;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public void setName(final String firstName, final String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void setType(final UserType type) {
        this.type = type;
    }

    public void addCourse(Course course){
        courses.add(course);
    }

    public void removeCourse(Course course){
        if(courses.contains(course)){
            courses.remove(course);
        } else {
            System.out.println("User not registered to course");
        }
    }

    public List<ScheduledAppointment> getSchedule() {
        List<ScheduledAppointment> scheduledAppointments = new ArrayList<>();
        for (Course course: getCourses().getCourses()) {
            scheduledAppointments.addAll(course.getScheduledAppointments());
        }
        return scheduledAppointments;
    }


    public CourseList getCourses() {
        return courses;
    }

    public String getStatusInFileFormat(){
        return "U\n";
    }

}
