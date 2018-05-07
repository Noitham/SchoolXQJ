/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schoolxqj.controller;

import java.io.IOException;
import schoolxqj.model.Course;
import schoolxqj.model.Model;
import schoolxqj.model.Student;
import schoolxqj.views.View;
import java.util.List;
import javax.xml.bind.JAXBException;
import javax.xml.xquery.XQException;

/**
 *
 * @author DanielMoralesGonzale
 */
public class Controller {

    private final Model model;
    private final View view;

    /**
     * Constructor
     *
     * @param model model
     * @throws javax.xml.xquery.XQException
     * @throws javax.xml.bind.JAXBException
     * @throws java.io.IOException
     */
    public Controller(Model model) throws XQException, JAXBException, IOException {
        this.model = model;
        this.view = new View(this, model);
        view.displayMenu();
    }

    /**
     * Selects the option from the view
     *
     * @param action action
     * @throws javax.xml.xquery.XQException
     * @throws javax.xml.bind.JAXBException
     * @throws java.io.IOException
     */
    public void processAction(String action) throws XQException, JAXBException, IOException {

        try {

            switch (action) {

                case "exit": // Exit to the app
                    view.setExit(true);
                    break;
                case "listcourses": // List all courses
                    listAllCourses();
                    break;
                case "liststudents": // List all students
                    listAllStudents();
                    break;
                case "liststudentsinacourse": // List courses of a student
                    listAllStudentsInACourse();
                    break;
                case "listcoursesofstudent": // List students of a course
                    listCoursesOfAStudent();
                    break;
                case "modifystudent": // Modify student
                    modifyStudent();
                    break;
                case "enrollstudent": // Assign a course to a student
                    enrollStudentInCourse();
                    break;
                case "unrollestudent": // Unassign course from a student
                    unEnrollStudentInCourse();
                    break;
                case "deletestudent": // Deletes an student
                    deleteStudent();
                    break;
                case "listolder18": // List students older than 18 in a course
                    listAllStudentsOlderThan18();
                    break;
                default:
                    view.alert("Unknown error\n");
                    break;
            }

        } catch (Exception e) {
            System.out.println("Error: Wrong Data / Not Found");
        }
    }

    /**
     * Gets all coursescourse from the data source.
     * {@link schoolxqj.model.Model#listAllCourses()}
     *
     * @throws javax.xml.xquery.XQException
     * @throws javax.xml.bind.JAXBException
     * @throws java.io.IOException
     */
    public void listAllCourses() throws XQException, JAXBException, JAXBException, JAXBException, JAXBException, IOException {
        model.listAllCourses();
    }

    /**
     * Gets all courses from the data source. {@link Model#listAllStudents()}
     *
     * @throws javax.xml.xquery.XQException
     * @throws javax.xml.bind.JAXBException
     * @throws java.io.IOException
     */
    public void listAllStudents() throws XQException, JAXBException, JAXBException, JAXBException, JAXBException, IOException {
        model.listAllStudents();
    }

    /**
     * Gets students older than 18 from a group
     * {@link Model#listAllStudentsOlderThan18(Course)}
     *
     * @throws javax.xml.xquery.XQException
     * @throws javax.xml.bind.JAXBException
     * @throws java.io.IOException
     */
    public void listAllStudentsOlderThan18() throws XQException, JAXBException, JAXBException, JAXBException, JAXBException, IOException {

        String idCourse = view.inputString("Input the course id: ");

        if (idCourse != null || !"".equals(idCourse)) {

            Course course = model.searchCourseById(idCourse);

            if (course != null) {

                List studentlist = model.listAllStudentsOlderThan18(course);

                view.displayList(studentlist);

            } else {
                view.alert("There's not a student older than 18 in the specified course");
            }
        } else {
            view.alert("ID not found");
        }
    }

    /**
     * Method that will ask user the id of the student to modify. If the student
     * exists, we will show it. If not, we will show error message. We will ask
     * for user for confirmation. If confirm, we will send the data to the
     * controller. Which will use the model for modifying the student.
     * <p>
     * uses {@link schoolxqj.model.Model#modifyStudent(schoolxqj.model.Student, schoolxqj.model.Student) , {@link schoolxqj.model.Model#searchStudentById(java.lang.String)
     * }}
     */
    public void modifyStudent() {

        String idStudent = view.inputString("Input id: ");

        if (idStudent != null || !"".equals(idStudent)) {
            Student student = model.searchStudentById(idStudent);
            Student newStudent;
            if (student != null) {
                view.alert(student.toString());
                String answer = view.inputString("\nWould you like to modify this student? (Y/N)");
                if (answer.equals("Y") || answer.equals("y")) {
                    newStudent = view.inputStudent();
                    student.setName(newStudent.getName());
                    student.setAge(newStudent.getAge());
                    model.modifyStudent(student, newStudent);
                } else {
                    view.alert("Aborted\n");
                }
            } else {
                view.alert("Student could not be found");
            }
        } else {
            view.alert("ID not found");
        }
    }

    /**
     * Method that will ask user the id of the course to show(its students). If
     * a course with the given id exists, we will show it. If not, we will show
     * error message. We will ask for user for confirmation. If confirm, we will
     * send the data to the controller. Which will use the model for modifying
     * the student.
     * <p>
     * uses {@link schoolxqj.model.Model#listAllStudentsInACourse(schoolxqj.model.Course)
     * }
     *
     * @throws javax.xml.bind.JAXBException
     * @throws javax.xml.xquery.XQException
     * @throws java.io.IOException
     *
     */
    public void listAllStudentsInACourse() throws JAXBException, XQException, IOException {

        String idCourse = view.inputString("Input the course id: ");

        if (idCourse != null || "".equals(idCourse)) {

            Course course = model.searchCourseById(idCourse);

            if (course != null) {

                List studentlist = model.listAllStudentsInACourse(course);

                view.displayList(studentlist);

            } else {
                view.alert("No course found");
            }
        } else {
            view.alert("ID not found");
        }

    }

