package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import DAO.JDBC;
import javafx.stage.Stage;

/**This class controls the add customer button.
 * @author Jessie Van Epps
 * */
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


    /** This method initializes the add customer screen. Sets Customer ID text field to not be edited. Adds countries to combobox. Gets next custID into text field. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addCustIdField.setEditable(false);

        //Add Countries to combobox
        addCustCountryComboBox.setItems(JDBC.getAllCountry());
        addCustCityComboBox.setDisable(true);

        //Add next ID to text field
        addCustIdField.setText(JDBC.getNextCustomerId());
    }

    /** This method controls the action of the country combobox. This queries the database to get the country divisions from selected country.*/
    @FXML
    void addCustomerComBoxAction(ActionEvent event) {
        String a = addCustCountryComboBox.getValue();

        if (a != null){
            addCustCityComboBox.setDisable(false);
        }

        addCustCityComboBox.setItems(JDBC.getCountryDivisions(a));
    }

    /** This controls the OK button on the add customer screen. This method adds the customer data to the database.*/
    @FXML
    void addCustOkButtonPressed(ActionEvent event) throws IOException {
        String newCustId = addCustIdField.getText();
        String newCustName = addCustNameField.getText();
        String newCustAddress = addCustAddyField.getText();
        String newCustCountry = addCustCountryComboBox.getValue();
        String newCustDivision = addCustCityComboBox.getValue();
        String newCustPostalCode = addCustPostalCodeField.getText();
        String newCustPhoneNum = addCustPhoneField.getText();

        if (addCustIdField.getText().isBlank() || addCustNameField.getText().isBlank() || addCustAddyField.getText().isBlank() ||
        addCustCountryComboBox.getSelectionModel().isEmpty() || addCustCityComboBox.getSelectionModel().isEmpty() ||
        addCustPostalCodeField.getText().isBlank() || addCustPhoneField.getText().isBlank()){
            Alert blankFields = new Alert(Alert.AlertType.ERROR);
            blankFields.setTitle("ERROR");
            blankFields.setContentText("Please ensure all fields are filled");
            blankFields.showAndWait();
        }else{
            Alert a = new Alert(Alert.AlertType.CONFIRMATION);
            a.setTitle("Add new customer");
            a.setContentText("Are you sure you want to add this customer?");
            a.showAndWait();
            if (a.getResult() == ButtonType.OK){
                if (JDBC.addCustomer(newCustId, newCustName, newCustAddress, newCustCountry, newCustDivision, newCustPostalCode, newCustPhoneNum)) {

                    Stage stage;
                    stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                    Parent scene = FXMLLoader.load(getClass().getResource("/View/RecordOverview.fxml"));
                    stage.setScene(new Scene(scene));
                    stage.show();
                }
            }
        }


    }

    /** This controls the actions of the cancel button. This method deletes the temp customer created when the add customer button was pressed.*/
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
