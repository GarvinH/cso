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
    static JPanel ioPanel;
    static JPanel schPanel;
    static JButton courseLockInButton;
    static JLabel selectionDescription;

    public static void main(String[] args){
        System.out.println("Schedule");
        window = new Schedule();

    }

    Schedule() {
        super("CSO");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        window.setResizable(false);

        // set up the panel to display questions
        ioPanel = new UIPanel();
        this.add(new UIPanel());

        // set up the panel to display the schedule
        schPanel = new SchedulePanel();
        this.add(new SchedulePanel());
    }
    private class UIPanel extends JPanel {

    }
    private class SchedulePanel extends JPanel {

    }
}
