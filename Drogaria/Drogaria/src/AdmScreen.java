import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class AdmScreen {

    @FXML
    private Button addProd;

    @FXML
    private Button addWork;

    @FXML
    private Button listProd;

    @FXML
    private Button outB;

    @FXML
    private Button remProduct;

    @FXML
    private Button upStock;

    @FXML
    void addProduct(ActionEvent event) {
        Drogaria.changeScene("ADDPROD");

    }

    @FXML
    void addWorker(ActionEvent event) {
        Drogaria.changeScene("ADDFUNC");
    }
    
    @FXML
    void listProduct(ActionEvent event) {
        Drogaria.changeScene("LISTPROD");
        
    }
    
    @FXML
    void outButton(ActionEvent event) {
        Drogaria.changeScene("OUT");
        
    }
    
    @FXML
    void removeProduct(ActionEvent event) {
        Drogaria.changeScene("DELPROD");
        
    }
    
    @FXML
    void updateStock(ActionEvent event) {
        Drogaria.changeScene("UPPROD");
        
    }
    
}
