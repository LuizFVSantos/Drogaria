import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
        if (ean.equals("") || stock.equals("")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Existem Campos Vazios");
            Optional<ButtonType> result = alert.showAndWait();
            Boolean confirm = result.isPresent() && result.get() == ButtonType.OK;
            if (confirm.equals(true))
                return;
        }
        Conexao exec = new Conexao();
        String sql = ("UPDATE produtos SET quantidade = ? WHERE ean = ?");
        try {
            Connection connection = exec.openDatabase();
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setInt(1, Integer.parseInt(stock));
            pstm.setString(2, ean);
            pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            exec.closeDatabase();
            stockProduct.clear();
            eanProduct.clear();
        }
        Drogaria.changeScene("ADM");
    }

    @FXML
    void outB(ActionEvent event) {
        Drogaria.changeScene("ADM");
        stockProduct.clear();
        eanProduct.clear();
    }
}
