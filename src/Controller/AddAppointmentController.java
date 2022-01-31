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
import java.time.LocalDateTime;
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
    private ChoiceBox<Integer> sTimeMonth;

    @FXML
    private ChoiceBox<Integer> sTimeDay;

    @FXML
    private ChoiceBox<Integer> sTimeYear;

    @FXML
    private ChoiceBox<Integer> sTimeHr;

    @FXML
    private ChoiceBox<Integer> sTimeMin;

    @FXML
    private ChoiceBox<String> sTimeAMPM;

    @FXML
    private ChoiceBox<String> eTimeHr;

    @FXML
    private ChoiceBox<String> eTimeMin;

    @FXML
    private ChoiceBox<String> eTimeAMPM;

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
        String newAppContact = addAppointmentChoiceBox.getValue();
        String newAppType = addAppointmentTypeField.getText();
        //Start time/date
        int sMonth = sTimeMonth.getValue();
        int sDay = sTimeDay.getValue();
        int sYear = sTimeYear.getValue();
        int sHour = sTimeHr.getValue();
        int sMin = sTimeMin.getValue();
        String sAMPM = sTimeAMPM.getValue();
        //end time/date

        String eHour = eTimeHr.getValue();
        String eMin = eTimeMin.getValue();
        String eAMPM = eTimeAMPM.getValue();
        String newAppCustomerID = addAppointmentCustIDField.getText();
        String newAppUserID = addAppointmentUserIDField.getText();

        LocalDateTime ldt = LocalDateTime.of(sYear,sMonth, sDay ,sHour, sMin);
        System.out.println("ldt = " + ldt);


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addAppointmentAppIDField.setText(JDBC.getNextAppointmentId());
        addAppointmentAppIDField.setEditable(false);
        addAppointmentChoiceBox.getItems().addAll("Anika Costa", "Daniel Garcia", "Li Lee");
        sTimeMonth.getItems().addAll(1 , 2 , 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
        sTimeDay.getItems().addAll(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,
                26,27,28,29,30,31);
        sTimeYear.getItems().addAll(2022);
        sTimeHr.getItems().addAll(8,9,10,11,12,1,2,3,4,5,6,7,8,9,10);
        sTimeMin.getItems().addAll(00, 15,30,45);
    }
}
