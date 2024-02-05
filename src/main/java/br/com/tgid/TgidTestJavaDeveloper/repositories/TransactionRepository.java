package br.com.tgid.TgidTestJavaDeveloper.repositories;

import br.com.tgid.TgidTestJavaDeveloper.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
