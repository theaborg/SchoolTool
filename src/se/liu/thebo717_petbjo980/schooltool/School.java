package se.liu.thebo717_petbjo980.schooltool;

import se.liu.thebo717_petbjo980.schooltool.calendar.ScheduledAppointment;
import se.liu.thebo717_petbjo980.schooltool.calendar.TimePoint;
import se.liu.thebo717_petbjo980.schooltool.calendar.Weekday;

/**
 * Main school class.
 * Uses all other base classes.
 * <p>
 * Contains necessary function for managing school.
 * Automatically saves changes using SaveManager.
 */
public class School
{
    private String name;
    private Employee headMaster = null;

    private String news = "";

    private UserList employees;
    private UserList students;

    private StudentGroupList studentGroups;
    private CourseList courses;

    private SaveManager saveManager = new SaveManager();

    public School(final String name) {
	this.name = name;

	employees = new UserList();
	students = new UserList();
	studentGroups = new StudentGroupList();
	courses = new CourseList();
    }

    public User getUserByUsername(String username){
	return getUsers().getUser(username);
    }

    public Student getStudentByUsername(String username){
	User user = getUserByUsername(username);
	if(user.getType().equals(UserType.STUDENT)){
	    return (Student) user;
	}
	else return null;
    }

    public Employee getEmployeeByUsername(String username){
	User user = getUserByUsername(username);
	if (user.getType().equals(UserType.EMPLOYEE)){
	    return (Employee) user;

	}
	else return null;
    }

    public String getHeadMasterName() {
	if (headMaster == null){
	    return "Ej angivet";
	}
	return headMaster.getName();
    }

    public Course getCourseByCode(String code){
	return courses.getCourse(code);
    }

    public StudentGroup getStudentGroupByName(String name){
	return studentGroups.getStudentGroup(name);
    }

    public void registerEmployee(String username, String password, String firstName, String lastName, boolean admin){
	registerEmployee(new Employee(username, password, firstName, lastName, admin));
    }
    public void registerEmployee(Employee employee){
	employees.addUser(employee);
	saveManager.saveUser(employee, this);
    }

    public void registerStudent(String username, String password, String firstName, String lastName){
	registerStudent(new Student(username, password, firstName, lastName));
    }
    public void registerStudent(Student student){
	students.addUser(student);
	saveManager.saveUser(student, this);
    }

    public boolean unRegisterUser(User user){
	saveManager.deleteUser(user.getUsername(), this);
	if (user.getType().equals(UserType.STUDENT)){
	    if(students.contains(user)){
		students.removeUser(user);
		return true;
	    }
	    return false;
	}
	else if (user.getType().equals(UserType.EMPLOYEE)){
	    if(employees.contains(user)){
		employees.removeUser(user);
		return true;
	    }
	    return false;
	}
	return false;
    }

    public void registerCourse(String code, String title, int points, int maxAttendants){
	registerCourse(new Course(code, title, points, maxAttendants));
    }
    public void registerCourse(Course course){
	courses.add(course);
	saveManager.saveCourse(course, this);
    }

    public boolean unRegisterCourse(Course course){
	if(courses.contains(course)){
	    courses.remove(course);
	    saveManager.deleteCourse(course.getCode(), this);
	    return true;
	}
	return false;
    }

    public void registerStudentGroup(String name, int grade){
	registerStudentGroup(new StudentGroup(name, grade));
    }
    public void registerStudentGroup(StudentGroup group){
	studentGroups.add(group);
	saveManager.saveStudentGroup(group, this);
    }

    public boolean unRegisterStudentGroup(StudentGroup group){
	if(studentGroups.contains(group)){
	    studentGroups.remove(group);
	    saveManager.deleteStudentGroup(group.getName(), this);
	    return true;
	}
	return false;
    }

    public void registerStudentToCourse(Student student, Course course){
	course.registerStudent(student);
	saveManager.saveCourse(course, this);
    }

    public void unRegisterStudentFromCourse(Student student, Course course){
	course.unregisterStudent(student);
	saveManager.saveCourse(course, this);
    }

    public void registerTeacherToCourse(Employee employee, Course course){
	course.registerTeacher(employee);
	saveManager.saveCourse(course, this);
    }

    public void unRegisterTeacherFromCourse(Employee employee, Course course){
	course.unregisterTeacher(employee);
	saveManager.saveCourse(course, this);
    }

    public void registerStudentGroupToCourse(StudentGroup group, Course course){
	course.registerStudentGroup(group);
	saveManager.saveCourse(course, this);
    }

    public void unRegisterStudentGroupFromCourse(StudentGroup group, Course course){
	course.unregisterStudentGroup(group);
	saveManager.saveCourse(course, this);
    }

    public void registerStudentToStudentGroup(Student student, StudentGroup group){
	if(student.getStudentGroup() != null){
	    unRegisterStudentFromStudentGroup(student, student.getStudentGroup());
	}
	group.registerStudent(student);
	saveManager.saveStudentGroup(group, this);
    }

    public void unRegisterStudentFromStudentGroup(Student student, StudentGroup group){
	group.unregisterStudent(student);
	saveManager.saveStudentGroup(group, this);
    }

    public void addAppointment(ScheduledAppointment appointment){
	appointment.getCourse().addScheduleAppointment(appointment);
	saveManager.saveCourse(appointment.getCourse(), this);
    }

    public void removeAppointmentFromCourseAt(Weekday weekday, TimePoint timePoint, Course course){
	course.removeScheduleAppointmentAt(weekday, timePoint);
	saveManager.saveCourse(course, this);
    }

    public void changeNews(String news){
	this.news = news;
	saveManager.saveSchoolInfo(this);
    }

    public void changeHeadMaster(Employee employee){
	headMaster = employee;
	saveManager.saveSchoolInfo(this);
    }

    public void changeNameOfUser(User user, String firstName, String lastName){
	System.out.println(user.getUsername());
	user.setName(firstName, lastName);
	saveManager.saveUser(user, this);
    }

    public void changePasswordOfUser(User user, String password){
	user.setPassword(password);
	saveManager.saveUser(user, this);
    }

    public Employee getHeadMaster() {
	return headMaster;
    }

    public UserList getEmployees() {
	return employees;
    }

    public UserList getStudents() {
	return students;
    }

    public UserList getUsers(){
	return employees.combine(students);
    }

    public CourseList getCourses() {
	return courses;
    }

    public StudentGroupList getStudentGroups() {
	return studentGroups;
    }

    public String getName() {
	return name;
    }

    public String getNews() {
	return news;
    }

    public void setNews(final String news) {
	this.news = news;
    }

    public void setHeadMaster(final Employee headMaster) {
	this.headMaster = headMaster;
    }
}
