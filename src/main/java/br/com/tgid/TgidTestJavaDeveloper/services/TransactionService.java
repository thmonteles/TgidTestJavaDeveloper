package br.com.tgid.TgidTestJavaDeveloper.services;


import br.com.tgid.TgidTestJavaDeveloper.DTOs.CreateTransactionDTO;
import br.com.tgid.TgidTestJavaDeveloper.exceptions.TransactionServiceException;
import br.com.tgid.TgidTestJavaDeveloper.models.Transaction;

public interface TransactionService {
    Transaction process(CreateTransactionDTO dto) throws TransactionServiceException;
}
