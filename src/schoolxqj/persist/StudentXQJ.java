
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
public class StudentXQJ {

    CourseXQJ coursexqj;
    DBConnect connection;

    private final String queryGetCourses = "declare variable $z as xs:string external;"
            + "for $x in doc('schooldb/school.xml')/school/courses/course "
            + "where $x/enrolled/studentref = $z "
            + "return $x";

    public StudentXQJ() {

        this.connection = new DBConnect();

    }

    /**
     * Retrieves all students from the database
     *
     * @return all students from the database
     * @throws javax.xml.xquery.XQException exception
     * @throws javax.xml.bind.JAXBException exception
     * @throws java.io.IOException exception
     */
    public List<Student> listAll() throws XQException, JAXBException, IOException {

        List<Student> studentlist = new ArrayList<>();

        XQConnection conn = connection.getConnection();

        String query = "doc('schooldb/school.xml')/school/students/student";

        XQExpression xqe = conn.createExpression();
        XQResultSequence rs = xqe.executeQuery(query);

        while (rs.next()) {

            Student student = new Student();

            getStudentData(student, studentlist, rs);

            XQPreparedExpression xqpe = conn.prepareExpression(queryGetCourses);
            xqpe.bindString(new QName("z"), student.getId(), null);
            XQResultSequence rs2 = xqpe.executeQuery();

            List<Course> courselist = new ArrayList<>();

            while (rs2.next()) {

                Course course = new Course();

                getCourseData(course, courselist, rs2);
                
            }
            student.setCourses(courselist);

        }

        return studentlist;
    }

    /**
     * Finds a student by the given id
     *
     * @param id to search
     * @return 1 if student found or 0 if not.
     * @throws javax.xml.xquery.XQException exception
     * @throws javax.xml.bind.JAXBException exception
     * @throws java.io.IOException exception
     */
    public Student findById(String id) throws XQException, JAXBException, IOException {

        List<Student> studentlist = new ArrayList<>();

        XQConnection conn = connection.getConnection();

        String query = "declare variable $z as xs:string external;"
                + "for $x in doc('schooldb/school.xml')/school/students/student "
                + "where $x/@id = $z "
                + "return $x";

        XQPreparedExpression xqpe = conn.prepareExpression(query);
        xqpe.bindString(new QName("z"), id, null);
        XQResultSequence rs = xqpe.executeQuery();

        while (rs.next()) {

            Student student = new Student();

            getStudentData(student, studentlist, rs);

            XQPreparedExpression xqpe2 = conn.prepareExpression(queryGetCourses);
            xqpe2.bindString(new QName("z"), student.getId(), null);
            XQResultSequence rs2 = xqpe2.executeQuery();

            List<Course> courselist = new ArrayList<>();

            while (rs2.next()) {

                Course course = new Course();

                getCourseData(course, courselist, rs2);

            }
            student.setCourses(courselist);
        }

        return studentlist.get(0);

    }

    /**
     * Retrieves all students older than 18 from the database
     *
     * @param c course
     * @return all students from the database
     * @throws javax.xml.xquery.XQException exception
     * @throws javax.xml.bind.JAXBException exception
     * @throws java.io.IOException exception
     */
    public List<Student> listStudentsOlder18(Course c) throws XQException, JAXBException, IOException {

        List<Student> studentlist = new ArrayList<>();

        XQConnection conn = connection.getConnection();

        String query = "declare variable $z as xs:string external;"
                + "for $x in doc('schooldb/school.xml')/school/students/student "
                + "where $x/course/courseref = $z "
                + "and $x/age > 17 "
                + "return $x";

        XQPreparedExpression xqpe = conn.prepareExpression(query);
        xqpe.bindString(new QName("z"), c.getId(), null);
        XQResultSequence rs = xqpe.executeQuery();

        while (rs.next()) {

            Student student = new Student();

            getStudentData(student, studentlist, rs);

            XQPreparedExpression xqpe2 = conn.prepareExpression(queryGetCourses);
            xqpe2.bindString(new QName("z"), student.getId(), null);
            XQResultSequence rs2 = xqpe2.executeQuery();

            List<Course> courselist = new ArrayList<>();

            while (rs2.next()) {

                Course course = new Course();

                getCourseData(course, courselist, rs2);

            }
            student.setCourses(courselist);
        }
        return studentlist;

    }

    /**
     * Retrieves students from a course from the database
     *
     * @param c course
     * @return students from the database
     * @throws javax.xml.xquery.XQException exception
     * @throws javax.xml.bind.JAXBException exception
     * @throws java.io.IOException exception
     *
     */
    public List<Student> listStudentsFromACourse(Course c) throws XQException, JAXBException, IOException {

        List<Student> studentlist = new ArrayList<>();

        XQConnection conn = connection.getConnection();

        String query = "declare variable $z as xs:string external;"
                + "for $x in doc('schooldb/school.xml')/school/students/student "
                + "where $x/course/courseref = $z "
                + "return $x";

        XQPreparedExpression xqpe = conn.prepareExpression(query);
        xqpe.bindString(new QName("z"), c.getId(), null);
        XQResultSequence rs = xqpe.executeQuery();

        while (rs.next()) {

            Student student = new Student();

            getStudentData(student, studentlist, rs);

            XQPreparedExpression xqpe2 = conn.prepareExpression(queryGetCourses);
            xqpe2.bindString(new QName("z"), student.getId(), null);
            XQResultSequence rs2 = xqpe2.executeQuery();

            List<Course> courselist = new ArrayList<>();

            while (rs2.next()) {

                Course course = new Course();

                getCourseData(course, courselist, rs2);

            }
            student.setCourses(courselist);
        }

        return studentlist;
    }

