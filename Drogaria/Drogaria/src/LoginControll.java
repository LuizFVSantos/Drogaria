import java.sql.SQLException;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
        System.out.println(cpf);
        System.out.println(pass);
        if(cpf.equals("") || pass.equals("")){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Existem campos vazios");
            Optional<ButtonType> result = alert.showAndWait();
            Boolean confirm = result.isPresent() && result.get() == ButtonType.OK;
            if(confirm = true){
            return;
            }
        }else{
            Conexao exec = new Conexao();
            exec.acessarComoFuncionario(cpf,pass);
            userText.clear();
            passWord.clear();
        }
    }
}