package Controller;

import DAO.JDBC;
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
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AdditionalReportsController implements Initializable {
    @FXML
    private ChoiceBox<String> reportsMonthBox;

    @FXML
    private ChoiceBox<String> reportsTypeBox;

    @FXML
    private TextField reportsTotalField;

    @FXML
    void searchReportsButtonPressed(ActionEvent event) throws SQLException {
        if (reportsMonthBox.getSelectionModel().isEmpty() || reportsTypeBox.getSelectionModel().isEmpty()){
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("ERROR");
            a.setContentText("Please select a month and type");
            a.showAndWait();
        }else{
            String month = reportsMonthBox.getValue();
            String type = reportsTypeBox.getValue();

            int totalOfType = JDBC.getAmountOfType(month,type);
            reportsTotalField.setText(String.valueOf(totalOfType));
        }
    }

    @FXML
    void reportsBackButtonPressed(ActionEvent event) throws IOException {

        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(getClass().getResource("/View/RecordOverview.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        reportsTotalField.setEditable(false);
        reportsMonthBox.getItems().addAll("1","2","3","4","5","6","7","8","9","10","11","12");
        try {
            reportsTypeBox.setItems(JDBC.getAppointmentTypes());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