    /**
     * Deletes a student from the database
     *
     * @param student to add
     * @return the student deleted
     * @throws javax.xml.bind.JAXBException exception
     * @throws java.io.IOException exception
     */
    public int delete(Student student) throws JAXBException, IOException {

        int num = 0;

        try {

            XQConnection conn = connection.getConnection();

            String query = "for $x in doc('schooldb/school.xml')/school/students/student "
                    + "where $x/@id = '" + student.getId() + "' "
                    + "return update delete $x";

            XQExpression xqe = conn.createExpression();
            xqe.executeCommand(query);

            num = 1;

        } catch (XQException ex) {
            System.out.println(ex.getMessage());
            num = 0;
        }

        return num;
    }

    /**
     * Modifies a student that we previously searched by name from database
     *
     * @param olds olds
     * @param news news
     * @return 1 if product modified or 0 if not.
     */
    public int update(Student olds, Student news) {

        int num = 0;

        try {

            XQConnection conn = connection.getConnection();

            String queryAge = "for $x in doc('schooldb/school.xml')/school/students/student where $x/@id = '" + olds.getId() + "' "
                    + "return update replace $x/age with <age>" + news.getAge() + "</age>";

            String queryName = "for $x in doc('schooldb/school.xml')/school/students/student where $x/@id = '" + olds.getId() + "' "
                    + "return update replace $x/studentname with <studentname>" + news.getName() + "</studentname>";

            XQExpression xqe = conn.createExpression();
            xqe.executeCommand(queryAge);
            xqe.executeCommand(queryName);

            num = 1;

        } catch (XQException ex) {
            System.out.println(ex.getMessage());
            num = 0;
        }

        return num;
    }

    /**
     * Modifies a student that we previously searched by name from database
     *
     * @param student student
     * @param course course
     * @return 1 if course modified or 0 if not.
     * @throws javax.xml.xquery.XQException exception
     */
    public int enroll(Student student, Course course) throws XQException {

        XQConnection conn = connection.getConnection();

        int num = 0;

        try {

            String queryStudent = "update insert <courseref>" + course.getId() + "</courseref> into doc('schooldb/school.xml')/school/students/student[@id = '" + student.getId() + "' ]/course";

            String queryCourse = "update insert <studentref>" + student.getId() + "</studentref> into doc('schooldb/school.xml')/school/courses/course[@id = '" + course.getId() + "' ]/enrolled";

            XQExpression xqe = conn.createExpression();
            xqe.executeCommand(queryStudent);
            xqe.executeCommand(queryCourse);

            num = 1;

        } catch (SecurityException ex) {
            System.out.println(ex.getMessage());
        }

        return num;

    }

    /**
     * Modifies a student that we previously searched by name from database
     *
     * @param student student
     * @param course course
     * @return 1 if course modified or 0 if not.
     * @throws javax.xml.xquery.XQException exception
     */
    public int unroll(Student student, Course course) throws XQException {

        XQConnection conn = connection.getConnection();

        int num = 0;

        try {

            String queryStudent = "for $x in doc('schooldb/school.xml')/school/students/student "
                    + "where $x/@id = '" + student.getId() + "' "
                    + "return update delete $x/course/courseref[.='" + course.getId() + "']";

            String queryCourse = "for $x in doc('schooldb/school.xml')/school/courses/course "
                    + "where $x/@id = '" + course.getId() + "' "
                    + "return update delete $x/enrolled/studentref[.='" + student.getId() + "']";

            XQExpression xqe = conn.createExpression();
            xqe.executeCommand(queryStudent);
            xqe.executeCommand(queryCourse);

            num = 1;

        } catch (SecurityException ex) {
            System.out.println(ex.getMessage());
        }

        return num;
    }

    /**
     * Receives an empty student object, and will fill it with the obtained
     * data.
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
        String studentname = getTagValue("studentname", e);
        int age = Integer.parseInt(getTagValue("age", e));

        student.setId(idStudent);
        student.setName(studentname);
        student.setAge(age);

        studentlist.add(student);

        return student;
    }

    /**
     * Receives an empty student course, and will fill it with the obtained
     * data.
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

        course.setId(idCourse);

        courselist.add(course);

        return course;
    }

    private static String getTagValue(String sTag, Element eElement) {

        NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();

        Node nValue = (Node) nlList.item(0);

        return nValue.getNodeValue();
    }

}
