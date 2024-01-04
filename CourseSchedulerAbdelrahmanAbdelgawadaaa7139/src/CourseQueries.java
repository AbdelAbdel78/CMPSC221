
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author abdel
 */
public class CourseQueries {
    
    private static Connection connection;
    private static ArrayList<String> faculty = new ArrayList<String>();
    private static PreparedStatement addCourse;
    private static PreparedStatement getCourseList;
    private static PreparedStatement getCourseCodesList;
    private static PreparedStatement getCourseSeats;
    private static PreparedStatement dropCourse;
    private static PreparedStatement getCourseDesc;
    private static ResultSet resultSet;
    
    public static void addCourse(CourseEntry course) {
        connection = DBConnection.getConnection();
        try {
            addCourse = connection.prepareStatement("insert into app.course (semester, coursecode, description, seats) values (?, ?, ?, ?)");
            addCourse.setString(1, course.getSemester());
            addCourse.setString(2, course.getCourseCode());
            addCourse.setString(3, course.getCourseDescription());
            addCourse.setInt(4, course.getSeats());
            addCourse.executeUpdate();
        } catch(SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    
    public static ArrayList<CourseEntry> getAllCourses(String semester) {
        connection = DBConnection.getConnection();
        ArrayList<CourseEntry> courses = new ArrayList<CourseEntry>();
        try {
            getCourseList = connection.prepareStatement("select * from app.course where semester = (?)");
            getCourseList.setString(1, semester);
            resultSet = getCourseList.executeQuery();
            
            while(resultSet.next()) {
                courses.add(new CourseEntry(
                        semester,
                        resultSet.getString("coursecode"),
                        resultSet.getString("description"),
                        resultSet.getInt("seats")));
            }
        } catch(SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return courses;
    }
    
    public static ArrayList<String> getAllCourseCodes(String semester) {
        connection = DBConnection.getConnection();
        ArrayList<String> courseCodes = new ArrayList<String>();
        try {
            getCourseCodesList = connection.prepareStatement("select coursecode from app.course where semester = (?)");
            getCourseCodesList.setString(1, semester);
            resultSet = getCourseCodesList.executeQuery();
            
            while(resultSet.next()) {
                courseCodes.add(resultSet.getString("coursecode"));
            }
        } catch(SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return courseCodes;
    }
    
    public static int getCourseSeats(String semester, String courseCode) {
        connection = DBConnection.getConnection();
        int seats = -1;
        try {
            getCourseSeats = connection.prepareStatement("select seats from app.course where semester = (?) and coursecode = (?)");
            getCourseSeats.setString(1, semester);
            getCourseSeats.setString(2, courseCode);
            resultSet = getCourseSeats.executeQuery();
            
            while (resultSet.next()) {
                seats = resultSet.getInt(1);
            }
                    
        } catch(SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return seats;
    }
    
    public static void dropCourse(String semester, String courseCode) {
        connection = DBConnection.getConnection();
        try {
            dropCourse = connection.prepareStatement("delete from app.course where semester = (?) and coursecode = (?)");
            dropCourse.setString(1, semester);
            dropCourse.setString(2, courseCode);
            int resultSet = dropCourse.executeUpdate();
        } catch(SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    
    //// extra methods I have added for my own use
    public static String getCourseDesc(String semester, String courseCode) {
        connection = DBConnection.getConnection();
        String desc = null;
        try {
            getCourseDesc = connection.prepareStatement("select description from app.course where semester = (?) and coursecode = (?)");
            getCourseDesc.setString(1, semester);
            getCourseDesc.setString(2, courseCode);
            resultSet = getCourseDesc.executeQuery();
            
            while (resultSet.next()) {
                desc = resultSet.getString(1);
            }
                    
        } catch(SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return desc;
    }
}