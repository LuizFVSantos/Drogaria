import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
public class Drogaria extends Application{
    private static Stage stage;
    private static Scene loginScreen;
    private static Scene admScreen;
    private static Scene sellerScreen;
    private static Scene addEmployee;
    private static Scene addProduct;
    private static Scene deleteProduct;
    private static Scene listProduct;
    private static Scene sellProduct;
    private static Scene updateStock;
    
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;

        loginScreen = loadScene("/loginScreen.fxml");
        admScreen = loadScene("/admScreen.fxml");
        sellerScreen = loadScene("/sellerScreen.fxml");
        addEmployee = loadScene("/addEmployee.fxml");
        addProduct = loadScene("/addProduct.fxml");
        deleteProduct = loadScene("/deleteProduct.fxml");
        listProduct = loadScene("/listProduct.fxml");
        sellProduct = loadScene("/sellProduct.fxml");
        updateStock = loadScene("/updateStock.fxml");

        stage.setTitle("Drogaria");
        stage.setScene(loginScreen);
        stage.show();
    }

    private Scene loadScene(String fxmlPath) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = fxmlLoader.load();
            return new Scene(root);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void changeScene(String change) {
        switch (change) {
            case "ADM":
                stage.setScene(admScreen);
                break;
            case "OUT":
                stage.setScene(loginScreen);
                break;
            case "VENDEDOR":
                stage.setScene(sellerScreen);
                break;
            case "ADDFUNC":
                stage.setScene(addEmployee);
                break;
            case "ADDPROD":
                stage.setScene(addProduct);
                break;
            case "DELPROD":
                stage.setScene(deleteProduct);
                break;
            case "LISTPROD":
                stage.setScene(listProduct);
                break;
            case "SELLPROD":
                stage.setScene(sellProduct);
                break;
            case "UPPROD":
                stage.setScene(updateStock);
                break;
        }
    }
    public static void main(String[] args) throws Exception {
        launch (args);
    }

    public static Boolean confirmarReceita() {
        AtomicBoolean confirmed = new AtomicBoolean(false);
        CountDownLatch latch = new CountDownLatch(1);

        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmação de Receita");
            alert.setHeaderText(null);
            alert.setContentText("Este produto exige receita médica. Você confirmou a entrega da receita?");
            Optional<ButtonType> result = alert.showAndWait();
            confirmed.set(result.isPresent() && result.get() == ButtonType.OK);
            latch.countDown();
        });

        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        return confirmed.get();
    }

}       