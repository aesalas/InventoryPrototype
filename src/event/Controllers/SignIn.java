package event.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mainJava.EmployeeDB;

import java.io.IOException;
import java.sql.SQLException;

public class SignIn {
    Stage primaryStage;
    @FXML
    private TextField empId;
    @FXML
    private TextField password;
    @FXML
    private Label errorMessage;

    public void signInActivated(ActionEvent actionEvent) throws IOException, SQLException {
        // create employee to check password and Id
        EmployeeDB emp = new EmployeeDB();
        // if the password is correct, check id for correct home page
        if(emp.checkPassword(String.valueOf(password),Integer.parseInt(empId.getText()))){
            // if id is an employee, enter employee home page
            if(emp.searchDB(Integer.parseInt(empId.getText())).equals("emp")) {
                Parent root = FXMLLoader.load(getClass().getResource("views/EmpHome.fxml"));
                primaryStage.setTitle("Home page");
                primaryStage.setScene(new Scene(root, 300, 275));
                primaryStage.show();
            // if id is the owner id, enter the owner homepage
            }else if(emp.searchDB(Integer.parseInt(empId.getText())).equals("own")){
                Parent root = FXMLLoader.load(getClass().getResource("views/ownerHome.fxml"));
                primaryStage.setTitle("Home page");
                primaryStage.setScene(new Scene(root, 300, 275));
                primaryStage.show();
            // if the id doesn't exist, print error message
            }else{
                errorMessage.setText("Incorrect id or password");
            }
        // if password is incorrect, print error message
        }else{
            errorMessage.setText("Incorrect id or password");
        }

    }
}
