package DAO;

import Model.Customer;
import com.mysql.cj.protocol.Resultset;
import com.mysql.cj.x.protobuf.MysqlxPrepare;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.xml.transform.Result;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

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

    //Returns observablelist of all countries
    public static ObservableList<String> getAllCountry(){
        ObservableList<String> allCountryObsList = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM countries;";
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ResultSet rSet = ps.executeQuery();

            while (rSet.next()){
                allCountryObsList.add(rSet.getString("Country"));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return allCountryObsList;
    }

    //This returns an observablelist of divisions related to to the country that was given
    public static ObservableList<String> getCountryDivisions(String country){
        ObservableList<String> divisionObsList = FXCollections.observableArrayList();
        try{
            String sql = "select Division from first_level_divisions JOIN countries ON first_level_divisions.Country_ID = countries.Country_ID WHERE countries.Country =  '" + country + "'";
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ResultSet rSet = ps.executeQuery();

            while (rSet.next()){
                divisionObsList.add(rSet.getString("Division"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return divisionObsList;
    }

    //Adds customer to db
    public static boolean addCustomer(String id, String name, String address, String country, String division, String postCode, String phone){
        //String sql = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update," +
        //        "Last_Updated_By, Division_ID) VALUES ()";
        try {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime ldt = LocalDateTime.now();
            ZonedDateTime currTime = ldt.atZone(ZoneId.systemDefault());
            String createdDate = currTime.format(dtf).toString();

            //Testing data is currently present. divId variable is always set to 1.
            String createdBy = "test";
            String updatedBy = "test";
            //String divId = "1";
            String lastUpdate = createdDate;
            /*String sql = "INSERT INTO customers (Customer_Name, Address," +
                    "Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By," +
                    "Division_ID) VALUES ('" + name + "','" + address + "','" + postCode + "','" + phone + "','" + createdDate +
                    "','" + createdBy + "','" + lastUpdate + "','" + updatedBy + "','" + divId + "');";
            System.out.println("QUERY" + sql);
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.executeUpdate();
            ps.close();*/
            String sql = String.format("UPDATE customers SET Customer_Name= '%s', Address= '%s', Postal_Code= '%s', Phone= '%s', Create_Date= '%s'," +
                    "Created_By= '%s', Last_Update= '%s', Last_Updated_By= '%s', Division_ID=(Select Division_ID from first_level_divisions where Division='%s') WHERE " +
                            "Customer_ID = '%s'", name,address,postCode,phone,createdDate,
                    createdBy,lastUpdate,updatedBy,division, id);
            System.out.println(sql);
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.executeUpdate();


            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    //Gets next customer ID
    public static String getNextCustomerId(){
        try {
            String maxId;
            String sql = "SELECT MAX(Customer_ID) FROM customers;";
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ResultSet rSet = ps.executeQuery();
            rSet.next();
            //maxId = rSet.getInt("MAX(Customer_ID)");
            maxId = rSet.getString("MAX(Customer_ID)");
            //maxId += 1;
            //return Integer.toString(maxId);
            return maxId;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return "ERROR";
        }
    }

    //Creates a temp customer to be modified when a new customer is added
    public static void createTempCustomer(){
        try{
            String sql = "INSERT INTO customers (Customer_Name, Division_ID) VALUES ('tempName','1')";
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.executeUpdate();
        }catch(SQLException throwables){
            throwables.printStackTrace();
        }
    }

    public static void deleteCustomer(String id){
        try {
            String sql = "DELETE FROM customers WHERE Customer_ID='" + id + "';";
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.executeUpdate();

        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
    }
}
