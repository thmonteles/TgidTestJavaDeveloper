package br.com.tgid.TgidTestJavaDeveloper.DTOs;

import jakarta.validation.constraints.NotBlank;


public record CreateCompanyDTO(@NotBlank String cnpj, @NotBlank double fee, @NotBlank String name, @NotBlank String email, @NotBlank String phone, @NotBlank String password) {
}
