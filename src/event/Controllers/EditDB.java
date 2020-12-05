package event.Controllers;

import DatabaseAndInventory.EmployeeDB;
import DatabaseAndInventory.OrderDB;
import DatabaseAndInventory.SuppliesDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class EditDB {
    Stage primaryStage = null;

    @FXML
    private MenuItem employ;
    @FXML
    private MenuItem supp;
    @FXML
    private TextField input1;
    @FXML
    private TextField input2;
    @FXML
    private Label category1;
    @FXML
    private Label category2;
    @FXML
    private Label category3;
    @FXML
    private Label category4;
    @FXML
    private Label message;

    // initialize database objects to be used when displaying
    EmployeeDB employee = new EmployeeDB();
    SuppliesDB supplies = new SuppliesDB();

    public void enterActivated(ActionEvent actionEvent) throws SQLException {
        if(employ.isVisible()){
            employee.editDB(Integer.parseInt(String.valueOf(input1)), String.valueOf(category1) ,String.valueOf(input2));
        }else if(supp.isVisible()){
            supplies.editDB(Integer.parseInt(String.valueOf(input1)), String.valueOf(category1) ,String.valueOf(input2));
        }else{
            message.setText("Something went wrong. Try again.");
        }
    }

    public void suppSelected(ActionEvent actionEvent) {
        category1.setText("Item Name:");
        category2.setText("Quantity:");
        category3.setText("Category:");
        category4.setText("Unit Price:");
    }

    public void EmpSelected(ActionEvent actionEvent) {
        category1.setText("First name:");
        category2.setText("Last name:");
        category3.setText("Job title:");
        category4.setText("Pay rate:");
    }

}
