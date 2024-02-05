package br.com.tgid.TgidTestJavaDeveloper.services.clientImpl;

import br.com.tgid.TgidTestJavaDeveloper.DTOs.CreateClientDTO;
import br.com.tgid.TgidTestJavaDeveloper.exceptions.ClientServiceException;
import br.com.tgid.TgidTestJavaDeveloper.models.Client;
import br.com.tgid.TgidTestJavaDeveloper.repositories.ClientRepository;
import br.com.tgid.TgidTestJavaDeveloper.services.ClientService;
import br.com.tgid.TgidTestJavaDeveloper.utils.CPFUtil;
import org.springframework.stereotype.Service;


@Service
public class ClientServiceImpl implements ClientService {
    private final ClientRepository repository;

    public ClientServiceImpl(ClientRepository repository) {
        this.repository = repository;
    }


    @Override
    public boolean create(CreateClientDTO dto) throws ClientServiceException {
        var cpfWithoutInvalidCharacters = CPFUtil.cleaning(dto.cpf());

        if (!CPFUtil.validate(cpfWithoutInvalidCharacters)) {
            throw new ClientServiceException("invalid cpf format cpf: " + dto.cpf());
        }

        var client = repository.findByEmail(dto.email());

        if (client.isPresent()) {
            throw new ClientServiceException("Client with email '" + dto.email() + "' already exists");
        }

        Client newClient = Client.fromDTO(dto);
        return repository.save(newClient) != null;
    }

}
