package br.com.tgid.TgidTestJavaDeveloper.services;

import br.com.tgid.TgidTestJavaDeveloper.DTOs.CreateCompanyDTO;

public interface CompanyService {
    boolean create(CreateCompanyDTO dto);
}
