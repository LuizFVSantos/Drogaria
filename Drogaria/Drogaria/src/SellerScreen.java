import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class SellerScreen {

    @FXML
    private Button listProd;

    @FXML
    private Button out;

    @FXML
    private Button sell;

    @FXML
    void listProduct(ActionEvent event) {
        Drogaria.changeScene("LISPROD");
    }

    @FXML
    void outB(ActionEvent event) {
        Drogaria.changeScene("OUT");
    }

    @FXML
    void sellProd(ActionEvent event) {
        Drogaria.changeScene("SELLPROD");
    }
}
