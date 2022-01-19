package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;
import DAO.JDBC;

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
}
