import java.sql.*;
import java.util.*;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
    stage=primaryStage;
    
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/loginScreen.fxml"));
    Parent root = fxmlLoader.load();
    loginScreen = new Scene(root);

    FXMLLoader fxmlLoader2 = new FXMLLoader(getClass().getResource("/admScreen.fxml"));
    Parent root2 = fxmlLoader2.load();
    admScreen = new Scene(root2);

    FXMLLoader fxmlLoader3 = new FXMLLoader(getClass().getResource("/sellerScreen.fxml"));
    Parent root3 = fxmlLoader3.load();
    sellerScreen = new Scene(root3);
        
    FXMLLoader fxmlLoader4 = new FXMLLoader(getClass().getResource("/addEmployee.fxml"));
    Parent root4 = fxmlLoader4.load();
    addEmployee = new Scene(root4);

    FXMLLoader fxmlLoader5 = new FXMLLoader(getClass().getResource("/addProduct.fxml"));
    Parent root5 = fxmlLoader5.load();
    addProduct = new Scene(root5);

    FXMLLoader fxmlLoader6 = new FXMLLoader(getClass().getResource("/deleteProduct.fxml"));
    Parent root6 = fxmlLoader6.load();
    deleteProduct = new Scene(root6);

    FXMLLoader fxmlLoader7 = new FXMLLoader(getClass().getResource("/listProduct.fxml"));
    Parent root7 = fxmlLoader7.load();
    listProduct = new Scene(root7);

    FXMLLoader fxmlLoader8 = new FXMLLoader(getClass().getResource("/sellProduct.fxml"));
    Parent root8 = fxmlLoader8.load();
    sellProduct = new Scene(root8);

    FXMLLoader fxmlLoader9 = new FXMLLoader(getClass().getResource("/updateStock.fxml"));
    Parent root9 = fxmlLoader9.load();
    updateStock = new Scene(root9);

    stage.setTitle("Drogaria");
    stage.setScene(loginScreen);
    stage.show();
    }

    public static void changeScene(String change){
        switch(change){
            case "ADM":
                stage.setScene(admScreen);
                break;
            case "VENDEDOR":
                stage.setScene(sellerScreen);
                break;
        }
    }
    public static void main(String[] args) throws Exception {
        launch (args);
    }

}