/* [Schedule.java]
 * The main class for the optimizer, also the frame for UI
 * Albert Quon, Kelvin Du, Garvin Hui, Gordon
 * 2020/01/11
 */
//imports
//java.awt
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.*;
//javax.swing
import javax.swing.*;


public class Schedule extends JFrame {
    static JFrame window;


    public static void main(String[] args){
        System.out.println("Schedule");
        window = new Schedule();
    }

    Schedule() {
        super("CSO");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
