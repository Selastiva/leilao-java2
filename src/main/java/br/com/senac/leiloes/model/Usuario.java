package br.com.senac.leiloes.model;

public class Usuario {
    private final int id;
    private final String nome;

    public Usuario(int id, String nome) {
        if (id <= 0) throw new IllegalArgumentException("O ID do usuário deve ser positivo.");
        if (nome == null || nome.isBlank()) throw new IllegalArgumentException("O nome do usuário é obrigatório.");
        this.id = id;
        this.nome = nome.trim();
    }

    public int getId() { return id; }
    public String getNome() { return nome; }

    @Override
    public String toString() { return "Usuario{id=" + id + ", nome='" + nome + "'}"; }
}
