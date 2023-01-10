package se.liu.thebo717_petbjo980.schooltool.viewers;

import se.liu.thebo717_petbjo980.schooltool.Employee;
import se.liu.thebo717_petbjo980.schooltool.School;
import se.liu.thebo717_petbjo980.schooltool.StudentGroup;
import se.liu.thebo717_petbjo980.schooltool.User;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This is the homepage for admins, and is opened by pressing the button "Admin"
 * in the BarComponent after logging in.
 * It allows for the user to view students, employees, courses and studentgroups registered
 * to the currently active school. They are viewed via a CourseComboBox, UserComboBox or GroupComboBox.
 * There are also buttons which allow for the admin to add new objects of above mentioned classes.
 */
public class AdminViewer extends BaseViewer
{
    private User currentUser;
    private Frame currentFrame = new Frame();

    public AdminViewer(final School school, final User user) {
	super(user, school);
	this.currentUser = user;
    }

    public String[] getStudentsFromSchool(){
	List<String> usernames = new ArrayList<>();
	for (User student:school.getStudents().getUsers()) {
	    usernames.add(student.getUsername());
	}
	String[] usernames1 = new String[usernames.size()];
	return usernames.toArray(usernames1);
    }

    public String[] getEmployeesFromSchool(){
	List<String> usernames = new ArrayList<>();
	for (User user:school.getEmployees().getUsers()) {
	    usernames.add(user.getUsername());
	}
	String[] usernames1 = new String[usernames.size()];
	return usernames.toArray(usernames1);
    }

    public boolean legalString(String s, int minLength, int maxLength){
	if(s == null){
	    return false;
	}
	if(s.length() < minLength || s.length() > maxLength ||  s.contains("[")){
	    return false;
	}
	return true;
    }

    private String[] getUserInfo(){
	String username = JOptionPane.showInputDialog("Användarnamn: ", "");
	int minLength = 4;
	int maxLength = 16;
	if(!legalString(username, minLength, maxLength)){
	    JOptionPane.showMessageDialog(null, "Invalid username");
	    return null;
	} else if(checkUserExists(username)){
	    JOptionPane.showMessageDialog(null, "Användaren finns redan");
	    return null;
	}
	String password = JOptionPane.showInputDialog("Lösenord: ", "");
	if(!legalString(password, minLength, maxLength)){
	    JOptionPane.showMessageDialog(null, "Invalid password");
	    return null;
	}
	String firstName = JOptionPane.showInputDialog("Förnamn: ", "");
	if(!legalString(firstName, 1, maxLength)){
	    JOptionPane.showMessageDialog(null, "Invalid name");
	    return null;
	}
	String lastName = JOptionPane.showInputDialog("Efternamn: ", "");
	if(!legalString(lastName, 1, maxLength)){
	    JOptionPane.showMessageDialog(null, "Invalid name");
	    return null;
	}
	String[] out = {username, password, firstName, lastName};
	return out;
    }

    private void refreshViewer(){
	currentFrame.dispose();
	view();
    }

    private boolean checkUserExists(String username){
	if(school.getUserByUsername(username) == null){
	    return false;
	}
	return true;
    }

    private boolean checkGroupExists(String name){
	if(school.getStudentGroupByName(name) == null){
	    return false;
	}
	return true;
    }

    private boolean checkCourseExists(String code){
	if(school.getCourseByCode(code) == null){
	    return false;
	}
	return true;
    }

    private int parseInt(String message){
	try{
	    int out = Integer.parseInt(JOptionPane.showInputDialog(message, ""));
	    if(out < 0){
		JOptionPane.showMessageDialog(null, "Negativt nummer");
	    }
	    return out;
	} catch (NumberFormatException ignored){
	    JOptionPane.showMessageDialog(null, "Inte ett nummer");
	    return -1;
	}
    }

    private Employee getEmployee(){
	String s = JOptionPane.showInputDialog("Ange användarnamn: ");
	if(s == null){
	    return null;
	}
	Employee employee = school.getEmployeeByUsername(s);
	if(employee == null){
	    JOptionPane.showMessageDialog(null, "Läraren " + s + " finns inte");
	}
	return employee;
    }

