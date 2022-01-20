package Controller;

import DAO.JDBC;
import Model.Customer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class ModifyCustomerController implements Initializable {

    @FXML
    private TextField modCustIdField;

    @FXML
    private TextField modCustNameField;

    @FXML
    private TextField modCustAddyField;

    @FXML
    private TextField modCustPostalCodeField;

    @FXML
    private TextField modCustPhoneField;

    @FXML
    private Button modCustOkButton;

    @FXML
    private Button modCustCancelButton;

    @FXML
    private ComboBox<String> modCustCountryComboBox;

    @FXML
    private ComboBox<String> modCustCityComboBox;

    @FXML
    void modCustCancelButtonPressed(ActionEvent event) {

    }

    @FXML
    void modCustOkButtonPressed(ActionEvent event) {

    }

    @FXML
    void modCustomerComBoxAction(ActionEvent event) {
        String a = modCustCountryComboBox.getValue();
        System.out.println(a);
        if (a != null){
            modCustCityComboBox.setDisable(false);
        }
        modCustCityComboBox.valueProperty().set(null);
        modCustCityComboBox.setItems(JDBC.getCountryDivisions(a));
    }

    public void sendCustomer(Customer c){
        modCustIdField.setText(String.valueOf(c.getId()));
        modCustNameField.setText(c.getCustName());
        modCustAddyField.setText(c.getAddress());
        modCustCountryComboBox.setValue(c.getCountry());
        modCustCityComboBox.setValue(c.getDivision());
        modCustPostalCodeField.setText(c.getZipCode());
        modCustPhoneField.setText(c.getPhoneNum());

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        modCustCountryComboBox.setItems(JDBC.getAllCountry());
        modCustIdField.setEditable(false);
    }
}
