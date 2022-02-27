package Controller;

import DAO.JDBC;
import Model.Appointment;
import Model.Customer;
import Model.User;
import javafx.beans.property.Property;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

/**This is the controller class for the record overview window.
 * @author Jessie Van Epps*/

public class RecordOverviewController implements Initializable {

    @FXML
    private Button quitButton;

    @FXML
    private Button addCustomerButton;

    @FXML
    private TableView<Customer> CustomerTable;

    @FXML
    private TableColumn<Customer, Integer> customerIdCol;

    @FXML
    private TableColumn<Customer, String> customerNameCol;

    @FXML
    private TableColumn<Customer, String> customerAddressCol;

    @FXML
    private TableColumn<Customer, String> customerCountryCol;

    @FXML
    private TableColumn<Customer, String> customerDivCol;

    @FXML
    private TableColumn<Customer, String> customerPostalCodeCol;

    @FXML
    private TableColumn<Customer, String> customerPhoneCol;

    @FXML
    private TableView<Appointment> AppointmentTable;

    @FXML
    private TableColumn<Appointment, Integer> appointmentIdCol;

    @FXML
    private TableColumn<Appointment, String> appointmentTitleCol;

    @FXML
    private TableColumn<Appointment, String> appointmentDescrCol;

    @FXML
    private TableColumn<Appointment, String> appointmentLocCol;

    @FXML
    private TableColumn<Appointment, String> appointmentContactCol;

    @FXML
    private TableColumn<Appointment, String> appointmentTypeCol;

    @FXML
    private TableColumn<Appointment, String> appointmentStartCol;

    @FXML
    private TableColumn<Appointment, String> appointmentEndCol;

    @FXML
    private TableColumn<Appointment, Integer> appointmentCustIdCol;

    @FXML
    private TableColumn<Appointment, Integer> appointmentUserIdCol;

    //Create observable list for customer table
    private ObservableList<Customer> customerObsList = FXCollections.observableArrayList();

    //Observable list for appointments table
    private ObservableList<Appointment> appointmentObsList = FXCollections.observableArrayList();

    @FXML
    private RadioButton allRadioButton;

    @FXML
    private RadioButton weeklyRadioButton;

    @FXML
    private RadioButton monthlyRadioButton;

    ToggleGroup tg = new ToggleGroup();

    Scene scene;
    Stage stage;


    /**This method adds data to the customer table. A query is executed that gets all customers from the database. */
    public void fillCustomerTable(){
        try {
            //String sql = "SELECT * FROM customers";
            String sql = "select * from customers join first_level_divisions on customers.Division_ID = first_level_divisions.Division_ID join countries on first_level_divisions.Country_ID = countries.Country_ID AND customers.Customer_ID NOT IN (1);";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            customerObsList.clear();

            while (rs.next()){

                customerObsList.add(new Customer(rs.getInt("Customer_ID"), rs.getString("Customer_Name"),rs.getString("Address"),rs.getString("Country"),rs.getString("Division"),rs.getString("Postal_Code"),rs.getString("Phone")));
                //System.out.println("Country and Divs: " + rs.getString("Country") + rs.getString("Division"));
            }
        }

        catch (SQLException e) {
            e.printStackTrace();
        }


        CustomerTable.setItems(customerObsList);
    }

    /** This method adds data to the appointments table. A query is executed that returns all appointments from the database.*/
    public void fillAppointmentTable(){

        try {
            //String sql = "SELECT * FROM appointments";
            String sql = "SELECT * from appointments JOIN contacts ON appointments.Contact_ID = contacts.Contact_ID";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            appointmentObsList.clear();

            while (rs.next()){
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String startUTCTime = rs.getString("Start");
                String endUTCTime = rs.getString("End");
                LocalDateTime startUTC = LocalDateTime.parse(startUTCTime,dtf);
                LocalDateTime endUTC = LocalDateTime.parse(endUTCTime,dtf);

                //ZonedDateTime zonedStartLDT = startUTC.atZone(ZoneId.systemDefault());
                //ZonedDateTime zonedEndLDT = endUTC.atZone(ZoneId.systemDefault());
                //TEST
                ZonedDateTime bzonedStartLDT = startUTC.atZone(ZoneId.systemDefault());
                ZonedDateTime bzonedEndLDT = endUTC.atZone(ZoneId.systemDefault());

                ZonedDateTime zonedStartLDT = bzonedStartLDT.withZoneSameInstant(ZoneId.systemDefault());
                ZonedDateTime zonedEndLDT = bzonedEndLDT.withZoneSameInstant(ZoneId.systemDefault());


                appointmentObsList.add(new Appointment(rs.getString("Appointment_ID"), rs.getString("Title"),
                        rs.getString("Description"), rs.getString("Location"),
                        rs.getString("Contact_Name"), rs.getString("Type"),
                        /*zonedStartLDT.format(dtf)*/rs.getTimestamp("Start").toString(), /*rs.getString("End")zonedEndLDT.format(dtf)*/rs.getTimestamp("End").toString(), rs.getInt("Customer_ID"),
                        rs.getInt("User_ID")));
            }
        }

        catch (SQLException e) {
            e.printStackTrace();
        }

        //Testing. Will need to order by month/week.
        AppointmentTable.setItems(appointmentObsList);
    }

