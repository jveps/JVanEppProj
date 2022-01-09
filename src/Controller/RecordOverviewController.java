package Controller;

import DAO.JDBC;
import Model.Customer;
import javafx.beans.property.Property;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

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
    private TableColumn<Customer, String> customerPostalCodeCol;

    @FXML
    private TableColumn<Customer, String> customerPhoneCol;

    //Create observable list for customer table
    private ObservableList<Customer> customerObsList = FXCollections.observableArrayList();

    @FXML
    void addCustomerButtonPress(ActionEvent event) {

    }

    public void fillCustomerTable(){
        try {
            String sql = "SELECT * FROM customers";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            customerObsList.clear();

            while (rs.next()){
                System.out.println("Customer Names");
                System.out.println(rs.getString("Customer_Name"));
                customerObsList.add(new Customer(rs.getInt("Customer_ID"), rs.getString("Customer_Name"),rs.getString("Address"),rs.getString("Postal_Code"),rs.getString("Phone")));
            }
        }

        catch (SQLException e) {
            e.printStackTrace();
        }

        //Testing. May need to be changed later.
        CustomerTable.setItems(customerObsList);
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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Test populate table
        CustomerTable.setItems(customerObsList);
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("custName"));
        customerAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        customerPostalCodeCol.setCellValueFactory(new PropertyValueFactory<>("zipCode"));
        customerPhoneCol.setCellValueFactory(new PropertyValueFactory<>("phoneNum"));

        fillCustomerTable();



    }
}
