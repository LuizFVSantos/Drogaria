import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Funcionariodb {
    Drogaria mainMenu = new Drogaria();
    Scanner scanner = new Scanner (System.in);
    Conexao conexao = new Conexao();
    public void criarFuncionario() throws SQLException{
        String nome = scanner.nextLine();
        int tipo = scanner.nextInt();
        scanner.nextLine();
        Conexao exec = new Conexao();
        int find = buscarFuncionarioPorId(1);
        if (find == 0){
        String sql = "create table funcionarios( "+
                        " id int not null auto_increment,"
                        + " nome varchar(50) not null,"
                        + " tipo int not null,"+
                        " primary key (id))";
        exec.openDatabase();
        exec.executarQuery(sql);
        sql = " insert into funcionarios"+
                "(nome,tipo)"+
                "values"+
                "('"+ nome + "', '" + tipo +"')";   
        exec.openDatabase();
        exec.executarQuery(sql);
        exec.closeDatabase();
        System.out.println("FUNCIONARIO:"+nome+"\nTipo: "+ tipo);
        }else{

        String sql = " insert into funcionarios"+
                "(nome,tipo)"+
                "values"+
                "('"+ nome + "', '" + tipo +"')";   
        exec.openDatabase();
        exec.executarQuery(sql);
        exec.closeDatabase();
        System.out.println("FUNCIONARIO:"+nome+"\n Tipo: "+ tipo);
        }
    }
    public void acessarComoFuncionario() throws SQLException {
        System.out.print("ID do funcionário: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        int tipoFuncionario = buscarFuncionarioPorId(id);
        if (tipoFuncionario == 0) {
            System.out.println("Funcionário não encontrado.");
            return;
        }
        if (tipoFuncionario == 1) {
            mainMenu.menuVendedor(null);
        } else if (tipoFuncionario == 2) {
            mainMenu.menuAdministrador(null);
        } else {
            System.out.println("Tipo de funcionário desconhecido.");
        }
    }
    public int buscarFuncionarioPorId(int id) throws SQLException{
        Conexao exec = new Conexao();
        String sql = "SELECT id, tipo FROM funcionarios WHERE id = ?";
        Connection connection = null;

        int tipo=0;
        try {
            connection = exec.openDatabase();
            if (connection != null) {
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    preparedStatement.setInt(1, id);
                    ResultSet resultSet = preparedStatement.executeQuery();
    
                    if (resultSet.next()) {
                        tipo = resultSet.getInt("tipo");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conexao.closeDatabase();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return tipo;
    }
    public List<Produto> listarProdutos(){
        ResultSet result = null;
        Conexao exec = new Conexao();
        String sql = "SELECT * from produtos";
        List<Produto> produto = new ArrayList<Produto>();       
        Connection connection = null;
        try {
            connection = exec.openDatabase();
            if (connection != null) {
                PreparedStatement selectStatement = connection.prepareStatement(sql);               
                result = selectStatement.executeQuery();                
                while(result.next()){
                    Produto produtos = new Produto(0, null, 0, 0, null);
                    produtos.setId(result.getInt(1));
                    produtos.setNome(result.getString(2));
                    produtos.setPreco(result.getDouble(3));
                    produtos.setQuantidade(result.getInt(4));
                    produtos.setTarja(result.getString(5));
                    produto.add(produtos);
                }
            }        
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    exec.closeDatabase();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return produto; 
    }

    public List<Produto> listarUmProduto(){
        ResultSet result = null;
        System.out.print("ID do produto: ");
        int id = scanner.nextInt();
        Conexao exec = new Conexao();
        String sql = "SELECT id, nome, valor, quantidade, tarja FROM produtos WHERE id = ?";
        List<Produto> produto = new ArrayList<Produto>();       
        Connection connection = null;
        try {
            connection = exec.openDatabase();
            if (connection != null) {
                PreparedStatement selectStatement = connection.prepareStatement(sql);
                selectStatement.setInt(1, id);
                result = selectStatement.executeQuery();
                
                if (result.next()) {
                    Produto produtos = new Produto(
                        result.getInt("id"),
                        result.getString("nome"),
                        result.getDouble("valor"),
                        result.getInt("quantidade"),
                        result.getString("tarja")
                    );
                    produto.add(produtos);
                } else {
                    System.out.println("Produto não encontrado.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    exec.closeDatabase();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return produto;
    }
}