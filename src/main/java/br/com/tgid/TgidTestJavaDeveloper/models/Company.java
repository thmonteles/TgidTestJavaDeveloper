package br.com.tgid.TgidTestJavaDeveloper.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CNPJ;

import java.math.BigDecimal;


@Entity
@Table(name= "Companies")
public class Company extends User {

    @NotBlank(message = "This field cnpj cannot be empty")
    @Column(unique = true, length = 14)
    @CNPJ(message = "Invalid CNPJ format")
    private String cnpj;
    private BigDecimal balance;
    private double fee;

    public Company() {
        setUserType(UserType.COMPANY);
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }


    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }
}

