package br.com.tgid.TgidTestJavaDeveloper.services;

import br.com.tgid.TgidTestJavaDeveloper.models.Transaction;

public interface ClientAlert {
    void sendEmailFinishTransaction(Transaction transaction);
}
