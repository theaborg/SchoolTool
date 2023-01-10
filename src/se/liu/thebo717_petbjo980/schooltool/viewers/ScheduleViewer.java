package se.liu.thebo717_petbjo980.schooltool.viewers;

import se.liu.thebo717_petbjo980.schooltool.School;
import se.liu.thebo717_petbjo980.schooltool.User;

import javax.swing.*;
import java.awt.*;

/**
 * ScheduleViewer views the weekly schedule of the current user.
 * It sorts through the users appointments, converts the lists for each weekday into Strings,
 * and shows them in the 5 different panels on the frame.
 */
public class ScheduleViewer extends BaseViewer
{
    private User currentUser;
    private JFrame frame = new JFrame();
    private BarComponent barComponent;

    public ScheduleViewer(final User user, final School school) {
	super(user, school);
	this.currentUser = user;
	this.barComponent = new BarComponent(user, school, frame);
    }


    public void view(){
	int panel1 = 1;
	int panel2 = 2;
	int panel3 = 3;
	int panel4 = 4;
	int panel5 = 5;
	//init frame
	frame.setJMenuBar(barComponent.getMenuBar());
	frame.setLayout(null);
	frame.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
	frame.setLayout(new BorderLayout());
	frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	frame.add(setPageTitle("schema"));
	//manage weekday titles
	frame.add(setBreadTitle("måndag", panel1));
	frame.add(setBreadTitle("tisdag", panel2));
	frame.add(setBreadTitle("onsdag", panel3));
	frame.add(setBreadTitle("torsdag", panel4));
	frame.add(setBreadTitle("fredag", panel5));
	//sort appointments
	sortAppointments(currentUser.getSchedule());
	//manage weekday content
	frame.add(setStringContent(panel1, convertToString(mondays)));
	frame.add(setStringContent(panel2, convertToString(tuesdays)));
	frame.add(setStringContent(panel3, convertToString(wednesdays)));
	frame.add(setStringContent(panel4, convertToString(thursdays)));
	frame.add(setStringContent(panel5, convertToString(fridays)));
	//view frame
	frame.add(setBugFix());
	frame.pack();
	frame.setVisible(true);
    }
}
