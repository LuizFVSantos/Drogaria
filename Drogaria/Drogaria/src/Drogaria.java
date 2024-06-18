import java.sql.*;
import java.util.*;
public class Drogaria {
    private static Scanner scanner = new Scanner(System.in);
    private static Funcionariodb function = new Funcionariodb();
    private static Administradordb admFunction = new Administradordb();
    private static Vendedordb sellerFunction = new Vendedordb();

    public static void main(String[] args) throws SQLException {
        while (true) {
            System.out.println("Menu:");
            System.out.println("1. Criar funcionário");
            System.out.println("2. Acessar como funcionário");
            System.out.println("3. Sair");
            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    function.criarFuncionario();
                    break;
                case 2:
                    function.acessarComoFuncionario();
                    break;
                case 3:
                    System.out.println("Saindo...");
                    return;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }
    
    public void menuAdministrador(Administradordb administradordb) throws SQLException {
        while (true) {
            System.out.println("Menu Administrador:");
            System.out.println("1. Adicionar produto");
            System.out.println("2. Remover produto");
            System.out.println("3. Listar produtos");
            System.out.println("4. Listar um produto");
            System.out.println("5. Alterar estoque");
            System.out.println("6. Voltar ao menu principal");
            int opcao = scanner.nextInt();
            scanner.nextLine();
            switch (opcao) {
                case 1:
                    admFunction.adicionarProduto();
                    break;
                case 2:
                    admFunction.removerProduto();
                    break;
                case 3:
                List<Produto> produtos = function.listarProdutos();
                if (produtos.isEmpty()) {
                    System.out.println("Nenhum dado encontrado.");
                } else {
                    System.out.println("Dados encontrados:");
                    for (Produto produto : produtos) {
                        System.out.println(produto);
                    }
                }
                    break;
                case 4:
                List<Produto> umProduto = function.listarUmProduto();
                if (umProduto.isEmpty()) {
                    System.out.println("Nenhum dado encontrado.");
                } else {
                    System.out.println("Dados encontrados:");
                    for (Produto produto : umProduto) {
                        System.out.println(produto);
                    }
                }
                    break;
                case 5:
                    admFunction.alterarEstoque();
                case 6:
                    return;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }
    
    public void menuVendedor(Vendedordb vendedordb) {
        while (true) {
            System.out.println("Menu Vendedor:");
            System.out.println("1. Realizar venda");
            System.out.println("2. Listar produtos");
            System.out.println("3. Listar um produto");
            System.out.println("4. Voltar ao menu principal");
            int opcao = scanner.nextInt();
            scanner.nextLine();
            switch (opcao) {
                case 1:
                    sellerFunction.realizarVenda();
                    break;
                case 2:
                List<Produto> produtos = function.listarProdutos();

                if (produtos.isEmpty()) {
                    System.out.println("Nenhum dado encontrado.");
                } else {
                    System.out.println("Dados encontrados:");
                    for (Produto produto : produtos) {
                        System.out.println(produto);
                    }
                }
                    break;
                case 3:
                List<Produto> umProduto = function.listarUmProduto();
                if (umProduto.isEmpty()) {
                    System.out.println("Nenhum dado encontrado.");
                } else {
                    System.out.println("Dados encontrados:");
                    for (Produto produto : umProduto) {
                        System.out.println(produto);
                    }
                }
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }
}