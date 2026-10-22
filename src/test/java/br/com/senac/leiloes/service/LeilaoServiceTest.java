package br.com.senac.leiloes.service;

import br.com.senac.leiloes.model.Produto;
import br.com.senac.leiloes.model.Usuario;
import br.com.senac.leiloes.repository.memory.ProdutoRepositoryMemoria;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LeilaoServiceTest {
    private LeilaoService servico;
    private Produto produto;
    private Usuario usuario;

    @BeforeEach
    void prepararCenario() {
        servico = new LeilaoService(new ProdutoRepositoryMemoria());
        produto = new Produto(1, "Notebook", new BigDecimal("500.00"));
        usuario = new Usuario(1, "Ana");
        servico.cadastrarProduto(produto);
    }

    @Test
    void aceitaLanceMaiorQueValorInicial() {
        servico.ofertarLance(1, usuario, new BigDecimal("600.00"));
        assertEquals(0, new BigDecimal("600.00").compareTo(produto.getMaiorLance()));
    }

    @Test
    void rejeitaLanceAbaixoDoValorInicial() {
        assertThrows(IllegalArgumentException.class,
                () -> servico.ofertarLance(1, usuario, new BigDecimal("400.00")));
        assertEquals(0, BigDecimal.ZERO.compareTo(produto.getMaiorLance()));
    }

    @Test
    void rejeitaLanceQueNaoSuperaMaiorLance() {
        servico.ofertarLance(1, usuario, new BigDecimal("600.00"));
        assertThrows(IllegalArgumentException.class,
                () -> servico.ofertarLance(1, usuario, new BigDecimal("600.00")));
        assertEquals(0, new BigDecimal("600.00").compareTo(produto.getMaiorLance()));
    }

    @Test
    void permiteVendaDepoisDeLanceValido() {
        servico.ofertarLance(1, usuario, new BigDecimal("600.00"));
        servico.venderProduto(1);
        assertTrue(produto.isVendido());
    }

    @Test
    void rejeitaVendaSemLance() {
        assertThrows(IllegalStateException.class, () -> servico.venderProduto(1));
        assertFalse(produto.isVendido());
    }

    @Test
    void rejeitaLanceDepoisDaVenda() {
        servico.ofertarLance(1, usuario, new BigDecimal("600.00"));
        servico.venderProduto(1);
        assertThrows(IllegalStateException.class,
                () -> servico.ofertarLance(1, usuario, new BigDecimal("700.00")));
    }

    @Test
    void rejeitaCadastroComIdDuplicado() {
        Produto repetido = new Produto(1, "Monitor", new BigDecimal("200.00"));
        assertThrows(IllegalArgumentException.class, () -> servico.cadastrarProduto(repetido));
    }
}
