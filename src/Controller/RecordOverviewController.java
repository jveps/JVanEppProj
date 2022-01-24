package Controller;

import DAO.JDBC;
import Model.Appointment;
import Model.Customer;
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
import java.util.ResourceBundle;

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

    Scene scene;
    Stage stage;

    public void fillCustomerTable(){
        try {
            //String sql = "SELECT * FROM customers";
            String sql = "select * from customers join first_level_divisions on customers.Division_ID = first_level_divisions.Division_ID join countries on first_level_divisions.Country_ID = countries.Country_ID;";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            customerObsList.clear();

            while (rs.next()){

                customerObsList.add(new Customer(rs.getInt("Customer_ID"), rs.getString("Customer_Name"),rs.getString("Address"),rs.getString("Country"),rs.getString("Division"),rs.getString("Postal_Code"),rs.getString("Phone")));
                System.out.println("Country and Divs: " + rs.getString("Country") + rs.getString("Division"));
            }
        }

        catch (SQLException e) {
            e.printStackTrace();
        }

        //Testing. May need to be changed later.
        CustomerTable.setItems(customerObsList);
    }

    //Add data to appointments table
    public void fillAppointmentTable(){
        try {
            //String sql = "SELECT * FROM appointments";
            String sql = "SELECT * from appointments JOIN contacts ON appointments.Contact_ID = contacts.Contact_ID";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            appointmentObsList.clear();

            while (rs.next()){
                appointmentObsList.add(new Appointment(rs.getInt("Appointment_ID"), rs.getString("Title"),
                        rs.getString("Description"), rs.getString("Location"),
                        rs.getString("Contact_Name"), rs.getString("Type"),
                        rs.getString("Start"), rs.getString("End"), rs.getInt("Customer_ID"),
                        rs.getInt("User_ID")));
            }
        }

        catch (SQLException e) {
            e.printStackTrace();
        }

        //Testing. Will need to order by month/week.
        AppointmentTable.setItems(appointmentObsList);
    }

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

    @FXML
    void addAppointmentButtonPressed(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = FXMLLoader.load(getClass().getResource("/View/addAppointment.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
    }

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

            }
        }
    }

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



    }
}
