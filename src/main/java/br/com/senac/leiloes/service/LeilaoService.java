package br.com.senac.leiloes.service;

import br.com.senac.leiloes.dto.*;
import br.com.senac.leiloes.model.*;
import br.com.senac.leiloes.repository.*;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LeilaoService {
 private final ProdutoRepository produtos; private final LanceRepository lances;
 public LeilaoService(ProdutoRepository produtos,LanceRepository lances){this.produtos=produtos;this.lances=lances;}
 public List<Produto> listar(){return produtos.findAll();}
 public Produto buscar(Long id){return produtos.findById(id).orElseThrow(()->new IllegalArgumentException("Produto não encontrado."));}
 public Produto cadastrar(ProdutoRequest r){return produtos.save(new Produto(r.nome(),r.categoria(),r.descricao(),r.valorInicial()));}
 @Transactional public Lance ofertar(Long id,LanceRequest r){Produto p=buscar(id);p.registrarLance(r.valor());return lances.save(new Lance(p,r.nome(),r.valor()));}
 @Transactional public Produto vender(Long id){Produto p=buscar(id);p.marcarComoVendido();return produtos.save(p);}
 public List<Lance> listarLances(Long id){buscar(id);return lances.findByProdutoIdOrderByDataHoraDesc(id);}
}
