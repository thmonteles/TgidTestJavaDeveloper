package br.com.tgid.TgidTestJavaDeveloper.models;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @ManyToOne
    private Client client;

    @ManyToOne
    private Company company;
    private BigDecimal amount;

    @Column(name = "raw_amount")
    private BigDecimal rawAmount;

    @Column(name = "fee_applied_on_this_operation")
    private double feeAppliedOnThisOperation;

    @Column
    private LocalDateTime timestamp;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Transaction() {
        this.timestamp = LocalDateTime.now();
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public BigDecimal getRawAmount() {
        return rawAmount;
    }

    public void setRawAmount(BigDecimal rawAmount) {
        this.rawAmount = rawAmount;
    }

    public double getFeeAppliedOnThisOperation() {
        return feeAppliedOnThisOperation;
    }

    public void setFeeAppliedOnThisOperation(double feeAppliedOnThisOperation) {
        this.feeAppliedOnThisOperation = feeAppliedOnThisOperation;
    }

    public static Transaction from(Company company, Client client, BigDecimal amount, BigDecimal rawAmount, double fee ) {
        var transaction = new Transaction();
        transaction.setCompany(company);
        transaction.setClient(client);
        transaction.setAmount(amount);
        transaction.setRawAmount(rawAmount);
        transaction.setFeeAppliedOnThisOperation(fee);
        return transaction;
    }
}

