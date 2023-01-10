package se.liu.thebo717_petbjo980.schooltool.viewers;

import se.liu.thebo717_petbjo980.schooltool.Course;
import se.liu.thebo717_petbjo980.schooltool.Employee;
import se.liu.thebo717_petbjo980.schooltool.School;
import se.liu.thebo717_petbjo980.schooltool.Student;
import se.liu.thebo717_petbjo980.schooltool.StudentGroup;
import se.liu.thebo717_petbjo980.schooltool.UserType;
import se.liu.thebo717_petbjo980.schooltool.User;
import se.liu.thebo717_petbjo980.schooltool.calendar.ScheduledAppointment;
import se.liu.thebo717_petbjo980.schooltool.calendar.TimePoint;
import se.liu.thebo717_petbjo980.schooltool.calendar.TimeSpan;
import se.liu.thebo717_petbjo980.schooltool.calendar.Weekday;

import javax.swing.*;

/**
 * A ComboBox for courses which inherits from the ComboBoxComponent baseclass.
 * When a course is selected, information about it is viewed. If the boolean changeable is true, then
 * the user may select "Ändra" or "Ta bort", whereupon editCourse is called. A new pop up then shows what
 * attributes can be changed. The changes are made by methods in this class.
 */
public class CourseComboBox extends ComboBoxComponent
{
    public CourseComboBox(final School school, final User user, final String[] content,
			  final int framePos, final JFrame frame, boolean changeable)
    {
	super(school, user, content, framePos, frame, changeable);
    }

    private Employee getEmployee(){
	String username = JOptionPane.showInputDialog("Ange den anställdes namn: ");
	Employee usr = school.getEmployeeByUsername(username);
	if(usr == null){
	    JOptionPane.showConfirmDialog(null, username + " är inte anställd",
					  "Fel", JOptionPane.OK_CANCEL_OPTION);
	}
	return usr;
    }

    private StudentGroup getStudentGroup(){
	String name = JOptionPane.showInputDialog("Ange klassens namn: ");
	StudentGroup grp = school.getStudentGroupByName(name);
	if(grp == null){
	    JOptionPane.showConfirmDialog(null, name + " är inte en klass",
					  "Fel", JOptionPane.OK_CANCEL_OPTION);
	}
	return grp;
    }

    private int parseInt(String s){
	try{
	    return Integer.parseInt(s);
	} catch (NumberFormatException ignored){
	    return -1;
	}
    }

    private TimePoint parseTime(String s){
	if(s.length() != 5){
	    JOptionPane.showMessageDialog(null, "Tidpunkt ej korrekt formatterad");
	    return null;
	}
	int hour = parseInt(s.substring(0, 2));
	int minute = parseInt(s.substring(3,5));
	if(hour < 0 || hour > 23 || minute < 0 || minute > 59){
	    JOptionPane.showMessageDialog(null, "Tidpunkt ej korrekt formatterad");
	    return null;
	}
	return new TimePoint(hour, minute);
    }

    private Weekday parseWeekday(String s){
	switch (s.toUpperCase()){
	    case "MÅN":
		return Weekday.MONDAY;
	    case "TIS":
		return Weekday.TUESDAY;
	    case "ONS":
		return Weekday.WEDNESDAY;
	    case "TOR":
		return Weekday.THURSDAY;
	    case "FRE":
		return Weekday.FRIDAY;
	}
	return null;
    }

    private void bookInSchedule(Course crs){
	Weekday weekday = parseWeekday(JOptionPane.showInputDialog("Dag: (MÅN/TIS/ONS/TOR/FRE)", ""));
	if(weekday == null){
	    JOptionPane.showMessageDialog(null, "Felformatterad veckodag");
	    return;
	}
	TimePoint start = parseTime(JOptionPane.showInputDialog("Starttid: (HH:MM)", ""));
	if(start == null){
	    return;
	}
	TimePoint end = parseTime(JOptionPane.showInputDialog("Sluttid: (HH:MM)", ""));
	if(end == null){
	    return;
	}
	String desc = JOptionPane.showInputDialog("Beskrivning: ", "");
	if(desc.contains("[")){
	    JOptionPane.showMessageDialog(null, "Invalid title");
	    return;
	}
	TimeSpan span = new TimeSpan(start, end);
	System.out.println(span.getEnd());

	school.addAppointment(new ScheduledAppointment(crs, span, weekday, desc));
    }

