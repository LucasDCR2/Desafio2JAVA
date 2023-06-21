package classes;

import java.time.LocalDateTime;
import java.util.Scanner;

public class Venda {
    
    private Produto produto;
    private int quantidade;
    private LocalDateTime dataHora;
    private String formaPagamento;

//=======================================================<Construtor>=========================================================//

    public Venda (Produto produto, int qtd) {
        this.produto = produto;
        this.quantidade = qtd;
        this.dataHora = LocalDateTime.now();
        this.formaPagamento = selecFormaPagamento();
    }

//===================================================<Getters and Setters>====================================================//

    public Produto getProduto() {
        return produto;
    }

    
    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    
    public int getQuantidade() {
        return quantidade;
    }

    
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    
    public LocalDateTime getDataHora() {
        return dataHora;
    }

    
    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    
    public String getFormaPagamento() {
        return formaPagamento;
    }

    
    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

//=======================================================<Forma Pagamento>=======================================================//

    private String selecFormaPagamento() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("---Selecione a forma de pagamento!---");
        System.out.println("1. Dinheiro");
        System.out.println("2. Débito");
        System.out.println("3. Crédito");
        System.out.println("4. Pix");

        int opcao = scanner.nextInt();
        scanner.nextLine(); // Limpa o buffer do scanner

        switch (opcao) {
            case 1:
                return "Dinheiro";

            case 2:
                return "Débito";

            case 3:
                return "Crédito";

            case 4:
                return "Pix";

            default:
                return "Forma de pagamento inválida, tente novamente";
        }
    }   

}
