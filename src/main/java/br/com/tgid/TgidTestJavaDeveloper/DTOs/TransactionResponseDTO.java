package br.com.tgid.TgidTestJavaDeveloper.DTOs;

import br.com.tgid.TgidTestJavaDeveloper.models.Transaction;

import java.util.Optional;

public record TransactionResponseDTO(boolean success, String meg, Optional<Transaction> transaction) {
}
