/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schoolxqj.persist;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBException;
import javax.xml.namespace.QName;
import javax.xml.xquery.XQConnection;
import javax.xml.xquery.XQException;
import javax.xml.xquery.XQExpression;
import javax.xml.xquery.XQPreparedExpression;
import javax.xml.xquery.XQResultSequence;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import schoolxqj.model.Course;
import schoolxqj.model.Student;

/**
 *
 * @author DanielMoralesGonzale
 */
public class CourseXQJ {

    DBConnect connection;

    private final String queryGetStudents = "declare variable $z as xs:string external;"
            + "for $x in doc('schooldb/school.xml')/school/students/student "
            + "where $x/course/courseref = $z "
            + "return $x";

    public CourseXQJ() {

        this.connection = new DBConnect();

    }

    /**
     * Retrieves all courses from the database
     *
     * @return all courses from the database
     * @throws javax.xml.xquery.XQException exception
     * @throws javax.xml.bind.JAXBException exception
     * @throws java.io.IOException exception
     */
    public List<Course> listAll() throws XQException, JAXBException, IOException {

        List<Course> courselist = new ArrayList<>();

        XQConnection conn = connection.getConnection();

        String queryList = "doc('schooldb/school.xml')/school/courses/course";

        XQExpression xqe = conn.createExpression();
        XQResultSequence rs = xqe.executeQuery(queryList);

        while (rs.next()) {

            Course course = new Course();

            getCourseData(course, courselist, rs);

            XQPreparedExpression xqpe = conn.prepareExpression(queryGetStudents);
            xqpe.bindString(new QName("z"), course.getId(), null);
            XQResultSequence rs2 = xqpe.executeQuery();

            List<Student> studentlist = new ArrayList<>();

            while (rs2.next()) {

                Student student = new Student();

                getStudentData(student, studentlist, rs2);

            }
            course.setStudentref(studentlist);

        }

        return courselist;

    }

    /**
     * Finds a course by the given code.
     *
     * @param id to search
     * @return 1 if course found or 0 if not.
     *
     * @throws javax.xml.xquery.XQException exception
     * @throws javax.xml.bind.JAXBException exception
     * @throws java.io.IOException exception
     */
    public Course findById(String id) throws XQException, JAXBException, IOException {

        List<Course> courselist = new ArrayList<>();

        XQConnection conn = connection.getConnection();

        String query = "declare variable $z as xs:string external;"
                + "for $x in doc('schooldb/school.xml')/school/courses/course "
                + "where $x/@id = $z "
                + "return $x";

        XQPreparedExpression xqpe = conn.prepareExpression(query);
        xqpe.bindString(new QName("z"), id, null);
        XQResultSequence rs = xqpe.executeQuery();

        while (rs.next()) {

            Course course = new Course();

            getCourseData(course, courselist, rs);

            XQPreparedExpression xqpe2 = conn.prepareExpression(queryGetStudents);
            xqpe2.bindString(new QName("z"), course.getId(), null);
            XQResultSequence rs2 = xqpe2.executeQuery();

            List<Student> studentlist = new ArrayList<>();

            while (rs2.next()) {

                Student student = new Student();

                getStudentData(student, studentlist, rs2);

            }
            course.setStudentref(studentlist);

        }
        return courselist.get(0);
        
    }

    /**
     * Retrieves courses from a student from the database.
     *
     * @param s student
     * @return students from the database
     * @throws javax.xml.xquery.XQException exception
     * @throws javax.xml.bind.JAXBException exception
     * @throws java.io.IOException exception
     *
     */
    public List<Course> listCoursesOfAStudent(Student s) throws XQException, JAXBException, IOException {

        List<Course> courselist = new ArrayList<>();

        XQConnection conn = connection.getConnection();

        String query = "declare variable $z as xs:string external;"
                + "for $x in doc('schooldb/school.xml')/school/courses/course "
                + "where $x/enrolled/studentref = $z "
                + "return $x";

        XQPreparedExpression xqpe = conn.prepareExpression(query);
        xqpe.bindString(new QName("z"), s.getId(), null);
        XQResultSequence rs = xqpe.executeQuery();

        while (rs.next()) {

            Course course = new Course();

            getCourseData(course, courselist, rs);

            XQPreparedExpression xqpe2 = conn.prepareExpression(queryGetStudents);
            xqpe2.bindString(new QName("z"), course.getId(), null);
            XQResultSequence rs2 = xqpe2.executeQuery();

            List<Student> studentlist = new ArrayList<>();

            while (rs2.next()) {

                Student student = new Student();

                getStudentData(student, studentlist, rs2);

            }
            course.setStudentref(studentlist);
        }

        return courselist;
    }

    /**
     * Receives an empty student object, and will fill it with the obtained data
     * from the XQRsultSequence.
     *
     * @param student student
     * @param studentlist studentlist
     * @param rs rs
     * @return student
     * @throws XQException exception
     */
    public Student getStudentData(Student student, List<Student> studentlist, XQResultSequence rs) throws XQException {

        Node n = rs.getNode();
        Element e = (Element) n;

        String idStudent = e.getAttribute("id");

        student.setId(idStudent);

        studentlist.add(student);

        return student;
    }

    /**
     * Receives an empty course object, and will fill it with the obtained data
     * from the XQRsultSequence.
     *
     * @param course course
     * @param courselist courselist
     * @param rs rs
     * @return course
     * @throws XQException exception
     */
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
