package br.com.senac.leiloes.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Produto {
    private final int id;
    private final String nome;
    private final BigDecimal valorInicial;
    private BigDecimal maiorLance;
    private boolean vendido;

    public Produto(int id, String nome, BigDecimal valorInicial) {
        if (id <= 0) throw new IllegalArgumentException("O ID deve ser positivo.");
        if (nome == null || nome.isBlank()) throw new IllegalArgumentException("O nome é obrigatório.");
        if (valorInicial == null || valorInicial.signum() <= 0)
            throw new IllegalArgumentException("O valor inicial deve ser maior que zero.");
        this.id = id;
        this.nome = nome.trim();
        this.valorInicial = valorInicial;
        this.maiorLance = BigDecimal.ZERO;
        this.vendido = false;
    }

    public int getId() { return id; }
    public String getNome() { return nome; }
    public BigDecimal getValorInicial() { return valorInicial; }
    public BigDecimal getMaiorLance() { return maiorLance; }
    public boolean isVendido() { return vendido; }

    public void registrarLance(BigDecimal valor) {
        if (vendido) throw new IllegalStateException("Não é possível ofertar em produto vendido.");
        if (valor == null || valor.compareTo(valorInicial) < 0)
            throw new IllegalArgumentException("O lance deve ser igual ou superior ao valor inicial.");
        if (valor.compareTo(maiorLance) <= 0)
            throw new IllegalArgumentException("O lance deve superar o maior lance atual.");
        maiorLance = valor;
    }

    public void marcarComoVendido() {
        if (vendido) throw new IllegalStateException("O produto já foi vendido.");
        vendido = true;
    }

    @Override
    public String toString() {
        return "Produto{id=" + id + ", nome='" + nome + "', valorInicial=" + valorInicial
                + ", maiorLance=" + maiorLance + ", vendido=" + vendido + "}";
    }

    @Override
    public boolean equals(Object outro) {
        if (this == outro) return true;
        if (!(outro instanceof Produto produto)) return false;
        return id == produto.id;
    }

    @Override
    public int hashCode() { return Objects.hash(id); }
}
