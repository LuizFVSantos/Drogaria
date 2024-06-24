import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class SellProduct {

    @FXML
    private Button confirm;

    @FXML
    private TextField eanProduct;

    @FXML
    private Button out;

    @FXML
    private TextField stockProduct;

    @FXML
    void outB(ActionEvent event) {
        Drogaria.changeScene("VENDEDOR");
    }

    @FXML
    void confirmB(ActionEvent event){
        String ean = eanProduct.getText();
        System.out.println(ean);
        String stock = stockProduct.getText();
        Conexao exec = new Conexao();
        try {
            String tarja = exec.testeTarja(ean);
            if(tarja.equalsIgnoreCase("Vermelha") || tarja.equalsIgnoreCase("Preta")){
                if(Drogaria.confirmarReceita());
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            exec.realizarVenda(ean, stock);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Drogaria.changeScene("VENDEDOR");
    }
}
