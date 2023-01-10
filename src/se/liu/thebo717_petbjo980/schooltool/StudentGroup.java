package se.liu.thebo717_petbjo980.schooltool;

/**
 * Base class for StudentGroups (School class).
 * Contains functions for registering and unregistering Students to group.
 */
public class StudentGroup
{
    private UserList students;
    private CourseList courses;

    private int grade;
    private String name;

    public UserList getStudents(){
        return students;
    }

    public StudentGroup(final String name, final int grade) {
        this.grade = grade;
        this.name = name;
        students = new UserList();
        courses = new CourseList();
    }

    //funktionen nedan används inte, men är användbar för framtiden
    /*
    public void registerStudents(UserList students){
        for(User user : students.getList()){
            if(user.getType().equals(USERTYPE.STUDENT)){
                registerStudent((Student) user);
            }
        }
    }
    */

    public void registerStudent(Student student){
        if(!students.contains(student)){
            student.setStudentGroup(this);
            students.addUser(student);
        }
    }

    public void unregisterStudent(Student student){
        if(student.getStudentGroup().equals(this)){
            student.setStudentGroup(null); //Temporärt? Null när inte tilldelad för tillfället
        }
        if(students.contains(student)){
            students.removeUser(student);
        } else {
            System.out.println("User not in Class!");
        }
    }

    public void unregisterAll(){
        for(User user : getStudents().getUsers()){
            unregisterAll();
        }
    }

    public void addCourse(Course course){
        courses.add(course);
    }

    public void removeCourse(Course course){
        if (courses.contains(course)){
            courses.remove(course);
        }else {
            System.out.println("StudentGroup not registered to course");
        }
    }

    public int getGrade() {
        return grade;
    }

    public CourseList getCourses() {
        return courses;
    }

    public String getName() {
        return name;
    }


}