    public void registerNewStudent(){
	String[] info = getUserInfo();
	if(info != null){
	    //tal efter tal -> inför ingen variabel!
	    school.registerStudent(info[0], info[1], info[2], info[3]);
	    refreshViewer();
	}
    }
    public void registerNewEmployee(){
	String[] info = getUserInfo();
	if(info == null){
	    return;
	}
	Object[] choices = {"Ja", "Nej"};
	int action = JOptionPane.showOptionDialog(null, "Ge anställd adminstatus?", "Admin",
						  JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
						  null, choices, choices[0]);
	boolean admin;
	if(action == 0){
	    admin = true;
	} else if(action == 1){
	    admin = false;
	} else {
	    return;
	}

	school.registerEmployee(info[0], info[1], info[2], info[3], admin);
	refreshViewer();

    }
    public void registerNewStudentGroup(){
	int maxLength = 12;
	String name = JOptionPane.showInputDialog("Namn: ", "");
	if(!legalString(name, 1, maxLength)){
	    JOptionPane.showMessageDialog(null, "Invalid name");
	    return;
	} else if (checkGroupExists(name)) {
	    JOptionPane.showMessageDialog(null, "Klassen finns redan");
	    return;
	}
	int grade = parseInt("Årskurs: ");
	System.out.println(grade);
	if(grade < 0){
	    return;
	}

	school.registerStudentGroup(name, grade);
	refreshViewer();
    }
    public void registerNewCourse(){
	int minLength = 1;
	int maxCodeLength = 16;
	int maxTitleLength = 48;
	String code = JOptionPane.showInputDialog("Kurskod: ", "");
	if(!legalString(code, minLength, maxCodeLength)){
	    JOptionPane.showMessageDialog(null, "Invalid code");
	    return;
	} else if(checkCourseExists(code)){
	    JOptionPane.showMessageDialog(null, "Kursen finns redan");
	    return;
	}
	String title = JOptionPane.showInputDialog("Titel: ", "");
	if(!legalString(title, 1, maxTitleLength)){
	    JOptionPane.showMessageDialog(null, "Invalid title");
	    return;
	}
	int points = parseInt("Poäng: ");
	if(points < 0){
	    return;
	}

	school.registerCourse(code, title, points, 70);
	refreshViewer();
    }
    private void changeNews(){
	String s = JOptionPane.showInputDialog("Ange nya nyheter: ");
	if(s != null){
	    school.changeNews(s);
	}
    }
    private void changeHeadmaster(){
	Employee newHeadmaster = getEmployee();
	if(newHeadmaster != null){
	    school.changeHeadMaster(newHeadmaster);
	}
    }

    private JPanel makeButtonPanel(){
	JPanel buttons = new JPanel();
	buttons.setBounds(0, FRAME_HEIGHT - HEIGHT_MARGIN*HEIGHT_MARGIN, FRAME_WIDTH, TITLE_HEIGHT);
	//buttons.setLayout(new BorderLayout());
	JButton buttonStudent = new JButton("Reg. Student");
	JButton buttonEmployee = new JButton("Reg. Anställd");
	JButton buttonGroup = new JButton("Reg. Klass");
	JButton buttonCourse = new JButton("Reg. Kurs");
	JButton buttonNews = new JButton("Ändra Nyheter");
	JButton buttonHeadmaster = new JButton("Ändra Rektor");

	buttonStudent.addActionListener(e -> registerNewStudent());
	buttonEmployee.addActionListener(e -> registerNewEmployee());
	buttonGroup.addActionListener(e -> registerNewStudentGroup());
	buttonCourse.addActionListener(e -> registerNewCourse());
	buttonNews.addActionListener(e -> changeNews());
	buttonHeadmaster.addActionListener(e -> changeHeadmaster());

	buttons.add(buttonCourse);
	buttons.add(buttonStudent);
	buttons.add(buttonEmployee);
	buttons.add(buttonGroup);
	buttons.add(buttonNews);
	buttons.add(buttonHeadmaster);

	return buttons;
    }

    public String[] getGroupsFromSchool(){
	List<String> groups = new ArrayList<>();
	for (StudentGroup group:school.getStudentGroups().getStudentGroups()) {
	    groups.add(group.getName());
	}
	String[] groupArr = new String[groups.size()];
	return groups.toArray(groupArr);
    }

    public void view(){
	JFrame frame = new JFrame();
	currentFrame = frame;
	AdminBarComponent barComponent = new AdminBarComponent(currentUser, school, frame);
	CourseComboBox comboBox1 = new CourseComboBox(school, currentUser, getCoursesFrom("school"),
						      1, frame, true);
	UserComboBox comboBox2 = new UserComboBox(school, currentUser, getStudentsFromSchool(),
						  2, frame, true);
	UserComboBox comboBox3 = new UserComboBox(school, currentUser, getEmployeesFromSchool(),
						  3, frame, true);
	GroupComboBox comboBox4 = new GroupComboBox(school, currentUser, getGroupsFromSchool(),
						    4, frame, true);
	frame.add(comboBox1.getComboBox());
	frame.add(comboBox2.getComboBox());
	frame.add(comboBox3.getComboBox());
	frame.add(comboBox4.getComboBox());
	frame.add(makeButtonPanel());
	frame.add(setBreadTitle("Kurser ", PANEL_1));
	frame.add(setBreadTitle("Studenter ", PANEL_2));
	frame.add(setBreadTitle("Anställda ", PANEL_3));
	frame.add(setBreadTitle("Klasser ", PANEL_4));
	frame.setJMenuBar(barComponent.getMenuBar());
	frame.setLayout(null);
	frame.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
	frame.setLayout(new BorderLayout());
	frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	//title management
	frame.add(setPageTitle("administration"));
	//view
	frame.add(setBugFix());
	frame.pack();
	frame.setVisible(true);
    }

}

