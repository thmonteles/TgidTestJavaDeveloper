package br.com.tgid.TgidTestJavaDeveloper.services;

import br.com.tgid.TgidTestJavaDeveloper.DTOs.CreateClientDTO;
import br.com.tgid.TgidTestJavaDeveloper.exceptions.ClientServiceException;


public interface ClientService {
    boolean create(CreateClientDTO dto) throws ClientServiceException;
}
