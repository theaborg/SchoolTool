package se.liu.thebo717_petbjo980.schooltool;

import se.liu.thebo717_petbjo980.schooltool.calendar.ScheduledAppointment;
import se.liu.thebo717_petbjo980.schooltool.calendar.TimePoint;
import se.liu.thebo717_petbjo980.schooltool.calendar.TimeSpan;
import se.liu.thebo717_petbjo980.schooltool.calendar.Weekday;
import se.liu.thebo717_petbjo980.schooltool.security.EncryptionManager;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Saves and loads Users, Courses, Studentgroups and school data using
 * custom file formats. Files are stored under resources/SCHOOLNAME.
 * <p>
 * Entire School instance is loaded using loadSchool
 */
public class SaveManager
{
    private String separator = File.separator;
    private EncryptionManager manager = new EncryptionManager();

    public boolean schoolExists(String name){
	File file = new File("resources" + separator + name);
	return file.exists();
    }

    private String intToTwoDigit(int i){
	/*
	Här nedan får vi en varning som inte stämmer, vi vill inte ha det mellanslag som föreslås.
	Dessutom får vi "magic number"-varning på if-satsen nedan. 9 används helt enkelt för att det är det
	största ensiffriga talet, därför inför vi inte en variabel. Samma resonemang för 99.
	 */
	if(i <= 9){
	    return "0" + i;
	} else if(i <= 99){
	    return Integer.toString(i);
	} else {
	    return "99";
	}
    }

    private String getStringFromWeekday(Weekday weekday){
	Map<Weekday, String> weekdayStringMap = Map.of(Weekday.MONDAY, "MON", Weekday.TUESDAY, "TUES",
						       Weekday.WEDNESDAY, "WED", Weekday.THURSDAY, "THU",
						       Weekday.FRIDAY, "FRI", Weekday.SATURDAY, "SAT",
						       Weekday.SUNDAY, "SUN");
	if (weekday == null){
	    return "MON";
	}
	return weekdayStringMap.get(weekday);
    }

    private Weekday getWeekdayFromString(String str){
	Map<String, Weekday> weekdayStringMap = Map.of("MON", Weekday.MONDAY, "TUES", Weekday.TUESDAY,
						       "WED", Weekday.WEDNESDAY, "THU", Weekday.THURSDAY,
						       "FRI", Weekday.FRIDAY, "SAT", Weekday.SATURDAY,
						       "SUN", Weekday.SUNDAY);
	if (str == null){
	    return Weekday.MONDAY;
	}
	return weekdayStringMap.get(str);
    }

    private void saveToFile(String content, String path){
	if(tryMakeDirectory(path)){
	    try (PrintWriter file = new PrintWriter(path)) {
		file.write(content);
	    }
	    catch (IOException e) {
		e.printStackTrace();
		//JOptionPane så användaren ser tydligt
		JOptionPane.showConfirmDialog(null, "Failed to save data");
	    }
	} else {
	    //FELHANTERING
	    System.out.println("Directory" + path + "couldn't be created");
	}
    }

    private void deleteFile(String path){
	File file = new File(path);
	if(file.exists()){
	    if(!file.delete()){
		System.out.println("Couldn't delete file, trying delete on exit");
		file.deleteOnExit();
	    }
	} else {
	    System.out.println("File missing"); //FELHANTERING
	}
    }

    private List<String> getStringsFromFilename(String filename){
	List<String> lines = new ArrayList<>();
	File file = new File(filename);
	if(!file.exists()){
	    return lines;
	}
	try{
	    BufferedReader reader = new BufferedReader(new FileReader(filename));
	    String line;
	    while ((line = reader.readLine()) != null){
		lines.add(line);
	    }
	    reader.close();
	} catch (IOException e){
	    e.printStackTrace();
	    //JOptionPane så användaren ser tydligt
	    JOptionPane.showConfirmDialog(null, "Failed to load data");
	}
	return lines;
    }

    private boolean tryMakeDirectory(String path){
	String dirPath = new File(path).getParent();
	File dir = new File(dirPath);
	//System.out.println(dir.getPath());
	if(!dir.exists()){
	    return dir.mkdirs(); // true if directory is created
	}
	return true; // True if there is already a directory
    }

