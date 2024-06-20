import java.sql.*;
import java.util.ArrayList;
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
        }
        if (dbconn != null) {
            dbconn.close();
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

        if(passFuncionario.equals(senha)) {
            if (funcaoFuncionario == null) {
                return "Usuario não encontrado.";
            }
            if (funcaoFuncionario.equals("ADM")) {
                Drogaria.changeScene("ADM");
                System.out.println(funcaoFuncionario);
            } else if (funcaoFuncionario.equals("VENDEDOR")) {
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

}