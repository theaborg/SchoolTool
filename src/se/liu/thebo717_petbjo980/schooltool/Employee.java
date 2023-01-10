package se.liu.thebo717_petbjo980.schooltool;

/**
 * Employee class which derives from User.
 * Can have administrator status.
 * <p>
 * The courses CourseList from User is used for Courses
 * which the Employee is a teacher of.
 */
public class Employee extends User
{
    private boolean adminStatus;
    public Employee(final String username, final String password, final String firstName,
		    final String lastName, final boolean isAdmin) {
	super(username, password, firstName, lastName);
	this.adminStatus = isAdmin;
	setType(UserType.EMPLOYEE);
    }

    public boolean getAdminStatus() {
	return adminStatus;
    }

    @Override public String getStatusInFileFormat(){
	if (adminStatus) {
	    return "A\n";
	}
	return "E\n";
    }

    @Override public UserType getType() {
	return UserType.EMPLOYEE;
    }
}
