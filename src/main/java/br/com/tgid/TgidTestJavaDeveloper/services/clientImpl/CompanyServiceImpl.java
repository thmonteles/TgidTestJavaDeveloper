package br.com.tgid.TgidTestJavaDeveloper.services.clientImpl;

import br.com.tgid.TgidTestJavaDeveloper.DTOs.CompanyResponseDTO;
import br.com.tgid.TgidTestJavaDeveloper.DTOs.CreateCompanyDTO;
import br.com.tgid.TgidTestJavaDeveloper.exceptions.CompanyServiceException;
import br.com.tgid.TgidTestJavaDeveloper.models.Company;
import br.com.tgid.TgidTestJavaDeveloper.repositories.CompanyRepository;
import br.com.tgid.TgidTestJavaDeveloper.services.CompanyService;
import br.com.tgid.TgidTestJavaDeveloper.utils.CNPJUtil;
import br.com.tgid.TgidTestJavaDeveloper.utils.CPFUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository companyRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public boolean create(CreateCompanyDTO dto) throws CompanyServiceException {
        var cnpjWithoutInvalidCharacters = CNPJUtil.cleaning(dto.cnpj());

        if (!CNPJUtil.validate(cnpjWithoutInvalidCharacters)) {
            throw new CompanyServiceException("invalid cnpj format cnpj: " + dto.cnpj());
        }

        var company = companyRepository.findByCnpj(cnpjWithoutInvalidCharacters);

        if (company.isPresent()) {
            throw new CompanyServiceException("company already exists with this cnpj: " + dto.cnpj());
        }
        var newCompany = Company.fromDTO(dto);
        return companyRepository.save(newCompany) != null;
    }

    @Override
    public List<CompanyResponseDTO> listAllCompanies() throws CompanyServiceException {
        return companyRepository
                .findAll()
                .stream()
                .map(company -> new CompanyResponseDTO(company.getName(), company.getCnpj()))
                .toList();

    }
}
