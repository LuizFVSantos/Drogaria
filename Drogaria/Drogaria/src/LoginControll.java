import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class LoginControll {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button logginB;

    @FXML
    private TextField userText;

    @FXML
    void confirmLogin(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert logginB != null : "fx:id=\"logginB\" was not injected: check your FXML file 'loginScreen.fxml'.";
        assert userText != null : "fx:id=\"userText\" was not injected: check your FXML file 'loginScreen.fxml'.";

    }

}
