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
            employee.editDB(Integer.parseInt(String.valueOf(input1)), String.valueOf(category1) ,String.valueOf(input2));



        }else if(supp.isVisible()){


        }else if(orders.isVisible()){

        }else{

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
