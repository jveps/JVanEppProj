package DAO;

import com.mysql.cj.protocol.Resultset;

import java.sql.*;

public abstract class JDBC {
    private static final String protocol = "jdbc";
    private static final String  vendor = ":mysql:";
    private static final String location = "//localhost/";
    private static final String databaseName = "client_schedule";
    private static final String jdbcUrl = protocol + vendor + location + databaseName + "?connectionTimeZone = SERVER"; //LOCAL
    private static final String driver = "com.mysql.cj.jdbc.Driver"; //Driver reference
    private static final String userName = "sqlUser"; //Username
    private static final String password = "Passw0rd!"; //password
    public static Connection connection;

    public static void openConnection(){
        try{
            Class.forName(driver); //LocateDriver
            connection = DriverManager.getConnection(jdbcUrl, userName, password);

            System.out.println("Successfully Connected!");
        }
        catch (Exception e){
            System.out.println(("Error: " + e.getMessage()));
            e.printStackTrace();
        }
    }

    public static void closeConnection(){
        try{
            connection.close();
            System.out.println("Connection closed!");
        }
        catch (Exception e){
            //System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static Connection getConnection(){
        return connection;
    }

    //This method checks the password data related to the name user_name column.
    //if the password matches the string entered by the user, this method returns true
    public static boolean loginTest(String name,String pass){
        try {
            String sql = "SELECT Password FROM users WHERE user_name = '" + name + "'";
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ResultSet rSet = ps.executeQuery();
            rSet.next();


            if (rSet.getString("Password").equals(pass)){

                return true;
            }

            else{
                return false;
            }
        }
        catch(SQLException e){
            e.printStackTrace();
            return false;
        }

    }
}
