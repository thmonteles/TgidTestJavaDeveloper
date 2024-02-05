package br.com.tgid.TgidTestJavaDeveloper.services;

import br.com.tgid.TgidTestJavaDeveloper.DTOs.CreateCompanyDTO;
import br.com.tgid.TgidTestJavaDeveloper.exceptions.CompanyServiceException;

public interface CompanyService {
    boolean create(CreateCompanyDTO dto) throws CompanyServiceException;
}
