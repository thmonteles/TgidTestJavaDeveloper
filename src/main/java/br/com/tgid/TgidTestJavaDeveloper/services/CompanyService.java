package br.com.tgid.TgidTestJavaDeveloper.services;

import br.com.tgid.TgidTestJavaDeveloper.DTOs.CompanyResponseDTO;
import br.com.tgid.TgidTestJavaDeveloper.DTOs.CreateCompanyDTO;
import br.com.tgid.TgidTestJavaDeveloper.exceptions.CompanyServiceException;

import java.util.List;

public interface CompanyService {
    boolean create(CreateCompanyDTO dto) throws CompanyServiceException;
    public List<CompanyResponseDTO> listAllCompanies() throws CompanyServiceException;

}
