package br.com.tgid.TgidTestJavaDeveloper.controllers;


import br.com.tgid.TgidTestJavaDeveloper.DTOs.CreateTransactionDTO;
import br.com.tgid.TgidTestJavaDeveloper.DTOs.TransactionResponseDTO;
import br.com.tgid.TgidTestJavaDeveloper.exceptions.TransactionServiceException;
import br.com.tgid.TgidTestJavaDeveloper.services.TransactionService;
import br.com.tgid.TgidTestJavaDeveloper.services.CompanyWebhook;
import br.com.tgid.TgidTestJavaDeveloper.services.clientImpl.HttpCompanyWebhookImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;


    @PostMapping("/create")
    public ResponseEntity<TransactionResponseDTO> processTransaction(@RequestBody CreateTransactionDTO createTransactionDTO) {
        try {
            var transactionResult = transactionService.process(createTransactionDTO);
            if (transactionResult != null) {
                return ResponseEntity.ok(new TransactionResponseDTO(true, "Transaction processed successfully", Optional.of(transactionResult)));
            }
            return ResponseEntity.badRequest().body(new TransactionResponseDTO(false, "Transaction processing failed", Optional.empty()));
        } catch (TransactionServiceException e) {
            return ResponseEntity.badRequest().body(new TransactionResponseDTO(false, e.getMessage(), Optional.empty()));
        }
    }
}
