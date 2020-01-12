/* [Schedule.java]
 * The main class for the optimizer, also the frame for UI
 * Albert Quon, Kelvin Du, Garvin Hui, Gordon Tang
 * 2020/01/11
 */
//imports
//java.awt
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.*;
//javax.swing
/*  Prof name
    Room number
    Course number
    building name/abbreviation
    time
 */
// graphic import
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Schedule extends JFrame {

    // initialize J elements
    static JFrame window;

    public static void main(String[] args){
        System.out.println("Schedule");
        window = new Schedule();

    }

    Schedule() {
        super("CSO");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        this.setResizable(false);

        add(new ScheduleTabs());
        this.setVisible(true);
    }

    private class ScheduleTabs extends JTabbedPane {
        ScheduleTabs() {
            addTab("Course selection", new AddPanel());
            addTab("Calender", new ViewPanel());
        }
    }

    private class ViewPanel extends JPanel {
        JComboBox selectedSubject; // displays subjects on the calendar tab
        JComboBox selectedNumber; // displays number of course
        JComboBox selectedTime; // displays the time of class
        JComboBox selectedRoom; // displays the building and room of the class in the calendar tab

            ViewPanel(){
            selectedSubject = new JComboBox();
        }
    }

    private class AddPanel extends JPanel implements ActionListener {
        JComboBox courseSubject;
        JComboBox courseLevel;
        JComboBox courseNumber;
        JComboBox semesterOptions;
        JComboBox courseLevelOptions;
        JComboBox courseOptions;
        JTextField CRN;
        JButton addCourse;
        JButton clearbutton;

        AddPanel() {
            setLayout(null);
            // add a filein function next version to make semesters automatically updated
            String[] semesterString = {"-","winter2020", "summer2020"};
            String[] courseLevelStrings = {"All Levels", "Undergraduate", "Graduate"};
            String[] courseString = {"All Subjects", "ACCT", "AERO", "AFRI", "ASLA", "ANTH", "ALDS", "COMP", "MATH"};

            // drop down strings
            courseLevelOptions = new JComboBox(courseLevelStrings);
            semesterOptions = new JComboBox(semesterString);
            courseOptions = new JComboBox(courseString);

            // buttons
            addCourse = new JButton("Add Course");//this button adds course
            clearbutton = new JButton("Clear");//this button clears the calendar

            semesterOptions.addActionListener(this);
            courseOptions.addActionListener(this);
            addCourse.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    boolean add = true;
                    if (semesterOptions.getSelectedItem().equals("-"));
                        semesterOptions.setBorder(BorderFactory.createLineBorder(Color.RED));
                        add = false;
                }   else {
                    semesterOptions.setBorder(BorderFactory.createEmptyBorder());
            });
            clearbutton.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {

                }
            });
            }
        }

    }

    JScrollPane levelScrollPane;
    JScrollPane subjectScrollPane;
    JPanel schPanel;
    JButton courseLockInButton;
    JLabel selectionDescription;


    private class UIPanel extends JPanel {
        JScrollPane ioPanel;
    }
    private class staticPanel extends JPanel{

    }
    private class SchedulePanel extends JPanel {

    }
}
