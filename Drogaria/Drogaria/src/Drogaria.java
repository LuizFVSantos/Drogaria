import java.sql.*;
import java.util.*;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
public class Drogaria extends Application{

    public static void main(String[] args) throws Exception {
        launch (args);
    }

    @Override
    public void start(Stage stage) throws Exception {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/loginScreen.fxml"));
    Parent root = fxmlLoader.load();
    Scene telaLogin = new Scene(root);

    stage.setTitle("Drogaria");
    stage.setScene(telaLogin);
    stage.show();
    }
}