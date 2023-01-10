package se.liu.thebo717_petbjo980.schooltool.viewers;

import se.liu.thebo717_petbjo980.schooltool.School;
import se.liu.thebo717_petbjo980.schooltool.User;
import se.liu.thebo717_petbjo980.schooltool.calendar.ScheduledAppointment;

import javax.swing.*;
import java.awt.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

/**
 * The viewer for the homepage. Shows todays appointments and any news registered at the current school.
 * Inherits from the BaseViewer.
 */
public class HomeViewer extends BaseViewer
{

    public HomeViewer(final School school, final User user) {
	super(user,school);
    }

    public void view(){
	JFrame frame = new JFrame();
	String todaysNews = school.getNews();
	BarComponent barComponent = new BarComponent(user, school, frame);
	DayOfWeek today = LocalDate.now().getDayOfWeek();
	List<ScheduledAppointment> todaysAppointments = null;
	//frame init
	frame.setJMenuBar(barComponent.getMenuBar());
	frame.setLayout(null);
	frame.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
	frame.setLayout(new BorderLayout());
	frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	//title management
	String title = ("Välkommen, " + user.getFirstName());
	JTextArea textArea = setPageTitle(title);
	textArea.setBounds(FRAME_WIDTH/2-((title.length()*TITLE_FONT.getSize()/2)),
			   0, TITLE_WIDTH, TITLE_HEIGHT);
	frame.add(textArea);
	sortAppointments(user.getSchedule());
	switch (today){
	    case MONDAY -> todaysAppointments = mondays;
	    case TUESDAY -> todaysAppointments = tuesdays;
	    case WEDNESDAY -> todaysAppointments = wednesdays;
	    case THURSDAY -> todaysAppointments = thursdays;
	    case FRIDAY -> todaysAppointments = fridays;
	}
	String appTitle = "dagens schema: ";
	String newsTitle = "nyheter: ";
	frame.add(setBreadTitle(appTitle,PANEL_2));
	frame.add(setStringContent(PANEL_2, convertToString(todaysAppointments)));
	frame.add(setBreadTitle(newsTitle,PANEL_4));
	frame.add(setStringContent(PANEL_4, todaysNews));
	//view
	frame.add(setBugFix());
	frame.pack();
	frame.setVisible(true);
    }
    
}
