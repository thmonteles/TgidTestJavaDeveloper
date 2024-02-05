package br.com.tgid.TgidTestJavaDeveloper.services.clientImpl;

import br.com.tgid.TgidTestJavaDeveloper.DTOs.CreateCompanyDTO;
import br.com.tgid.TgidTestJavaDeveloper.exceptions.CompanyServiceException;
import br.com.tgid.TgidTestJavaDeveloper.models.Company;
import br.com.tgid.TgidTestJavaDeveloper.repositories.CompanyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CompanyServiceImplTest {

    private String validCnpj;

    @BeforeEach
    public void setupData() {
        validCnpj = "13.505.754/0001-45";
    }

    @Test
    void testCreateCompanyWithValidDTO() throws CompanyServiceException {
        CompanyRepository companyRepository = mock(CompanyRepository.class);
        CompanyServiceImpl companyService = new CompanyServiceImpl(companyRepository);

        CreateCompanyDTO validDto = new CreateCompanyDTO(validCnpj, 10.0, "company@example.com", "123456789", "some-password");

        when(companyRepository.findByCnpj(validDto.cnpj())).thenReturn(Optional.empty());
        when(companyRepository.save(any(Company.class))).thenReturn(new Company());

        boolean result = companyService.create(validDto);
        assertTrue(result);
        verify(companyRepository, times(1)).findByCnpj(validDto.cnpj());
        verify(companyRepository, times(1)).save(any(Company.class));
    }

    @Test
    void testCreateCompanyWithInvalidCNPJ() {
        CompanyRepository companyRepository = mock(CompanyRepository.class);
        CompanyServiceImpl companyService = new CompanyServiceImpl(companyRepository);
        CreateCompanyDTO invalidDto = new CreateCompanyDTO("invalid-validCnpj", 10.0, "company@example.com", "123456789", "some-password");
        assertThrows(CompanyServiceException.class, () -> companyService.create(invalidDto));
        verify(companyRepository, never()).findByCnpj(anyString());
        verify(companyRepository, never()).save(any(Company.class));
    }

    @Test
    void testCreateCompanyWithExistingCNPJ() {
        CompanyRepository companyRepository = mock(CompanyRepository.class);
        CompanyServiceImpl companyService = new CompanyServiceImpl(companyRepository);
        CreateCompanyDTO existingCnpjDto = new CreateCompanyDTO(validCnpj, 10.0, "existing.company@example.com", "123456789", "some-password");
        when(companyRepository.findByCnpj(existingCnpjDto.cnpj())).thenReturn(Optional.of(new Company()));
        assertThrows(CompanyServiceException.class, () -> companyService.create(existingCnpjDto));
        verify(companyRepository, times(1)).findByCnpj(existingCnpjDto.cnpj());
        verify(companyRepository, never()).save(any(Company.class));
    }
}
