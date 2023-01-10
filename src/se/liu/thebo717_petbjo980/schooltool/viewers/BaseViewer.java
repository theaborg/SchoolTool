package se.liu.thebo717_petbjo980.schooltool.viewers;

import se.liu.thebo717_petbjo980.schooltool.Course;
import se.liu.thebo717_petbjo980.schooltool.School;
import se.liu.thebo717_petbjo980.schooltool.User;
import se.liu.thebo717_petbjo980.schooltool.calendar.ScheduledAppointment;
import se.liu.thebo717_petbjo980.schooltool.calendar.Weekday;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * This viewer contains the constants which apply to all the viewers
 * in this project. It also contains methods for adding a pagetitle,
 * for adding content divided into 5 panels, and adding titles to said panels.
 * There is also a method for distributing appointments into different lists,
 * depending on what weekday they are scheduled for. It can also convert
 * a list of appointments to strings.
 */
public class BaseViewer
{
    protected User user;
    protected School school;
    //constants
    static protected final int PANEL_1 = 1;
    static protected final int PANEL_2 = 2;
    static protected final int PANEL_3 = 3;
    static protected final int PANEL_4 = 4;
    static protected final int PANEL_5 = 5;
    static protected final int FRAME_WIDTH = 1400;
    static protected final int FRAME_HEIGHT = 900;
    static protected final int TITLE_HEIGHT = 75;
    static protected final int HEIGHT_MARGIN = 10;
    static protected final int SIDE_MARGIN = FRAME_WIDTH / 20;
    static protected final int TITLE_WIDTH = FRAME_WIDTH - 2 * SIDE_MARGIN;
    static protected final int BREAD_HEIGHT = 500;
    static protected final int BREAD_DISTANCE = 20;
    static protected final int PANEL_AMOUNT = 5;
    static protected final int BREAD_WIDTH = (FRAME_WIDTH - 2 * SIDE_MARGIN - 4 * BREAD_DISTANCE) / PANEL_AMOUNT;
    //fonts
    protected final static Font BREAD_TITLE_FONT = new Font("Weekday Title",
                                                            Font.BOLD | Font.ITALIC, 23);
    protected final static Font TITLE_FONT = new Font("Title", Font.BOLD, 40);
    protected final static Font WEEKDAY_CONTENT_FONT = new Font("Weekday Content",
                                                              Font.PLAIN, 13);
    //weekdays
    protected List<ScheduledAppointment> mondays = new ArrayList<>();
    protected List<ScheduledAppointment> tuesdays = new ArrayList<>();
    protected List<ScheduledAppointment> wednesdays = new ArrayList<>();
    protected List<ScheduledAppointment> thursdays = new ArrayList<>();
    protected List<ScheduledAppointment> fridays = new ArrayList<>();


    public BaseViewer(final User user, final School school) {
        this.user = user;
        this.school = school;
    }

    public String[] getCoursesFrom(String object){
        List<String> courseCodes = new ArrayList<>();
        if (object.equals("user")){
            for (Course course:user.getCourses().getCourses()) {
                courseCodes.add(course.getCode());
            }
        }
        else if (object.equals("school")){
            for (Course course:school.getCourses().getCourses()) {
                courseCodes.add(course.getCode());
            }
        }
        String[] courseArr = new String[courseCodes.size()];
        return courseCodes.toArray(courseArr);
    }

    public JTextArea setBreadTitle(String title, int framePos){
        JTextArea weekday = new JTextArea(title.toUpperCase(Locale.ROOT), 1, 5);
        weekday.setFont(BREAD_TITLE_FONT);
        weekday.setEditable(false);
        weekday.setBounds(SIDE_MARGIN + (BREAD_WIDTH + BREAD_DISTANCE) * (framePos - 1),
                          TITLE_HEIGHT + HEIGHT_MARGIN, BREAD_WIDTH, TITLE_HEIGHT);
        weekday.setLineWrap(true);
        return weekday;
    }

    public void sortAppointments(List<ScheduledAppointment> appointments){
        for (int i = 0; i < appointments.size(); i++) {
            ScheduledAppointment appointment = user.getSchedule().get(i);
            Weekday weekday = user.getSchedule().get(i).getWeekday();
            switch (weekday) {
                case MONDAY -> mondays.add(appointment);
                case TUESDAY -> tuesdays.add(appointment);
                case WEDNESDAY -> wednesdays.add(appointment);
                case THURSDAY -> thursdays.add(appointment);
                case FRIDAY -> fridays.add(appointment);
            }
        }
    }

    public JTextArea setPageTitle(String pageTitle){
        //manage title
        //TITLE_WIDTH = pagetitle.length() * TITLE_FONT.getSize(); funkar inte
        JTextArea title = new JTextArea(pageTitle.toUpperCase(Locale.ROOT), 1, 1);
        title.setFont(TITLE_FONT);
        title.setEditable(false);
        title.setBounds(FRAME_WIDTH/2-((pageTitle.length()*TITLE_FONT.getSize()/2)), //funkar dåligt
                        0, TITLE_WIDTH, TITLE_HEIGHT);
        title.setLineWrap(true);
        return title;
    }

    public JTextArea setBugFix(){
        //Is added last to make sure all JComponents are placed correctly
        JTextArea bugfix = new JTextArea(" ", 0, 0);
        bugfix.setEditable(false);
        return bugfix;
    }


    public String convertToString(List<ScheduledAppointment> appointments) {
        StringBuilder schedule = new StringBuilder();
        if (appointments == null || appointments.isEmpty()) {
            schedule = new StringBuilder("Inget schemalagt idag.");
        } else {
            for (ScheduledAppointment app : appointments) {
                schedule.append(app);
            }
        }
        return schedule.toString();
    }

    public JTextArea setStringContent(int framePos, String content){
        JTextArea panel = new JTextArea(1, 5);
        panel.append(content);
        panel.setFont(WEEKDAY_CONTENT_FONT);
        panel.setEditable(false);
        panel.setBounds(SIDE_MARGIN + (BREAD_WIDTH + BREAD_DISTANCE) * (framePos - 1),
                       2*TITLE_HEIGHT + HEIGHT_MARGIN, BREAD_WIDTH, BREAD_HEIGHT);
        panel.setLineWrap(true);
        panel.setForeground(Color.DARK_GRAY);
        panel.setBackground(Color.LIGHT_GRAY);
        return panel;
    }

}
