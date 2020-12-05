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

public class AddDB {
    Stage primaryStage = null;

    @FXML
    private MenuItem employ;
    @FXML
    private MenuItem supp;
    @FXML
    private MenuItem orders;
    @FXML
    private TextField input1;
    @FXML
    private TextField input2;
    @FXML
    private TextField input3;
    @FXML
    private TextField input4;
    @FXML
    private TextField input5;
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
    OrderDB order = new OrderDB();

    public void enterActivated(ActionEvent actionEvent) throws SQLException {
        if(employ.isVisible()){
            if(employee.isValidPassword(String.valueOf(input4))) {
                employee.addToDB(String.valueOf(input1), String.valueOf(input2), String.valueOf(input3), String.valueOf(input4), Double.parseDouble(String.valueOf(input5)));
                message.setText("Successfully added");
            }else{
                message.setText("Password is Invalid. Make sure it is at least 8 characters and contains one of each of the following: \n " +
                        "Uppercase, lowercase, Numbers, and Special Characters: '!' '.' ',' '@' '#'");
            }
        }else if(supp.isVisible()){
            supplies.addToDB(String.valueOf(input1),String.valueOf(input2),String.valueOf(input3),String.valueOf(input4),Double.parseDouble(String.valueOf(input5)));
            message.setText("Successfully added");
        }else if(orders.isVisible()){
            order.addToDB(String.valueOf(input1),String.valueOf(input2),String.valueOf(input3),String.valueOf(input4),Double.parseDouble(String.valueOf(input5)));
            message.setText("Successfully added");
        }else{
            message.setText("Error, Please try again");
        }
    }

    public void suppSelected(ActionEvent actionEvent) throws IOException {
        category1.setText("Item Name:");
        category2.setText("Quantity:");
        category3.setText("Category:");
        category4.setText("Unit Price:");
    }
    public void orderSelected(ActionEvent actionEvent) throws IOException {
        category1.setText("Item Name:");
        category2.setText("Quantity:");
        category3.setText("Category:");
        category4.setText("Unit Price:");
    }
}
