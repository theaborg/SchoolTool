package se.liu.thebo717_petbjo980.schooltool;

import java.util.ArrayList;
import java.util.List;

/**
 * A class which represents a list of courses. Is used
 * to handle all courses registered to a School object.
 * Can also combine two different CourseLists into one.
 */
public class CourseList
{
    private List<Course> courses;
    public CourseList(){
	this.courses = new ArrayList<>();
    }
    public void add(Course course){
	courses.add(course);
    }

    public boolean contains(Course course){
	return courses.contains(course);
    }

    public void remove(Course course){
	courses.remove(course);
    }

    public Course getCourse(String code){
	for(Course course : courses){
	    if(course.getCode().equals(code)){
		return course;
	    }
	}
	return null;
    }

    //Nedan finns två oanvända, men användbara, funktioner
    /*
    public boolean isEmpty(){
	return courses.isEmpty();
    }

    public void addOther(CourseList courses){
	this.courses.addAll(courses.getCourses());
    }
     */

    public List<Course> getCourses(){
	return courses;
    }

    public CourseList combine(CourseList other){
	CourseList combined = new CourseList();
	for(Course course : courses){
	    combined.add(course);
	}
	for(Course course : other.getCourses()){
	    if(!combined.contains(course)){
		combined.add(course);
	    }
	}
	return combined;
    }
}
