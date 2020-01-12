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

import javax.print.attribute.AttributeSet;
//javax.swing
/*  Prof name
    Room number
    Course number
    building name/abbreviation
    time
 */
// graphic import
import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;

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
        add(new AddPanel());
        this.setVisible(true);
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

        private int maxX, maxY;

        AddPanel() {
            setLayout(null);

            maxX = Toolkit.getDefaultToolkit().getScreenSize().width;
            maxY = Toolkit.getDefaultToolkit().getScreenSize().height;

            // add a filein function next version to make semesters automatically updated
            String[] semesterString = {"Winter 2020", "Summer 2020"};
            String[] courseLevelStrings = {"Undergraduate", "Graduate"};
            String[] courseString = {"ACCT", "AERO", "AFRI", "ASLA", "ANTH", "ALDS", "COMP", "MATH"};

            // drop down strings
            courseLevelOptions = new JComboBox(courseLevelStrings);
            semesterOptions = new JComboBox(semesterString);
            courseOptions = new JComboBox(courseString);

            // buttons
            addCourse = new JButton("Add Course");//this button adds course
            clearButton = new JButton("Clear");//this button clears the calendar

            // user input
            courseNumber = new JTextField();
            //courseNumber.setDocument(new JTextFieldLimit(4));

            semesterOptions.setBounds(10,40,120,25);
            courseLevelOptions.setBounds(10, 130, 120, 25);
            courseOptions.setBounds(10, 225, 120, 25);
            courseNumber.setBounds(10, 315, 120, 25);

            add(courseNumber);
            courseNumber.setBorder(BorderFactory.createEmptyBorder());

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

            add(courseLevelOptions);
            add(semesterOptions);
            add(courseOptions);



        }

        private class UIPanel extends JPanel {

        }

        private class staticPanel extends JPanel {

        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            this.setDoubleBuffered(true);
            repaint();
            g.setFont(new Font("Arial", Font.BOLD, 16));
            g.drawString("Semester:", 10, 25);
            g.drawString("Course Level:", 10 ,115 );
            g.drawString("Subject", 10, 210);
            g.drawString("Course Number", 10, 300);

            //draw calendar
            g.setColor(Color.gray);
            g.fillRect(maxX/2, 100, maxX-50, maxY-50);
        }


    }

    private class JTextFieldLimit extends DocumentFilter {
        private int limit;
        JTextFieldLimit(int limit) {
            super();
            this.limit = limit;
        }

        public void insertString(DocumentFilter.FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
            if (isNum(string)) {
                if (fb.getDocument().getLength()+string.length()<=limit) {
                    fb.insertString(offset, string, attr);
                }
            }
        }
    }

    static boolean isNum(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch(NumberFormatException e) {
            return false;
        }
    }
}