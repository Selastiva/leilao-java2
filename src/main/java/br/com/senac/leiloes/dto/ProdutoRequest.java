package br.com.senac.leiloes.dto;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
public record ProdutoRequest(@NotBlank @Size(max=70) String nome,@NotBlank String categoria,@NotBlank @Size(min=10,max=500) String descricao,@NotNull @DecimalMin(value="0.01") BigDecimal valorInicial) {}
