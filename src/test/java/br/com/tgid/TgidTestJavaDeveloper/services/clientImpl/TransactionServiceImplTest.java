package br.com.tgid.TgidTestJavaDeveloper.services.clientImpl;

import br.com.tgid.TgidTestJavaDeveloper.DTOs.CreateTransactionDTO;
import br.com.tgid.TgidTestJavaDeveloper.exceptions.TransactionServiceException;
import br.com.tgid.TgidTestJavaDeveloper.models.Client;
import br.com.tgid.TgidTestJavaDeveloper.models.Company;
import br.com.tgid.TgidTestJavaDeveloper.models.Transaction;
import br.com.tgid.TgidTestJavaDeveloper.repositories.ClientRepository;
import br.com.tgid.TgidTestJavaDeveloper.repositories.CompanyRepository;
import br.com.tgid.TgidTestJavaDeveloper.repositories.TransactionRepository;
import br.com.tgid.TgidTestJavaDeveloper.utils.CNPJUtil;
import br.com.tgid.TgidTestJavaDeveloper.utils.CPFUtil;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class TransactionServiceImplTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private CompanyRepository companyRepository;

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    @Test
    void processSuccessfulTransaction() throws TransactionServiceException {
        var expectedValueTransaction = BigDecimal.valueOf(11.76);
        var expectedCompanyBalanceTransaction = BigDecimal.valueOf(311.76);
        CreateTransactionDTO dto = new CreateTransactionDTO("clientCpf", "companyCnpj", BigDecimal.valueOf(12.0D));
        Client client = new Client();
        Company company = new Company();
        company.setBalance(BigDecimal.valueOf(300.0D));
        company.setFee(0.02);

        when(clientRepository.findByCpf(CPFUtil.cleaning(dto.clientCpf()))).thenReturn(Optional.of(client));
        when(companyRepository.findByCnpj(CNPJUtil.cleaning(dto.companyCnpj()))).thenReturn(Optional.of(company));
        when(transactionRepository.save(any(Transaction.class))).thenAnswer(invocation -> invocation.<Transaction>getArgument(0));

        Transaction result = transactionService.process(dto);

        assertEquals(expectedValueTransaction, result.getAmount().setScale(2, RoundingMode.HALF_EVEN));
        assertEquals(expectedCompanyBalanceTransaction, result.getCompany().getBalance().setScale(2, RoundingMode.HALF_EVEN));
        assertNotNull(result);
        verify(companyRepository, times(1)).save(any(Company.class));
        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }

    @Test
    void processWithClientNotFound() {
        CreateTransactionDTO dto = new CreateTransactionDTO("000.000.000.00", "companyCnpj", BigDecimal.TEN);
        when(clientRepository.findByCpf(CPFUtil.cleaning(dto.clientCpf()))).thenReturn(Optional.empty());
        TransactionServiceException exception = assertThrows(TransactionServiceException.class, () -> transactionService.process(dto));
        assertEquals("client not found for this cpf: " + dto.clientCpf(), exception.getMessage());
    }

    @Test
    void processWithCompanyNotFound() {
        CreateTransactionDTO dto = new CreateTransactionDTO("000.000.000.00", "51.501.889/0001-21", BigDecimal.TEN);
        Client client = new Client();
        when(clientRepository.findByCpf(CPFUtil.cleaning(dto.clientCpf()))).thenReturn(Optional.of(client));
        when(companyRepository.findByCnpj(CNPJUtil.cleaning(dto.companyCnpj()))).thenReturn(Optional.empty());
        TransactionServiceException exception = assertThrows(TransactionServiceException.class, () -> transactionService.process(dto));
        assertEquals("company not found for this cnpj: " + dto.companyCnpj(), exception.getMessage());
    }

    @Test
    void processWithInsufficientBalance() {
        CreateTransactionDTO dto = new CreateTransactionDTO("000.000.000.00", "51.501.889/0001-21", BigDecimal.valueOf(-100.0));
        Client client = new Client();
        Company company = new Company();
        company.setBalance(BigDecimal.valueOf(12));
        company.setFee(0.2);
        when(clientRepository.findByCpf(CPFUtil.cleaning(dto.clientCpf()))).thenReturn(Optional.of(client));
        when(companyRepository.findByCnpj(CNPJUtil.cleaning(dto.companyCnpj()))).thenReturn(Optional.of(company));
        TransactionServiceException exception = assertThrows(TransactionServiceException.class, () -> transactionService.process(dto));
        assertEquals("Insufficient balance amount for this company", exception.getMessage());
    }


}
