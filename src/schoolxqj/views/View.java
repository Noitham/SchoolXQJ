package schoolxqj.views;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Scanner;
import javax.xml.bind.JAXBException;
import javax.xml.xquery.XQException;

import schoolxqj.controller.Controller;
import schoolxqj.model.Model;
import schoolxqj.model.Student;

public class View {

    List<Student> student;
    private final Controller control;
    private final SchoolMenu menu;
    private boolean exit = false;

    //Constructor
    public View(Controller control, Model model) {
        this.control = control;
        this.menu = new SchoolMenu();
    }

    public boolean isExit() {
        return exit;
    }

    public void setExit(boolean exit) {
        this.exit = exit;
    }

    /**
     * Show the Menu
     *
     * @throws javax.xml.xquery.XQException
     * @throws javax.xml.bind.JAXBException
     * @throws java.io.IOException
     */
    public void displayMenu() throws XQException, JAXBException, IOException {
        do {
            menu.show();
            String action = menu.getSelectedOptionActionCommand();
            control.processAction(action);
        } while (!exit);
    }

    /**
     * Show alert message.
     *
     * @param s message
     */
    public void alert(String s) {
        System.out.print(s);
    }

    /**
     * Displays a list of objects.
     *
     * @param lst list of objects
     */
    public void displayList(List lst) {
        lst.forEach((o) -> {
            System.out.println(o.toString());
        });
    }

    /**
     * This methods reads a string from the user and returns it.
     *
     * @param string string
     * @return string
     */
    public String inputString(String string) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String s = "";
        alert(string);
        try {
            s = reader.readLine();
        } catch (IOException e) {
        }
        return s;
    }

    /**
     * Input new data student.
     *
     * @return new student
     */
    public Student inputStudent() {
        Student newStudent;
        String studentname;
        int age;

        try {
            Scanner scan = new Scanner(System.in);
            System.out.printf("Introduce name: ");
            studentname = scan.nextLine();
            System.out.printf("Introduce age: ");
            age = scan.nextInt();
            newStudent = new Student(studentname, age);
        } catch (NumberFormatException ex) {
            System.out.printf(ex.getMessage());
            newStudent = null;
        }
        return newStudent;
    }

    /**
     * Method that will display list.
     *
     * @param lst
     */
    public static void showList(java.util.List lst) {

        if (lst != null) {
            lst.forEach((o) -> {
                System.out.println(o.toString());
            });
        }
    }

}
