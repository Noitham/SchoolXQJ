/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schoolxqj.model;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author DanielMoralesGonzale
 */
public class Course {

    private String id;
    private String coursename;
    private String description;
    private int duration;
    private List<Student> studentref;

    public Course() {

    }

    public Course(String id, String coursename, String description, int duration, List<Student> studentref) {
        this.id = id;
        this.coursename = coursename;
        this.description = description;
        this.duration = duration;
        this.studentref = studentref;
    }
    
    public Course(String id, String coursename, String description, int duration) {
        this.id = id;
        this.coursename = coursename;
        this.description = description;
        this.duration = duration;
    }
    
    public Course(String id, String coursename) {
        this.id = id;
        this.coursename = coursename;
    }
   
    public Course(String coursename, String description, int duration, List<Student> studentref) {
        this.coursename = coursename;
        this.description = description;
        this.duration = duration;
        this.studentref = studentref;
    }

    public Course(String coursename, String description, int duration) {
        this.coursename = coursename;
        this.description = description;
        this.duration = duration;
    }

    public Course(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCoursename() {
        return coursename;
    }

    public void setCoursename(String coursename) {
        this.coursename = coursename;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public List<Student> getStudentref() {
        return studentref;
    }

    public void setStudentref(List<Student> studentref) {
        this.studentref = studentref;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Course other = (Course) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        Class classObj = getClass();
        String className = classObj.getSimpleName();
        sb.append(className).append(" {");
        Field[] fields = classObj.getDeclaredFields();
        for (Field f : fields) {
            String fieldName = f.getName();
            try {
                Object fieldValue = f.get(this);
                if (fieldValue != null && !fieldValue.toString().equals("0")) {
                    sb.append("[");
                    sb.append(fieldName).append("=");

                    sb.append(fieldValue.toString());

                    sb.append("]");
                }
            } catch (IllegalArgumentException | IllegalAccessException ex) {
                Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        sb.append("}");
        return sb.toString();
    }

}
