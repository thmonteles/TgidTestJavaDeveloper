package br.com.tgid.TgidTestJavaDeveloper.services.clientImpl;

import br.com.tgid.TgidTestJavaDeveloper.DTOs.CreateClientDTO;
import br.com.tgid.TgidTestJavaDeveloper.exceptions.ClientServiceException;
import br.com.tgid.TgidTestJavaDeveloper.models.Client;
import br.com.tgid.TgidTestJavaDeveloper.repositories.ClientRepository;
import br.com.tgid.TgidTestJavaDeveloper.utils.CPFUtil;
import org.junit.jupiter.api.Test;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClientServiceImplTest {

    @Test
    void testCreateClientWithValidDTO() throws ClientServiceException {
        ClientRepository clientRepository = mock(ClientRepository.class);
        ClientServiceImpl clientService = new ClientServiceImpl(clientRepository);
        CreateClientDTO validDto = new CreateClientDTO("John Doe", "123.456.789-09", "john.doe@example.com", "some-password", "123456789");
        when(clientRepository.findByCpf(CPFUtil.cleaning(validDto.cpf()))).thenReturn(Optional.empty());
        when(clientRepository.save(any(Client.class))).thenReturn(new Client());
        boolean result = clientService.create(validDto);
        assertTrue(result);
        verify(clientRepository, times(1)).findByCpf(CPFUtil.cleaning(validDto.cpf()));
        verify(clientRepository, times(1)).save(any(Client.class));
    }

    @Test
    void testCreateClientWithInvalidCPF() {
        ClientRepository clientRepository = mock(ClientRepository.class);
        ClientServiceImpl clientService = new ClientServiceImpl(clientRepository);
        CreateClientDTO invalidDto = new CreateClientDTO("John Doe", "invalid-cpf", "john.doe@example.com", "some-password", "123456789");
        assertThrows(ClientServiceException.class, () -> clientService.create(invalidDto));
        verify(clientRepository, never()).findByCpf(anyString());
        verify(clientRepository, never()).save(any(Client.class));
    }

    @Test
    void testCreateClientWithExistingCpf() {
        ClientRepository clientRepository = mock(ClientRepository.class);
        ClientServiceImpl clientService = new ClientServiceImpl(clientRepository);
        CreateClientDTO existingCpfDto = new CreateClientDTO("John Doe", "123.456.789-09", "existing.email@example.com", "some-password", "123456789");
        when(clientRepository.findByCpf(CPFUtil.cleaning(existingCpfDto.cpf())))
                .thenReturn(Optional.of(new Client()));
        assertThrows(ClientServiceException.class, () -> clientService.create(existingCpfDto));
        verify(clientRepository, times(1)).findByCpf(CPFUtil.cleaning(existingCpfDto.cpf()));
        verify(clientRepository, never()).save(any(Client.class));
    }


}
