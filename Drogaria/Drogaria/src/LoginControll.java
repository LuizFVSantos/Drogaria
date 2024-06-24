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

    @FXML
    private TextField userText;

    @FXML
    void confirmLogin(ActionEvent event) throws SQLException{
        String cpf = userText.getText();
        String pass = passWord.getText();
        Conexao exec = new Conexao();
        exec.acessarComoFuncionario(cpf,pass);
        userText.clear();
        passWord.clear();
    }
}