    /**
     * Method that will ask user the id of the student to show(its courses). If
     * a student with the given id exists, we will show it. If not, we will show
     * error message. We will ask for user for confirmation. If confirm, we will
     * send the data to the controller. Which will use the model for modifying
     * the student.
     * <p>
     * uses {@link schoolxqj.model.Model#listCoursesOfAStudent(schoolxqj.model.Student)
     * }
     *
     * @throws javax.xml.bind.JAXBException
     * @throws javax.xml.xquery.XQException
     * @throws java.io.IOException
     */
    public void listCoursesOfAStudent() throws JAXBException, XQException, IOException {

        String idStudent = view.inputString("Input the student id: ");

        if (idStudent != null || !"".equals(idStudent)) {

            Student student = model.searchStudentById(idStudent);

            if (student != null) {

                List courselist = model.listCoursesOfAStudent(student);

                view.displayList(courselist);

            } else {
                view.alert("No course found");
            }
        } else {
            view.alert("ID not found");
        }

    }

    /**
     * Method that will ask user the id of the student to delete. If the student
     * exists, we will show it. If not, we will show error message. We will ask
     * for user for confirmation. If confirm, we will send the data to the
     * controller. Which will use the model for removing the student.
     * <p>
     * uses {@link Controller#deleteStudent()} (bdmvcschool.model.Student) }
     *
     * @throws javax.xml.bind.JAXBException
     * @throws java.io.IOException
     */
    public void deleteStudent() throws JAXBException, IOException {
        String idStudent = view.inputString("Input id: ");
        if (idStudent != null || !"".equals(idStudent)) {
            Student student = model.searchStudentById(idStudent);
            if (student != null) {
                view.alert(student.toString());
                String answer = view.inputString("\nWould you like to delete this student? (Y/N)");
                if (answer.equals("Y") || answer.equals("y")) {
                    if (student.getCourses().isEmpty()) {
                        model.deleteStudent(student);
                    } else {
                        System.out.println("Not possible, user is currently enrolled in a group");
                    }
                } else {
                    view.alert("Aborted\n");
                }
            } else {
                view.alert("Student could not be found");
            }
        } else {
            view.alert("ID not found");
        }
    }

    /**
     * Method that will ask user the id of the student to enroll into a course.
     * If the student exists, we will show it. If not, we will show error
     * message. We will ask for user for confirmation. If confirm, we will send
     * the data to the controller. Which will use model for enrolling the
     * student to the course.
     * <p>
     * uses {@link schoolxqj.model.Model#enrollStudentInCourse(bdmvcschool.model.Student, schoolxqj.model.Course)
     * }
     *
     * @throws javax.xml.xquery.XQException
     */
    public void enrollStudentInCourse() throws XQException {

        String idStudent = view.inputString("Input student id: ");
        if (idStudent != null || !"".equals(idStudent)) {
            Student student = model.searchStudentById(idStudent);
            String idCourse = view.inputString("Input course id: ");
            if (idCourse != null || !"".equals(idCourse)) {
                Course course = model.searchCourseById(idCourse);
                if (student != null) {
                    if (course != null) {
                        view.alert(student.toString());
                        String answer = view.inputString("\nWould you like to insert this student into the following group?\n" + course.toString() + " \n(Y/N)");
                        if (answer.equals("Y") || answer.equals("y")) {
                            if (student.getCourses().contains(course)) {
                                System.out.println("Student is already enrolled in the requested course");
                            } else {
                                model.enrollStudentInCourse(student, course);
                            }
                        } else {
                            view.alert("Aborted\n");
                        }
                    } else {
                        view.alert("Course could not be found");
                    }
                } else {
                    view.alert("Student could not be found");
                }
            } else {
                view.alert("Student not be found");
            }
        } else {
            view.alert("Course not found");
        }
    }

    /**
     * Method that will ask user the id of the student to unenroll from a
     * course. If the student exists, we will show it. If not, we will show
     * error message. We will ask for user for confirmation. If confirm, we will
     * send the data to the controller. Which will use model for unenrolling the
     * student to the course.
     * <p>
     * uses {@link schoolxqj.model.Model#unEnrollStudentInCourse(bdmvcschool.model.Student, schoolxqj.model.Course)
     * }
     *
     * @throws javax.xml.xquery.XQException
     */
    public void unEnrollStudentInCourse() throws XQException {

        String idStudent = view.inputString("Input student id: ");
        if (idStudent != null || !"".equals(idStudent)) {
            Student student = model.searchStudentById(idStudent);
            String idCourse = view.inputString("Input course id: ");
            if (idCourse != null || !"".equals(idCourse)) {
                Course course = model.searchCourseById(idCourse);
                if (student != null) {
                    if (course != null) {
                        view.alert(student.toString());
                        String answer = view.inputString("\nWould you like to unroll this student into the following group?\n" + course.toString() + " (Y/N)");
                        if (answer.equals("Y") || answer.equals("y")) {
                            if (student.getCourses().contains(course)) {
                                model.unEnrollStudentInCourse(student, course);
                            } else {
                                System.out.println("Student is not currenly enrolled in the requested course");
                            }
                        } else {
                            view.alert("Aborted\n");
                        }
                    } else {
                        view.alert("Course could not be found");
                    }
                } else {
                    view.alert("Student could not be found");
                }
            } else {
                view.alert("Student not be found");
            }
        } else {
            view.alert("Course not found");
        }
    }

}
