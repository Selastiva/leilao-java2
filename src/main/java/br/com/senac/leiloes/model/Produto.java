package br.com.senac.leiloes.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name="produtos")
public class Produto {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false,length=70) private String nome;
 @Column(nullable=false,length=40) private String categoria;
 @Column(nullable=false,length=500) private String descricao;
 @Column(name="valor_inicial",nullable=false,precision=12,scale=2) private BigDecimal valorInicial;
 @Column(name="maior_lance",nullable=false,precision=12,scale=2) private BigDecimal maiorLance = BigDecimal.ZERO;
 @Column(nullable=false) private boolean vendido = false;
 @Column(nullable=false) private LocalDateTime criadoEm = LocalDateTime.now();
 protected Produto() {}
 public Produto(String nome,String categoria,String descricao,BigDecimal valorInicial){
  if(nome==null||nome.isBlank()) throw new IllegalArgumentException("O nome é obrigatório.");
  if(categoria==null||categoria.isBlank()) throw new IllegalArgumentException("A categoria é obrigatória.");
  if(descricao==null||descricao.trim().length()<10) throw new IllegalArgumentException("A descrição deve ter pelo menos 10 caracteres.");
  if(valorInicial==null||valorInicial.signum()<=0) throw new IllegalArgumentException("O valor inicial deve ser maior que zero.");
  this.nome=nome.trim();this.categoria=categoria.trim();this.descricao=descricao.trim();this.valorInicial=valorInicial;
 }
 public Long getId(){return id;} public String getNome(){return nome;} public String getCategoria(){return categoria;} public String getDescricao(){return descricao;} public BigDecimal getValorInicial(){return valorInicial;} public BigDecimal getMaiorLance(){return maiorLance;} public boolean isVendido(){return vendido;} public LocalDateTime getCriadoEm(){return criadoEm;}
 public void registrarLance(BigDecimal valor){ if(vendido) throw new IllegalStateException("Não é possível ofertar em produto vendido."); if(valor==null||valor.compareTo(valorInicial)<0) throw new IllegalArgumentException("O lance deve ser igual ou superior ao valor inicial."); if(valor.compareTo(maiorLance)<=0) throw new IllegalArgumentException("O lance deve superar o maior lance atual."); maiorLance=valor; }
 public void marcarComoVendido(){if(vendido)throw new IllegalStateException("O produto já foi vendido.");if(maiorLance.signum()<=0)throw new IllegalStateException("Não é possível vender sem lance válido.");vendido=true;}
}
