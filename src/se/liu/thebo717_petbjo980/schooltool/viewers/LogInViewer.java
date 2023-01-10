package se.liu.thebo717_petbjo980.schooltool.viewers;

import se.liu.thebo717_petbjo980.schooltool.SaveManager;
import se.liu.thebo717_petbjo980.schooltool.School;
import se.liu.thebo717_petbjo980.schooltool.User;
import se.liu.thebo717_petbjo980.schooltool.UserList;

import javax.swing.*;

/**
 * Lets the user log in. First, an existing school is selected. If the entered
 * schoolname does not exist, the user is prompted to register a new school.
 * If the user chooses to do so, the new school recieves some default-attributes, to
 * enable editing.
 * If the school exists, and the entered username and password are correct, a homeviewer is created,
 * and its view-method is called.
 */
public class LogInViewer extends JComponent
{
    private SaveManager saveManager = new SaveManager();

    public LogInViewer() {
    }

    public void showLogIn(){
        String schoolname = JOptionPane.showInputDialog("Ange skola:",
                                                        "");
        if(schoolname == null){
            System.exit(0);
        }
        School school;
        if(saveManager.schoolExists(schoolname)){
            school = saveManager.loadSchool(schoolname);
        } else {
            Object[] choices = {"Ja", "Nej"};
            int action = JOptionPane.showOptionDialog(null, "Skolan " + schoolname + " finns inte, skapa den?",
                                                      "Ny skola", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                                                      null, choices, choices[1]);
            if(action != 0){
                return;
            }
            school = new School(schoolname);
            school.registerEmployee("admin", "admin", "Firstname", "Lastname", true);
            school.changeNews("Nyheter");
            JOptionPane.showConfirmDialog(null, "Inloggning \nAnvändarnamn: admin\nLösenord: admin",
                                          "Skola skapad", JOptionPane.OK_CANCEL_OPTION);
        }
        String name = JOptionPane.showInputDialog("Ange användarnamn:",
                                                  "");
        if(name == null){
            System.exit(0);
        }
        else if (school.getUserByUsername(name) != null)
        {
            User user = school.getUserByUsername(name);
            String password = JOptionPane.showInputDialog("Ange lösenord:", "");
            if (password == null){
                System.exit(0);
            }
            else if (user.getPassword().equals(password)){
                HomeViewer homeViewer = new HomeViewer(school, user);
                homeViewer.view();
            } else{
                tryAgainPrompt();
            }
        }
        else {
            tryAgainPrompt();
        }

    }

    public void tryAgainPrompt(){
        int exit = JOptionPane.showConfirmDialog(null,
                                                 "Fel användarnamn eller lösenord. Testa igen?",
                                                 "Misslyckades", JOptionPane.OK_CANCEL_OPTION);
        if(exit==JOptionPane.CANCEL_OPTION){
            System.exit(0);
        }
        else if (exit==JOptionPane.OK_OPTION){
            showLogIn();
        }
    }
}

