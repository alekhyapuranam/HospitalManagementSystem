//package hospitalmanagementsys;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

//package HospitalManagementSystem;

public class Patient {

     private final Connection connection; // to connect to database
     private final Scanner scanner; // takes scanner object from main class
     public Patient(Connection connection, Scanner scanner)
     {
         this.connection=connection;
         this.scanner=scanner;
     }

     public void  addPatient()
     {
            System.out.print("Enter your Name");
            String name= scanner.next();
            System.out.println("Enter your Age");
            int age=scanner.nextInt();
            System.out.println("Enter your Gender");
            String gender=scanner.next();
            try {
                String sql="insert into Patient(name,Age,Gender) values(?,?,?)";
                //sending sql query to database using connections obj because preparestatement is the method of connection interface
                PreparedStatement preparestatement=connection.prepareStatement(sql); 
                preparestatement.setString(1, name);
                preparestatement.setInt(2, age);
                preparestatement.setString(3, gender);
                int affectedrow=preparestatement.executeUpdate();//to execute the query in database
                if(affectedrow>0)
                {
                    System.out.println("Added successfully");
                }
                else
                {
                    System.out.println("Failed to add patient!!");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
           

     }

     public void  viewPatient()
     { 
        try {
            String query="select * from patient";
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            ResultSet resultSet=preparedStatement.executeQuery(); // returns the query output to resultset obj.
            while (resultSet.next()) {// resultset obj contain cursor pointing to current row.next method helps in moving to next row
                   int id= resultSet.getInt("id");
                   String name = resultSet.getString("name");
                   int age=resultSet.getInt("age");
                   String gender=resultSet.getString("gender");
                   System.out.println("id :"+id);
                   System.out.println("name :"+name);
                   System.out.println("age : "+age);
                   System.out.println("gender : "+ gender);
                   
                
            }
            System.out.println(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
            
     }
      public boolean viewPatientByID(int id)
      {
        try {
            String query="select * from patient where Patient_ID=?";
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet=preparedStatement.executeQuery();
            if (resultSet.next()) {
                
               

                    int patientid =resultSet.getInt("Patient_ID");
                    String name=resultSet.getString("name");
                    int age=resultSet.getInt("age");
                    String gender=resultSet.getString("gender");
                    System.out.println("id :"+patientid);
                    System.out.println("name :"+name);
                    System.out.println("age : "+age);
                    System.out.println("gender : "+ gender);
                    return true;
                    
        
                
            }
            else{
                return false;
            }
             
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;  
             

      }
     


}
