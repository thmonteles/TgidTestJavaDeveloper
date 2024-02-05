package br.com.tgid.TgidTestJavaDeveloper.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CreateTransactionDTO(@NotBlank String clientCpf, @NotBlank String companyCnpj, @NotNull BigDecimal amount) {
}
