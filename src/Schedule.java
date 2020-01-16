
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
import javax.swing.text.AbstractDocument;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

import org.json.JSONObject;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class Schedule extends JFrame {

    // initialize J elements
    static JFrame window;

    public static void main(String[] args) {
        System.out.println("Schedule");
        window = new Schedule();
    }

    Schedule() {
        super("Carleton Schedule Optimizer");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        this.setResizable(false);
        AddPanel wind = new AddPanel();
        add(wind);
        Object[] semesterString = { "Winter 2020", "Summer 2020" };
        wind.semester = (String)JOptionPane.showInputDialog(null, "Your Semester:", "Select Semester", JOptionPane.PLAIN_MESSAGE, null, semesterString, "Winter 2020");
        if (wind.semester == "Winter 2020") {
            wind.semester = "202010";
        } else {
            wind.semester = "202020";
        }
        wind.data.put(wind.semester, new JSONObject());
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
        JTextField courseNumber;

        JComboBox courseLevelOptions;
        JComboBox courseOptions;
        JButton addCourse;
        JButton clearButton;

        JScrollPane levelScrollPane;
        JList levelList;

        ArrayList<String> courses;
        DefaultListModel<String> courseModel;
        JSONObject data = new JSONObject();
        String semester;

        AddPanel() {
            setLayout(null);
            // add a filein function next version to make semesters automatically updated
            String[] courseLevelStrings = { "Undergraduate", "Graduate" };
            String[] courseString = { "ACCT", "AERO", "AFRI", "ASLA", "ANTH", "ALDS", "COMP", "MATH" };
            courses = new ArrayList<String>();
            courseModel = new DefaultListModel<String>();

            // drop down strings
            courseLevelOptions = new JComboBox(courseLevelStrings);
            courseOptions = new JComboBox(courseString);

            // buttons
            addCourse = new JButton("Add Course");// this button adds course
            clearButton = new JButton("Clear");// this button clears the calendar

            // user input
            courseNumber = new JTextField();
            courseNumber.setDocument(new JTextFieldLimit(4));

            courseLevelOptions.setBounds(10, 40, 120, 30);
            courseOptions.setBounds(10, 130, 120, 30);
            courseNumber.setBounds(10, 225, 120, 30);
            addCourse.setBounds(10, 315, 120, 30);

            JScrollPane showCourses = new JScrollPane();
            showCourses.setBounds(10,400, 120, 200);
            JList courseList = new JList(courseModel);
            showCourses.setViewportView(courseList);

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
                        levelList.clearSelection();// allows only 1 level to be selected at a given time
                    }
                }
            });
            levelList.setFont(new Font("monospaced", Font.PLAIN, 12));
            levelScrollPane.setViewportView(levelList);// setting scrollpane to display list
            levelScrollPane.setBounds(10, 30, 600, 130);

            addCourse.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    courses.add(courseOptions.getSelectedItem().toString() + " "+courseNumber.getText());
                    //if (data.has())
                    updateModels();
                }
            });

            add(courseLevelOptions);
            add(courseOptions);
            add(addCourse);
            add(showCourses);
        }

        private void updateModels() {
            courseModel.removeAllElements();
            for (int i = 0; i < courses.size(); i++) {
                courseModel.addElement(courses.get(i));
            }
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
            g.drawString("Course Level:", 10, 25);
            g.drawString("Subject", 10, 115);
            g.drawString("Course Number", 10, 210);
            g.drawString("Your Courses", 10, 300);
        }

    }

    private class JTextFieldLimit extends PlainDocument {
        private int limit;

        JTextFieldLimit(int limit) {
            super();
            this.limit = limit;
        }

        @Override
        public void insertString(int offs, String str, javax.swing.text.AttributeSet a) throws BadLocationException {
            if (isNum(str)) {
                if (getLength() + str.length() <= limit) {
                    super.insertString(offs, str, a);
                }
            }
        }
    }

    static boolean isNum(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}