package br.com.senac.leiloes.service;

import br.com.senac.leiloes.model.Lance;
import br.com.senac.leiloes.model.Produto;
import br.com.senac.leiloes.model.Usuario;
import br.com.senac.leiloes.repository.ProdutoRepository;
import java.math.BigDecimal;
import java.util.List;

public class LeilaoService {
    private final ProdutoRepository produtoRepository;

    public LeilaoService(ProdutoRepository produtoRepository) {
        if (produtoRepository == null) throw new IllegalArgumentException("O repositório é obrigatório.");
        this.produtoRepository = produtoRepository;
    }

    public void cadastrarProduto(Produto produto) {
        if (produtoRepository.buscarPorId(produto.getId()).isPresent())
            throw new IllegalArgumentException("Já existe produto com esse ID.");
        produtoRepository.salvar(produto);
    }

    public List<Produto> listarProdutos() {
        return produtoRepository.listarTodos();
    }

    public Lance ofertarLance(int produtoId, Usuario usuario, BigDecimal valor) {
        Produto produto = produtoRepository.buscarPorId(produtoId)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado."));
        produto.registrarLance(valor);
        return new Lance(usuario, produto, valor);
    }

    public void venderProduto(int produtoId) {
        Produto produto = produtoRepository.buscarPorId(produtoId)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado."));
        if (produto.getMaiorLance().signum() <= 0)
            throw new IllegalStateException("Não é possível vender sem ao menos um lance válido.");
        produto.marcarComoVendido();
    }
}
