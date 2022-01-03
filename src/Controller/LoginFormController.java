package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import DAO.JDBC;

public class LoginFormController implements Initializable {
    @FXML
    private Label loginUsernameLabel;

    @FXML
    private Label loginPasswordLabel;

    @FXML
    private TextField loginUsernameField;

    @FXML
    private TextField loginPasswordField;

    @FXML
    private Button loginOkButton;

    @FXML
    private Button loginCancelButton;

    @FXML
    void onActionOkButton(ActionEvent event) {
        String uName = loginUsernameField.getText();
        String pWord = loginPasswordField.getText();
        if (JDBC.loginTest(uName,pWord)){
            System.out.println("CORRECT PASSWORD");
        }
        else{
            //Show error in appropriate language for incorrect username or password
            ResourceBundle rb = ResourceBundle.getBundle("sample/Nat", Locale.getDefault());
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle(rb.getString("Error"));
            a.setContentText(rb.getString("Username or password incorrect"));
            a.showAndWait();

        }

    }

    public void doLoginProcedure(){
        String uName = loginUsernameField.getText();
        String pWord = loginPasswordField.getText();
    }

    public void initialize(URL url, ResourceBundle rb){
        //Gets default language
        rb = ResourceBundle.getBundle("sample/Nat", Locale.getDefault());

        //Applies text based on default language
        loginUsernameLabel.setText(rb.getString("Username") + ":");
        loginPasswordLabel.setText(rb.getString("Password") + ":");
        loginOkButton.setText(rb.getString("Okay"));
        loginCancelButton.setText(rb.getString("Cancel"));

    }

}
