package br.com.senac.leiloes.repository;
import br.com.senac.leiloes.model.Lance;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
public interface LanceRepository extends JpaRepository<Lance,Long> { List<Lance> findByProdutoIdOrderByDataHoraDesc(Long produtoId); }
