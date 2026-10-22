package br.com.senac.leiloes.app;

import br.com.senac.leiloes.model.Lance;
import br.com.senac.leiloes.model.Produto;
import br.com.senac.leiloes.model.Usuario;
import br.com.senac.leiloes.repository.memory.ProdutoRepositoryMemoria;
import br.com.senac.leiloes.service.LeilaoService;
import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Testes demonstrativos — Casa de Leilões ===");
        var repositorio = new ProdutoRepositoryMemoria();
        var servico = new LeilaoService(repositorio);
        var produto = new Produto(1, "Notebook usado", new BigDecimal("500.00"));
        var usuario = new Usuario(1, "Maria Silva");

        // Teste 1: cadastro
        servico.cadastrarProduto(produto);
        verificar(servico.listarProdutos().size() == 1, "Cadastro e listagem de produto");

        // Teste 2: lance válido
        Lance lance = servico.ofertarLance(1, usuario, new BigDecimal("650.00"));
        verificar(lance.getValor().compareTo(new BigDecimal("650.00")) == 0, "Registro de lance válido");

        // Teste 3: lance inferior deve ser rejeitado
        boolean lanceInvalidoRejeitado = false;
        try {
            servico.ofertarLance(1, usuario, new BigDecimal("600.00"));
        } catch (IllegalArgumentException esperado) {
            lanceInvalidoRejeitado = true;
        }
        verificar(lanceInvalidoRejeitado, "Rejeição de lance inferior ao maior lance");

        // Teste 4: venda com lance registrado
        servico.venderProduto(1);
        verificar(produto.isVendido(), "Venda de produto com lance válido");

        // Teste 5: produto vendido não aceita novos lances
        boolean lanceAposVendaRejeitado = false;
        try {
            servico.ofertarLance(1, usuario, new BigDecimal("700.00"));
        } catch (IllegalStateException esperado) {
            lanceAposVendaRejeitado = true;
        }
        verificar(lanceAposVendaRejeitado, "Bloqueio de lance após venda");

        System.out.println("\nProdutos cadastrados:");
        servico.listarProdutos().forEach(System.out::println);
        System.out.println("\nTodos os testes demonstrativos foram concluídos.");
    }

    private static void verificar(boolean condicao, String descricao) {
        if (!condicao) throw new AssertionError("FALHOU: " + descricao);
        System.out.println("[OK] " + descricao);
    }
}
