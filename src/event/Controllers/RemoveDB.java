package event.Controllers;

import DatabaseAndInventory.EmployeeDB;
import DatabaseAndInventory.OrderDB;
import DatabaseAndInventory.SuppliesDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class RemoveDB {
    @FXML
    public SplitMenuButton employ;
    @FXML
    public MenuItem supp;
    @FXML
    public MenuItem orders;
    @FXML
    public TextField id;

    EmployeeDB employee = new EmployeeDB();
    SuppliesDB supplies = new SuppliesDB();
    OrderDB order = new OrderDB();

    public void enterActivated(ActionEvent actionEvent) throws SQLException {
        if(supp.isVisible()){ supplies.removeFromDB(Integer.parseInt(String.valueOf(id)));}
        else if(employ.isVisible()){ employee.removeFromDB(Integer.parseInt(String.valueOf(id)));}
        else if(orders.isVisible()){order.removeFromDB(Integer.parseInt(String.valueOf(id)));}
    }
}