    /**This method controls what happens when quit is pressed.
     * When pressed, this button closes the application.*/
    @FXML
    void quitButtonPressed(ActionEvent event) {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setTitle("Quit");
        a.setContentText("Are you sure you want to quit?");
        a.showAndWait();
        if (a.getResult() == ButtonType.OK){
            JDBC.closeConnection();
            System.exit(0);
        }

    }

    /**This method controls what happens when the add customer button is pressed.
     * This method opens the add customer window.
     * */
    @FXML
    void addCustomerButtonPressed(ActionEvent event) {
        try {
            JDBC.createTempCustomer();
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = FXMLLoader.load(getClass().getResource("/View/addCustomer.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**This method controls what happens when the add appointment button is pressed.
     * This method opens the add appointment window.
     * */
    @FXML
    void addAppointmentButtonPressed(ActionEvent event) throws IOException {
        JDBC.createTempAppointment();
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(getClass().getResource("/View/addAppointment.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** This method controls what happens when the modify customer button is pressed.
     * * This method opens the modify customer window and sends selected customer to it.
     * */
    @FXML
    void modifyCustomerButtonPressed(ActionEvent event) throws IOException{
        if (CustomerTable.getSelectionModel().getSelectedItem() == null){
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("ERROR");
            a.setContentText("Please select a customer");
            a.showAndWait();
        }else{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/View/modifyCustomer.fxml"));
            loader.load();
            ModifyCustomerController modCustCont = loader.getController();
            modCustCont.sendCustomer(CustomerTable.getSelectionModel().getSelectedItem());

            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /**This method controls the modify appointments button. The modify appointments screen is opened and the selected appointment is sent to it.*/
    @FXML
    void modAppointmentButtonPressed(ActionEvent event) throws IOException {
        if (AppointmentTable.getSelectionModel().getSelectedItem() == null) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("ERROR");
            a.setContentText("Please select an appointment");
            a.showAndWait();
        }else{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/View/modifyAppointment.fxml"));
            loader.load();
            ModifyAppointmentController modAppCont = loader.getController();
            modAppCont.sendAppointment(AppointmentTable.getSelectionModel().getSelectedItem());

            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /**This controls the delecte customer button. This deletes a customer when the button is pressed.*/
    @FXML
    void deleteCustomerButtonPressed(ActionEvent event) throws IOException {
        if (CustomerTable.getSelectionModel().getSelectedItem() == null){
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("ERROR");
            a.setContentText("Please select a customer");
            a.showAndWait();
        }else{
            Alert a = new Alert(Alert.AlertType.CONFIRMATION);
            a.setTitle("Alert");
            a.setContentText("Are you sure you want to delete this customer?");
            a.showAndWait();
            if(a.getResult() == ButtonType.OK) {
                String s = Integer.toString(CustomerTable.getSelectionModel().getSelectedItem().getId());
                JDBC.deleteCustomer(s);
                fillCustomerTable();
                fillAppointmentTable();

            }
        }
    }

    /** This controls the actions of the delete appointment button. This deletes the selected appointment from the database.*/
    @FXML
    void delAppointmentButtonPressed(ActionEvent event) {
        if (AppointmentTable.getSelectionModel().getSelectedItem() == null) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("ERROR");
            a.setContentText("Please select a appointment");
            a.showAndWait();

        } else {
            Alert a = new Alert(Alert.AlertType.CONFIRMATION);
            a.setTitle("Alert");
            a.setContentText("Are you sure you want to delete this appointment?");
            a.showAndWait();

            if (a.getResult() == ButtonType.OK) {
                String s = AppointmentTable.getSelectionModel().getSelectedItem().getAppointmentId();
                String delType = AppointmentTable.getSelectionModel().getSelectedItem().getType();
                if (JDBC.deleteAppointment(s)) {
                    fillAppointmentTable();

                    Alert deletedAppAlert = new Alert(Alert.AlertType.INFORMATION);
                    deletedAppAlert.setTitle("Deleted Appointment Information");
                    deletedAppAlert.setContentText(String.format("Deleted AppointmentID: %s, Deleted Appointment Type: %s", s, delType));
                    deletedAppAlert.showAndWait();

                }else{
                    Alert deletedAppFail = new Alert(Alert.AlertType.ERROR);
                    deletedAppFail.setTitle("ERROR");
                    deletedAppFail.setContentText("Something went wrong");
                    deletedAppFail.showAndWait();
                }
            }
        }
    }

    /**This controls the behavior of the reports button. This button opens the additional reports screen. */
    @FXML
    void reportsButtonPressed(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(getClass().getResource("/View/additionalReports.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** This method controls the weekly appointments radio button. This lambda expression looks for appointments within one week of the current date,
     * then fills the table with them.*/
    @FXML
    void weeklyAppointmentsSelected(ActionEvent event) {

        ObservableList<Appointment> currWeekApptsObsList = FXCollections.observableArrayList();
        LocalDateTime currDate = LocalDateTime.now();

        appointmentObsList.forEach(currWeek -> {
            LocalDateTime currMonthAppts = LocalDateTime.parse(currWeek.getStartDateTime(), DateTimeFormatter.ofPattern("yyyy-M-d H:m:s.S"));
            System.out.println(currMonthAppts.format(DateTimeFormatter.ofPattern("yyyy-M-d")));
            if (currMonthAppts.isAfter(currDate) && currMonthAppts.isBefore(currDate.plusWeeks(1))){
                System.out.println("Weekly appt found");
                currWeekApptsObsList.add(currWeek);
            }

        });
        AppointmentTable.setItems(currWeekApptsObsList);
    }

    /**This method controls the behavior of the all appointments radio button. This calls the fillAppointmentTable() method and adds all appointments
     * to the table. */
    @FXML
    void allAppointmentsSelected(ActionEvent event) {
        fillAppointmentTable();
    }

    /**This method controls the behavior of the monthly appointments radio button. The lambda expression within this method looks for appointments within
     * the current month and adds them to the table. */
    @FXML
    void monthlyAppointmentsSelected(ActionEvent event) throws SQLException {

        LocalDateTime currDate = LocalDateTime.now();
        ObservableList<Appointment> currMonthApptsObsList = FXCollections.observableArrayList();

        appointmentObsList.forEach(dayTime -> {
            LocalDateTime currMonthAppts = LocalDateTime.parse(dayTime.getStartDateTime(), DateTimeFormatter.ofPattern("yyyy-M-d H:m:s.S"));

            if (currDate.getMonthValue() == currMonthAppts.getMonthValue()){

                currMonthApptsObsList.add(dayTime);
            }

        });

        AppointmentTable.setItems(currMonthApptsObsList);

    }

    /**This method calls the methods that fill the appointment and customer tables. Also adds radio buttons to toggle group. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Test populate customer table
        CustomerTable.setItems(customerObsList);
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("custName"));
        customerAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        customerCountryCol.setCellValueFactory(new PropertyValueFactory<>("country"));
        customerDivCol.setCellValueFactory(new PropertyValueFactory<>("division"));
        customerPostalCodeCol.setCellValueFactory(new PropertyValueFactory<>("zipCode"));
        customerPhoneCol.setCellValueFactory(new PropertyValueFactory<>("phoneNum"));

        //Populate appointment table
        AppointmentTable.setItems(appointmentObsList);
        appointmentIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        appointmentTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        appointmentDescrCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        appointmentLocCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        appointmentContactCol.setCellValueFactory(new PropertyValueFactory<>("contact"));
        appointmentTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        appointmentStartCol.setCellValueFactory(new PropertyValueFactory<>("startDateTime"));
        appointmentEndCol.setCellValueFactory(new PropertyValueFactory<>("endDateTime"));
        appointmentCustIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        appointmentUserIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));

        fillCustomerTable();
        fillAppointmentTable();

        //Add radio buttons to toggle group
        allRadioButton.setToggleGroup(tg);
        weeklyRadioButton.setToggleGroup(tg);
        monthlyRadioButton.setToggleGroup(tg);
        allRadioButton.setSelected(true);
        allRadioButton.requestFocus();



    }
}
