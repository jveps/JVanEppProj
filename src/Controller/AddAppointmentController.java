package Controller;

import DAO.JDBC;
import Model.Appointment;
import Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
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
    private ChoiceBox<Integer> eTimeHr;

    @FXML
    private ChoiceBox<Integer> eTimeMin;

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
    void addAppointmentOkButtonPressed(ActionEvent event) throws IOException {

        //Check if fields are blank
        if (addAppointmentTitleField.getText().isBlank() || addAppointmentDescriptionField.getText().isBlank() || addAppointmentLocationField.getText().isBlank() ||
                addAppointmentChoiceBox.getSelectionModel().isEmpty() || addAppointmentTypeField.getText().isBlank() || sTimeMonth.getSelectionModel().isEmpty() ||
                sTimeDay.getSelectionModel().isEmpty() || sTimeYear.getSelectionModel().isEmpty() || sTimeHr.getSelectionModel().isEmpty() ||
                sTimeMin.getSelectionModel().isEmpty() || sTimeAMPM.getSelectionModel().isEmpty() || eTimeHr.getSelectionModel().isEmpty() ||
                eTimeAMPM.getSelectionModel().isEmpty() || addAppointmentCustIDField.getText().isBlank() || addAppointmentUserIDField.getText().isBlank()){
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("ERROR");
            a.setContentText("Please ensure all fields are filled");
            a.showAndWait();
        }
        else{
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

            int eHour = eTimeHr.getValue();
            int eMin = eTimeMin.getValue();
            String eAMPM = eTimeAMPM.getValue();
            String newAppCustomerID = addAppointmentCustIDField.getText();
            String newAppUserID = addAppointmentUserIDField.getText();

            //AM - PM add 12
            if (sAMPM == "PM" && sHour != 12){
                sHour += 12;
            }
            else if (eAMPM == "PM" && eHour != 12){
                eHour += 12;
            }

            LocalDateTime ldt = LocalDateTime.of(sYear,sMonth, sDay ,sHour, sMin);
            LocalDateTime edt = LocalDateTime.of(sYear,sMonth,sDay,eHour, eMin);

            //Check if time is within operating hours
            LocalDateTime opHoursOpen = LocalDateTime.of(sYear,sMonth,sDay,8,0);
            LocalDateTime opHoursClose = LocalDateTime.of(sYear,sMonth,sDay,22,0);

            //if (ldt.getHour() < 8 || edt.getHour() > 22 || (edt.getHour() == 22 && edt.getMinute() > 0));
            if (ldt.isBefore(opHoursOpen) || edt.isAfter(opHoursClose)){
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("Error");
                a.setContentText("Appointment is outside of business hours");
                a.showAndWait();
            }
            else{
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String formLDT = ldt.atOffset(ZoneOffset.UTC).format(dtf);
                String formEDT = edt.atOffset(ZoneOffset.UTC).format(dtf);
                System.out.println("FORMLDT: " + formLDT);

                Appointment a = new Appointment(newAppID, newAppTitle, newAppDescription, newAppLocation, newAppContact,
                newAppType, formLDT, formEDT, Integer.parseInt(newAppCustomerID), Integer.parseInt(newAppUserID));

                //check for overlapping appointments
                if (!JDBC.checkOverlappingAppointments(a)){

                    if(JDBC.addAppointment(a)){
                        Stage stage;
                        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                        Parent scene = FXMLLoader.load(getClass().getResource("/View/RecordOverview.fxml"));
                        stage.setScene(new Scene(scene));
                        stage.show();
                    }
                    else{
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setTitle("Error");
                        errorAlert.setContentText("Something went wrong");
                        errorAlert.showAndWait();
                    }
                }
                else{
                    Alert overLapAlert = new Alert(Alert.AlertType.ERROR);
                    overLapAlert.setTitle("Error");
                    overLapAlert.setContentText("Overlapping appointment");
                    overLapAlert.showAndWait();
                }


            }
        }








        //Next task here: needs to be converted to UTC???
        //Also, needs to be added to database
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addAppointmentAppIDField.setText(JDBC.getNextAppointmentId());
        addAppointmentAppIDField.setEditable(false);
        addAppointmentUserIDField.setText(User.getID());
        addAppointmentChoiceBox.getItems().addAll("Anika Costa", "Daniel Garcia", "Li Lee");
        sTimeMonth.getItems().addAll(1 , 2 , 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
        sTimeDay.getItems().addAll(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,
                26,27,28,29,30,31);
        sTimeYear.getItems().addAll(2022);
        sTimeHr.getItems().addAll(8,9,10,11,12,1,2,3,4,5,6,7,8,9,10);
        sTimeMin.getItems().addAll(00, 15,30,45);
        sTimeAMPM.getItems().addAll("AM", "PM");
        eTimeHr.getItems().addAll(8,9,10,11,12,1,2,3,4,5,6,7,8,9,10);
        eTimeMin.getItems().addAll(00, 15,30,45);
        eTimeAMPM.getItems().addAll("AM", "PM");
    }
}
