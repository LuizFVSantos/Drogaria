import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class ListProduct implements Initializable{
    
    @FXML
    private ListView<Produto> listProduct;
    Conexao exec = new Conexao();
    List<Produto> produtos = exec.listarProdutos();
    

    @FXML
    private Button out;
    @FXML
    void outB(ActionEvent event) { 
        Drogaria.changeScene("ADM");
        
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listProduct.getItems().addAll(produtos);
    }

}
