package br.com.tgid.TgidTestJavaDeveloper.DTOs;

import jakarta.validation.constraints.NotBlank;

public record CreateClientDTO(@NotBlank String name, @NotBlank String cpf, @NotBlank String email, @NotBlank String password, @NotBlank String phone) {

    public String toString() {
        return String.format("CreateClientDTO{name='%s', cpf='%s', email='%s', phone='%s'}",
                name, cpf, email, phone);
    }
}
