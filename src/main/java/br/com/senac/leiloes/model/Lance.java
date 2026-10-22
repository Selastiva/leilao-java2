package br.com.senac.leiloes.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity @Table(name="lances")
public class Lance {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @ManyToOne(optional=false,fetch=FetchType.LAZY) @JoinColumn(name="produto_id") private Produto produto;
 @Column(nullable=false,length=60) private String nomeParticipante;
 @Column(nullable=false,precision=12,scale=2) private BigDecimal valor;
 @Column(nullable=false) private LocalDateTime dataHora=LocalDateTime.now();
 protected Lance() {}
 public Lance(Produto produto,String nomeParticipante,BigDecimal valor){this.produto=produto;this.nomeParticipante=nomeParticipante.trim();this.valor=valor;}
 public Long getId(){return id;} public String getNomeParticipante(){return nomeParticipante;} public BigDecimal getValor(){return valor;} public LocalDateTime getDataHora(){return dataHora;}
}
