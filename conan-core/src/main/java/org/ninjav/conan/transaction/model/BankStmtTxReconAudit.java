/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ninjav.conan.transaction.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Alan.Pickard
 */
@Entity
@Table(name = "BankStmtTxReconAudit")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BankStmtTxReconAudit.findAll", query = "SELECT b FROM BankStmtTxReconAudit b"),
    @NamedQuery(name = "BankStmtTxReconAudit.findByBankStmtTxReconAuditpk", query = "SELECT b FROM BankStmtTxReconAudit b WHERE b.bankStmtTxReconAuditpk = :bankStmtTxReconAuditpk"),
    @NamedQuery(name = "BankStmtTxReconAudit.findByAmount", query = "SELECT b FROM BankStmtTxReconAudit b WHERE b.amount = :amount"),
    @NamedQuery(name = "BankStmtTxReconAudit.findByUserId", query = "SELECT b FROM BankStmtTxReconAudit b WHERE b.userId = :userId"),
    @NamedQuery(name = "BankStmtTxReconAudit.findByChangeDescription", query = "SELECT b FROM BankStmtTxReconAudit b WHERE b.changeDescription = :changeDescription"),
    @NamedQuery(name = "BankStmtTxReconAudit.findByAuditDate", query = "SELECT b FROM BankStmtTxReconAudit b WHERE b.auditDate = :auditDate")})
public class BankStmtTxReconAudit implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "BankStmtTxReconAudit_pk")
    private Integer bankStmtTxReconAuditpk;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Amount")
    private int amount;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "UserId")
    private String userId;
    @Basic(optional = false)
    @NotNull
    @Size(max = 255)
    @Column(name = "ChangeDescription")
    private String changeDescription;
    @Basic(optional = false)
    @NotNull
    @Column(name = "AuditDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date auditDate;
    @JoinColumn(name = "BankStmtTx_fk", referencedColumnName = "BankStmtTx_pk")
    @ManyToOne(optional = false)
    private BankStmtTx bankStmtTxfk;
    @JoinColumn(name = "BankStmtTxRecon_fk", referencedColumnName = "BankStmtTxRecon_pk")
    @ManyToOne(optional = false)
    private BankStmtTxRecon bankStmtTxReconfk;
    @JoinColumn(name = "BankStmtTxReconAuditStatus_fk", referencedColumnName = "BankStmtTxReconAuditStatus_pk")
    @ManyToOne(optional = false)
    private BankStmtTxReconAuditStatus bankStmtTxReconAuditStatusfk;

    public BankStmtTxReconAudit() {
    }

    public BankStmtTxReconAudit(Integer bankStmtTxReconAuditpk) {
        this.bankStmtTxReconAuditpk = bankStmtTxReconAuditpk;
    }

    public BankStmtTxReconAudit(Integer bankStmtTxReconAuditpk, int amount, String userId, String changeDescription, Date auditDate) {
        this.bankStmtTxReconAuditpk = bankStmtTxReconAuditpk;
        this.amount = amount;
        this.userId = userId;
        this.changeDescription = changeDescription;
        this.auditDate = auditDate;
    }

    public Integer getBankStmtTxReconAuditpk() {
        return bankStmtTxReconAuditpk;
    }

    public void setBankStmtTxReconAuditpk(Integer bankStmtTxReconAuditpk) {
        this.bankStmtTxReconAuditpk = bankStmtTxReconAuditpk;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getChangeDescription() {
        return changeDescription;
    }

    public void setChangeDescription(String changeDescription) {
        this.changeDescription = changeDescription;
    }

    public Date getAuditDate() {
        return auditDate;
    }

    public void setAuditDate(Date auditDate) {
        this.auditDate = auditDate;
    }

    public BankStmtTx getBankStmtTxfk() {
        return bankStmtTxfk;
    }

    public void setBankStmtTxfk(BankStmtTx bankStmtTxfk) {
        this.bankStmtTxfk = bankStmtTxfk;
    }

    public BankStmtTxRecon getBankStmtTxReconfk() {
        return bankStmtTxReconfk;
    }

    public void setBankStmtTxReconfk(BankStmtTxRecon bankStmtTxReconfk) {
        this.bankStmtTxReconfk = bankStmtTxReconfk;
    }

    public BankStmtTxReconAuditStatus getBankStmtTxReconAuditStatusfk() {
        return bankStmtTxReconAuditStatusfk;
    }

    public void setBankStmtTxReconAuditStatusfk(BankStmtTxReconAuditStatus bankStmtTxReconAuditStatusfk) {
        this.bankStmtTxReconAuditStatusfk = bankStmtTxReconAuditStatusfk;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (bankStmtTxReconAuditpk != null ? bankStmtTxReconAuditpk.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BankStmtTxReconAudit)) {
            return false;
        }
        BankStmtTxReconAudit other = (BankStmtTxReconAudit) object;
        if ((this.bankStmtTxReconAuditpk == null && other.bankStmtTxReconAuditpk != null) || (this.bankStmtTxReconAuditpk != null && !this.bankStmtTxReconAuditpk.equals(other.bankStmtTxReconAuditpk))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "coza.npda.dcm.smart.surrogate.model.BankStmtTxReconAudit[ bankStmtTxReconAuditpk=" + bankStmtTxReconAuditpk + " ]";
    }

}
