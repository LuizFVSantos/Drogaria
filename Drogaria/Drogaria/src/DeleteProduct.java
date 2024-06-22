import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class DeleteProduct {

    @FXML
    private Button confirm;

    @FXML
    private TextField eanProduct;

    @FXML
    private Button out;

    @FXML
    void confirmB(ActionEvent event) throws SQLException {
        String ean = eanProduct.getText();
        Conexao exec = new Conexao();
        String sql = ("DELETE FROM produtos WHERE ean = " + ean);
        exec.openDatabase();
        exec.executarQuery(sql);
        exec.closeDatabase();    
        Drogaria.changeScene("ADM");
    }

    @FXML
    void outB(ActionEvent event) {
        Drogaria.changeScene("ADM");
    }

}
