package event.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mainJava.EmployeeDB;

import java.io.IOException;

public class SignIn {
    Stage primaryStage;
    @FXML
    private TextField empId;
    @FXML
    private TextField password;

    public void signInActivated(ActionEvent actionEvent) throws IOException {
        EmployeeDB emp = new EmployeeDB();
        if() {
            Parent root = FXMLLoader.load(getClass().getResource("views/EmpHome.fxml"));
            primaryStage.setTitle("Home page");
            primaryStage.setScene(new Scene(root, 300, 275));
            primaryStage.show();
        }else if(){
            Parent root = FXMLLoader.load(getClass().getResource("views/ownerHome.fxml"));
            primaryStage.setTitle("Home page");
            primaryStage.setScene(new Scene(root, 300, 275));
            primaryStage.show();
        }else{

        }

    }
}
