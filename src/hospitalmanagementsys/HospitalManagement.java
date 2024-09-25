//package src\hospitalmanagementsys;
//import hospitalmanagementsys.Doctor;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class HospitalManagement {

    private static final String url = "jdbc:mysql://localhost:3306/hospital";
    private static final String username = "root";
    private static final String password = "Allu@1999";

    public static void main(String[] args) {
        
            
       
        //Connection connection = null;
        try {
            //class.forname is used to load the class.when class is loaded static block will be executed.
            //that static block contains the logic to register the driver to drivermanager to connect to database.
            Class.forName("com.mysql.cj.jdbc.Driver");
            
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
       try {
         // with class.forname driver is registered with driver manager, now have to get connection from database
            //getconnection method is in DriverManager class,since getconnection is static method we can call with directly with class name.
            //getconnection method returns connection object which contains info like its tab les, supported sql grammer, stored procedures etc.
            Connection connection = DriverManager.getConnection(url, username, password);
            System.out.println(connection);
            Scanner scanner = new Scanner(System.in);
            Patient patient = new Patient(connection, scanner);
            Doctor doctor = new Doctor(connection, scanner);
            while (true) {
                System.out.println("HOSPITAL MANAGEMENT SYSTEM");
                System.out.println("***Patient Menu***");
                System.out.println("1. Add Patient");
                System.out.println("2. View Patient");
                System.out.println("3. View Patient by ID");
                System.out.println("***Doctor Menu***");
                System.out.println("4. View Doctor");
                System.out.println("5. View Doctor by Name");
                System.out.println("6. Book Appointment");
                int choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        //add patient
                        patient.addPatient();
    
                        break;
                    case 2:
                        //View patient
                        patient.viewPatient();
                        break;
                    case 3:
                        //View patient by ID
                        System.out.println("Enter patient id");
                        int id = scanner.nextInt();
                        patient.viewPatientByID(id);
                        break;
    
                    case 4:
                        //view Doctor
                        doctor.viewDoctor();
                        break;
    
                    case 5:
                        //view doctor by name
                        System.out.println("Enter the name");
                        String name=scanner.next();
                        doctor.viewDoctorByName(name);
                        break;
                    case 6:
                        Appointment(doctor, patient, connection, scanner);
                        break;
                    default:
                        System.out.println("Please enter valid option");
                        break;
    
            } 
       
       }

        }catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
    
    

    public static void Appointment(Doctor doctor, Patient patient, Connection connection, Scanner scanner) {
        System.out.println("Enter Doctor Name");
        String doctorname = scanner.next();
        System.out.println("Enter Patient Name");
        String patientname = scanner.next();
        System.out.println("Enter Date to Book Appointment in (yyyy-mm-dd)");
        String date = scanner.next();
        int doctorid=doctor.getDoctorIdByName(doctorname);
        int patientid=patient.getPatientIdByName(patientname);
        if (doctor.viewDoctorByID(doctorid) && patient.viewPatientByID(patientid)) {
            if (checkAppointment(connection, doctorid, date)) {
                try {
                    String query = "insert into appointment(Patient_ID,Doctor_ID, Appointment_Date, Doctor_name, patient_name) values(?,?,?,?,?)";
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setInt(1, patientid);
                    preparedStatement.setInt(2, doctorid);
                    preparedStatement.setDate(3, Date.valueOf(date));// date.valueOf converts string date to date  datatype 
                    preparedStatement.setString(4, doctorname);
                    preparedStatement.setString(5, patientname);
                    int result = preparedStatement.executeUpdate();
                    if (result>0) {
                        System.out.println("Appointment booked successfully");
                    }
                    
                } catch (SQLException e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }
            }else{
                System.out.println("Doctor already has appointment . unable to Book!!");
            }

        }
    }

    public static boolean checkAppointment(Connection connection, int doctorid, String date) {
        try {
            String query = "Select * from appointment where Doctor_ID=? AND Appointment_Date=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, doctorid);
            preparedStatement.setDate(2, Date.valueOf(date));
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return false;

            } else {
                return true;
            }

        } catch (SQLException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return true;
    }
}
