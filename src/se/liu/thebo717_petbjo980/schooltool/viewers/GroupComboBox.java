package se.liu.thebo717_petbjo980.schooltool.viewers;

import se.liu.thebo717_petbjo980.schooltool.School;
import se.liu.thebo717_petbjo980.schooltool.Student;
import se.liu.thebo717_petbjo980.schooltool.StudentGroup;
import se.liu.thebo717_petbjo980.schooltool.User;

import javax.swing.*;

/**
 * The ComboBox class for viewing UserLists. Inherits from the baseclass.
 * When a UserList is chosen from the ComboBox, its information is showed in a pop-up.
 * If the ComboBox is changeable, the current user can edit or delete the selected UserList,
 * which is done with the methods in this class.
 */
public class GroupComboBox extends ComboBoxComponent
{
    public GroupComboBox(final School school, final User user, final String[] content, final int framePos,
			 final JFrame frame, boolean changeable)
    {
	super(school, user, content, framePos, frame, changeable);
    }

    public void removeGroup(StudentGroup group){
	int unregister = JOptionPane.showConfirmDialog(null,
						       "Vill du ta bort " + group.getName() + "?",
						       "Ta bort klass", JOptionPane.YES_NO_OPTION);
	if (unregister == JOptionPane.YES_OPTION){
	    if (school.unRegisterStudentGroup(group)){
		JOptionPane.showConfirmDialog(null, group.getName() + " togs bort",
					      "Klass avregistrerad", JOptionPane.OK_CANCEL_OPTION);
		AdminViewer adminViewer = new AdminViewer(school, user);
		frame.dispose();
		adminViewer.view();
	    } else {
		JOptionPane.showConfirmDialog(null, "Kunde inte avregistrera klass",
					      "Fel", JOptionPane.OK_CANCEL_OPTION);
	    }
	}
    }


    public void editGroup(int action, StudentGroup group) {
	String message = "Klass: " + group.getName() + "\n" + "Vad vill du ändra? ";
	if (action == 1) {
	    String[] choices = { "Reg. Elev", "Avreg. Elev" };
	    int action2 = JOptionPane.showOptionDialog(null, message, group.getName(),
						       JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
						       null, choices, choices[0]);
	    if(action2 == 0){
		Student student = getStudent();
		if(student != null){
		    school.registerStudentToStudentGroup(student, group);
		}
	    } else if(action2 == 1){
		Student student = getStudent();
		if(student != null){
		    school.unRegisterStudentFromStudentGroup(student, group);

		}
	    }

	}
	else if(action == 2){
	    removeGroup(group);
	}
    }

    @Override public void showContent(String s){
	StudentGroup studentGroup = school.getStudentGroupByName(s);
	if(studentGroup == null){
	    JOptionPane.showConfirmDialog(null, "Klassen " + s + " finns inte",
					  "Fel", JOptionPane.OK_CANCEL_OPTION);
	    return;
	}
	String message = "Klass:  " + s + "\n";
	if (!changeable){
	    JOptionPane.showConfirmDialog(null, message, s, JOptionPane.DEFAULT_OPTION);
	}
	else {
	    int action = JOptionPane.showOptionDialog(null, message, s,
						      JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
						      null, choices, choices[0]);
	    if(action != 0){
		editGroup(action, studentGroup);
	    }

	}
    }
}
