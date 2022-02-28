package DAO;

import Model.Appointment;
import Model.Customer;
import Model.User;
import com.mysql.cj.protocol.Resultset;
import com.mysql.cj.x.protobuf.MysqlxPrepare;
import com.sun.scenario.effect.Offset;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.xml.transform.Result;
import java.sql.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
/** This class runs database queries. This class connects to the database and executes sql queries.
 * @author Jessie Van Epps*/
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


    /** This method connects to the database. This method connects to the database to run queries.*/
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

    /** This method closes the connection to the database. This method terminates the connection between the database and the application.*/
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

    /** This method returns the connection object. This method returns the connection information.
     * @return Connection*/
    public static Connection getConnection(){
        return connection;
    }


    /** This method checks the password data related to the user_name column. If password matches string entered by the user, this returns true, otherwise false.
     * @return boolean True or false depending on if the password  is correct.
     * @param name The username entered by user
     * @param pass The password entered by the user*/
    public static boolean loginTest(String name,String pass){
        try {
            String sql = "SELECT Password FROM users WHERE user_name = '" + name + "'";
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ResultSet rSet = ps.executeQuery();
            while (rSet.next()){
                if (rSet.getString("Password").equals(pass)){

                return true;
                }

                else{
                    return false;
                }
            }



        }
        catch(SQLException e){
            e.printStackTrace();
            return false;
        }
        return false;


    }


    /** This method returns an observable list of all countries in the database. This method runs a query getting all entries in the countries table.
     * @return allCountryObsList An observable list containing countries.*/
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

    /** This method returns an observable list of divisions based on a given country. Queries the database table first_level_divisions to get divisions of a country.
     * @param country A name of a country
     * @return divisionObsList An observable list of strings of country divisions.*/
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

    /** This method updates customer information in the database. Executes a command updating an entry in the customers table.
     * @param id Customer ID
     * @param name Customer name
     * @param address Customer address
     * @param country Customer country
     * @param division Customer division
     * @param postCode Zip code
     * @param phone Customer phone
     * @return boolean Returns true if customer data updated successfully, otherwise false.*/
    public static boolean addCustomer(String id, String name, String address, String country, String division, String postCode, String phone){
        //String sql = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update," +
        //        "Last_Updated_By, Division_ID) VALUES ()";
        try {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime ldt = LocalDateTime.now();
            ZonedDateTime currTime = ldt.atZone(ZoneId.systemDefault());
            String createdDate = currTime.format(dtf).toString();


            String createdBy = User.getUsername();
            String updatedBy = User.getUsername();

            String lastUpdate = createdDate;
            /*String sql = "INSERT INTO customers (Customer_Name, Address," +
                    "Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By," +
                    "Division_ID) VALUES ('" + name + "','" + address + "','" + postCode + "','" + phone + "','" + createdDate +
                    "','" + createdBy + "','" + lastUpdate + "','" + updatedBy + "','" + divId + "');";
            System.out.println("QUERY" + sql);
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.executeUpdate();
            ps.close();*/
            /*String sql = String.format("UPDATE customers SET Customer_Name= '%s', Address= '%s', Postal_Code= '%s', Phone= '%s', Create_Date= '%s'," +
                    "Created_By= '%s', Last_Update= '%s', Last_Updated_By= '%s', Division_ID=(Select Division_ID from first_level_divisions where Division='%s') WHERE " +
                            "Customer_ID = '%s'", name,address,postCode,phone,createdDate,
                    createdBy,lastUpdate,updatedBy,division, id);*/
            //PreparedStatement ps = getConnection().prepareStatement(sql);
            PreparedStatement ps = getConnection().prepareStatement("UPDATE customers SET Customer_Name= ?, Address= ?, Postal_Code= ?, Phone= ?, Create_Date= ?," +
                    "Created_By= ?, Last_Update= ?, Last_Updated_By= ?, Division_ID=(Select Division_ID from first_level_divisions where Division=?) WHERE " +
                            "Customer_ID = ?");
            ps.setString(1,name);
            ps.setString(2,address);
            ps.setString(3,postCode);
            ps.setString(4,phone);
            ps.setTimestamp(5,Timestamp.from(Instant.now()));
            ps.setString(6,User.getUsername());
            ps.setTimestamp(7,Timestamp.from(Instant.now()));
            ps.setString(8,User.getUsername());
            ps.setString(9,division);
            ps.setString(10, id);

            ps.executeUpdate();


            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    /** This method updates appointment information in the database. This method updates appointment information in the appointments table.
     * @param a An Appointment.
     * @return boolean Returns true if successful, otherwise false*/
    public static boolean  addAppointment(Appointment a){
        try{
            ZonedDateTime zdt = LocalDateTime.now().atZone(ZoneOffset.UTC);
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            Timestamp createdTS = Timestamp.from(Instant.now());
            String createdDate= zdt.format(dtf);
            String lastUpdate = zdt.format(dtf);
            LocalDateTime sLDTUTC = LocalDateTime.parse(a.getStartDateTime(),dtf);
            ZonedDateTime zoneStart = ZonedDateTime.of(sLDTUTC,ZoneId.systemDefault());
            System.out.println("PST: " + zoneStart.getZone() + zoneStart.toString());
            System.out.println("UTC? " + zoneStart.withZoneSameInstant(ZoneOffset.UTC).getZone() + zoneStart.withZoneSameInstant(ZoneOffset.UTC));


            String contactSQL = String.format("SELECT Contact_ID from contacts where Contact_Name= '%s'", a.getContact());
            PreparedStatement contactPS = getConnection().prepareStatement(contactSQL);
            ResultSet rSet = contactPS.executeQuery();
            rSet.next();
            String contactID = rSet.getString("Contact_ID");
            System.out.println("Contact ID: " + contactID);

            /*String sql = String.format("UPDATE appointments SET Title= '%s', Description= '%s', Location= '%s'," +
                    "Type= '%s', Start= '%s', End= '%s', Create_Date= '%s', Created_By= '%s', Last_Update= '%s', " +
                    "Last_Updated_By= '%s', Customer_ID= '%s', User_ID= '%s', Contact_ID= '%s' WHERE Appointment_ID= '%s';", a.getTitle(),
                    a.getDescription(), a.getLocation(), a.getType(), a.getStartDateTime(), a.getEndDateTime(), OffsetDateTime.now(ZoneOffset.UTC).format(dtf), User.getUsername(), OffsetDateTime.now(ZoneOffset.UTC).format(dtf),
                    User.getUsername(), a.getCustomerId(), a.getUserId(), contactID, a.getAppointmentId());
            System.out.println(sql);*/

            //PreparedStatement ps = getConnection().prepareStatement(sql);
            /*PreparedStatement ps = getConnection().prepareStatement(String.format("UPDATE appointments SET Title= '%s', Description= '%s', Location= '%s'," +
                    "Type= '%s', Start= '%s', End= '%s', Create_Date= '%s', Created_By= '?', Last_Update= '%s', " +
                    "Last_Updated_By= '%s', Customer_ID= '%s', User_ID= '%s', Contact_ID= '%s' WHERE Appointment_ID= '%s';", a.getTitle(),
                    a.getDescription(), a.getLocation(), a.getType(), a.getStartDateTime(), a.getEndDateTime(), ps.setTimestamp(1,createdTS) /*OffsetDateTime.now(ZoneOffset.UTC).format(dtf), User.getUsername(), OffsetDateTime.now(ZoneOffset.UTC).format(dtf),
                    User.getUsername(), a.getCustomerId(), a.getUserId(), contactID, a.getAppointmentId()));*/
            PreparedStatement ps = getConnection().prepareStatement("UPDATE appointments SET Title=?, Description=?, Location=?, Type=?, Start=?, End=?, Create_Date=?, Created_By=?, Last_Update=?," +
                    "Last_Updated_By=?, Customer_ID=?, User_ID=?, Contact_ID=? WHERE Appointment_ID=?");
            ps.setString(1,a.getTitle());
            ps.setString(2,a.getDescription());
            ps.setString(3,a.getLocation());
            ps.setString(4,a.getType());
            ps.setTimestamp(5,Timestamp.valueOf(LocalDateTime.parse(a.getStartDateTime(),dtf)));
            ps.setTimestamp(6,Timestamp.valueOf(LocalDateTime.parse(a.getEndDateTime(),dtf)));
            ps.setTimestamp(7,Timestamp.from(Instant.now()));
            ps.setString(8, User.getUsername());
            ps.setTimestamp(9,Timestamp.from(Instant.now()));
            ps.setString(10,User.getUsername());
            ps.setString(11,String.valueOf(a.getCustomerId()));
            ps.setString(12, String.valueOf(a.getUserId()));
            ps.setString(13, contactID);
            ps.setString(14, a.getAppointmentId());
            ps.executeUpdate();
            return true;


        }catch (SQLException throwables){
            throwables.printStackTrace();
            return false;
        }
    }

    /** This method gets the MAX Customer_ID from the customers table. This method quries the database customers table for the MAX(Customer_ID)
     * @return maxId Returns the greatest customer ID from customers table.
     * @return "ERROR" Returns a string that says error if something went wrong.*/
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

    /** This method returns the MAX Appointment_ID. This method queries the database to get the MAX(Appointment_ID) from appointments table.
     * @return maxAppId A string with the MAX appointment_ID
     * @return "ERROR" Returns this string if something went wrong.*/
    public static String getNextAppointmentId(){
        try {
            String maxAppId;
            String sql = "SELECT MAX(Appointment_ID) FROM appointments;";
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ResultSet rSet = ps.executeQuery();
            rSet.next();
            maxAppId = rSet.getString("MAX(Appointment_ID)");
            return maxAppId;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return "ERROR";
        }
    }

    /** This method creates a generic customer to be updated by the user. This method inserts a generic customer into the customers table to be updated or deleted later.*/
    public static void createTempCustomer(){
        try{
            String sql = "INSERT INTO customers (Customer_Name, Division_ID) VALUES ('tempName','1')";
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.executeUpdate();
        }catch(SQLException throwables){
            throwables.printStackTrace();
        }
    }

    //Creates a temp appointment to be modified when a new appointment is added
    /** This method creates a generic apointment to be updated by the user. This method inserts a generic appointment into the appointments table to be updated or deleted later.
     * */
    public static void createTempAppointment(){
        try{
            //Testing this implementation. selects max(customer_id). same for contact_id.
            String sql = "insert into appointments (Title, Customer_ID, User_ID, Contact_ID) values ('temp appointment', (select max(Customer_ID) from customers), '" + User.getID() +"', (select max(Contact_ID) from contacts));";
            System.out.println(sql);
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** This method deletes a customer from the customers table. This method first deletes appointments matching the customer id, then deletes the customer from customers.
     * @param id The ID of the customer.*/
    public static void deleteCustomer(String id){
        try {

            String delApptsSQL = String.format("DELETE FROM appointments where Customer_ID= '%s'", id);
            PreparedStatement delAppPS = getConnection().prepareStatement(delApptsSQL);
            delAppPS.executeUpdate();

            String sql = "DELETE FROM customers WHERE Customer_ID='" + id + "';";
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.executeUpdate();

        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
    }

    /** This method deletes an appointment. This method executes a command that deletes an appointment from the appointments table.
     * @return  boolean Returns true if successful, otherwise false.*/
    public static boolean deleteAppointment(String id){
        try{
            String sql = "DELETE FROM appointments WHERE Appointment_ID='" + id + "';";
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /** This method checks of an appointment overlaps with another. This method queries the database and compares times with the given appointment. Returns a true or false if there is an overlap.
     * @param a An appointment.
     * @return collide True or false depending on whether or not there is an overlapping appointment.*/
    public static boolean checkOverlappingAppointments(Appointment a){
        boolean collide = false;
        try{
            String sql = String.format("SELECT Start, End from appointments WHERE Customer_ID = '%s'", a.getCustomerId() +
                    "' AND Appointment_ID NOT LIKE '" + a.getAppointmentId());
            System.out.println("Mod apointment sql: " + sql);
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            LocalDateTime newAppStart = LocalDateTime.parse(a.getStartDateTime(),DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            LocalDateTime newAppEnd = LocalDateTime.parse(a.getEndDateTime(),DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            while (rs.next()){
                System.out.println("RSNEST: " + rs.toString());
                System.out.println(rs.getString("Start"));
                if (!rs.wasNull()){
                    //LocalDateTime start = LocalDateTime.parse(rs.getString("Start"),DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    //LocalDateTime end = LocalDateTime.parse(rs.getString("End"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                    LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();


                    //Test
                    System.out.println("NEWAPPSTART: " + newAppStart);
                    System.out.println("NEWAPPEND: " + newAppEnd);
                    System.out.println("RS start: " + start);
                    System.out.println("RS end: " + end);


                    if ((newAppStart.isAfter(start) || newAppStart.isEqual(start)) && newAppStart.isBefore(end)){
                        System.out.println("OVerlap occured condition 1");
                        collide = true;
                        return collide;
                    }

                    else if (newAppEnd.isAfter(start) && (newAppEnd.isBefore(end) || newAppEnd.isEqual(end))){
                        System.out.println("Overlap occured condition 2");
                        collide = true;
                        return collide;
                    }

                    else if ((newAppStart.isBefore(start) || newAppStart.isEqual(start)) && (newAppEnd.isAfter(end) || newAppEnd.isEqual(end))){
                        System.out.println("Overlap occured condition 3");
                        collide = true;
                        return collide;
                    }
                    else{
                        collide = false;
                    }
                }



            }
        }catch (Exception e){
            e.printStackTrace();
            collide = true;
        }

        return collide;

    }

    /** This method checks to see if there is an appointment within 15 minutes of its execution. This method queries the database appointments table and
     * checks to see if an appointment occurs within 15 minutes.
     * @return boolean True if there is an appointment within 15 minutes, otherwise false.*/
    public static boolean checkFifteenMins() throws SQLException {
        System.out.println("Checking if there is an appointment within 15 minutes");
        String sql = "select * from appointments;";
        //ZonedDateTime zdt = Instant.now().atZone(ZoneId.of("America/New_York"));
        //LocalDateTime ldtEST = zdt.withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
        //System.out.println("LDTEST " + ldtEST.toString());
        LocalDateTime ldt = LocalDateTime.now();
        ZonedDateTime zdt = ldt.atZone(ZoneId.systemDefault());
        PreparedStatement ps = getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            String returnedTimes = rs.getString("Start");

            //LocalDateTime returnedTimesLDT = LocalDateTime.parse(returnedTimes, DateTimeFormatter.ofPattern("yyyy-M-d H:m:s"));
            LocalDateTime returnedTimesLDT = rs.getTimestamp("Start").toLocalDateTime();
            //LocalDateTime returnedTimesLDT = LocalDateTime.now().plusMinutes(10);
            //System.out.println("Confirmation 15 mins parsing correctly: " + returnedTimesLDT.toString());
            if (returnedTimesLDT.isAfter(zdt.toLocalDateTime()) && returnedTimesLDT.isBefore(zdt.toLocalDateTime().plusMinutes(15))){
                System.out.println("Appointment found. RETURNED TIME: " + returnedTimesLDT.toString());
                return true;
            }else{
                System.out.println("No appointments within 15 mins");



            }
        }
        return false;
    }

    /** This method gets types of appointments. This method queries the database appointments table to get the different types of appointments
     * that currently exist.
     * @return typesOL An observable list of types of appointments.*/
    public static ObservableList<String> getAppointmentTypes() throws SQLException {
        String sql = "SELECT * FROM appointments;";
        PreparedStatement ps = getConnection().prepareStatement(sql);
        ResultSet typesRS = ps.executeQuery();
        ObservableList<String> typesOL = FXCollections.observableArrayList();
        String returnedType;
        while (typesRS.next()){
            returnedType = typesRS.getString("Type");
            if (!typesOL.contains(returnedType)){
                typesOL.add(returnedType);
            }
        }
        return typesOL;
    }

    /** This method counts the number of a type of an appointment in given month. This method queries the database appointments table
     * and returns an int of the amount of an appointment type within a month.
     * @param m A string representing a month
     * @param t A string representing the type of appointment.
     * @return returnedCount The amount of types in a month.*/
    public static int getAmountOfType(String m, String t) throws SQLException {
        //String sql = String.format("SELECT COUNT(Appointment_ID) as COUNT FROM appointments WHERE Start LIKE '%-%%s-%' and Type = '%s';", m,t);
        String sql = "SELECT COUNT(Appointment_ID) as COUNT FROM appointments WHERE Start LIKE '%-%" + m + "-%' and TYPE = '" + t + "';";
        System.out.println("SQL FOR AMOUNT: "+ sql);
        PreparedStatement ps = getConnection().prepareStatement(sql);
        ResultSet countRS = ps.executeQuery();
        countRS.next();
        int returnedCount = countRS.getInt("COUNT");
        return returnedCount;
    }

    /** This method gets the names of contacts. This method queries the database contacts table to get an observable list of contact_name.
     * @return contactOL An observablelist of contact names.*/
    public static ObservableList getContacts() throws SQLException {
        ObservableList contactOL = FXCollections.observableArrayList();
        String sql = "SELECT * FROM contacts where Contact_ID NOT LIKE '999';";
        PreparedStatement ps = getConnection().prepareStatement(sql);
        ResultSet contacttRS = ps.executeQuery();
        while (contacttRS.next()){
            contactOL.add(contacttRS.getString("Contact_Name"));
        }
        return contactOL;
    }

    /** This method gets the appointments belonging to a certain contact. This method queries the database to get the appointments belonging to a named contact. Lambda expression filters observable list by contact name.
     * @param contactName A string of the name of a contact
     * @return contactAppointmentsOL An observablelist of appointments*/
    public static ObservableList getContactAppointments(String contactName) throws SQLException {
        ObservableList<Appointment> contactAppointmentsOL = FXCollections.observableArrayList();
        //DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        //String sql = String.format("SELECT * FROM appointments JOIN contacts ON appointments.Contact_ID = contacts.Contact_ID WHERE Contact_Name='%s';", contactName);
        String sql = "SELECT * FROM appointments JOIN contacts ON appointments.Contact_ID = contacts.Contact_ID;";
        PreparedStatement ps = getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Appointment a = new Appointment(rs.getString("Appointment_ID"), rs.getString("Title"),
                        rs.getString("Description"), rs.getString("Location"),
                        rs.getString("Contact_Name"), rs.getString("Type"),
                        /*rs.getString("Start")*/rs.getTimestamp("Start").toString(), /*rs.getString("End")*/rs.getTimestamp("End").toString(), rs.getInt("Customer_ID"),
                        rs.getInt("User_ID"));
            contactAppointmentsOL.add(a);

        }
        //return contactAppointmentsOL;
        return contactAppointmentsOL.filtered(appointment -> appointment.getContact().equals(contactName));
    }

    /** This method returns the number of appointments belonging to a contact in a certain year. This method queries the database appointments table
     * to return the COUNT of appointments belonging to a contact in a given year.
     * @param contact The name of the contact.
     * @param year A string of the year to be searched.
     * @return yearlyCount a String containing the COUNT of the query.*/
    public static String getYearlyContactCount(String year, String contact) throws SQLException {
        String sql = "select COUNT(Appointment_ID) as COUNT from appointments JOIN contacts on appointments.Contact_ID = contacts.Contact_ID where Contact_Name='" + contact +
                "' and Start LIKE '" + year + "-%-%';";
        PreparedStatement ps = getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        String yearlyCount = "0";
        while (rs.next()){
            yearlyCount = rs.getString("COUNT");

        }
        return yearlyCount;
    }

    /** This method gets the userID of a user. This method queries the database to get the User_ID from the users table, given a username.
     * @param name The username of a user.
     * @return id A string containing the user ID of the named user.*/
    public static String getCurrentUserID(String name) throws SQLException {
        String sql = "SELECT User_ID FROM users WHERE User_Name='" + name + "';";
        PreparedStatement ps = getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        String id = "";
        while (rs.next()){
            id = rs.getString("User_ID");
        }
        
        return id;
    }

    public static boolean doesCustomerExist(String id) throws SQLException {
        boolean matchFound = false;
        String sql = "Select * from customers where Customer_ID= '" + id + "';";
        PreparedStatement ps = getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()){

            if (id.equals(rs.getString("Customer_ID"))){
                matchFound = true;

            }else{
                matchFound = false;

            }
        }
        return matchFound;
    }

    public static boolean doesUserExist(String id) throws SQLException {
        boolean matchFound = false;
        String sql = "SELECT * FROM users where User_ID = '" + id + "';";
        PreparedStatement ps = getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            if (id.equals(rs.getString("User_ID"))){
                matchFound = true;
            }else{
                matchFound = false;
            }
        }
        return matchFound;
    }
}
