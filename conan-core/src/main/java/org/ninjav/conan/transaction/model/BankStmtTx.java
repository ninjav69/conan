/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ninjav.conan.transaction.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Alan.Pickard
 */
@Entity
@Table(name = "BankStmtTx")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BankStmtTx.findAll", query = "SELECT b FROM BankStmtTx b"),
    @NamedQuery(name = "BankStmtTx.findByBankStmtTxpk", query = "SELECT b FROM BankStmtTx b WHERE b.bankStmtTxpk = :bankStmtTxpk"),
    @NamedQuery(name = "BankStmtTx.findByTransactionAmount", query = "SELECT b FROM BankStmtTx b WHERE b.transactionAmount = :transactionAmount"),
    @NamedQuery(name = "BankStmtTx.findByTransactionDate", query = "SELECT b FROM BankStmtTx b WHERE b.transactionDate = :transactionDate"),
    @NamedQuery(name = "BankStmtTx.findByTransactionReference", query = "SELECT b FROM BankStmtTx b WHERE b.transactionReference = :transactionReference")})
public class BankStmtTx implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "BankStmtTx_pk")
    private Integer bankStmtTxpk;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TransactionAmount")
    private int transactionAmount;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TransactionDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date transactionDate;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "TransactionReference")
    private String transactionReference;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bankStmtTxfk")
    private Set<BankStmtTxRecon> bankStmtTxReconSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bankStmtTxfk")
    private Set<BankStmtTxReconAudit> bankStmtTxReconAuditSet;

    public BankStmtTx() {
    }

    public BankStmtTx(Integer bankStmtTxpk) {
        this.bankStmtTxpk = bankStmtTxpk;
    }

    public BankStmtTx(Integer bankStmtTxpk, int transactionAmount, Date transactionDate, String transactionReferencee) {
        this.bankStmtTxpk = bankStmtTxpk;
        this.transactionAmount = transactionAmount;
        this.transactionDate = transactionDate;
        this.transactionReference = transactionReference;
    }

    public Integer getBankStmtTxpk() {
        return bankStmtTxpk;
    }

    public void setBankStmtTxpk(Integer bankStmtTxpk) {
        this.bankStmtTxpk = bankStmtTxpk;
    }

    public int getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(int transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getTransactionReference() {
        return transactionReference;
    }

    public void setTransactionReference(String transactionReference) {
        this.transactionReference = transactionReference;
    }

    @XmlTransient
    public Set<BankStmtTxRecon> getBankStmtTxReconSet() {
        return bankStmtTxReconSet;
    }

    public void setBankStmtTxReconSet(Set<BankStmtTxRecon> bankStmtTxReconSet) {
        this.bankStmtTxReconSet = bankStmtTxReconSet;
    }

    @XmlTransient
    public Set<BankStmtTxReconAudit> getBankStmtTxReconAuditSet() {
        return bankStmtTxReconAuditSet;
    }

    public void setBankStmtTxReconAuditSet(Set<BankStmtTxReconAudit> bankStmtTxReconAuditSet) {
        this.bankStmtTxReconAuditSet = bankStmtTxReconAuditSet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (bankStmtTxpk != null ? bankStmtTxpk.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BankStmtTx)) {
            return false;
        }
        BankStmtTx other = (BankStmtTx) object;
        if ((this.bankStmtTxpk == null && other.bankStmtTxpk != null) || (this.bankStmtTxpk != null && !this.bankStmtTxpk.equals(other.bankStmtTxpk))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "coza.npda.dcm.smart.surrogate.model.BankStmtTx[ bankStmtTxpk=" + bankStmtTxpk + " ]";
    }
    
}
