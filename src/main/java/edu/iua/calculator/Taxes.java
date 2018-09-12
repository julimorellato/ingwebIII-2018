package edu.iua.calculator;
import java.sql.*;
import java.util.HashMap;


public class Taxes {


    private Connection getConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/taxes" + "?useSSL=false", "root", "rootroot");
            return con;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public HashMap<String, Float> getTaxesPercentage() {

        HashMap<String, Float> taxesPercentage = new HashMap<String, Float>();

        try{
                Statement stmt=getConnection().createStatement();
                ResultSet rs=stmt.executeQuery("select tax_name, tax_percentage from taxes_details");
                while(rs.next()){
                    taxesPercentage.put(rs.getString(1), rs.getFloat(2));
                }

        } catch(Exception e) {
            System.out.println(e);
        }

        return taxesPercentage;
    }

}
