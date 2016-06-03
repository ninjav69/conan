package org.ninjav.conan.debitorder.model;

import org.ninjav.conan.account.model.Account;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by ninjav on 6/3/16.
 */
@Entity
public class DebitOrder {
    public static final int PAID = 0;
    public static final int INSUFFICIENT_FUNDS = 2;
    public static final int NOT_A_DEBIT_ACCOUNT = 3;
    public static final int PAYMENT_STOPPED = 4;
    public static final int ACCOUNT_FROZEN = 6;
    public static final int ACCOUNT_IN_SEQUISTRATION = 8;

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    private Long transactionId;

    @Basic(optional = false)
    @NotNull
    private Double amount;

    @Basic(optional = false)
    @NotNull
    private Integer resultCode;

    @Basic(optional = false)
    @NotNull
    @Temporal(TemporalType.DATE)
    private Date date;

    @ManyToOne(optional = false)
    @JoinColumn(name = "account", referencedColumnName = "reference")
    private Account account;

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getResultCode() {
        return resultCode;
    }

    public void setResultCode(Integer resultCode) {
        this.resultCode = resultCode;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
