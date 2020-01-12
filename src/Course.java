/* [Course.java]
 * Details of all courses are stored in this class
 * Albert Quon
 * 2020/01/11
 */

import java.util.ArrayList;

class Course {
    private String name, type, time, days;
    private ArrayList<Course> tutorials;

    Course(String name, String type, String time, String days){
        this.name = name;
        this.type = type;
        this.time = time;
        this.days = days;
        this.tutorials = new ArrayList<>();
    }



    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getTime() {
        return time;
    }



    public ArrayList<Course> getTutorials(){
        return tutorials;
    }

    public String getDays(){
        return this.days;
    }
}