    public School loadSchool(String name){
	String s = "resources" + separator + name + separator;
	School school = new School(name);
	File userDirectory = new File(s + "Users" + separator);
	File courseDirectory = new File(s + "Courses" + separator);
	File groupDirectory = new File(s + "Groups" + separator);

	if(userDirectory.exists()){
	    String userEnd = ".usr";
	    //enligt kodanalysen kan nullpointerexception uppstå, men det get varning i iDEA pga "prohibited exception"
	    for(File file : userDirectory.listFiles()){
		String fileName = file.getName();
		if(fileName.endsWith(userEnd)){
		    loadUser(fileName.substring(0, fileName.length() - userEnd.length()), school);
		}
	    }
	}
	if(groupDirectory.exists()){
	    String groupEnd = ".grp";
	    //enligt kodanalysen kan nullpointerexception uppstå, men det get varning i iDEA pga "prohibited exception"

	    for(File file : groupDirectory.listFiles()){
		String fileName = file.getName();
		if(fileName.endsWith(groupEnd)){
		    loadStudentGroup(fileName.substring(0, fileName.length() - groupEnd.length()), school);
		}
	    }
	}
	if(courseDirectory.exists()){
	    String courseEnd = ".crs";
	    //enligt kodanalysen kan nullpointerexception uppstå, men det get varning i iDEA pga "prohibited exception"
	    for(File file : courseDirectory.listFiles()){
		String fileName = file.getName();
		if(fileName.endsWith(courseEnd)){
		    loadCourse(fileName.substring(0, fileName.length() - courseEnd.length()), school);
		}
	    }
	}

	loadSchoolInfo(school);

	return school;
    }

    public void saveCourse(Course course, School school){
	StringBuilder builder = new StringBuilder();
	builder.append(course.getCode()).append("\n").append(course.getTitle()).append("\n");
	builder.append(course.getPoints()).append("\n").append(course.getMaximumAttendants()).append("\n");
	//Vi får allvarlig varning pga vi använt backslash, men det hör till vårat sparfilsformat.
	if(!course.getStudents().isEmpty()){
	    builder.append("[STUDENTS]\n");
	    for(User user : course.getStudents().getUsers()){
		builder.append(user.getUsername()).append("\n");
	    }
	    builder.append("[/STUDENTS]\n");
	}
	//Vi får allvarlig varning pga vi använt backslash, men det hör till vårat sparfilsformat.
	if(!course.getTeachers().isEmpty()){
	    builder.append("[TEACHERS]\n");
	    for(User user : course.getTeachers().getUsers()){
		builder.append(user.getUsername()).append("\n");
	    }
	    builder.append("[/TEACHERS]\n");
	}
	//Vi får allvarlig varning pga vi använt backslash, men det hör till vårat sparfilsformat.
	if(!course.getStudentGroups().isEmpty()){
	    builder.append("[GROUPS]\n");
	    for(StudentGroup group : course.getStudentGroups().getStudentGroups()){
		builder.append(group.getName()).append("\n");
	    }
	    builder.append("[/GROUPS]\n");
	}
	//Vi får allvarlig varning pga vi använt backslash, men det hör till vårat sparfilsformat.
	if(!course.getScheduledAppointments().isEmpty()){
	    builder.append("[SCHEDULE]\n");
	    for(ScheduledAppointment appointment : course.getScheduledAppointments()){
		TimeSpan span = appointment.getTimeSpan();
		builder.append(getStringFromWeekday(appointment.getWeekday()));
		builder.append(intToTwoDigit(span.getStart().getHour()));
		builder.append(intToTwoDigit(span.getStart().getMinute()));
		builder.append(intToTwoDigit(span.getEnd().getHour()));
		builder.append(intToTwoDigit(span.getEnd().getMinute()));
		builder.append(appointment.getDescription()).append("\n");
	    }
	    builder.append("[/SCHEDULE]\n");
	}

	String out = builder.toString();
	String path = "resources" + separator + school.getName() + separator + "Courses" + separator + course.getCode() + ".crs";
	saveToFile(out, path);
    }

    public void deleteCourse(String courseCode, School school){
	String path = "resources" + separator + school.getName() + separator + "Courses" + separator + courseCode + ".crs";
	deleteFile(path);
    }

    public void loadCourse(String courseCode, School school){
	List<String> lines = getStringsFromFilename("resources" + separator + school.getName() +
						    separator + "Courses" + separator + courseCode + ".crs");
	//vi anser att vi inte behöver en variabel nedan, trots "magic number"-varning,
	//då vi läser in en rad efter en annan.
	String code = lines.get(0);
	String title = lines.get(1);
	int points = Integer.parseInt(lines.get(2));
	int maxAttendants = Integer.parseInt(lines.get(3));
	Course newCourse = new Course(code, title, points, maxAttendants);
	String tag = "";
	int weekNameLength = 3;
	int timeLength = 2;
	for(String line : lines){
	    if(line.startsWith("[")){
		tag = line;
	    } else if(tag.equals("[STUDENTS]")){
		Student student = school.getStudentByUsername(line);
		if(student != null){
		    newCourse.registerStudent(student);
		}
	    } else if(tag.equals("[TEACHERS]")){
		Employee employee = school.getEmployeeByUsername(line);
		if(employee != null){
		    newCourse.registerTeacher(employee);
		}
	    } else if(tag.equals("[GROUPS]")){
		StudentGroup group = school.getStudentGroupByName(line);
		if(group != null){
		    newCourse.registerStudentGroup(group);
		}
	    } else if(tag.equals("[SCHEDULE]")){
		int index = weekNameLength;
		Weekday weekday = getWeekdayFromString(line.substring(0, index));
		int startHour = Integer.parseInt(line.substring(index, index + timeLength));
		index += timeLength;
		int startMin = Integer.parseInt(line.substring(index,index + timeLength));
		index += timeLength;
		int endHour = Integer.parseInt(line.substring(index,index+timeLength));
		index += timeLength;
		int endMin = Integer.parseInt(line.substring(index,index+timeLength));
		index += timeLength;
		TimeSpan span = new TimeSpan(new TimePoint(startHour, startMin), new TimePoint(endHour, endMin));
		String description = line.substring(index);
		newCourse.addScheduleAppointment(weekday, span, description);
	    }
	}
	school.registerCourse(newCourse);
    }

