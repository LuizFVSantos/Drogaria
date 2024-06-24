import java.sql.SQLException;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
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
        System.out.println(ean);
        String stock = stockProduct.getText();
        Conexao exec = new Conexao();

        try {
            String tarja = exec.testeTarja(ean);
            if (tarja.equalsIgnoreCase("Vermelha") || tarja.equalsIgnoreCase("Preta")) {
                latch = new CountDownLatch(1);

                Platform.runLater(() -> {
                    Alert alert = new Alert(AlertType.WARNING
                    );
                    alert.setTitle("Confirmação de Receita");
                    alert.setHeaderText(null);
                    alert.setContentText("Este produto exige receita médica. Você confirmou a entrega da receita?");
                    Optional<ButtonType> result = alert.showAndWait();
                    confirmed = result.isPresent() && result.get() == ButtonType.OK;
                    latch.countDown();
                });

                try {
                    latch.await(); // Wait for the alert to be handled
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt(); // Restore interrupted status
                }
                if (confirmed) {
                    System.out.println("ok");
                } else {
                    System.out.println("Prescription not confirmed.");
                }
            }

            Drogaria.changeScene("VENDEDOR");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
    

