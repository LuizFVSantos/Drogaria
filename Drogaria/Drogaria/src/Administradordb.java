import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Administradordb{
    private  Scanner scanner = new Scanner (System.in);
    Conexao conexao = new Conexao();
    public void adicionarProduto() throws SQLException {
        System.out.println("Digite o nome do produto");
        String nome = scanner.nextLine();
        System.out.println("Digite o valor do produto");
        double valor = scanner.nextDouble();
        System.out.println("Digite a quantidade inicial do produto");
        int quantidade = scanner.nextInt();
        scanner.nextLine();
        String tarja=null;
        while (true) {
            System.out.println("Selecione a tarja");
            System.out.println("1. Sem tarja");
            System.out.println("2. Tarja Amarela");
            System.out.println("3. Tarja Vermelha");
            System.out.println("4. Tarja Preta");
            int tarjaSwitch = scanner.nextInt();
            switch (tarjaSwitch) {
                case 1:
                    tarja = "Sem tarja";
                    break;
                case 2:
                    tarja = "Amarela";
                    break;
                case 3:
                    tarja = "Vermelha";
                    break;
                case 4:
                    tarja = "Preta";
                    break;
                default:
                    System.out.println("Seleção inválida, tente novamente.");
                    continue;
            }
            break;
        }
        Conexao exec = new Conexao();
        String sql = "create table produtos( "+
                        " id int not null auto_increment,"
                        + " nome varchar(50) not null,"
                        + " valor decimal (5,2) not null,"
                        + " quantidade int,"+
                        " tarja varchar(15) not null,"+
                        " primary key (id))";
        exec.openDatabase();
        exec.executarQuery(sql);
        sql = " insert into produtos"+
                "(nome,valor, quantidade, tarja)"+
                "values"+
                "('"+ nome + "', '" + valor + "', '" + quantidade + "','"+ tarja + "')";       
        exec.openDatabase();
        exec.executarQuery(sql);
        exec.closeDatabase();
    }

    public void removerProduto() throws SQLException {
        System.out.print("ID do produto a ser removido: ");
        int id = scanner.nextInt();
        Conexao exec = new Conexao();
        String sql = ("DELETE FROM produtos WHERE id = "+ id);
        exec.openDatabase();
        exec.executarQuery(sql);
        exec.closeDatabase();    
    }

    public int alterarEstoque() {
        System.out.print("ID do produto: ");
        int id = scanner.nextInt();
        System.out.print("Quantidade: ");
        int quantidade = scanner.nextInt();
        scanner.nextLine();
        Conexao exec = new Conexao();
        String sql = ("SELECT id, quantidade FROM produtos WHERE id = ?");
        String updateSql = ("UPDATE produtos SET quantidade = ? WHERE id = ?");
        Connection connection = null;
        int quantidadedb=0;
        try {
            connection = exec.openDatabase();
            if (connection != null) {
                try (PreparedStatement selectStatement = connection.prepareStatement(sql)) {
                    selectStatement.setInt(1, id);
                    try (ResultSet resultSet = selectStatement.executeQuery()) {
                        if (resultSet.next()) {
                            quantidadedb = resultSet.getInt("quantidade");
                        } else {
                            System.out.println("Produto não encontrado.");
                            return -1;
                        }
                    }
                }
                int quantidadeAtual = quantidadedb + quantidade;
                try (PreparedStatement updateStatement = connection.prepareStatement(updateSql)) {
                    updateStatement.setInt(1, quantidadeAtual);
                    updateStatement.setInt(2, id);
                    updateStatement.executeUpdate();
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
        return quantidadedb;
    }
}