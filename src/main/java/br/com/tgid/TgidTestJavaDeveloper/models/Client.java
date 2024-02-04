package br.com.tgid.TgidTestJavaDeveloper.models;


import jakarta.persistence.Entity;

@Entity
public class Client extends User{

    public Client() {
        setUserType(UserType.CLIENT);
    }
}
