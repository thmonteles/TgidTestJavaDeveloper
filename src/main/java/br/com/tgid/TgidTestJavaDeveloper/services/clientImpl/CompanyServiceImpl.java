package br.com.tgid.TgidTestJavaDeveloper.services.clientImpl;

import br.com.tgid.TgidTestJavaDeveloper.DTOs.CreateCompanyDTO;
import br.com.tgid.TgidTestJavaDeveloper.exceptions.CompanyServiceException;
import br.com.tgid.TgidTestJavaDeveloper.models.Company;
import br.com.tgid.TgidTestJavaDeveloper.repositories.CompanyRepository;
import br.com.tgid.TgidTestJavaDeveloper.services.CompanyService;
import br.com.tgid.TgidTestJavaDeveloper.utils.CNPJUtil;
import br.com.tgid.TgidTestJavaDeveloper.utils.CPFUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyServiceImpl implements CompanyService {
    @Autowired
    CompanyRepository companyRepository;

    @Override
    public boolean create(CreateCompanyDTO dto) throws CompanyServiceException {
        var cnpjWithoutInvalidCharacters = CNPJUtil.cleaning(dto.cnpj());

        if (!CPFUtil.validate(cnpjWithoutInvalidCharacters)) {
            throw new CompanyServiceException("invalid cnpj format cnpj: " + dto.cnpj());
        }

        var company = companyRepository.findByCnpj(dto.cnpj());

        if (company.isPresent()) {
            throw new CompanyServiceException("company already exists with this cnpj: " + dto.cnpj());
        }
        var newCompany = Company.fromDTO(dto);
        return companyRepository.save(newCompany) != null;
    }
}
