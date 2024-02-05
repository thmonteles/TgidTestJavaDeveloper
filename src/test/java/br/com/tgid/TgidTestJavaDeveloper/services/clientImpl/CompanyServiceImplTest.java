package br.com.tgid.TgidTestJavaDeveloper.services.clientImpl;

import br.com.tgid.TgidTestJavaDeveloper.DTOs.CreateCompanyDTO;
import br.com.tgid.TgidTestJavaDeveloper.exceptions.CompanyServiceException;
import br.com.tgid.TgidTestJavaDeveloper.models.Company;
import br.com.tgid.TgidTestJavaDeveloper.repositories.CompanyRepository;
import br.com.tgid.TgidTestJavaDeveloper.utils.CNPJUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CompanyServiceImplTest {

    private String validCnpj;
    private String validName;

    @BeforeEach
    public void setupData() {
        validCnpj = "13.505.754/0001-45";
        validName = "testName";
    }

    @Test
    void testCreateCompanyWithValidDTO() throws CompanyServiceException {
        CompanyRepository companyRepository = mock(CompanyRepository.class);
        CompanyServiceImpl companyService = new CompanyServiceImpl(companyRepository);

        CreateCompanyDTO validDto = new CreateCompanyDTO(validCnpj, 10.0, validName, "company@example.com", "123456789", "some-password");

        when(companyRepository.findByCnpj(validDto.cnpj())).thenReturn(Optional.empty());
        when(companyRepository.save(any(Company.class))).thenReturn(new Company());

        boolean result = companyService.create(validDto);
        assertTrue(result);
        verify(companyRepository, times(1)).findByCnpj(CNPJUtil.cleaning(CNPJUtil.cleaning(validDto.cnpj())));
        verify(companyRepository, times(1)).save(any(Company.class));
    }

    @Test
    void testCreateCompanyWithInvalidCNPJ() {
        CompanyRepository companyRepository = mock(CompanyRepository.class);
        CompanyServiceImpl companyService = new CompanyServiceImpl(companyRepository);
        CreateCompanyDTO invalidDto = new CreateCompanyDTO("invalid-validCnpj", 10.0, validName, "company@example.com", "123456789", "some-password");
        assertThrows(CompanyServiceException.class, () -> companyService.create(invalidDto));
        verify(companyRepository, never()).findByCnpj(anyString());
        verify(companyRepository, never()).save(any(Company.class));
    }

    @Test
    void testCreateCompanyWithExistingCNPJ() {
        CompanyRepository companyRepository = mock(CompanyRepository.class);
        CompanyServiceImpl companyService = new CompanyServiceImpl(companyRepository);
        CreateCompanyDTO existingCnpjDto = new CreateCompanyDTO(validCnpj, 10.0, validName, "existing.company@example.com", "123456789", "some-password");
        when(companyRepository.findByCnpj(CNPJUtil.cleaning(existingCnpjDto.cnpj()))).thenReturn(Optional.of(new Company()));
        assertThrows(CompanyServiceException.class, () -> companyService.create(existingCnpjDto));
        verify(companyRepository, times(1)).findByCnpj(CNPJUtil.cleaning(existingCnpjDto.cnpj()));
        verify(companyRepository, never()).save(any(Company.class));
    }
}
