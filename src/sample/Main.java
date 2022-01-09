package sample;

import DAO.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;

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


    public static void main(String[] args) {
        //Set language to "fr"
        //Locale.setDefault(new Locale("fr"));

        JDBC.openConnection();

        //ResourceBundle rb = ResourceBundle.getBundle("sample/Nat", Locale.getDefault());
        //if (Locale.getDefault().getLanguage().equals("fr")){

        //    System.out.println(rb.getString("Username"));
        //}

        launch(args);
        JDBC.closeConnection();


    }
}
