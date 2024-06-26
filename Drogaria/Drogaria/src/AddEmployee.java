import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class AddEmployee implements Initializable{

    @FXML
    private Button confirm;

    @FXML
    private ChoiceBox<String> choiceBox;
    private String funcoes[] = {"Administrador", "Vendedor"};

    @FXML
    private TextField cpf;

    @FXML
    private TextField nameEmployee;

    @FXML
    private Button out;

    @FXML
    private PasswordField password;

    @FXML
    void confirmB(ActionEvent event) {
        ArrayList<String> funcionario = new ArrayList<>();
        funcionario.add(cpf.getText());
        funcionario.add(nameEmployee.getText());
        funcionario.add(choiceBox.getValue());
        funcionario.add(password.getText());
        try {
            Conexao exec = new Conexao();   
            exec.adicionarFuncionarios(funcionario);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        cpf.clear();
        nameEmployee.clear();
        password.clear();
    }

    @FXML
    void outB(ActionEvent event) {
        Drogaria.changeScene("ADM");
    }

    @FXML
    void selectFunction(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        choiceBox.getItems().addAll(funcoes);

    }
}
