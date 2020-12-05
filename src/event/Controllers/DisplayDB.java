package event.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import DatabaseAndInventory.ConsumDB;
import DatabaseAndInventory.EmployeeDB;
import DatabaseAndInventory.EquipmentDB;

import java.io.IOException;

/**
 * Controller for displayDB.fxml to display database info
 */
public class DisplayDB {

    Stage primaryStage = null;
    @FXML
    private TextArea displayArea;

    // initialize database objects to be used when displaying
    EmployeeDB employ = new EmployeeDB();
    EquipmentDB equip = new EquipmentDB();
    ConsumDB consum = new ConsumDB();

    // if home button pressed, returns to the sign in page
    public void homeActivated(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("views/SignIn.fxml"));
        primaryStage.setTitle("Display");
        primaryStage.setScene(new Scene(root, 537, 400));
        primaryStage.show();
    }

    // Based on the menu item selected, display info
    public void consumSelected(ActionEvent actionEvent) {displayArea.setText(consum.printDB()); }
    public void equipSelected(ActionEvent actionEvent) { displayArea.setText(equip.printDB()); }
    public void empSelected(ActionEvent actionEvent) { displayArea.setText(employ.printDB()); }
}
