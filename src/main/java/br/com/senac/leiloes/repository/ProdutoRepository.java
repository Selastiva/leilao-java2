package br.com.senac.leiloes.repository;
import br.com.senac.leiloes.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ProdutoRepository extends JpaRepository<Produto,Long> {}