    public void saveStudentGroup(StudentGroup group, School school){
	StringBuilder builder = new StringBuilder();
	builder.append(group.getName()).append("\n").append(group.getGrade()).append("\n");

	if(!group.getStudents().isEmpty()){
	    builder.append("[STUDENTS]\n");
	    for(User user : group.getStudents().getUsers()){
		builder.append(user.getUsername()).append("\n");
	    }
	    builder.append("[/STUDENTS]\n");
	}

	String out = builder.toString();
	String path = "resources" + separator + school.getName() + separator + "Groups" + separator + group.getName() + ".grp";
	saveToFile(out, path);
    }

    public void deleteStudentGroup(String groupName, School school){
	String path = "resources" + separator + school.getName() + separator + "Groups" + separator + groupName + ".grp";
	deleteFile(path);
    }

    public void loadStudentGroup(String groupName, School school){
	List<String> lines = getStringsFromFilename("resources" + separator + school.getName() + separator + "Groups" + separator + groupName + ".grp");
	String name = lines.get(0);
	int grade = Integer.parseInt(lines.get(1));

	StudentGroup newGroup = new StudentGroup(name, grade);

	String tag = "";
	for(String line : lines){
	    if(line.startsWith("[")){
		tag = line;
	    } else if(tag.equals("[STUDENTS]")){
		Student student = school.getStudentByUsername(line);
		if(student != null){
		    newGroup.registerStudent(student);
		}
	    }
	}
	school.registerStudentGroup(newGroup);
    }

    public void saveUser(User user, School school){
	String out = user.getUsername() + "\n" + manager.encryptString(user.getPassword()) +
		     "\n" + user.getFirstName() + "\n" + user.getLastName() + "\n" + user.getStatusInFileFormat();
	String path = "resources" + separator + school.getName() + separator + "Users" + separator + user.getUsername() + ".usr";
	saveToFile(out, path);
    }

    public void deleteUser(String username, School school){
	String path = "resources" + separator + school.getName() + separator + "Users" + separator + username + ".usr";
	deleteFile(path);
    }

    public void loadUser(String userName, School school){
	List<String> lines = getStringsFromFilename("resources" + separator + school.getName()
						    + separator + "Users" + separator + userName + ".usr");
	int dataAmount = 5;
	if(lines.size() < dataAmount){
	    return;
	}
	//nedan hämtar vi en rad efter en annan, därför inför vi ingen variabel
	String tempUserName = lines.get(0);
	String tempPassword = manager.decryptString(lines.get(1));
	String firstName = lines.get(2);
	String lastName = lines.get(3);
	String type = lines.get(4);

	boolean admin = false;
	if(type.equals("A")){
	    admin = true;
	}

	if(type.equals("S")){
	    school.registerStudent(tempUserName, tempPassword, firstName, lastName);
	} else if (type.equals("E") || type.equals("A")) {
	    school.registerEmployee(tempUserName, tempPassword, firstName, lastName, admin);
	} else{ // Felhantering
	    System.out.println("Undefined user type");
	}
    }

    public void saveSchoolInfo(School school){
	StringBuilder builder = new StringBuilder();
	builder.append(school.getNews()).append("\n");
	if(school.getHeadMaster() != null){
	    builder.append("[HM]").append(school.getHeadMaster().getUsername()).append("\n");
	}
	String out = builder.toString();
	String path = "resources" + separator + school.getName() + separator + "Schooldata";
	saveToFile(out, path);
    }

    public void loadSchoolInfo(School school){
	List<String> lines = getStringsFromFilename("resources" + separator + school.getName() + separator + "Schooldata");
	if(lines.isEmpty()) { //FELHANTERING
	    System.out.println("School data does not exist");
	    return;
	}
	school.setNews(lines.get(0));
	for (String line : lines){
	    String headMasterTrace = "[HM]";
	    if(line.startsWith(headMasterTrace)){
		Employee employee = school.getEmployeeByUsername(line.substring(headMasterTrace.length()));
		if (employee != null){
		    school.setHeadMaster(employee);
		}
	    }
	}
    }
}
