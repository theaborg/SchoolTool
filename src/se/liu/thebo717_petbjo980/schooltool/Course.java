package se.liu.thebo717_petbjo980.schooltool;

import se.liu.thebo717_petbjo980.schooltool.calendar.ScheduledAppointment;
import se.liu.thebo717_petbjo980.schooltool.calendar.TimePoint;
import se.liu.thebo717_petbjo980.schooltool.calendar.TimeSpan;
import se.liu.thebo717_petbjo980.schooltool.calendar.Weekday;

import java.util.ArrayList;
import java.util.List;

/**
 * Base class for course in school.
 * Contains functions for registering and
 * unregistering Users and StudentGroups
 */
public class Course {
    private String code;
    private String title;
    private UserList students;
    private UserList teachers;
    private StudentGroupList studentGroups;
    private int points;
    private int maximumAttendants;
    private List<ScheduledAppointment> scheduledAppointments;

    public Course(String code, String title, int points, int maximumAttendants) {
	this.code = code;
	this.title = title;
	this.points = points;
	this.maximumAttendants = maximumAttendants;

	students = new UserList();
	teachers = new UserList();
	studentGroups = new StudentGroupList();
	scheduledAppointments = new ArrayList<>();
    }

    public void addScheduleAppointment(Weekday weekday, TimeSpan timeSpan, String description){
	ScheduledAppointment appointment = new ScheduledAppointment(this, timeSpan, weekday, description);
	scheduledAppointments.add(appointment);
    }
    public void addScheduleAppointment(ScheduledAppointment appt){
	scheduledAppointments.add(appt);
    }

    public void removeScheduleAppointmentAt(Weekday weekday, TimePoint point){
	List<ScheduledAppointment> shouldRemove = new ArrayList<>();
	for(ScheduledAppointment appt : scheduledAppointments){
	    if (appt.getTimeSpan().getStart().compareTo(point) == 0 && appt.getWeekday() == weekday){
		shouldRemove.add(appt);
	    }
	}
	for (ScheduledAppointment appt : shouldRemove){
	    scheduledAppointments.remove(appt);
	}
    }

    public List<ScheduledAppointment> getScheduledAppointments(){
	return scheduledAppointments;
    }


    public void registerStudent(User student){
	if(!students.contains(student)){
	    students.addUser(student);
	    student.addCourse(this);
	}
    }

    public void registerTeacher(User teacher){
	if(!teachers.contains(teacher)){
	    teachers.addUser(teacher);
	    teacher.addCourse(this);
	}
    }

    public void unregisterStudent(User student){
	students.removeUser(student);
	student.removeCourse(this);
    }

    public void unregisterTeacher(User teacher){
	teachers.removeUser(teacher);
	teacher.removeCourse(this);
    }

    public void registerStudentGroup(StudentGroup group){
	if(!studentGroups.contains(group)){
	    studentGroups.add(group);
	    group.addCourse(this);
	}
    }

    public void unregisterStudentGroup(StudentGroup group){
	studentGroups.remove(group);
	group.removeCourse(this);
    }

    //nedan har vi en funktion som aldrig används, men som vi tror är användbar om appen utvecklas ytterligare
    /*
    public void unregisterAll(){
	for(User user : getStudents().getList()){
	    unregisterStudent(user);
	}
	for(User user : getTeachers().getList()){
	    unregisterTeacher(user);
	}
	for(StudentGroup group : getStudentGroups().getList()){
	    unregisterStudentGroup(group);
	}
    }
     */

    public String getTeacherName(int index) {
	if(index >= teachers.getSize()){
	    //Index error
	    return "Nobody";
	} else {
	    return teachers.getUser(index).getName();
	}
    }

    public String getCode(){
	return code;
    }

    public String getTitle() {
	return title;
    }

    public UserList getStudents() {
	return students;
    }

    public UserList getTeachers() {
	return teachers;
    }

    public StudentGroupList getStudentGroups() {
	return studentGroups;
    }

    public int getPoints() {
	return points;
    }

    public int getMaximumAttendants() {
	return maximumAttendants;
    }
}

