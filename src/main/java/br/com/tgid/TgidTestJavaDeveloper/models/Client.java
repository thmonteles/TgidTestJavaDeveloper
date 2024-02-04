package br.com.tgid.TgidTestJavaDeveloper.models;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

@Entity
public class Client extends User{

    @NotBlank(message = "This field cpf cannot be empty")
    @CPF(message = "Invalid cpf format")
    @Column(unique = true)
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


}
