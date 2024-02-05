package br.com.tgid.TgidTestJavaDeveloper.services.clientImpl;

import br.com.tgid.TgidTestJavaDeveloper.controllers.CompanyController;
import br.com.tgid.TgidTestJavaDeveloper.models.Transaction;
import br.com.tgid.TgidTestJavaDeveloper.services.ClientAlert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class DummyClientAlertImpl implements ClientAlert {

    private static final Logger logger = LoggerFactory.getLogger(CompanyController.class);

    @Async
    public void sendEmailFinishTransaction(Transaction transaction) {
        logger.info("DummySendEmail{msg='finished transaction into', companyCnpj="
                + transaction.getCompany().getCnpj()
                + ", clientCpf="
                + transaction.getClient().getCpf()
                + ", amount=" + transaction.getAmount() + "}"
        );
    }

}
