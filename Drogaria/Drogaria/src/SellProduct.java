import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
    void confirmB(ActionEvent event) throws SQLException {
    String ean = eanProduct.getText();
    String stock = stockProduct.getText();
    Conexao exec = new Conexao();
    String sql = "SELECT ean, quantidade, tarja FROM produtos WHERE ean = ?";
    String updateSql = "UPDATE produtos SET quantidade = ? WHERE ean = ?";
    Connection connection = null;
    int quantidadedb = 0;
    String tarja = null;

    try {
        connection = exec.openDatabase();
        if (connection != null) {
            try (PreparedStatement selectStatement = connection.prepareStatement(sql)) {
                selectStatement.setString(1, ean);
                try (ResultSet resultSet = selectStatement.executeQuery()) {
                    if (resultSet.next()) {
                        quantidadedb = resultSet.getInt("quantidade");
                        tarja = resultSet.getString("tarja");
                    } else {
                        return;
                    }
                }
            }

            if ("Vermelha".equalsIgnoreCase(tarja) || "Preta".equalsIgnoreCase(tarja)) {
                if (!confirmarReceita()) {
                    return ;
                }
            }

            if (quantidadedb < Integer.parseInt(stock)) {
                return ;
            }

            int quantidadeAtual = quantidadedb - Integer.parseInt(stock);
            try (PreparedStatement updateStatement = connection.prepareStatement(updateSql)) {
                updateStatement.setInt(1, quantidadeAtual);
                updateStatement.setString(2, ean);
                updateStatement.executeUpdate();
            }

            return ;

        } else {
            return ;
        }

    } catch (SQLException e) {
        e.printStackTrace();
        return ;

    } finally {
        if (connection != null) {
            try {
                exec.closeDatabase();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

    public static boolean confirmarReceita() {
        AtomicBoolean confirmed = new AtomicBoolean(false);
        AtomicBoolean dialogClosed = new AtomicBoolean(false);
        
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmação de Receita");
            alert.setHeaderText(null);
            alert.setContentText("Este produto exige receita médica. Você confirmou a entrega da receita?");
            Optional<ButtonType> result = alert.showAndWait();
            confirmed.set(result.isPresent() && result.get() == ButtonType.OK);
            dialogClosed.set(true);
        });
        
        while (!dialogClosed.get()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        
        return confirmed.get();
    }
}