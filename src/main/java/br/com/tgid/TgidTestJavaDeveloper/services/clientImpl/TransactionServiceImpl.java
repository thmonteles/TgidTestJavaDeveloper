package br.com.tgid.TgidTestJavaDeveloper.services.clientImpl;


import br.com.tgid.TgidTestJavaDeveloper.DTOs.CreateTransactionDTO;
import br.com.tgid.TgidTestJavaDeveloper.exceptions.TransactionServiceException;
import br.com.tgid.TgidTestJavaDeveloper.models.Company;
import br.com.tgid.TgidTestJavaDeveloper.models.Transaction;
import br.com.tgid.TgidTestJavaDeveloper.repositories.ClientRepository;
import br.com.tgid.TgidTestJavaDeveloper.repositories.CompanyRepository;
import br.com.tgid.TgidTestJavaDeveloper.repositories.TransactionRepository;
import br.com.tgid.TgidTestJavaDeveloper.services.TransactionService;
import br.com.tgid.TgidTestJavaDeveloper.utils.CNPJUtil;
import br.com.tgid.TgidTestJavaDeveloper.utils.CPFUtil;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final CompanyRepository companyRepository;
    private final ClientRepository clientRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository, CompanyRepository companyRepository, ClientRepository clientRepository) {
        this.transactionRepository = transactionRepository;
        this.companyRepository = companyRepository;
        this.clientRepository = clientRepository;
    }

    public Transaction process(CreateTransactionDTO dto) throws TransactionServiceException {
        var amount = dto.amount();
        var client = clientRepository.findByCpf(CPFUtil.cleaning(dto.clientCpf()))
                .orElseThrow(() -> new TransactionServiceException("client not found for this cpf: " + dto.clientCpf()));

        var company = companyRepository
                .findByCnpj(CNPJUtil.cleaning(dto.companyCnpj()))
                .orElseThrow(() -> new TransactionServiceException("company not found for this cnpj: " + dto.companyCnpj()));

        var calculatedFeeFromAmount = amount
                .abs()
                .multiply(BigDecimal.valueOf(company.getFee()).setScale(2, RoundingMode.HALF_EVEN));

        var thisAmountIsPositive = amount.compareTo(BigDecimal.ZERO) > 0;
        BigDecimal transactionAmount;
        if (thisAmountIsPositive) {
            transactionAmount = processPositiveTransaction(company, amount, calculatedFeeFromAmount);
        } else {
            transactionAmount = processNegativeTransaction(company, amount, calculatedFeeFromAmount);
        }

        var transaction = Transaction.from(company, client, transactionAmount, amount, company.getFee());
        return transactionRepository.save(transaction);
    }

    BigDecimal processPositiveTransaction(Company company, BigDecimal amount, BigDecimal calculatedFeeFromAmount) {
        var totalAmountForTransaction = amount.subtract(calculatedFeeFromAmount);
        var newBalance = company
                .getBalance()
                .add(totalAmountForTransaction);

        company.setBalance(newBalance);
        companyRepository.save(company);
        return totalAmountForTransaction;
    }

    BigDecimal processNegativeTransaction(Company company, BigDecimal amount, BigDecimal calculatedFeeFromAmount) throws TransactionServiceException {
        var sumOfNegativeAmountWithFee = amount
                .abs()
                .add(calculatedFeeFromAmount);
        var transactionAmount = sumOfNegativeAmountWithFee.negate();
        var checkIfBalanceIsInsufficientToTransaction = company
                .getBalance()
                .compareTo(transactionAmount) >= 0;

        if (checkIfBalanceIsInsufficientToTransaction)
            throw new TransactionServiceException("Insufficient balance amount for this company");

        var newBalance = company
                .getBalance()
                .subtract(sumOfNegativeAmountWithFee);
        company.setBalance(newBalance);
        companyRepository.save(company);
        return transactionAmount;
    }

}
