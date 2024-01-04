
import java.security.Timestamp;
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
public class ScheduleQueries {
    
    private static Connection connection;
    private static ArrayList<String> faculty = new ArrayList<String>();
    private static PreparedStatement addSchedule;
    private static PreparedStatement getStudentSchedule;
    private static PreparedStatement getSeatsOpen;
    private static PreparedStatement studentDropCourse;
    private static PreparedStatement getCoursesList;
    private static PreparedStatement getSchedStudents;
    private static PreparedStatement getWaitStudents;
    private static PreparedStatement dropCourse;
    private static PreparedStatement getStatus;
    private static PreparedStatement updateSchedule;
    private static ResultSet resultSet;
    
    public static void addScheduleEntry(ScheduleEntry entry) {
        connection = DBConnection.getConnection();
        try {
            addSchedule = connection.prepareStatement("insert into app.schedule (semester, studentid, coursecode, status, timestamp) values (?, ?, ?, ?, ?)");
            addSchedule.setString(1, entry.getSemester());
            addSchedule.setString(2, entry.getStudentID());
            addSchedule.setString(3, entry.getCourseCode());
            addSchedule.setString(4, entry.getStatus());
            addSchedule.setTimestamp(5, entry.getTimestamp());
            addSchedule.executeUpdate();
        } catch(SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    
    public static ArrayList<ScheduleEntry> getScheduleByStudent(String semester, String studentID) {
        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> studentSchedule = new ArrayList<ScheduleEntry>();
        try {
            getStudentSchedule = connection.prepareStatement("select * from app.schedule where semester = (?) and studentid = (?)");
            getStudentSchedule.setString(1, semester);
            getStudentSchedule.setString(2, studentID);
            resultSet = getStudentSchedule.executeQuery();
            
            while(resultSet.next()) {
                studentSchedule.add(new ScheduleEntry(semester,
                        resultSet.getString("coursecode"),
                        studentID,
                        resultSet.getString("status"),
                        resultSet.getTimestamp("timestamp")));
            }
        } catch(SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return studentSchedule;
    }
    
    public static int getScheduledStudentCount(String semester, String courseCode) {
        connection = DBConnection.getConnection();
        int count = -100;
        try {
            getSeatsOpen = connection.prepareStatement("select count(studentID) from app.schedule where semester = (?) and courseCode = (?)");
            getSeatsOpen.setString(1, semester);
            getSeatsOpen.setString(2, courseCode);
            resultSet = getSeatsOpen.executeQuery();
            
            while (resultSet.next()) {
                count = resultSet.getInt(1);
            }
            
        } catch(SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return count;
    }
    
    public static ArrayList<ScheduleEntry> getScheduledStudentsByCourse(String semester, String courseCode) {
        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> schedules = new ArrayList<ScheduleEntry>();
        try {
            getSchedStudents = connection.prepareStatement("select * from app.schedule where semester = (?) and courseCode = (?) and status = (?)");
            getSchedStudents.setString(1, semester);
            getSchedStudents.setString(2, courseCode);
            getSchedStudents.setString(3, "S");
            resultSet = getSchedStudents.executeQuery();
            
            while(resultSet.next()) {
                schedules.add(new ScheduleEntry(semester,
                        courseCode,
                        resultSet.getString("studentid"),
                        resultSet.getString("status"),
                        resultSet.getTimestamp("timestamp")));
            }
        } catch(SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return schedules;
    }
    
    public static ArrayList<ScheduleEntry> getWaitlistedStudentsByCourse(String semester, String courseCode) {
        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> schedules = new ArrayList<ScheduleEntry>();
        try {
            getWaitStudents = connection.prepareStatement("select * from app.schedule where semester = (?) and courseCode = (?) and status = (?) order by timestamp asc");
            getWaitStudents.setString(1, semester);
            getWaitStudents.setString(2, courseCode);
            getWaitStudents.setString(3, "W");
            resultSet = getWaitStudents.executeQuery();
            
            while(resultSet.next()) {
                schedules.add(new ScheduleEntry(semester,
                        courseCode,
                        resultSet.getString("studentid"),
                        resultSet.getString("status"),
                        resultSet.getTimestamp("timestamp")));
            }
        } catch(SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return schedules;
    }
    
    public static void dropStudentScheduleByCourse(String semester, String studentID, String courseCode) {
        connection = DBConnection.getConnection();
        try {
            studentDropCourse = connection.prepareStatement("delete from app.schedule where semester = (?) and studentID = (?) and courseCode = (?)");
            studentDropCourse.setString(1, semester);
            studentDropCourse.setString(2, studentID);
            studentDropCourse.setString(3, courseCode);
            int resultSet = studentDropCourse.executeUpdate();
        } catch(SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    
    public static void dropScheduleByCourse(String semester, String courseCode) {
        connection = DBConnection.getConnection();
        try {
            dropCourse = connection.prepareStatement("delete from app.schedule where semester = (?) and courseCode = (?)");
            dropCourse.setString(1, semester);
            dropCourse.setString(2, courseCode);
            int resultSet = dropCourse.executeUpdate();
        } catch(SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    
    public static void updateScheduleEntry(String semester, ScheduleEntry entry) {
        connection = DBConnection.getConnection();
        int enrolled = ScheduleQueries.getScheduledStudentsByCourse(semester, entry.getCourseCode()).size();
//        System.out.println("enrolled "+enrolled);
        int open = CourseQueries.getCourseSeats(semester, entry.getCourseCode()) - enrolled;
//        System.out.println("open "+open);
        try {
            getStatus = connection.prepareStatement("select * from app.schedule where semester = (?) and studentid = (?) and coursecode = (?)");
            getStatus.setString(1, semester);
            getStatus.setString(2, entry.getStudentID());
            getStatus.setString(3, entry.getCourseCode());
            resultSet = getStatus.executeQuery();
            
            while (resultSet.next()) {
                if (open > 0) {
                    try (PreparedStatement stmt = connection.prepareStatement("update app.schedule set status = (?) where semester = (?) and studentid = (?) and coursecode = (?)")) {
                        stmt.setString(1, "S");
                        stmt.setString(2, semester);
                        stmt.setString(3, entry.getStudentID());
                        stmt.setString(4, entry.getCourseCode());
                        stmt.executeUpdate();
                    }
                }
            }
        } catch(SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    
    // extra methods I have added for my own use
    public static ArrayList<CourseEntry> getCoursesByStudent(String semester, String studentID) {
        connection = DBConnection.getConnection();
        ArrayList<CourseEntry> courses = new ArrayList<CourseEntry>();
        try {
            getCoursesList = connection.prepareStatement("select * from app.schedule where semester = (?) and studentid = (?)");
            getCoursesList.setString(1, semester);
            getCoursesList.setString(2, studentID);
            resultSet = getCoursesList.executeQuery();
            
            while(resultSet.next()) {
                courses.add(new CourseEntry(
                        semester,
                        resultSet.getString("coursecode"),
                        CourseQueries.getCourseDesc(semester, resultSet.getString("coursecode")),
                        CourseQueries.getCourseSeats(semester, resultSet.getString("coursecode"))));
            }
        } catch(SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return courses;
    }
    
    public static String getStatus(String semester, String studentID, String courseCode) {
        connection = DBConnection.getConnection();
        String status = "";
        try {
            getStatus = connection.prepareStatement("select * from app.schedule where semester = (?) and studentid = (?) and coursecode = (?)");
            getStatus.setString(1, semester);
            getStatus.setString(2, studentID);
            getStatus.setString(3, courseCode);
            resultSet = getStatus.executeQuery();
            
            while(resultSet.next()) {
                status = resultSet.getString("Status");
            }
        } catch(SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return status;
    }
    
    public static ScheduleEntry getScheduleEntry(String semester, String studentID, String courseCode) {
        connection = DBConnection.getConnection();
        String status = null;
        java.sql.Timestamp ts = null;
        try {
            getStatus = connection.prepareStatement("select * from app.schedule where semester = (?) and studentid = (?) and coursecode = (?)");
            getStatus.setString(1, semester);
            getStatus.setString(2, studentID);
            getStatus.setString(3, courseCode);
            resultSet = getStatus.executeQuery();
            
            while(resultSet.next()) {
                status = resultSet.getString("status");
                ts = resultSet.getTimestamp("timestamp");
            }
        } catch(SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return new ScheduleEntry(semester, courseCode, studentID, status, ts);
    }
}