    private void unbookFromSchedule(Course crs){
	Weekday weekday = parseWeekday(JOptionPane.showInputDialog("Dag: (MÅN/TIS/ONS/TOR/FRE)", ""));
	if(weekday == null){
	    return;
	}
	TimePoint time = parseTime(JOptionPane.showInputDialog("Starttid: (HH:MM)", ""));
	if(time == null){
	    return;
	}
	school.removeAppointmentFromCourseAt(weekday, time, crs);
    }

    private void editCourse(int action, Course crs){
	if(action == 1){
	    String[] choices = {"Reg. Student", "Avreg. Student", "Reg. Lärare", "Avreg. Lärare", "Reg. Klass", "Avreg. Klass",
		    		"Boka i schema", "Avboka i schema"};
	    String message = "Redigerar  " + crs.getCode();
	    int action2 = JOptionPane.showOptionDialog(null, message, crs.getCode(),
						       JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
						       null, choices, choices[0]);
	    if(action2 == -1){
		return;
	    }
	    Student stu;
	    Employee emp;
	    StudentGroup grp;
	    switch (action2){
		case 0:
		    stu = getStudent();
		    if(stu != null){
			school.registerStudentToCourse(stu, crs);
		    }
		    break;
		case 1:
		    stu = getStudent();
		    if(stu != null){
			school.unRegisterStudentFromCourse(stu, crs);
		    }
		    break;
		case 2:
		    emp = getEmployee();
		    if(emp != null){
			school.registerTeacherToCourse(emp, crs);
		    }
		    break;
		case 3:
		    emp = getEmployee();
		    if(emp != null){
			school.unRegisterTeacherFromCourse(emp, crs);
		    }
		    break;
		case 4:
		    grp = getStudentGroup();
		    if(grp != null){
			school.registerStudentGroupToCourse(grp, crs);
		    }
		    break;
		case 5:
		    grp = getStudentGroup();
		    if(grp != null){
			school.unRegisterStudentGroupFromCourse(grp, crs);
		    }
		    break;
		case 6:
		    bookInSchedule(crs);
		    break;
		case 7:
		    unbookFromSchedule(crs);
		    break;
	    }
	}
	else if (action == 2){
		removeCourse(crs);
	}
    }

    private void removeCourse(Course crs){
	int unregister = JOptionPane.showConfirmDialog(null,
						       "Vill du ta bort " + crs.getCode() + "?",
						       "Ta bort kurs", JOptionPane.YES_NO_OPTION);
	if (unregister == JOptionPane.YES_OPTION){
	    if (school.unRegisterCourse(crs)){
		JOptionPane.showConfirmDialog(null, crs.getCode() + " togs bort",
					      "Kurs avregistrerad", JOptionPane.OK_CANCEL_OPTION);
		AdminViewer adminViewer = new AdminViewer(school, user);
		frame.dispose();
		adminViewer.view();
	    }
	    else {
		JOptionPane.showConfirmDialog(null, "Kunde inte avregistrera kurs",
					      "Fel", JOptionPane.OK_CANCEL_OPTION);
	    }
	}
    }

    @Override public void showContent(String s){
	Course course = school.getCourseByCode(s);
	if(course == null){
	    JOptionPane.showConfirmDialog(null, "Kursen " + s + " finns inte",
					  "Fel", JOptionPane.OK_CANCEL_OPTION);
	    return;
	}
	String teachers = getTeachersNames(course);
	String message = "Kurskod: " + s + "\n" +
			 "Kurstitel: " + course.getTitle() + "\n" +
			 "Examinator: " + teachers + "\n" +
			 "Poäng: " + course.getPoints() + "\n";
	if (!changeable){
	    if(user.getType().equals(UserType.STUDENT)){
		Student student = (Student) user;
		message += "Betyg: " + student.getGrade(course) + "\n";
	    }
	    JOptionPane.showConfirmDialog(null, message, s,
					  JOptionPane.DEFAULT_OPTION);
	}
	else {
	    int action = JOptionPane.showOptionDialog(null, message, s,
						      JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
						      null, choices, choices[0]);

	    if(action != 0){
		editCourse(action, course);
	    }
	}
    }
}
