package se.liu.thebo717_petbjo980.schooltool.viewers;

import se.liu.thebo717_petbjo980.schooltool.School;
import se.liu.thebo717_petbjo980.schooltool.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The JMenuBar which is visible in admin-mode.
 * This class uses an actionlistener to let the user go back
 * to normal mode by pressing the button "Hem",
 * view a pop ut with info about SchoolTool by pressing the menu "Meny" and choosing "SchoolTool", or
 * log out by pressing the button "Logga ut".
 */
public class AdminBarComponent extends JComponent implements ActionListener
{
    private JMenuBar menuBar = new JMenuBar();
    private User user;
    private School school;
    private final JMenu menu = new JMenu("Meny");
    private JMenuItem menuItem1 = new JMenuItem("SchoolTool", 'S');
    private JFrame frame;
    private JButton home = new JButton("Hem");
    private JButton logOut = new JButton("Logga ut");

    public AdminBarComponent(User user, School school, JFrame frame) {
	this.frame = frame;
	this.user = user;
	this.school = school;
	//menu
	menu.add(menuItem1);
	//buttons actionlisteners
	logOut.addActionListener(this);
	home.addActionListener(this);
	//menuitems actionlisteners
	menuItem1.addActionListener(this);
	//add all components
	menuBar.add(logOut);
	menuBar.add(home);
	menuBar.add(menu);
	JMenuItem bugfix = new JMenuItem(" ", ' ');
	menuBar.add(bugfix);
    }

    public JMenuBar getMenuBar() {
	return menuBar;
    }

    public void actionPerformed(final ActionEvent e) {
	if(e.getSource().equals(logOut)){
	    System.out.println("Logga ut");
	    boolean exit = JOptionPane.showConfirmDialog(
		    null, "Vill du logga ut från SchoolTool?",
		    "", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
	    if(exit){
		System.exit(0);
	    }
	}
	else if (e.getSource().equals(home)) {
	    HomeViewer viewer = new HomeViewer(school, user);
	    frame.dispose();
	    viewer.view();
	}
	else if (e.getSource().equals(menuItem1)){
	    JOptionPane.showConfirmDialog(null,
					  "För att veta mer om SchoolTool, kontakta Thea!", "SchoolTool",
					  JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
					  null);
	}
    }
}
