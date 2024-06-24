import java.sql.SQLException;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
        Boolean confirmed = false;
        Conexao exec = new Conexao();
        String sql = ("DELETE FROM produtos WHERE ean = " + ean);
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Tem certeza que deseja apagar este produto?");
        alert.setContentText(null);
        alert.setHeaderText("Uma vez deletado não tem como recuperar os dados");
        Optional <ButtonType> result = alert.showAndWait();
        confirmed = result.isPresent() && result.get()==ButtonType.OK;
        if(confirmed.equals(true)){
            exec.openDatabase();
            exec.executarQuery(sql);
            exec.closeDatabase();    
        }else{
            Drogaria.changeScene("ADM");
            eanProduct.clear();
        }
    }

    @FXML
    void outB(ActionEvent event) {
        Drogaria.changeScene("ADM");
        eanProduct.clear();
    }
}
