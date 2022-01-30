package Controller;

import DAO.JDBC;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddAppointmentController implements Initializable {
    @FXML
    private TextField addAppointmentAppIDField;

    @FXML
    private TextField addAppointmentTitleField;

    @FXML
    private TextField addAppointmentDescriptionField;

    @FXML
    private TextField addAppointmentLocationField;

    @FXML
    private TextField addAppointmentTypeField;

    @FXML
    private TextField addAppointmentStartTimeField;

    @FXML
    private TextField addAppointmentendTimeField;

    @FXML
    private TextField addAppointmentCustIDField;

    @FXML
    private TextField addAppointmentUserIDField;

    @FXML
    private ChoiceBox<String> addAppointmentChoiceBox;

    @FXML
    void addAppointmentCancelButtonPressed(ActionEvent event) throws IOException {
        JDBC.deleteAppointment(addAppointmentAppIDField.getText());
        Stage stage;
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(getClass().getResource("/View/RecordOverview.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void addAppointmentOkButtonPressed(ActionEvent event) {
        String newAppID = addAppointmentAppIDField.getText();
        String newAppTitle = addAppointmentTitleField.getText();
        String newAppDescription = addAppointmentDescriptionField.getText();
        String newAppLocation = addAppointmentLocationField.getText();
        //Area reserved for contact drop down
        String newAppContact = addAppointmentChoiceBox.getValue();
        String newAppType = addAppointmentTypeField.getText();
        //Start time/date
        //end time/date
        String newAppCustomerID = addAppointmentCustIDField.getText();
        String newAppUserID = addAppointmentUserIDField.getText();


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addAppointmentAppIDField.setText(JDBC.getNextAppointmentId());
        addAppointmentAppIDField.setEditable(false);
        addAppointmentChoiceBox.getItems().addAll("Anika Costa", "Daniel Garcia", "Li Lee");
    }
}
