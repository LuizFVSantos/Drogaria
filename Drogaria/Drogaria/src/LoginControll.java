import java.sql.Connection;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginControll {

    @FXML
    private Button logginB;

    @FXML
    private PasswordField passWord;

    public String getPassWord() {
        String pass = passWord.getText();
        return pass;
    }

    @FXML
    private TextField userText;

    public String getUserText() {
        String text = userText.getText();
        return text;
    }

    @FXML
    void confirmLogin(ActionEvent event) throws SQLException{
        Conexao exec = new Conexao();
        exec.acessarComoFuncionario();
    }
}