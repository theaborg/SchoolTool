package se.liu.thebo717_petbjo980.schooltool.viewers;

import se.liu.thebo717_petbjo980.schooltool.School;
import se.liu.thebo717_petbjo980.schooltool.User;

import javax.swing.*;

/**
 * The ComboBox class for viewing UserLists. Inherits from the baseclass.
 * When a user is chosen from the ComboBox, its information is showed in a pop-up.
 * If the ComboBox is changeable, the current user can edit or delete the selected user
 * which is done with the methods in this class.
 */
public class UserComboBox extends ComboBoxComponent
{
    public UserComboBox(final School school, final User user, final String[] content,
			final int framePos, final JFrame frame, boolean changeable)
    {
	super(school, user, content, framePos, frame, changeable);
    }

    public void editName(User user, String info) {
	String newInfo = JOptionPane.showInputDialog(info + ": ", "Förnamn Efternamn");
	if(newInfo == null){
	    return;
	}
	if (checkIfValidName(newInfo)){
	    school.changeNameOfUser(user, divideBySpace(newInfo)[0], divideBySpace(newInfo)[1]);
	}
	else {
	    JOptionPane.showConfirmDialog(null,
					  "Förfrågan går inte att genomföra. Ange enligt format: " +
					  "\n" + "Förnamn Efternamn" + "\n",
					  "Fel format. ",
					  JOptionPane.OK_CANCEL_OPTION);
	}

    }

    public void editPassword(User user, String info) {
	//KOLLA OM LÖSEN OK
	String newInfo = JOptionPane.showInputDialog(info + ": ", "");
	if(newInfo != null && checkIfValidPassword(newInfo)){
	    school.changePasswordOfUser(user, newInfo);
	} else {
	    JOptionPane.showConfirmDialog(null, "Lösenord måste vara 6-16 tecken långt",
					  "Fel", JOptionPane.OK_CANCEL_OPTION);
	}
    }

    public void editUser(int action, User usr){
	String message = "Användarnamn: " + usr.getUsername()+ "\n" +
			 "Namn: " + usr.getName() + "\n" +
			 "Status: " + getUserStatus(usr) +"\n" +
			 "Vad vill du ändra? ";
	if (action == 1){
	    String[] choices = {"Namn", "Lösenord"};
	    int action2 = JOptionPane.showOptionDialog(null, message, usr.getUsername(),
						       JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
						       null, choices, choices[0]);
	    if(action2 == -1){
		return;
	    }
	    String info = choices[action2];
	    switch (action2){
		case 0:
		    editName(usr, info);
		    break;
		case 1:
		    editPassword(usr, info);
		    break;
	    }
	}
	else if (action == 2){
	    int unregister = JOptionPane.showConfirmDialog(null,
						     "Vill du ta bort " + usr.getUsername() + "?",
						     "Ta bort användare", JOptionPane.YES_NO_OPTION);
	    if (unregister == JOptionPane.YES_OPTION){
		if (school.unRegisterUser(usr)){
		    JOptionPane.showConfirmDialog(null, usr.getUsername() + " togs bort",
						  "Användare avregistrerad", JOptionPane.OK_CANCEL_OPTION);
		    AdminViewer adminViewer = new AdminViewer(school, user);
		    frame.dispose();
		    adminViewer.view();
		}
		else {
		    JOptionPane.showConfirmDialog(null, "Kunde inte avregistrera användare",
						  "Fel", JOptionPane.OK_CANCEL_OPTION);
		}
	    }
	}
	}

    @Override public void showContent(String s){
	User usr = school.getUserByUsername(s);
	if(usr == null){
	    JOptionPane.showConfirmDialog(null, "Användaren " + s + " finns inte",
					  "Fel", JOptionPane.OK_CANCEL_OPTION);
	    return;
	}
	String message = "Användarnamn: " + usr.getUsername()+ "\n" +
			 "Namn: " + usr.getName() + "\n" +
			 "Status: " + getUserStatus(usr) +"\n";
	if (!changeable){
	    JOptionPane.showConfirmDialog(null, message, s, JOptionPane.DEFAULT_OPTION);
	}
	else {
	    int action = JOptionPane.showOptionDialog(null, message, s,
						      JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
						      null, choices, choices[0]);
	    if (action!=0){
		editUser(action, usr);
	    }

	}
    }
}

