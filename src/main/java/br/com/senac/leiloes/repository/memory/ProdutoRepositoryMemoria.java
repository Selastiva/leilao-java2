package br.com.senac.leiloes.repository.memory;

import br.com.senac.leiloes.model.Produto;
import br.com.senac.leiloes.repository.ProdutoRepository;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ProdutoRepositoryMemoria implements ProdutoRepository {
    private final Map<Integer, Produto> produtos = new LinkedHashMap<>();

    @Override
    public void salvar(Produto produto) {
        if (produto == null) throw new IllegalArgumentException("O produto é obrigatório.");
        produtos.put(produto.getId(), produto);
    }

    @Override
    public Optional<Produto> buscarPorId(int id) {
        return Optional.ofNullable(produtos.get(id));
    }

    @Override
    public List<Produto> listarTodos() {
        return new ArrayList<>(produtos.values());
    }
}
