/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schoolxqj.model;

import java.io.IOException;
import schoolxqj.persist.CourseXQJ;
import schoolxqj.persist.StudentXQJ;
import java.util.List;
import javax.xml.bind.JAXBException;
import javax.xml.xquery.XQException;
import javax.xml.xquery.XQResultSequence;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import schoolxqj.views.View;

/**
 *
 * @author DanielMoralesGonzale
 */
public class Model {

    //XQJ
    private final StudentXQJ studentxqj;
    private final CourseXQJ coursexqj;

    //View
    private View view;

    // Constructors.
    public Model() {

        studentxqj = new StudentXQJ();
        coursexqj = new CourseXQJ();

    }

    // Methods to manage data.
    /**
     * Method for listing all courses. This method will be called by the
     * controller. It will manipulate the data, and return it to the view, which
     * will be the one that will show it to the user.
     *
     * @throws javax.xml.xquery.XQException
     * @throws javax.xml.bind.JAXBException
     * @throws java.io.IOException
     */
    public void listAllCourses() throws XQException, JAXBException, JAXBException, IOException {

        List<Course> resultList = coursexqj.listAll();

        View.showList(resultList);

    }

    /**
     * Method listing all students. This method will be called by the
     * controller. It will manipulate the data, and return it to the view, which
     * will be the one that will show it to the user.
     *
     * @throws javax.xml.xquery.XQException
     * @throws javax.xml.bind.JAXBException
     * @throws java.io.IOException
     */
    public void listAllStudents() throws XQException, JAXBException, IOException {

        List<Student> resultList = studentxqj.listAll();

        View.showList(resultList);

    }

    /**
     * Method listing all students older than 18. This method will be called by
     * the controller. It will manipulate the data, and return it to the view,
     * which will be the one that will show it to the user.
     *
     * @param course course
     * @return resultList
     * @throws javax.xml.xquery.XQException
     * @throws javax.xml.bind.JAXBException
     * @throws java.io.IOException
     */
    public List<Student> listAllStudentsOlderThan18(Course course) throws XQException, JAXBException, IOException {

        List<Student> resultList = studentxqj.listStudentsOlder18(course);

        return resultList;

    }

    /**
     * Search a course by the given id.
     *
     * @param id to find
     * @return data source with a course with the given code or null if not
     * found
     */
    public Course searchCourseById(String id) {

        Course found = null;

        try {
            found = coursexqj.findById(id);

        } catch (IOException | JAXBException | XQException ex) {

            System.out.println("Course doesn't exist");
        }

        return found;

    }

    /**
     * Search a student by the given id.
     *
     * @param id to find
     * @return data source with a course with the given code or null if not
     * found
     */
    public Student searchStudentById(String id) {

        Student found = null;

        try {
            found = studentxqj.findById(id);

        } catch (IOException | JAXBException | XQException ex) {

            System.out.println("Student doesn't exist");
        }

        return found;

    }

    /**
     * Method for modifying a existing student. This method will be called by
     * the controller. It will manipulate the data, and return it to the view,
     * which will be the one that will show it to the user.
     *
     * @param olds olds
     * @param news news
     */
    public void modifyStudent(Student olds, Student news) {

        if (studentxqj.update(olds, news) == 1) {

            System.out.println("Modified OK");

        } else {
            System.out.println("Fail modify");

        }

    }

    /**
     * Method for removing a existing student. This method will be called by the
     * controller. It will manipulate the data, and return it to the view, which
     * will be the one that will show it to the user.
     *
     * @param student to add
     * @throws javax.xml.bind.JAXBException
     * @throws java.io.IOException
     */
    public void deleteStudent(Student student) throws JAXBException, IOException {

        if (studentxqj.delete(student) == 1) {

            System.out.println("Delete OK");

        } else {

            System.out.println("Fail delete");
        }

    }

    /**
     * Method for listing all students in a course. This method will be called
     * by the controller. It will manipulate the data, and return it to the
     * view, which will be the one that will show it to the user.
     *
     * @param course to be searched
     * @return resultlist resultlist
     * @throws javax.xml.xquery.XQException
     * @throws javax.xml.bind.JAXBException
     * @throws java.io.IOException
     */
    public List<Student> listAllStudentsInACourse(Course course) throws XQException, JAXBException, IOException {

        List<Student> resultlist = studentxqj.listStudentsFromACourse(course);

        return resultlist;

    }

    /**
     * Method for listing all courses of a student. This method will be called
     * by the controller. It will manipulate the data, and return it to the
     * view, which will be the one that will show it to the user.
     *
     * @param student to be searched
     * @return resultlist resultlist
     *
     * @throws javax.xml.xquery.XQException
     * @throws javax.xml.bind.JAXBException
     * @throws java.io.IOException
     */
    public List<Course> listCoursesOfAStudent(Student student) throws XQException, JAXBException, IOException {

        List<Course> courselist = coursexqj.listCoursesOfAStudent(student);

        return courselist;

    }

    /**
     * Method for enrolling a existing student in a course. This method will be
     * called by the controller. It will manipulate the data, and return it to
     * the view, which will be the one that will show it to the user.
     *
     * @param student to enroll
     * @param course where we'll add the student
     * @throws javax.xml.xquery.XQException
     */
    public void enrollStudentInCourse(Student student, Course course) throws XQException {

        if (studentxqj.enroll(student, course) == 1) {

            System.out.println("Enroll OK");

        } else {

            System.out.println("Fail enroll");

        }
    }

    /**
     * Method for unenrolling a existing student from a course. This method will
     * be called by the controller. It will manipulate the data, and return it
     * to the view, which will be the one that will show it to the user.
     *
     * @param student to unenroll
     * @param course
     * @throws javax.xml.xquery.XQException
     */
    public void unEnrollStudentInCourse(Student student, Course course) throws XQException {

        if (studentxqj.unroll(student, course) == 1) {

            System.out.println("Enroll OK");

        } else {

            System.out.println("Fail unenroll");

        }
    }

    public Student getStudentData(Student student, List<Student> studentlist, XQResultSequence rs) throws XQException {

        Node n = rs.getNode();
        Element e = (Element) n;

        String idStudent = e.getAttribute("id");
        String studentname = getTagValue("studentname", e);
        int age = Integer.parseInt(getTagValue("age", e));

        student.setId(idStudent);
        student.setName(studentname);
        student.setAge(age);

        studentlist.add(student);

        return student;
    }

    public Course getCourseData(Course course, List<Course> courselist, XQResultSequence rs) throws XQException {

        Node n = rs.getNode();
        Element e = (Element) n;

        String idCourse = e.getAttribute("id");
        String coursename = getTagValue("coursename", e);
        int duration = Integer.parseInt(getTagValue("duration", e));
        String description = getTagValue("description", e);

        course.setId(idCourse);
        course.setCoursename(coursename);
        course.setDuration(duration);
        course.setDescription(description);

        courselist.add(course);

        return course;
    }

    private static String getTagValue(String sTag, Element eElement) {

        NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();

        Node nValue = (Node) nlList.item(0);

        return nValue.getNodeValue();
    }

}
