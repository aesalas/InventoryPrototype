package event.Controllers;

import DatabaseAndInventory.OrderDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class PurchaseOrders {
    // labels to change based on with option is selected
    @FXML
    private Label category1;
    @FXML
    private Label category2;
    @FXML
    private Label category3;
    @FXML
    private Label category4;
    @FXML
    private Label category5;
    // the corresponding input boxes
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
    // main split menu option for purchase orders
    @FXML
    private SplitMenuButton editFields;
    @FXML
    private MenuItem remove;
    @FXML
    private MenuItem edit;
    @FXML
    private MenuItem add;
    // menu options for editing
    @FXML
    private MenuItem nameOption;
    @FXML
    private MenuItem QuantityOption;
    @FXML
    private MenuItem cost;
    @FXML
    private MenuItem catOption;
    // Order database instance for access to methods
    OrderDB order = new OrderDB();
    // To make sure input is correct set string based on which split menu option
    String fieldToEdit = "";

    public void enterActivated(ActionEvent actionEvent) throws SQLException {
        if(remove.isVisible()){
            order.removeFromDB(Integer.parseInt(String.valueOf(input1)));
        }else if(edit.isVisible()){
            if(nameOption.isVisible()){ fieldToEdit = "name"; }
            else if(QuantityOption.isVisible()){ fieldToEdit = "quantity";}
            else if(cost.isVisible()){fieldToEdit = "category";}
            else if(catOption.isVisible()){fieldToEdit = "totalCost";}

            order.editDB(Integer.parseInt(String.valueOf(input1)),fieldToEdit,String.valueOf(input3));
        }else if(add.isVisible()){
            order.addToDB(String.valueOf(input1),String.valueOf(input2),String.valueOf(input3), String.valueOf(input4),Double.parseDouble(String.valueOf(input5)));
        }
    }

    public void removeSelected(ActionEvent actionEvent) {
        category1.setText("Order id:");
        input1.setVisible(true);
    }

    public void editSelected(ActionEvent actionEvent) {
        category1.setText("Order id:");
        input1.setVisible(true);
        category2.setText("Field to edit:");
        editFields.setVisible(true);
        category3.setText("New value:");
        input3.setVisible(true);
    }

    public void addSelected(ActionEvent actionEvent) {
        category1.setText("Name: ");
        input1.setVisible(true);
        category2.setText("Date placed: ");
        input2.setVisible(true);
        category3.setText("Items ordered:");
        input3.setVisible(true);
        category4.setText("Total Quantity:");
        input4.setVisible(true);
        category5.setText("Total cost:");
        input5.setVisible(true);
    }
}
