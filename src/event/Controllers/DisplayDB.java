package event.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import mainJava.ConsumDB;
import mainJava.EmployeeDB;
import mainJava.EquipmentDB;

import java.io.IOException;

public class DisplayDB {

    Stage primaryStage = null;
    @FXML
    private TextArea displayArea;

    EmployeeDB employ = new EmployeeDB();
    EquipmentDB equip = new EquipmentDB();
    ConsumDB consum = new ConsumDB();

    public void homeActivated(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("views/SignIn.fxml"));
        primaryStage.setTitle("Display");
        primaryStage.setScene(new Scene(root, 537, 400));
        primaryStage.show();
    }

    public void consumSelected(ActionEvent actionEvent) {
        displayArea.setText(consum.printDB());
    }
    public void equipSelected(ActionEvent actionEvent) { displayArea.setText(equip.printDB()); }
    public void empSelected(ActionEvent actionEvent) { displayArea.setText(employ.printDB()); }
}
