import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ListProduct implements Initializable{
    Conexao exec = new Conexao();
    private ArrayList<Produto> produtos = exec.listarProdutos();
    private ObservableList<Produto> obsProdutos;
    
    @FXML
    private TableView<Produto> tableOfProducts;
    @FXML
    private TableColumn<Produto, String> idCol;
    @FXML
    private TableColumn<Produto, String> nameCol;
    @FXML
    private TableColumn<Produto, String> valorCol;
    @FXML
    private TableColumn<Produto, String> quantCol;
    @FXML
    private TableColumn<Produto, String> eanCol;
    @FXML
    private TableColumn<Produto, String> tarjaCol;
    
    
    @FXML
    private Button out;

    @FXML
    void outB(ActionEvent event) { 
        Drogaria.changeScene("ADM");
        
    }

    public void loadProdList(){
        obsProdutos = FXCollections.observableArrayList(produtos);
        idCol.setCellValueFactory(new PropertyValueFactory<>("Id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("Nome"));
        valorCol.setCellValueFactory(new PropertyValueFactory<>("Valor"));
        quantCol.setCellValueFactory(new PropertyValueFactory<>("Quantidade"));
        eanCol.setCellValueFactory(new PropertyValueFactory<>("Ean"));
        tarjaCol.setCellValueFactory(new PropertyValueFactory<>("Tarja"));
        tableOfProducts.setItems(obsProdutos);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadProdList();
    }
}
