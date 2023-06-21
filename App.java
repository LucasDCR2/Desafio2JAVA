import classes.Produto;
import classes.Venda;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import javax.sound.sampled.SourceDataLine;

public class App {
    public static void main(String[] args) {
        executarPrograma();
    }

    //===========================================================<Main>=============================================================//
    public static void executarPrograma() {
        Scanner scanner = new Scanner(System.in);

        List<Produto> produtos = criarListaProdutos();
        List<Venda> vendas = new ArrayList<>();

        while (true) {
            System.out.println();
            System.out.println("Selecione uma opção:");
            System.out.println("--------------------");
            System.out.println("1. Registrar Venda");
            System.out.println("2. Finalizar Compra");
            System.out.println();

            int opcao = scanner.nextInt();
            scanner.nextLine();

            if (opcao == 1) {
                registrarVenda(produtos, vendas, scanner);
            } else if (opcao == 2) {
                if (vendas.isEmpty()) {
                    System.out.println();
                    System.out.println("Nenhuma venda registrada.");
                    System.out.println();
                    continue;
                } else {
                    listarResumoVendas(vendas, scanner);
                }
            } else {
                System.out.println("Opção inválida. Tente novamente.");
            }

            if (!desejaContinuar(scanner)) {
                break;
            }
        }

        scanner.close();
    }

    //=========================================================<Registrar Venda>======================================================//

    private static void registrarVenda(List<Produto> produtos, List<Venda> vendas, Scanner scanner) {
        listarProdutos(produtos);
    
        System.out.println("Escolha o produto pelo índice ou 0 para cancelar:");
        int produtoSelecionado = scanner.nextInt();
        scanner.nextLine();
    
        if (produtoSelecionado == 0) {
            System.out.println("Venda cancelada.");
            return;
        }
    
        if (produtoSelecionado < 1 || produtoSelecionado > produtos.size()) {
            System.out.println("Indice de produto invalido. Tente novamente.");
            return;
        }
    
        Produto produto = produtos.get(produtoSelecionado - 1);
    
        System.out.println();
        System.out.println("Informe a quantidade vendida: ");
        System.out.println();
        int quantidade = scanner.nextInt();
        scanner.nextLine();
    
        vendas.add(new Venda(produto, quantidade));
    
        System.out.println();
        System.out.println("Venda registrada com sucesso!");
        System.out.println();
    }
    

    //======================================================<Criar Lista Produtos>====================================================//

    private static List<Produto> criarListaProdutos() {
        List<Produto> produtos = new ArrayList<>();

        produtos.add(new Produto("Pastel", 4.80));
        produtos.add(new Produto("Del Valle", 5.00));
        produtos.add(new Produto("Coxinha", 5.75));
        produtos.add(new Produto("Café", 8.00));
        produtos.add(new Produto("Sorvete", 12.80));

        return produtos;
    }

    //==========================================================<Lista Produtos>======================================================//

    private static void listarProdutos(List<Produto> produtos) {
        System.out.println();
        System.out.println("Lista de Produtos:");
        System.out.println("-----------------");

        for (int i = 0; i < produtos.size(); i++) {
            Produto produto = produtos.get(i);
            System.out.println((i + 1) + ". " + produto.getDescricao() + " - R$ " + formatarValor(produto.getValor()));
        }

        System.out.println();
    }

    //===========================================================<Resumo Vendas=>=====================================================//

    private static void listarResumoVendas(List<Venda> vendas, Scanner scanner) {
        System.out.println("---------------------------------------------------------------------");
        System.out.println("|              Resumo das vendas");
        System.out.println("|");

        LocalDateTime inicioVendas = vendas.get(0).getDataHora();
        LocalDateTime fimVendas = vendas.get(vendas.size() - 1).getDataHora();

        System.out.println("|  Data e Hora de Início: " + inicioVendas.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
        System.out.println("|  Data e Hora de Fim: " + fimVendas.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
        System.out.println("---------------------------------------------------------------------");

        System.out.println();

        System.out.println("---------------------------------------------------------------------");
        System.out.println("|      Quantidade vendida de cada produto:");
        System.out.println("|");
        Map<Produto, Integer> quantidadePorProduto = new HashMap<>();
        Map<Produto, Double> totalPorProduto = new HashMap<>();

        for (Venda venda : vendas) {
            Produto produto = venda.getProduto();
            int quantidade = venda.getQuantidade();
            double valorTotal = quantidade * produto.getValor();

            quantidadePorProduto.put(produto, quantidadePorProduto.getOrDefault(produto, 0) + quantidade);
            totalPorProduto.put(produto, totalPorProduto.getOrDefault(produto, 0.0) + valorTotal);
        }

        quantidadePorProduto.forEach((produto, quantidade) -> {
            double valorTotal = totalPorProduto.get(produto);
            System.out.println("|  " + produto.getDescricao() + " - " + "quantidade: " + quantidade + " - Total: R$ " + formatarValor(valorTotal));
        });
            System.out.println("---------------------------------------------------------------------");
            System.out.println();

        System.out.println("---------------------------------------------------------------------");
        System.out.println("|  Resumo de vendas por forma de pagamento (do menor para o maior):");
        System.out.println("|");
        Map<String, Integer> quantidadePorFormaPagamento = new HashMap<>();
        Map<String, Double> totalPorFormaPagamento = new HashMap<>();
        double valorTotalVendas = 0.0;

        for (Venda venda : vendas) {
            String formaPagamento = venda.getFormaPagamento();
            int quantidade = venda.getQuantidade();
            double valorTotal = quantidade * venda.getProduto().getValor();

            quantidadePorFormaPagamento.put(formaPagamento, quantidadePorFormaPagamento.getOrDefault(formaPagamento, 0) + quantidade);
            totalPorFormaPagamento.put(formaPagamento, totalPorFormaPagamento.getOrDefault(formaPagamento, 0.0) + valorTotal);
            valorTotalVendas += valorTotal;
        }

        totalPorFormaPagamento.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .forEach(entry -> {
                    String formaPagamento = entry.getKey();
                    int quantidade = quantidadePorFormaPagamento.get(formaPagamento);
                    double valorTotal = entry.getValue();
                    System.out.println("|  " + formaPagamento + " - " + quantidade + " - Total: R$ " + formatarValor(valorTotal));
                });
        System.out.println("---------------------------------------------------------------------");

        System.out.println();
        System.out.println("------------------------------------------");
        System.out.println("|  " + "Total de Vendas Registradas: " + vendas.size() + "        |");
        System.out.println("|  " + "Valor Total das Vendas: R$ " + formatarValor(valorTotalVendas) + "  |");
        System.out.println("------------------------------------------");
    }



    //====================================================<Formatar Valor>====================================================//

    private static String formatarValor(double valor) {
        return NumberFormat.getCurrencyInstance().format(valor);
    }

    //===================================================<Deseja Continuar>===================================================//

    private static boolean desejaContinuar(Scanner scanner) {
        System.out.println();
        System.out.println("Deseja continuar? (S/N)");
        String continuar = scanner.nextLine();

        return continuar.equalsIgnoreCase("S");
    }
}
