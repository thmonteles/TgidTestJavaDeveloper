package br.com.tgid.TgidTestJavaDeveloper.models;

import br.com.tgid.TgidTestJavaDeveloper.DTOs.CreateClientDTO;
import br.com.tgid.TgidTestJavaDeveloper.utils.CPFUtil;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name= "Clients")
public class Client extends User{

    @NotBlank(message = "This field cpf cannot be empty")
    @Pattern(regexp = "\\d{11}", message = "Invalid cpf format")
    @Column(unique = true, length = 11)
    private String cpf;


    public Client() {
        setUserType(UserType.CLIENT);
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }


    public static Client fromDTO(CreateClientDTO createClientDTO) {
        Client client = new Client();
        client.setName(createClientDTO.name());
        client.setCpf(CPFUtil.cleaning(createClientDTO.cpf()));
        client.setEmail(createClientDTO.email());
        client.setPassword(createClientDTO.password());
        client.setPhone(createClientDTO.phone());

        return client;
    }
}
