package Controller;

import DAO.JDBC;
import Model.Customer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
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
    void modCustCancelButtonPressed(ActionEvent event) throws IOException {
        Stage stage;
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(getClass().getResource("/View/RecordOverview.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void modCustOkButtonPressed(ActionEvent event) throws IOException {
        String newCustId = modCustIdField.getText();
        String newCustName = modCustNameField.getText();
        String newCustAddress = modCustAddyField.getText();
        String newCustCountry = modCustCountryComboBox.getValue();
        String newCustDivision = modCustCityComboBox.getValue();
        String newCustPostalCode = modCustPostalCodeField.getText();
        String newCustPhoneNum = modCustPhoneField.getText();

        //There is currently a bug in this if statement. The default selection in the comboboxes are being detected as empty right now.
        if (newCustName.trim().isEmpty()  || newCustAddress.trim().isEmpty() || modCustCityComboBox.getSelectionModel().isEmpty() || modCustCityComboBox.getSelectionModel().isEmpty() ||
        newCustPostalCode.trim().isEmpty() || newCustPhoneNum.trim().isEmpty()){
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("ERROR");
            a.setContentText("Please ensure all fields are filled");
            a.showAndWait();
        }

        else{

            Alert a = new Alert(Alert.AlertType.CONFIRMATION);
            a.setTitle("Alert");
            a.setContentText("Are you sure you want to update this customer?");
            a.showAndWait();

            if (a.getResult() == ButtonType.OK) {
                JDBC.addCustomer(newCustId, newCustName, newCustAddress, newCustCountry, newCustDivision, newCustPostalCode, newCustPhoneNum);
            }
            //Open RecordOverview screen
            Stage stage;
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            Parent scene = FXMLLoader.load(getClass().getResource("/View/RecordOverview.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        };
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
