package event.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.io.IOException;

public class EmpHome {
    Stage primaryStage = null;

    public void viewActivated(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("views/DisplayDB.fxml"));
        primaryStage.setTitle("Display");
        primaryStage.setScene(new Scene(root, 537, 400));
        primaryStage.show();
    }
    public void requestActivated(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("views/PurchaseOrders.fxml"));
        primaryStage.setTitle("Orders");
        primaryStage.setScene(new Scene(root, 537, 400));
        primaryStage.show();
    }
}