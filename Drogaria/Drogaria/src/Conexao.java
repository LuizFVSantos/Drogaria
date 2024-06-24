import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Conexao {
    static final String DB_URL = "jdbc:mysql://localhost:3306/drogaria";
    static final String USER = "root";
    static final String PASS = "teste123!";

    private Connection dbconn = null;
    private Statement sqlmgr = null;

    public Connection openDatabase() {
        try {
            dbconn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Conectado com sucesso");
            sqlmgr = dbconn.createStatement();
        } catch (Exception Error) {
            System.out.println("Erro ao conectar: " + Error.getMessage());
        }
        return dbconn;
    }

    public void closeDatabase() throws SQLException {
        if (sqlmgr != null) {
            sqlmgr.close();
            System.out.println("DataBase FECHADO");
        }
        if (dbconn != null) {
            dbconn.close();
            System.out.println("DataBase FECHADO");
        }
    }

    public int executarQuery(String sql) {
        try {
            return sqlmgr.executeUpdate(sql);
        } catch (Exception Error) {
            System.out.println("Erro ao executar query: " + Error.getMessage());
        }
        return -1;
    }

    public String acessarComoFuncionario(String cpf, String senha) throws SQLException {
        ArrayList<String> funcionario = buscarFuncionarioPorCpf(cpf);

        if (funcionario.isEmpty()) {
            return "Usuario não encontrado.";
        }

        String passFuncionario = funcionario.get(0);
        String funcaoFuncionario = funcionario.get(1);

        if (passFuncionario.equals(senha)) {
            if (funcaoFuncionario == null) {
                return "Usuario não encontrado.";
            }
            if (funcaoFuncionario.equals("Administrador")) {
                Drogaria.changeScene("ADM");
                System.out.println(funcaoFuncionario);
            } else if (funcaoFuncionario.equals("Vendedor")) {
                Drogaria.changeScene("VENDEDOR");
            }
        } else {
            System.out.println("Senha incorreta.");
        }
        return funcaoFuncionario;
    }

    public ArrayList<String> buscarFuncionarioPorCpf(String cpf) throws SQLException {
        Conexao exec = new Conexao();
        String sql = "SELECT senha, funcao FROM funcionarios WHERE cpf = ?";
        ArrayList<String> infos = new ArrayList<>();

        try (Connection connection = exec.openDatabase();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, cpf);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String senha = resultSet.getString("senha");
                infos.add(senha);
                String funcao = resultSet.getString("funcao");
                infos.add(funcao);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            exec.closeDatabase();
        }
        return infos;
    }

    public ArrayList<String> adicionarFuncionarios(ArrayList<String> funcionario) throws SQLException {
        Conexao exec = new Conexao();
        String cpf = funcionario.get(0), nome = funcionario.get(1), funcao = funcionario.get(2),
                senha = funcionario.get(3);
        String sql = "insert into funcionarios (cpf, nome, funcao, senha) values (?,?,?,?)";
        try {
            Connection connection = exec.openDatabase();
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, cpf);
            pstm.setString(2, nome);
            pstm.setString(3, funcao);
            pstm.setString(4, senha);
            pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            exec.closeDatabase();
        }
        return funcionario;
    }

    public ArrayList<String> adicionarProduto(ArrayList<String> produto) throws SQLException {
        String nome = produto.get(0), valor = produto.get(1), quantidade = produto.get(2), ean = produto.get(3),
                tarja = produto.get(4);
        valor = valor.replace(",", ".");
        Conexao exec = new Conexao();
        String sql = "insert into produtos(nome,valor, quantidade, ean, tarja) values (?,?,?,?,?)";
        Connection connection = null;
        try {
            connection = exec.openDatabase();
            if (connection != null) {
                PreparedStatement pstm = connection.prepareStatement(sql);
                pstm.setString(1, nome);
                pstm.setBigDecimal(2, new java.math.BigDecimal(valor));
                pstm.setLong(3, Integer.parseInt(quantidade));
                pstm.setString(4, ean);
                pstm.setString(5, tarja);
                pstm.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            exec.closeDatabase();
        }
        return produto;
    }

    public List<Produto> listarProdutos() {
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
                while (result.next()) {
                    Produto produtos = new Produto(0, null, 0, 0, null, null);
                    produtos.setId(result.getInt(1));
                    produtos.setNome(result.getString(2));
                    produtos.setValor(result.getDouble(3));
                    produtos.setQuantidade(result.getInt(4));
                    produtos.setEan(result.getString(5));
                    produtos.setTarja(result.getString(6));
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

    public String testeTarja(String ean) throws SQLException {
        String sql = "SELECT ean, tarja FROM produtos WHERE ean = ?";
        Connection connection = null;
        String tarja = null;
        try {
            connection = openDatabase();
            if (connection != null) {
                try (PreparedStatement selectStatement = connection.prepareStatement(sql)) {
                    selectStatement.setString(1, ean);
                    try (ResultSet resultSet = selectStatement.executeQuery()) {
                        if (resultSet.next()) {
                            tarja = resultSet.getString("tarja");
                        } else {
                            return tarja;
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return tarja;

        } finally {
            closeDatabase();
        }return tarja; 
    }
        

    public void realizarVenda(String ean, String stock) throws SQLException {
        String sql = "SELECT ean, quantidade FROM produtos WHERE ean = ?";
        String updateSql = "UPDATE produtos SET quantidade = ? WHERE ean = ?";
        Connection connection = null;
        int quantidadedb = 0;

        try {
            connection = openDatabase();
            if (connection != null) {
                try (PreparedStatement selectStatement = connection.prepareStatement(sql)) {
                    selectStatement.setString(1, ean);
                    try (ResultSet resultSet = selectStatement.executeQuery()) {
                        if (resultSet.next()) {
                            quantidadedb = resultSet.getInt("quantidade");
                            System.out.println(quantidadedb);
                        } else {
                            return;
                        }
                    }
                }
                if (quantidadedb < Integer.parseInt(stock)) {
                    return;
                }
                int quantidadeAtual = quantidadedb - Integer.parseInt(stock);
                try (PreparedStatement updateStatement = connection.prepareStatement(updateSql)) {
                    updateStatement.setInt(1, quantidadeAtual);
                    updateStatement.setString(2, ean);
                    updateStatement.executeUpdate();
                    System.out.println(quantidadeAtual);
                    System.out.println(ean);
                }
                return;
            } else {
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return;

        } finally {
            if (connection != null) {
                try {
                    closeDatabase();
                    Drogaria.changeScene("VENDEDOR");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
