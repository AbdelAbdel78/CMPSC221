
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
public class StudentQueries {
    
    private static Connection connection;
    private static ArrayList<String> faculty = new ArrayList<String>();
    private static PreparedStatement addStudent;
    private static PreparedStatement getStudentList;
    private static PreparedStatement getStudent;
    private static PreparedStatement dropStudent;
    private static ResultSet resultSet;
    
    public static void addStudent(StudentEntry student) {
        connection = DBConnection.getConnection();
        try {
            addStudent = connection.prepareStatement("insert into app.student (studentid, firstname, lastname) values (?, ?, ?)");
            addStudent.setString(1, student.getStudentID());
            addStudent.setString(2, student.getFirstName());
            addStudent.setString(3, student.getLastName());
            addStudent.executeUpdate();
        } catch(SQLException sqlException) {
            sqlException.printStackTrace();
        }
        
    }
    
    public static ArrayList<StudentEntry> getAllStudents() {
        connection = DBConnection.getConnection();
        ArrayList<StudentEntry> students = new ArrayList<StudentEntry>();
        try {
            getStudentList = connection.prepareStatement("select * from app.student");
            resultSet = getStudentList.executeQuery();
            
            while(resultSet.next()) {
                students.add(new StudentEntry(
                        resultSet.getString("studentid"),
                        resultSet.getString("firstname"),
                        resultSet.getString("lastname")));
            }
        } catch(SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return students;
    }
    
    public static StudentEntry getStudent(String studentID) {
        connection = DBConnection.getConnection();
        String SID = "";
        String FN = "";
        String LN = "";
        try {
            getStudent = connection.prepareStatement("select * from app.student where studentid = (?)");
            getStudent.setString(1, studentID);
            resultSet = getStudent.executeQuery();
            
            while(resultSet.next()) {
                SID = resultSet.getString("studentid");
                FN = resultSet.getString("firstname");
                LN = resultSet.getString("lastname");
            }
        } catch(SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return new StudentEntry(SID, FN, LN);
    }
    
    public static void dropStudent(String studentID) {
        connection = DBConnection.getConnection();
        try {
            dropStudent = connection.prepareStatement("delete from app.student where studentID = (?)");
            dropStudent.setString(1, studentID);
            int resultSet = dropStudent.executeUpdate();
        } catch(SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
}