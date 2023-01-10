package se.liu.thebo717_petbjo980.schooltool.viewers;

import se.liu.thebo717_petbjo980.schooltool.Course;
import se.liu.thebo717_petbjo980.schooltool.School;
import se.liu.thebo717_petbjo980.schooltool.Student;
import se.liu.thebo717_petbjo980.schooltool.User;
import se.liu.thebo717_petbjo980.schooltool.UserType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A ComboBox baseclass, which contains the constants needed to position
 * this component correctly in the frame. It also implements an actionlistener, which allows
 * for the user to see data about the object chosen in the combobox.
 * This class contains methods for collecting said data from the chosen school or user.
 */
public class ComboBoxComponent extends JComponent implements ActionListener
{
    protected User user;
    protected JComboBox<String> comboBox;
    protected School school;
    protected String[] choices = { "Ok", "Ändra", "Ta bort" };
    protected JFrame frame;
    /*changeable is currently only used by one subclass, but may be useful
    when developing this program further. Therefore, changeable is left in the baseclass. */
    protected boolean changeable;
    //constants
    static protected final int PANEL_AMOUNT = 5;
    static protected final int FRAME_WIDTH = 1400;
    static protected final int TITLE_HEIGHT = 75;
    static protected final int HEIGHT_MARGIN = 10;
    static protected final int SIDE_MARGIN = FRAME_WIDTH / 20;
    static protected final int BREAD_HEIGHT = 50;
    static protected final int BREAD_DISTANCE = 20;
    static protected final int BREAD_WIDTH = (FRAME_WIDTH - 2 * SIDE_MARGIN - 4 * BREAD_DISTANCE) / PANEL_AMOUNT;
    protected Font comboFont = new Font("Combo", Font.ITALIC, 13);

    public ComboBoxComponent(final School school, User user, final String[] content,
                             int framePos, JFrame frame, boolean changeable) {
        this.school = school;
        this.user = user;
        this.changeable = changeable;
        this.frame = frame;
        comboBox = new JComboBox<>(content);
        comboBox.setFont(comboFont);
        comboBox.setEditable(false);
        comboBox.setBounds(SIDE_MARGIN + (BREAD_WIDTH + BREAD_DISTANCE) * (framePos - 1),
                           HEIGHT_MARGIN + 2 * TITLE_HEIGHT, BREAD_WIDTH, BREAD_HEIGHT);
        comboBox.setForeground(Color.DARK_GRAY);
        comboBox.setBackground(Color.LIGHT_GRAY);
        comboBox.addActionListener(this);
    }


    public JComboBox<String> getComboBox() {
        return comboBox;
    }

    public String getTeachersNames(Course course){
        StringBuilder teachers = new StringBuilder();
        for (int i = 0; i < course.getTeachers().getSize(); i++) {
            if (i>0){
                teachers.append(", ");
            }
            teachers.append(course.getTeacherName(i));
        }
        return teachers.toString();
    }


    public Student getStudent(){
        String name = JOptionPane.showInputDialog("Ange namn av elev");
        Student out = school.getStudentByUsername(name);
        if(out == null){
            JOptionPane.showConfirmDialog(null, name + " är inte registrerad",
                                          "Fel", JOptionPane.OK_CANCEL_OPTION);
        }
        return out;
    }

    public boolean checkIfValidName(String s) {
        int nameLength = 25;
        if (s == null || s.trim().isEmpty() || s.length() > nameLength) {
            return false;
        }
        String[] condition = {"false"};
        boolean condition2 = Arrays.equals(divideBySpace(s), condition);
        if (!condition2){
            Pattern p = Pattern.compile("[^A-Za-z]" + "åäöÅÄÖ");
            Matcher m1 = p.matcher(divideBySpace(s)[0]);
            Matcher m2 = p.matcher(divideBySpace(s)[1]);
            boolean b1 = m1.find();
            boolean b2 = m2.find();
            if (b1 || b2){
                return false;
            }
            return true;
        }
        return false;
    }

    public boolean checkIfValidPassword(String s){
        int passwordLength = 16;
        if(s.length() < 6 || s.length() > passwordLength){
            return false;
        }
        return true;
    }

    public String[] divideBySpace(String s){
        int index = s.indexOf(' ');
        String[] words;
        if (index == -1){
            words = new String[] { "false" };
        }
        else {
            words = new String[] { s.substring(0, index), s.substring(index + 1) };
        }
        return words;
    }

    public String getUserStatus(User currentUser){
        Map<UserType, String> types = Map.of(UserType.STUDENT, "student", UserType.EMPLOYEE, "employee",
                                             UserType.UNDEFINED, "ej angivet");
        return types.get(currentUser.getType());
    }

    public void showContent(String s){
    }

    @Override public void actionPerformed(final ActionEvent e) {
        //har kvar varning pga alla comboboxes kommer ha String-innehåll
        JComboBox<String> cb = (JComboBox<String>) e.getSource();
        String data = (String) cb.getSelectedItem();
        showContent(data);
    }
}
