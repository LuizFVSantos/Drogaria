import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Vendedordb {
    Scanner scanner = new Scanner (System.in);
    Conexao conexao = new Conexao();
    public int realizarVenda() {
        System.out.print("ID do produto: ");
        int id = scanner.nextInt();
        System.out.print("Quantidade: ");
        int quantidade = scanner.nextInt();
        scanner.nextLine();
        Conexao exec = new Conexao();
        String sql = ("SELECT id, quantidade, tarja FROM produtos WHERE id = ?");
        String updateSql = ("UPDATE produtos SET quantidade = ? WHERE id = ?");
        Connection connection = null;
        int quantidadedb=0;
        String tarja = null;
        try {
            connection = exec.openDatabase();
            if (connection != null) {
                try (PreparedStatement selectStatement = connection.prepareStatement(sql)) {
                    selectStatement.setInt(1, id);
                    try (ResultSet resultSet = selectStatement.executeQuery()) {
                        if (resultSet.next()) {
                            quantidadedb = resultSet.getInt("quantidade");
                            tarja = resultSet.getString("tarja");
                        } else {
                            System.out.println("Produto não encontrado.");
                            return -1;
                        }
                    }
                }
                if ("Vermelha".equalsIgnoreCase(tarja) || "Preta".equalsIgnoreCase(tarja)) {
                    System.out.println("O produto exige receita médica.");
                    System.out.println("O cliente apresentou a receita? (1. Sim / 2. Não): ");
                    int receita = scanner.nextInt();
                    scanner.nextLine();
                    while (true){
                        switch (receita) {
                            case 1:
                            break;
                            case 2:
                            System.out.println("Venda não realizada. Receita médica não apresentada.");
                            return -1;
                            default:
                            System.out.println("Seleção inválida, tente novamente.");
                            continue;
                        }
                        break;
                    }
                }
                if (quantidadedb < quantidade) {
                    System.out.println("Quantidade insuficiente no estoque.");
                    return -1;
                }
                int quantidadeAtual = quantidadedb - quantidade;
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
