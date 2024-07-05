import java.sql.SQLException;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

public class SellProduct {

    private Boolean confirmed = false;
    private CountDownLatch latch = new CountDownLatch(1);

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
    public void confirmB(ActionEvent event) {
        String ean = eanProduct.getText();
        String stock = stockProduct.getText();
        if (ean.equals("") || stock.equals("")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Existem campos vazios");
            Optional<ButtonType> result = alert.showAndWait();
            Boolean confirm = result.isPresent() && result.get() == ButtonType.OK;
            if (confirm.equals(true)) {
                return;
            }
        }
        Conexao exec = new Conexao();

        try {
            String tarja = exec.testeTarja(ean);
            if (tarja.equalsIgnoreCase("Vermelha") || tarja.equalsIgnoreCase("Preta")) {
                confirmed = null;
                latch = new CountDownLatch(1);
                ButtonType buttonTypeSim = new ButtonType("Sim", ButtonData.OK_DONE);
                ButtonType buttonTypeCancelar = new ButtonType("Cancelar", ButtonData.CANCEL_CLOSE);
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmação de Receita");
                alert.setHeaderText(null);
                alert.setContentText("Este produto exige receita médica. Você confirmou a entrega da receita?");
                alert.getButtonTypes().setAll(buttonTypeSim, buttonTypeCancelar);
                Optional<ButtonType> result = alert.showAndWait();
                confirmed = result.isPresent() && result.get() == buttonTypeSim;
                latch.countDown();
                try {
                    latch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
                if (confirmed.equals(true)) {
                    exec.realizarVenda(ean, stock);
                } else {
                    return;
                }
            } else {
                exec.realizarVenda(ean, stock);
            }
            Drogaria.changeScene("VENDEDOR");
            eanProduct.clear();
            stockProduct.clear();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
