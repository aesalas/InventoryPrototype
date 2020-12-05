package event.Controllers;

import DatabaseAndInventory.ConsumDB;
import DatabaseAndInventory.EmployeeDB;
import DatabaseAndInventory.EquipmentDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
    private MenuItem equip;
    @FXML
    private MenuItem consum;
    @FXML
    private TextField text1;
    @FXML
    private TextField text2;
    @FXML
    private TextField text3;
    @FXML
    private TextField text4;
    @FXML
    private Label label1;
    @FXML
    private Label label2;
    @FXML
    private Label label3;
    @FXML
    private Label label4;
    @FXML
    private Label message;

    // initialize database objects to be used when displaying
    EmployeeDB employee = new EmployeeDB();
    EquipmentDB equipment = new EquipmentDB();
    ConsumDB consumable = new ConsumDB();

    public void enterActivated(ActionEvent actionEvent) throws SQLException {
        if(employ.isVisible()){
            employee.addToDB(String.valueOf(text1),String.valueOf(text2),String.valueOf(text3),Double.parseDouble(String.valueOf(text4)));
            message.setText("Successfully added");
        }else if(equip.isVisible()){
            equipment.addToDB(String.valueOf(text1),String.valueOf(text2),String.valueOf(text3),Double.parseDouble(String.valueOf(text4)));
            message.setText("Successfully added");
        }else if(consum.isVisible()){
            consumable.addToDB(String.valueOf(text1),String.valueOf(text2),String.valueOf(text3),Double.parseDouble(String.valueOf(text4)));
            message.setText("Successfully added");
        }else{
            message.setText("Error, Please try again");
        }
    }

    public void equipSelected(ActionEvent actionEvent) throws IOException {
        label1.setText("Item Name:");
        label2.setText("Quantity:");
        label3.setText("Category:");
        label4.setText("Unit Price:");
    }
    public void consumSelected(ActionEvent actionEvent) throws IOException {
        label1.setText("Item Name:");
        label2.setText("Quantity:");
        label3.setText("Category:");
        label4.setText("Unit Price:");
    }
}
