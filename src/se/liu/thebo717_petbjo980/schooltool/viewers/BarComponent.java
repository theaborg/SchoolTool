package se.liu.thebo717_petbjo980.schooltool.viewers;


import se.liu.thebo717_petbjo980.schooltool.Employee;
import se.liu.thebo717_petbjo980.schooltool.School;
import se.liu.thebo717_petbjo980.schooltool.UserType;
import se.liu.thebo717_petbjo980.schooltool.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The JMenuBar which is visible in normal-mode.
 * This class uses an actionlistener to let the user
 * view their schedule by pressing the button "Schema",
 * view their homepage by pressing the button "Hem",
 * view their profile by pressing the button "Profil",
 * try to log in to admin-mode by pressing the button "Admin",
 * view a pop ut with info about SchoolTool by pressing the menu "Meny" and choosing "SchoolTool", or
 * log out by pressing the button "Logga ut".
 */
public class BarComponent extends JComponent implements ActionListener
{
    private JMenuBar menuBar = new JMenuBar();
    private User user;
    private School school;
    private JMenuItem menuItem1 = new JMenuItem("SchoolTool", 'S');
    private JFrame frame;
    private JButton schedule = new JButton("Schema");
    private JButton home = new JButton("Hem");
    private JButton userButton = new JButton("Profil");
    private JButton admin = new JButton("Admin");
    private JButton logOut = new JButton("Logga ut");

    public BarComponent(User user, School school, JFrame frame) {
	this.frame = frame;
	this.user = user;
	this.school = school;
	//menu
	JMenu menu = new JMenu("Meny");
	menu.add(menuItem1);
	//buttons actionlisteners
	schedule.addActionListener(this);
	logOut.addActionListener(this);
	home.addActionListener(this);
	userButton.addActionListener(this);
	admin.addActionListener(this);
	//menuitems actionlisteners
	menuItem1.addActionListener(this);
	//add all components
	menuBar.add(logOut);
	menuBar.add(schedule);
	menuBar.add(home);
	menuBar.add(userButton);
	menuBar.add(admin);
	menuBar.add(menu);
	JMenuItem bugfix = new JMenuItem(" ", ' ');
	menuBar.add(bugfix);
    }

    public JMenuBar getMenuBar() {
	return menuBar;
    }

    public void actionPerformed(final ActionEvent e) {
	if(e.getSource().equals(logOut)){
	    boolean exit = JOptionPane.showConfirmDialog(
		    null, "Vill du logga ut från SchoolTool?",
		    "", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
	    if(exit){
		System.exit(0);
	    }
	}
	else if (e.getSource().equals(schedule)){
	    ScheduleViewer scheduleViewer = new ScheduleViewer(user, school);
	    frame.dispose();
	    scheduleViewer.view();
	}
	else if (e.getSource().equals(home)){
	    HomeViewer homeViewer = new HomeViewer(school, user);
	    frame.dispose();
	    homeViewer.view();
	}
	else if (e.getSource().equals(userButton)){
	    UserViewer userViewer = new UserViewer(user, school);
	    frame.dispose();
	    userViewer.view();
	}

	else if (e.getSource().equals(admin)) {
	    if (user.getType().equals(UserType.EMPLOYEE) ){
		Employee employee = (Employee) user;
		if (employee.getAdminStatus()){
		    AdminViewer adminViewer = new AdminViewer(school, user);
		    frame.dispose();
		    adminViewer.view();
		}
	    }
	    else {
		    JOptionPane.showConfirmDialog(null,
						  "Du har inte behörighet för denna tjänst.", "Obehörig",
						  JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
						  null);
		}
	}
	else if (e.getSource().equals(menuItem1)){
	    JOptionPane.showConfirmDialog(null,
					  "För att veta mer om SchoolTool, fråga Petter!", "Controls",
					  JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
					  null);
	}

    }

}
