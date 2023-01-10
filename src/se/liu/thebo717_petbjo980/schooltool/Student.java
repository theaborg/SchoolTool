package se.liu.thebo717_petbjo980.schooltool;

import java.util.HashMap;
import java.util.Map;

/**
 * Student class which derives from User.
 * Contains grade information and a StudentGroup.
 * <p>
 * The courses CourseList from User is only used for
 * the Students individually registered courses, NOT courses
 * of the students StudentGroup. getCourses returns a combination
 * of the two lists.
 */
public class Student extends User{
    //Varning pga setGrade är utkommenterad
    private Map<Course, String> grades = new HashMap<>();
    private StudentGroup studentGroup;

    public Student(String username, String password, String firstName, String lastName) {
	super(username, password, firstName, lastName);
	studentGroup = null;
	setType(UserType.STUDENT);
    }

    @Override public CourseList getCourses() {
	if(studentGroup != null){
	    return super.getCourses().combine(studentGroup.getCourses());
 	}
	return super.getCourses();
    }

    public void setStudentGroup(StudentGroup group){
	studentGroup = group;
    }

    public StudentGroup getStudentGroup(){
	return studentGroup;
    }

    //funktionen nedan används aldrig men är användbar.
    /*
    public void setGrade(Course course, String grade){
	CourseList courses = this.getCourses();
	if (courses.contains(course)){
	    //if grades.contains(course) -> fråga om man vill skriva över det betyget
	    grades.put(course, grade);
	}
    }
     */

    public String getGrade(Course course){
	String errorMessage = "Betyget har inte satts ännu. ";
	if (grades.containsKey(course)){
	    return grades.get(course);
	}
	return errorMessage;
    }

    @Override public String getStatusInFileFormat(){
	return "S\n";
    }
}
