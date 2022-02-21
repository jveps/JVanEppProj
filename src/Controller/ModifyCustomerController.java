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
/** This class controls the modify customer screen.
 * @author Jessie Van Epps*/
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

    /** This controls the modify customer cancel button. This returns the user to the record overview screen.*/
    @FXML
    void modCustCancelButtonPressed(ActionEvent event) throws IOException {
        Stage stage;
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(getClass().getResource("/View/RecordOverview.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** This controls the modify customer OK button. Checks to see if fields are blank. Adds modified customer data to database.*/
    @FXML
    void modCustOkButtonPressed(ActionEvent event) throws IOException {
        String newCustId = modCustIdField.getText();
        String newCustName = modCustNameField.getText();
        String newCustAddress = modCustAddyField.getText();
        String newCustCountry = modCustCountryComboBox.getValue();
        String newCustDivision = modCustCityComboBox.getValue();
        String newCustPostalCode = modCustPostalCodeField.getText();
        String newCustPhoneNum = modCustPhoneField.getText();



        if (newCustName.isBlank()  || newCustAddress.isBlank() || newCustCountry.isBlank() || newCustDivision.isBlank() ||
        newCustPostalCode.isBlank() || newCustPhoneNum.isBlank()){
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

    /** Controls the action of the country combo box. Queries the database to get country divisions.*/
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

    /** Gets selected customer from record overview screen. Populates modify customer data with selected customer data.*/
    public void sendCustomer(Customer c){
        modCustIdField.setText(String.valueOf(c.getId()));
        modCustNameField.setText(c.getCustName());
        modCustAddyField.setText(c.getAddress());
        modCustCityComboBox.setItems(JDBC.getCountryDivisions(c.getCountry()));
        modCustCountryComboBox.getSelectionModel().select(c.getCountry());
        modCustCityComboBox.getSelectionModel().select(c.getDivision());

        modCustPostalCodeField.setText(c.getZipCode());
        modCustPhoneField.setText(c.getPhoneNum());

    }

    /** Initializes the class. This method sets the choices in the country combobox and sets custID field to not be editable.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        modCustCountryComboBox.setItems(JDBC.getAllCountry());
        modCustIdField.setEditable(false);
    }
}
