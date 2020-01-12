/* [Schedule.java]
 * The main class for the optimizer, also the frame for UI
 * Albert Quon, Kelvin Du, Garvin Hui, Gordon
 * 2020/01/11
 */
//imports
//java.awt

import org.json.JSONObject;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;


public class Schedule extends JFrame {
    static JFrame window;
    Course[][] timeFrames = new Course[5][26];

    ArrayList<Course> courses = new ArrayList<>(); // stores all courses


    public static void main(String[] args){ window = new Schedule(); }

    Schedule() {
        super("CSO");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        readFile("src\\temp.json");

        for(int i = 0; i < 5; i++) {
            for (int j = 0; j < 26; j++) {
                timeFrames[i][j] = null;
            }
        }
        ArrayList<Course> userChoices = assignCourses();
        ArrayList<ArrayList<Course>> available = organizeCourses(userChoices);

        createTimetable(available);
        for (int i = 0; i < timeFrames[0].length; i++) {
            for (int j = 0; j < timeFrames.length; j++) {
                if (timeFrames[j][i] != null) {
                    System.out.print(" " + timeFrames[j][i].getName() + " ");
                } else {
                    System.out.print(timeFrames[j][i]);
                }
            }
            System.out.println();
        }
    }

    void readFile(String fileName){
        //Object obj = null;
        try {
            //FileReader file = new FileReader(fileName);
            String contents = new String((Files.readAllBytes(Paths.get(fileName))));
            //obj = new JSONParser().parse(file);
            JSONObject jsonObject = new JSONObject(contents);

            Iterator keys = jsonObject.keys();
            while (keys.hasNext()) {
                Object key = keys.next();
                JSONObject value = jsonObject.getJSONObject((String) key);
                String name = value.getString("name");
                String type = value.getString("stype");
                String time = value.getString("time");
                String days = value.getString("days");
                //String location = value.getString("location");
                courses.add(new Course(name, type, time, days));
            }
        } catch(IOException e) {
            System.out.println("File not found or parse exception");
            //System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }

    public ArrayList<Course> assignCourses(){
        int numCourses = 0;
        String courseName;
        ArrayList<Course> choices = new ArrayList<Course>();
        boolean complete = false;
        boolean validName;
        Scanner keyInput = new Scanner(System.in);
        do {
            System.out.println("Enter a course; enter \"none\" after you have entered at least 5 courses to finish. DO not include sections");
            courseName = keyInput.next();
            validName = false;
            if (courseName.equalsIgnoreCase("none")){
                if (numCourses < 5) {
                    System.out.println("enter more courses");
                } else {
                    complete = true;
                }
            } else {

                for (Course course : courses) {
                    if (course.getName().substring(0,8).equalsIgnoreCase(courseName)) { //add all sections

                        if (choices.contains(course)){
                            System.out.println("course already added");
                        } else {
                            if (!validName){
                                validName = true;
                                numCourses++;
                                System.out.println("course added!");
                            }
                            choices.add(course);
                        }
                    }
                }
                if (!validName) {
                    System.out.println("Course not found!");
                }
            }

        } while(numCourses < 7 && !complete);
        return choices;
    }

    /**
     * Organizes the courses into their respective course codes
     * @param choices
     * @return
     */
    public ArrayList<ArrayList<Course>> organizeCourses(ArrayList<Course> choices){
        ArrayList<ArrayList<Course>> uniqueCourses = new ArrayList<>();

        int i = 0;
        int j = 0;
        do { // add sections into each index
            uniqueCourses.add(new ArrayList<Course>());

            uniqueCourses.get(i).add(choices.get(j));
            j++;

            if (j != choices.size()){
                while(j < choices.size() && choices.get(j-1).getName().substring(0, 8).equals(choices.get(j).getName().substring(0, 8))) {
                    uniqueCourses.get(i).add(choices.get(j));
                    if (choices.get(j-1).getName().substring(0,9).equalsIgnoreCase(choices.get(j).getName().substring(0,9))){
                        if (choices.get(j-1).getType().equals("tutorial")){
                            choices.get(j).getTutorials().add(choices.get(j-1));
                        } else {
                            choices.get(j-1).getTutorials().add(choices.get(j));
                        }
                    }
                    j++;

                }
            }
            i++;

        }while(choices.size() != j);

        return uniqueCourses;
    }

    void createTimetable(ArrayList<ArrayList<Course>> available){

        int j;
        boolean added;
        for (int i = 0; i < available.size(); i++) {
            j =0;
            added = false;
            while(j < available.get(i).size() && !added) {
                added = addCourse(available.get(i).get(j));
                if (added && available.get(i).get(j).getTutorials().size() > 0){
                    int k = 0;
                    added = false;
                    while(k < available.get(i).get(j).getTutorials().size() && !added){
                        added = addCourse(available.get(i).get(j).getTutorials().get(k));
                        k++;
                    }
                }
                j++;
            }

        }

    }
    boolean addCourse(Course course){
        int start, startHour, startMins, end, endHour, endMins, rowStartIndex, rowEndIndex;
        String days, time;
        String[] week = {"mon" , "tue" , "wed", "thu", "fri"};
        int[] cols = new int[2];

        cols[0] = -1;
        cols[1] = -1;
        time = course.getTime();


        start = Integer.parseInt(time.substring(0, time.indexOf("-")));
        startHour = start / 100;
        startMins = start % 100;

        end = Integer.parseInt(time.substring(time.lastIndexOf("-") + 1));
        endHour = end / 100;
        endMins = end % 100;

        rowStartIndex = (startHour - 8) * 2 + (int) Math.round(startMins / 30.0);
        rowEndIndex = (endHour - 8) * 2 + (int) Math.round(endMins / 30.0);

        days = course.getDays();

        for (int k = 0; k < days.length()/3; k++) {
            for (int l = 0; l < week.length; l++) {
                if (week[l].equalsIgnoreCase(days.substring(k*4, k*4 +3))) {
                    cols[k % 3] = l;
                }
            }
        }

        for (int k = rowStartIndex; k < rowEndIndex; k++) {
            if (timeFrames[cols[0]][k] == null) {
                timeFrames[cols[0]][k] = course;
                if (cols[1] != -1) {
                    if (timeFrames[cols[1]][k] == null) {
                        timeFrames[cols[1]][k] = course;
                    } else {
                        System.out.println("Conflict with course " + timeFrames[cols[1]][k].getName() + " and " + course.getName() + "A");
                        return false;
                    }
                } else {
                    //added = true;
                }
            } else {
                System.out.println("Conflict with course " + timeFrames[cols[0]][k].getName()+" and " + course.getName() );
                return false;
            }

        }

        return true;
    }
}
