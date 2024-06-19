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
        System.out.println("Digite o nome do funcionario");
        String nome = scanner.nextLine();
        int tipo=0;
        while(true){
            System.out.println("Digite a função: (1) para Vendedor e (2) para Administrador");
            int tipoSwitch = scanner.nextInt();
                switch (tipoSwitch) {
                    case 1:
                        tipo = 1;
                        break;
                    case 2:
                        tipo = 2;
                        break;
                    default:
                        System.out.println("Seleção inválida, tente novamente.");
                        continue;
                }
            break;
        }
        
        scanner.nextLine();
        Conexao exec = new Conexao();
        int find = buscarFuncionarioPorId(1);
        if (find == 0){
        String sql = "create table funcionarios( "+
                        " id int not null auto_increment,"+
                        "cpf varchar(11) not null,"
                        + " nome varchar(50) not null,"
                        + " tipo int not null,"+
                        " primary key (id))";
        exec.openDatabase();
        exec.executarQuery(sql);
        sql = " insert into funcionarios"+
                "(cpf,nome,tipo)"+
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