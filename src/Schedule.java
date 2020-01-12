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
import java.util.Arrays;

public class Schedule extends JFrame {

    // initialize J elements
    static JFrame window;

    public static void main(String[] args) {
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

        ViewPanel() {
            selectedSubject = new JComboBox();
            selectedNumber = new JComboBox();
            selectedTime = new JComboBox();
            selectedRoom = new JComboBox();
        }
    }

    private class AddPanel extends JPanel {
        JComboBox courseSubject;
        JComboBox courseLevel;
        JTextField courseNumber;
        JComboBox semesterOptions;
        JComboBox courseLevelOptions;
        JComboBox courseOptions;
        JTextField CRN;
        JButton addCourse;
        JButton clearButton;

        JScrollPane levelScrollPane;
        JScrollPane subjectScrollPane;
        JPanel schPanel;
        JButton courseLockInButton;
        JLabel selectionDescription;
        JList levelList;

        AddPanel() {
            setLayout(null);
            // add a filein function next version to make semesters automatically updated
            String[] semesterString = {"-", "winter2020", "summer2020"};
            String[] courseLevelStrings = {"All Levels", "Undergraduate", "Graduate"};
            String[] courseString = {"All Subjects", "ACCT", "AERO", "AFRI", "ASLA", "ANTH", "ALDS", "COMP", "MATH"};

            // drop down strings
            courseLevelOptions = new JComboBox(courseLevelStrings);
            semesterOptions = new JComboBox(semesterString);
            courseOptions = new JComboBox(courseString);

            // buttons
            addCourse = new JButton("Add Course");//this button adds course
            clearButton = new JButton("Clear");//this button clears the calendar

            // user input
            courseNumber = new JTextField();
            CRN = new JTextField();

            semesterOptions.setBounds(10,60,55,25);
            // add the boxes for JTextFields
            add(CRN);
            CRN.setBorder(BorderFactory.createEmptyBorder());
            CRN.setBounds(10, 285, 85, 25);

            add(courseNumber);
            courseNumber.setBorder(BorderFactory.createEmptyBorder());
            courseNumber.setBounds(10, 220, 85, 25);

            levelList = new JList(courseLevelStrings);
            levelScrollPane = new JScrollPane();
            levelScrollPane.setViewportView(levelList);
            levelList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            levelList.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    super.mousePressed(e);
                    if (!levelList.isSelectionEmpty()) {
                        levelList.clearSelection();//allows only 1 level to be selected at a given time
                    }
                }
            });
            levelList.setFont(new Font("monospaced", Font.PLAIN, 12));
            levelScrollPane.setViewportView(levelList);//setting scrollpane to display list
            levelScrollPane.setBounds(10, 30, 600, 130);
        }

        private class UIPanel extends JPanel {

        }

        private class staticPanel extends JPanel {

        }

        private class SchedulePanel extends JPanel {

        }
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            this.setDoubleBuffered(true);
            repaint();
            g.setFont(new Font("Arial", Font.BOLD, 16));
            g.drawString("Course Level:", 10 ,25 );
            g.drawString("Subject", 10, 115);
            g.drawString("Course Number", 10, 210);
            g.drawString("CRN", 10, 275);
        }


    }
}
