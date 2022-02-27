package Controller;

import DAO.JDBC;
import Model.Appointment;
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
import javafx.util.converter.LocalDateTimeStringConverter;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

/** This class controls the modify appointment screen.
 * @author Jessie Van Epps*/
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

    private Appointment modifiedAppointment;

    /** This controls the action of the cancel button. Returns user to the record overview screen.*/
    @FXML
    void modAppointmentCancelButtonPressed(ActionEvent event) throws IOException {
        Stage stage;
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(getClass().getResource("/View/RecordOverview.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**Controls the action of the OK button. Checks if fields are empty, Checks if appointment is outside business hours,
     * checks if appointment overlaps with other appointments. Adds modified details to database.*/
    @FXML
    void modAppointmentOkButtonPressed(ActionEvent event) throws IOException, SQLException {
        if (modAppointmentTitleField.getText().isBlank() || modAppointmentDescriptionField.getText().isBlank() ||
                modAppointmentLocationField.getText().isBlank() || modAppointmentChoiceBox.getSelectionModel().isEmpty() ||
                modAppointmentTypeField.getText().isBlank() || sTimeMonth.getSelectionModel().isEmpty() ||
                sTimeDay.getSelectionModel().isEmpty() || sTimeYear.getSelectionModel().isEmpty() ||
                sTimeHr.getSelectionModel().isEmpty() || sTimeMin.getSelectionModel().isEmpty() ||
                sTimeAMPM.getSelectionModel().isEmpty() || eTimeHr.getSelectionModel().isEmpty() ||
                eTimeMin.getSelectionModel().isEmpty() || eTimeAMPM.getSelectionModel().isEmpty() ||
                modAppointmentCustIDField.getText().isBlank() || modAppointmentUserIDField.getText().isBlank()){

            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("ERROR");
            a.setContentText("Please ensure all fields are filled");
            a.showAndWait();
        }else{
            String modAppID = modAppointmentAppIDField.getText();
            String modAppTitle = modAppointmentTitleField.getText();
            String modAppDesc = modAppointmentDescriptionField.getText();
            String modAppLoc = modAppointmentLocationField.getText();
            String modAppContact = modAppointmentChoiceBox.getSelectionModel().getSelectedItem();
            String modAppType = modAppointmentTypeField.getText();
            //Start date
            int sMonth = sTimeMonth.getValue();
            int sDay = sTimeDay.getValue();
            int sYear = sTimeYear.getValue();

            //start time
            int sHour = sTimeHr.getValue();
            int sMin = sTimeMin.getValue();
            String sAMPM = sTimeAMPM.getValue();

            //end time
            int eHour = eTimeHr.getValue();
            int eMin = eTimeMin.getValue();
            String eAMPM = eTimeAMPM.getValue();

            String modAppCustID = modAppointmentCustIDField.getText();
            String modAppUseID = modAppointmentUserIDField.getText();

            //LocalTime startLT = LocalTime.parse(String.valueOf(sHour + ":" + String.valueOf(sMin + " " + sAMPM)), DateTimeFormatter.ofPattern("h:m a"));
            //LocalTime endLT = LocalTime.parse(String.valueOf(eHour + ":" + String.valueOf(eMin) + " " + eAMPM), DateTimeFormatter.ofPattern("h:m a"));
            LocalDateTime startLDT = LocalDateTime.parse(String.valueOf(sYear) + "-" + String.valueOf(sMonth) + "-" + String.valueOf(sDay) +
                    " " + String.valueOf(sHour) + ":" + String.valueOf(sMin) + " " + sAMPM, DateTimeFormatter.ofPattern("yyyy-M-d h:m a"));

            LocalDateTime endLDT = LocalDateTime.parse(String.valueOf(sYear) + "-" + String.valueOf(sMonth) + "-" + String.valueOf(sDay) +
                    " " + String.valueOf(eHour) + ":" + String.valueOf(eMin) + " " + eAMPM, DateTimeFormatter.ofPattern("yyyy-M-d h:m a"));

            LocalTime openEST = LocalTime.of(8,0);
            LocalTime closeEST = LocalTime.of(22,0);
            System.out.println("startLDT: " + startLDT.toString());
            System.out.println("ENDLDT: " + endLDT);

            ZonedDateTime startZDT = startLDT.atZone(ZoneId.systemDefault());
            ZonedDateTime startZDTEST = startZDT.withZoneSameInstant(ZoneId.of("America/New_York"));
            ZonedDateTime endZDT = endLDT.atZone(ZoneId.systemDefault());
            ZonedDateTime endZDTEST = endZDT.withZoneSameInstant(ZoneId.of("America/New_York"));



            System.out.println("STARTZDT: "+ startZDT.getZone() + startZDT.toString());
            System.out.println("EST TIME: " + startZDTEST.getZone() + startZDTEST.toString());

            LocalDateTime openLDT = LocalDateTime.of(sYear, sMonth, sDay, 8,0);
            ZonedDateTime openZDT = openLDT.atZone(ZoneId.of("America/New_York"));
            System.out.println("Opening time in EST: " + openZDT.getZone() + openZDT.toString());
            System.out.println("Opening time in Default (PST) " + openZDT.withZoneSameInstant(ZoneId.systemDefault()));

            LocalDateTime closeLDT = LocalDateTime.of(sYear, sMonth, sDay, 22, 0);
            ZonedDateTime closeZDT = closeLDT.atZone(ZoneId.of("America/New_York"));
            if (startZDT.isBefore(openZDT) || endZDT.isAfter(closeZDT) || startZDT.isAfter(endZDT) || startZDT.isAfter(closeZDT) || endZDT.isBefore(openZDT)) {

            /*startZDTEST.isBefore(openLDT.atZone(ZoneId.of("America/New_York"))) || endZDTEST.isAfter(closeLDT.atZone(ZoneId.of("America/New_York"))) */
                /*System.out.println("Scheduled start: " + startZDTEST.getHour() + ":" + startZDTEST.getMinute() + " Day"+startZDTEST.getDayOfMonth());
                System.out.println("Scheduled end: " + endZDTEST.getHour() + ":" + endZDTEST.getMinute() + " Day" + endZDTEST.getDayOfMonth());
                System.out.println("Store opens: " + openLDT.getHour() + ":" + openLDT.getMinute() +" Day: " +openLDT.getDayOfMonth());
                System.out.println("Store closes: " + closeLDT.getHour() + ":" + closeLDT.getMinute() + " Day: " + closeLDT.getDayOfMonth());*/

                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("Error");
                a.setContentText("Appointment is outside of business hours or time is invalid");
                a.showAndWait();

            } else{

                Appointment tempAppointment = new Appointment(modAppID, modAppTitle, modAppDesc, modAppLoc, modAppContact,
                        modAppType, startLDT.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), endLDT.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                        Integer.parseInt(modAppCustID), Integer.parseInt(modAppUseID));
                if (JDBC.checkOverlappingAppointments(tempAppointment)){
                    Alert overlapError = new Alert (Alert.AlertType.ERROR);
                    overlapError.setTitle("ERROR");
                    overlapError.setContentText("Overlapping appointment");
                    overlapError.showAndWait();
                }else{
                    if(JDBC.doesCustomerExist(modAppCustID)){
                        modifiedAppointment = tempAppointment;
                        JDBC.addAppointment(modifiedAppointment);

                        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                        Parent scene = FXMLLoader.load(getClass().getResource("/View/RecordOverview.fxml"));
                        stage.setScene(new Scene(scene));
                        stage.show();
                    }else{
                        Alert noCustError = new Alert (Alert.AlertType.ERROR);
                        noCustError.setTitle("ERROR");
                        noCustError.setContentText("Must use existing customer");
                        noCustError.showAndWait();
                    }

                }

            }




        }
    }

    /** Adds data from selected appointment to populate fields on modify appointment screen. Gets data from selected appointment and populates data
     * on modify appointment screen.*/
    public void sendAppointment(Appointment a){
        modifiedAppointment = a;
        modAppointmentAppIDField.setText(a.getAppointmentId());
        modAppointmentTitleField.setText(a.getTitle());
        modAppointmentDescriptionField.setText(a.getDescription());
        modAppointmentLocationField.setText(a.getLocation());
        modAppointmentChoiceBox.getSelectionModel().select(a.getContact());
        modAppointmentTypeField.setText(a.getType());
        System.out.println("TEST PARSE: " + a.getStartDateTime());
        LocalDateTime sLDT = LocalDateTime.parse(a.getStartDateTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S"));
        LocalDateTime eLDT = LocalDateTime.parse(a.getEndDateTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S"));
        System.out.println(a.getStartDateTime());
        //DateTest

        LocalTime sLDT12 = LocalTime.parse(sLDT.format(DateTimeFormatter.ofPattern("hh:mm:ss")));
        LocalTime eLDT12 = LocalTime.parse(eLDT.format(DateTimeFormatter.ofPattern("hh:mm:ss")));

        String sLDT12Hour = sLDT.format(DateTimeFormatter.ofPattern("hh:mm:ss a"));
        String eLDT12Hour = eLDT.format(DateTimeFormatter.ofPattern("hh:mm:ss a"));


        sTimeMonth.setValue(sLDT.getMonthValue());

        //sTimeDay.getSelectionModel().select(sLDT.getDayOfMonth());
        sTimeDay.setValue(sLDT.getDayOfMonth());

        //sTimeYear.getSelectionModel().select(sLDT.getYear());
        sTimeYear.setValue(sLDT.getYear());
        //Start time
        //sTimeHr.getSelectionModel().select(sLDT.getHour());
        sTimeHr.setValue(sLDT12.getHour());
        //sTimeMin.getSelectionModel().select(sLDT.getMinute());
        sTimeMin.setValue(sLDT.getMinute());
        //Get AM or PM

        if (sLDT12Hour.contains("AM")){
            sTimeAMPM.getSelectionModel().select("AM");

        }
        else if (sLDT12Hour.contains("PM")){
            sTimeAMPM.getSelectionModel().select("PM");
        }
        //end time
        eTimeHr.setValue(eLDT12.getHour());

        System.out.println("ELDT HOUR: " + eLDT12.getHour());
        eTimeMin.setValue(eLDT.getMinute());

        //Get AM or PM End time
        if (eLDT12Hour.contains("PM")){
            eTimeAMPM.getSelectionModel().select("PM");
        }
        else if (eLDT12Hour.contains("AM")){
            eTimeAMPM.getSelectionModel().select("AM");
        }


        modAppointmentCustIDField.setText(String.valueOf(a.getCustomerId()));
        modAppointmentUserIDField.setText(String.valueOf(a.getUserId()));


    }

    /** Initializes the modify appointment class. Sets appointment id field to not be editable. Adds data to choice boxes.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        modAppointmentAppIDField.setEditable(false);
        modAppointmentChoiceBox.getItems().addAll("Anika Costa", "Daniel Garcia", "Li Lee");
        sTimeMonth.getItems().addAll(1 , 2 , 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
        sTimeDay.getItems().addAll(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,
                26,27,28,29,30,31);
        sTimeYear.getItems().addAll(2020, 2021, 2022);
        sTimeHr.getItems().addAll(8,9,10,11,12,1,2,3,4,5,6,7);
        sTimeMin.getItems().addAll(00, 15,30,45);
        sTimeAMPM.getItems().addAll("AM", "PM");
        eTimeHr.getItems().addAll(8,9,10,11,12,1,2,3,4,5,6,7);
        eTimeMin.getItems().addAll(00, 15,30,45);
        eTimeAMPM.getItems().addAll("AM", "PM");
    }
}
