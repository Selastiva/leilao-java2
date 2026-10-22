package br.com.senac.leiloes.service;

import br.com.senac.leiloes.dto.LanceRequest;
import br.com.senac.leiloes.dto.ProdutoRequest;
import br.com.senac.leiloes.model.Lance;
import br.com.senac.leiloes.model.Produto;
import br.com.senac.leiloes.repository.LanceRepository;
import br.com.senac.leiloes.repository.ProdutoRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LeilaoServiceTest {
    @Mock private ProdutoRepository produtos;
    @Mock private LanceRepository lances;
    @InjectMocks private LeilaoService service;
    private Produto produto;

    @BeforeEach
    void configurar() {
        produto = new Produto("Notebook", "Informática", "Notebook em bom estado", new BigDecimal("500.00"));
    }

    @Test
    void listaProdutosDoRepositorio() {
        when(produtos.findAll()).thenReturn(List.of(produto));
        assertEquals(1, service.listar().size());
        verify(produtos).findAll();
    }

    @Test
    void cadastraProdutoValido() {
        ProdutoRequest request = new ProdutoRequest("Notebook", "Informática", "Notebook em bom estado", new BigDecimal("500.00"));
        when(produtos.save(any(Produto.class))).thenAnswer(inv -> inv.getArgument(0));
        Produto salvo = service.cadastrar(request);
        assertEquals("Notebook", salvo.getNome());
        assertEquals(0, new BigDecimal("500.00").compareTo(salvo.getValorInicial()));
        verify(produtos).save(any(Produto.class));
    }

    @Test
    void registraLanceValido() {
        when(produtos.findById(1L)).thenReturn(Optional.of(produto));
        when(lances.save(any(Lance.class))).thenAnswer(inv -> inv.getArgument(0));
        Lance salvo = service.ofertar(1L, new LanceRequest("Ana", new BigDecimal("600.00")));
        assertEquals("Ana", salvo.getNomeParticipante());
        assertEquals(0, new BigDecimal("600.00").compareTo(produto.getMaiorLance()));
        verify(lances).save(any(Lance.class));
    }

    @Test
    void rejeitaProdutoInexistente() {
        when(produtos.findById(99L)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> service.buscar(99L));
    }
}
