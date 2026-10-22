package br.com.senac.leiloes.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Lance {
    private final Usuario usuario;
    private final Produto produto;
    private final BigDecimal valor;
    private final LocalDateTime dataHora;

    public Lance(Usuario usuario, Produto produto, BigDecimal valor) {
        if (usuario == null || produto == null || valor == null)
            throw new IllegalArgumentException("Usuário, produto e valor são obrigatórios.");
        this.usuario = usuario;
        this.produto = produto;
        this.valor = valor;
        this.dataHora = LocalDateTime.now();
    }

    public Usuario getUsuario() { return usuario; }
    public Produto getProduto() { return produto; }
    public BigDecimal getValor() { return valor; }
    public LocalDateTime getDataHora() { return dataHora; }

    @Override
    public String toString() {
        return "Lance{usuario=" + usuario.getNome() + ", produto=" + produto.getNome()
                + ", valor=" + valor + ", dataHora=" + dataHora + "}";
    }
}
