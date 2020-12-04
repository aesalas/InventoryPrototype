package event.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;

public class ownerHome {

        @FXML
        private MenuItem viewInvent;
        @FXML
        private MenuItem purchaseOrders;
        @FXML
        private MenuItem editInvent;
        @FXML
        private MenuItem removeInvent;

        public void submitActivated(ActionEvent actionEvent) {
            //see which was selected
            //if it does, send to appropriate page
        }
        public void viewSelected(ActionEvent actionEvent) {
                // displayDB
        }
        public void orderSelected(ActionEvent actionEvent) {

        }
        public void editSelected(ActionEvent actionEvent) {
                // displayDB
        }
        public void removeSelected(ActionEvent actionEvent) {

        }
}
