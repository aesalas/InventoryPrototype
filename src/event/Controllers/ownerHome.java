package event.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.io.IOException;

public class ownerHome {

        Stage primaryStage = new Stage();

        public void viewSelected(ActionEvent actionEvent) throws IOException {
                Parent root = FXMLLoader.load(getClass().getResource("views/DisplayDB.fxml"));
                primaryStage.setTitle("Display");
                primaryStage.setScene(new Scene(root, 537, 400));
                primaryStage.show();
        }
        public void orderSelected(ActionEvent actionEvent) throws IOException {
                Parent root = FXMLLoader.load(getClass().getResource("views/PurchaseOrders.fxml"));
                primaryStage.setTitle("Orders");
                primaryStage.setScene(new Scene(root, 537, 400));
                primaryStage.show();
        }
        public void editSelected(ActionEvent actionEvent) throws IOException {
                Parent root = FXMLLoader.load(getClass().getResource("views/Edit.fxml"));
                primaryStage.setTitle("Orders");
                primaryStage.setScene(new Scene(root, 537, 400));
                primaryStage.show();
        }
        public void removeSelected(ActionEvent actionEvent) throws IOException {
                Parent root = FXMLLoader.load(getClass().getResource("views/Remove.fxml"));
                primaryStage.setTitle("Orders");
                primaryStage.setScene(new Scene(root, 537, 400));
                primaryStage.show();
        }
}
