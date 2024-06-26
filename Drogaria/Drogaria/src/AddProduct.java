import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class AddProduct implements Initializable {

    @FXML
    private Button confirm;

    @FXML
    private TextField eanProduct;

    @FXML
    private TextField nameProduct;

    @FXML
    private Button out;

    @FXML
    private TextField priceProduct;

    @FXML
    private TextField stockProduct;

     @FXML
    private ChoiceBox<String> stripeProduct;
    private String tarja[] = {"Sem tarja", "Amarela", "Vermelha", "Preta"};
    
    @FXML
    void confirmB(ActionEvent event) {
        ArrayList<String> produto = new ArrayList<>();
        produto.add(nameProduct.getText());
        produto.add(priceProduct.getText());
        produto.add(stockProduct.getText());
        produto.add(eanProduct.getText());
        produto.add(stripeProduct.getValue());
        try {
            Conexao exec = new Conexao();   
            exec.adicionarProduto(produto);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        eanProduct.clear();
        nameProduct.clear();
        stockProduct.clear();
        priceProduct.clear();

    }

    @FXML
    void outB(ActionEvent event) {
        Drogaria.changeScene("ADM");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        stripeProduct.getItems().addAll(tarja);
    }
}
