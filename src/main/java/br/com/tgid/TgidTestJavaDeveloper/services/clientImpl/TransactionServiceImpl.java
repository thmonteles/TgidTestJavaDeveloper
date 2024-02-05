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
        var client = clientRepository.findByCpf(CPFUtil.cleaning(dto.clientCpf()))
                .orElseThrow(() -> new TransactionServiceException("client not found for this cpf: " + dto.clientCpf()));

        var company = companyRepository
                .findByCnpj(CNPJUtil.cleaning(dto.companyCnpj()))
                .orElseThrow(() -> new TransactionServiceException("company not found for this cnpj: " + dto.companyCnpj()));

        var calculatedFeeFromAmount = calculateDynamicFeeFromCompanyFee(company, dto.amount());
        var totalAmountForTransaction = calculateFinalAmountFromFee(dto.amount(), calculatedFeeFromAmount);
        var checkIfBalanceIsInsufficientToTransaction = balanceIsInsufficient(company, totalAmountForTransaction);

        if (checkIfBalanceIsInsufficientToTransaction)
            throw new TransactionServiceException("Insufficient balance amount for this company");

        var transaction = Transaction.from(company, client, totalAmountForTransaction);
        updateCompanyBalance(company, totalAmountForTransaction);
        return transactionRepository.save(transaction);
    }


    static BigDecimal calculateDynamicFeeFromCompanyFee(Company company, BigDecimal amount) {
        return amount
                .multiply(BigDecimal.valueOf(company.getFee()));
    }

    static BigDecimal calculateFinalAmountFromFee(BigDecimal rawAmount, BigDecimal fee) {
        return rawAmount.add(fee);
    }

    static boolean balanceIsInsufficient(Company company, BigDecimal amount) {
        return company
                .getBalance()
                .compareTo(amount) < 0;
    }

    void updateCompanyBalance(Company company, BigDecimal totalAmountForTransaction) {
        var newBalance = company
                .getBalance()
                .add(totalAmountForTransaction);

        company.setBalance(newBalance);
        companyRepository.save(company);
    }

}
