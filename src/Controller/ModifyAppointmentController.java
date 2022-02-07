package Controller;

import Model.Appointment;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class ModifyAppointmentController implements Initializable {
    @FXML
    private TextField modAppointmentAppIDField;

    @FXML
    private TextField modAppointmentTitleField;

    @FXML
    private TextField modAppointmentDescriptionField;

    @FXML
    private TextField modAppointmentLocationField;

    @FXML
    private TextField modAppointmentTypeField;

    @FXML
    private TextField modAppointmentCustIDField;

    @FXML
    private TextField modAppointmentUserIDField;

    @FXML
    private ChoiceBox<String> modAppointmentChoiceBox;

    @FXML
    private ChoiceBox<Integer> sTimeMonth;

    @FXML
    private ChoiceBox<Integer> sTimeDay;

    @FXML
    private ChoiceBox<Integer> sTimeHr;

    @FXML
    private ChoiceBox<Integer> sTimeMin;

    @FXML
    private ChoiceBox<String> sTimeAMPM;

    @FXML
    private ChoiceBox<Integer> eTimeHr;

    @FXML
    private ChoiceBox<Integer> eTimeMin;

    @FXML
    private ChoiceBox<String> eTimeAMPM;

    @FXML
    private ChoiceBox<Integer> sTimeYear;

    @FXML
    void modAppointmentCancelButtonPressed(ActionEvent event) throws IOException {
        Stage stage;
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(getClass().getResource("/View/RecordOverview.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void modAppointmentOkButtonPressed(ActionEvent event) {

    }

    public void sendAppointment(Appointment a){
        modAppointmentAppIDField.setText(a.getAppointmentId());
        modAppointmentTitleField.setText(a.getTitle());
        modAppointmentDescriptionField.setText(a.getDescription());
        modAppointmentLocationField.setText(a.getLocation());
        modAppointmentChoiceBox.getSelectionModel().select(a.getContact());
        modAppointmentTypeField.setText(a.getType());
        LocalDateTime sLDT = LocalDateTime.parse(a.getStartDateTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime eLDT = LocalDateTime.parse(a.getEndDateTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        //Date

        sTimeMonth.setValue(sLDT.getMonthValue());

        //sTimeDay.getSelectionModel().select(sLDT.getDayOfMonth());
        sTimeDay.setValue(sLDT.getDayOfMonth());

        //sTimeYear.getSelectionModel().select(sLDT.getYear());
        sTimeYear.setValue(sLDT.getYear());
        //Start time
        //sTimeHr.getSelectionModel().select(sLDT.getHour());
        sTimeHr.setValue(sLDT.getHour());
        //sTimeMin.getSelectionModel().select(sLDT.getMinute());
        sTimeMin.setValue(sLDT.getMinute());
        //Get AM or PM
        if (sLDT.getHour() > 11){
            sTimeAMPM.getSelectionModel().select("PM");
        }
        else{
            sTimeAMPM.getSelectionModel().select("AM");
        }
        //end time
        modAppointmentCustIDField.setText(String.valueOf(a.getCustomerId()));
        modAppointmentUserIDField.setText(String.valueOf(a.getUserId()));


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        modAppointmentAppIDField.setEditable(false);
        modAppointmentChoiceBox.getItems().addAll("Anika Costa", "Daniel Garcia", "Li Lee");
        sTimeMonth.getItems().addAll(1 , 2 , 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
        sTimeDay.getItems().addAll(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,
                26,27,28,29,30,31);
        sTimeYear.getItems().addAll(2020, 2021, 2022);
        sTimeHr.getItems().addAll(8,9,10,11,12,1,2,3,4,5,6,7,8,9,10);
        sTimeMin.getItems().addAll(00, 15,30,45);
        sTimeAMPM.getItems().addAll("AM", "PM");
        eTimeHr.getItems().addAll(8,9,10,11,12,1,2,3,4,5,6,7,8,9,10);
        eTimeMin.getItems().addAll(00, 15,30,45);
        eTimeAMPM.getItems().addAll("AM", "PM");
    }
}
