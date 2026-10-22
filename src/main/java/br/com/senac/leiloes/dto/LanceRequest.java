package br.com.senac.leiloes.dto;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
public record LanceRequest(@NotBlank @Size(max=60) String nome,@NotNull @DecimalMin(value="0.01") BigDecimal valor) {}
