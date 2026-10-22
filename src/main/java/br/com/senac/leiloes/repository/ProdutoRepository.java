package br.com.senac.leiloes.repository;

import br.com.senac.leiloes.model.Produto;
import java.util.List;
import java.util.Optional;

public interface ProdutoRepository {
    void salvar(Produto produto);
    Optional<Produto> buscarPorId(int id);
    List<Produto> listarTodos();
}
