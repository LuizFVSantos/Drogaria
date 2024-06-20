import java.sql.*;
import java.util.ArrayList;
public class Conexao {
    static final String DB_URL = "jdbc:mysql://localhost:3306/drogaria_una";
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

    public void acessarComoFuncionario() throws SQLException {
        LoginControll cpfLogin = new LoginControll();
        LoginControll passLogin = new LoginControll();
        String cpf = cpfLogin.getUserText();
        String pass = passLogin.getPassWord();
        ArrayList<String> funcionario = buscarFuncionarioPorCpf(cpf);
        
        if (funcionario.isEmpty()) {
            System.out.println("Usuario não encontrado.");
            return;
        }
        
        String passFuncionario = funcionario.get(0);
        String tipoFuncionario = funcionario.get(1);

        if(passFuncionario.equals(pass)) {
            if (tipoFuncionario == null) {
                return;
            }
            if (tipoFuncionario.equals("ADM")) {
                Drogaria.changeScene("ADM");
            } else if (tipoFuncionario.equals("VENDEDOR")) {
                Drogaria.changeScene("VENDEDOR");
            }
        } else {
            System.out.println("Senha incorreta.");
        }
    }

    public ArrayList<String> buscarFuncionarioPorCpf(String cpf) throws SQLException {
        Conexao exec = new Conexao();
        String sql = "SELECT senha, tipo FROM funcionarios WHERE cpf = ?";
        ArrayList<String> infos = new ArrayList<>();
        
        try (Connection connection = exec.openDatabase();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
             
            preparedStatement.setString(1, cpf);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String senha = resultSet.getString("senha");
                infos.add(senha);
                String tipo = resultSet.getString("tipo");
                infos.add(tipo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            exec.closeDatabase();
        }
        return infos;
    }

}