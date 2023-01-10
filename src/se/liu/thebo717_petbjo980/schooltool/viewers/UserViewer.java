package se.liu.thebo717_petbjo980.schooltool.viewers;

import se.liu.thebo717_petbjo980.schooltool.School;
import se.liu.thebo717_petbjo980.schooltool.Student;
import se.liu.thebo717_petbjo980.schooltool.User;
import se.liu.thebo717_petbjo980.schooltool.UserType;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

/**
 * This viewer shows the current users profile. It inherits from the baseviewer.
 * In one panel, the users information i shown. In another, the user can see information about the current school.
 */
public class UserViewer extends BaseViewer
{
    private User currentUser;
    private BarComponent barComponent;
    private JFrame frame = new JFrame();

    public UserViewer(final User user, final School school) {
	super(user, school);
	this.currentUser = user;
	this.barComponent = new BarComponent(user, school, frame);

    }

    public String getStudGroupLine(){
	if(currentUser.getType().equals(UserType.STUDENT)){
	    if(((Student) currentUser).getStudentGroup() != null){
		return "Klass: " + ((Student) currentUser).getStudentGroup().getName() + "\n";
	    } else {
		return "Ingen klass\n";
	    }
	}
	return "";
    }

    public String getUserStatus(){
	Map<UserType, String> types = Map.of(UserType.STUDENT, "student", UserType.EMPLOYEE, "employee",
					     UserType.UNDEFINED, "ej angivet");
	return types.get(currentUser.getType());
    }

    public void view() {
	//set messages and strings
	String message = "Hjälp? Kontakta SchoolTools support ";
	String details = "Namn: " + currentUser.getName() + "\n" +
			 "Användarnamn: " + currentUser.getUsername() + "\n" +
			 getStudGroupLine() +
			 "Status: " + getUserStatus() + "\n" +
			 "\n" + message;
	String schoolDetails = "Namn: " + school.getName() + "\n" +
			       "Rektor: " + school.getHeadMasterName() + "\n" ;
	//frame init
	frame.setLayout(null);
	frame.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
	frame.setLayout(new BorderLayout());
	frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	//set components
	frame.setJMenuBar(barComponent.getMenuBar());
	CourseComboBox boxComponent = new CourseComboBox(school, currentUser, getCoursesFrom("user"),
							 PANEL_3, frame,  false);
	//add frame content
	frame.add(boxComponent.getComboBox());
	frame.add(setPageTitle("profil"));
	frame.add(setBreadTitle("uppgifter", PANEL_1));
	frame.add(setBreadTitle("kurser", PANEL_3));
	frame.add(setBreadTitle("din skola", PANEL_5));
	frame.add(setStringContent(PANEL_1, details));
	frame.add(setStringContent(PANEL_5, schoolDetails));
	//view
	frame.add(setBugFix());
	frame.pack();
	frame.setVisible(true);
    }
}
