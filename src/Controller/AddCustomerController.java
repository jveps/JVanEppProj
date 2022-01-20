package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import DAO.JDBC;
import javafx.stage.Stage;

public class AddCustomerController implements Initializable {

    @FXML
    private TextField addCustIdField;

    @FXML
    private TextField addCustNameField;

    @FXML
    private TextField addCustAddyField;

    @FXML
    private TextField addCustPostalCodeField;

    @FXML
    private TextField addCustPhoneField;

    @FXML
    private ComboBox<String> addCustCountryComboBox;

    @FXML
    private ComboBox<String> addCustCityComboBox;

    @FXML
    private Button addCustOkButton;

    @FXML
    private Button addCustCancelButton;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addCustIdField.setEditable(false);

        //Add Countries to combobox
        addCustCountryComboBox.setItems(JDBC.getAllCountry());
        addCustCityComboBox.setDisable(true);

        //Add next ID to text field
        addCustIdField.setText(JDBC.getNextCustomerId());
    }

    @FXML
    void addCustomerComBoxAction(ActionEvent event) {
        String a = addCustCountryComboBox.getValue();
        System.out.println(a);
        if (a != null){
            addCustCityComboBox.setDisable(false);
        }

        addCustCityComboBox.setItems(JDBC.getCountryDivisions(a));
    }

    @FXML
    void addCustOkButtonPressed(ActionEvent event) {
        String newCustId = addCustIdField.getText();
        String newCustName = addCustNameField.getText();
        String newCustAddress = addCustAddyField.getText();
        String newCustCountry = addCustCountryComboBox.getValue();
        String newCustDivision = addCustCityComboBox.getValue();
        String newCustPostalCode = addCustPostalCodeField.getText();
        String newCustPhoneNum = addCustPhoneField.getText();

        if (JDBC.addCustomer(newCustId, newCustName, newCustAddress, newCustCountry, newCustDivision, newCustPostalCode, newCustPhoneNum)){
            System.out.println("Successful!");
        };
    }

    //Removes the temp customer and returns to record overview
    @FXML
    void addCustCancelButtonPressed(ActionEvent event) throws IOException {
        JDBC.deleteCustomer(addCustIdField.getText());
        Stage stage;
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(getClass().getResource("/View/RecordOverview.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
}
