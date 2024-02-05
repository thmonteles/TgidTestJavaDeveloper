package br.com.tgid.TgidTestJavaDeveloper.repositories;

import br.com.tgid.TgidTestJavaDeveloper.models.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    Optional<Company> findByCnpj(String cnpj);
}
