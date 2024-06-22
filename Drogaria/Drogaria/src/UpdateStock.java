import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class UpdateStock {

    @FXML
    private Button confirm;

    @FXML
    private TextField eanProduct;

    @FXML
    private Button out;

    @FXML
    private TextField stockProduct;

    @FXML
    void confirmB(ActionEvent event) throws SQLException {
        String ean = eanProduct.getText(), stock = stockProduct.getText();
        Conexao exec = new Conexao();
        String sql = ("UPDATE produtos SET quantidade = "+ stock + " WHERE ean = " + ean);
        exec.openDatabase();
        exec.executarQuery(sql);
        exec.closeDatabase();    
        Drogaria.changeScene("VENDEDOR");
    }

    @FXML
    void outB(ActionEvent event) {
         Drogaria.changeScene("VENDEDOR");
    }
}
