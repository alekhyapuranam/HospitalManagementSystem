package hospitalmanagementsys;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

//package HospitalManagementSystem;

public class Doctor {
         private final Connection connection;
         private final Scanner scanner;

         public Doctor(Connection connection, Scanner scanner){
                this.connection=connection;
                this.scanner=scanner;

         }

         public void viewDoctor()
         {
            try {
                String query="select * from doctor";
                PreparedStatement preparedStatement=connection.prepareStatement(query); 
                ResultSet resultSet=preparedStatement.executeQuery();
                while (resultSet.next()) {
                    int id = resultSet.getInt("Doctor_ID");
                    String name=resultSet.getString("name");
                    String specialization=resultSet.getString("specialization");

                    System.out.println("Id : "+id);
                    System.out.println("Name : "+name);
                    System.out.println("specialization : "+specialization);
                    
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
           }
           public void viewDoctorByName(String inputname)
           {
            try {
                String query="select * from doctor where Name=?";
                PreparedStatement preparedStatement=connection.prepareStatement(query);
                //System.out.println("Enter the name ");
                //String inputname=scanner.nextLine();
                preparedStatement.setString(1, inputname ); 
                ResultSet resultSet=preparedStatement.executeQuery();
                while (resultSet.next()) {
                    int id = resultSet.getInt("Doctor_ID");
                    String name=resultSet.getString("name");
                    String specialization=resultSet.getString("specialization");

                    System.out.println("Id : "+id);
                    System.out.println("Name : "+name);
                    System.out.println("specialization : "+specialization);
                   
                }              
            } catch (SQLException e) {
                e.printStackTrace();
            }
           

           }
           public boolean viewDoctorByID(int id)
           {
            try {
                String query="select * from doctor where  Doctor_ID=?";
                PreparedStatement preparedStatement=connection.prepareStatement(query);
                preparedStatement.setInt(1, id);
                ResultSet resultSet=preparedStatement.executeQuery();
                if (resultSet.next()) 
                {
                    System.out.println("Id : "+ resultSet.getInt("Doctor_ID"));
                    System.out.println("Name : "+ resultSet.getString("Name"));
                    System.out.println("Specialization : "+ resultSet.getString("Specialization"));
                    return true;
                }
                else{
                    return false;
                }
            } catch (SQLException e) {
                // TODO: handle exception
                e.printStackTrace();
            }
            
            return true;

           }
         
         public int  getDoctorIdByName(String name)
         { 
            try {
                String query="select Doctor_ID from doctor where Name=?";
                PreparedStatement preparedStatement=connection.prepareStatement(query);
                preparedStatement.setString(1, name);
                ResultSet resultSet=preparedStatement.executeQuery();
                if(resultSet.next())
                {
                    return resultSet.getInt("Doctor_ID");
                }
                else{
                    System.out.println("Invalid doctor name");
                    return 0;
                }
                
            } catch (Exception e) {
                // TODO: handle exception
            }
            return 0;
         }

}
