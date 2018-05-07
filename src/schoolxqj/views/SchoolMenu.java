package schoolxqj.views;

public class SchoolMenu extends Menu {

    public SchoolMenu() {
        super("Menu");
        addOption(new Option("Exit", "exit"));
        addOption(new Option("List courses", "listcourses"));
        addOption(new Option("List all students", "liststudents"));
        addOption(new Option("List courses of a student", "listcoursesofstudent"));
        addOption(new Option("List students of a course", "liststudentsinacourse"));
        addOption(new Option("Modify student", "modifystudent"));
        addOption(new Option("Enroll student in a course", "enrollstudent"));
        addOption(new Option("Unroll student from a course", "unrollestudent"));
        addOption(new Option("Delete student", "deletestudent"));
        addOption(new Option("List students older than 18 in a course", "listolder18"));
    }

}
