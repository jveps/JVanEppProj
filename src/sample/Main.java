package sample;

import DAO.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;
/**
 This is the main class.
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/View/LoginForm.fxml"));
        //primaryStage.setTitle("Hello world");
        //primaryStage.setScene(new Scene(root, 300, 275));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**This is the main method. This method starts the program.*/
    public static void main(String[] args) {
        //Set language to "fr"
        //Locale.setDefault(new Locale("fr"));

        JDBC.openConnection();



        launch(args);
        JDBC.closeConnection();


    }
}
