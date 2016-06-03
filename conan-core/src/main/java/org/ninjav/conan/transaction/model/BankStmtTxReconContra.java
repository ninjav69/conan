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
@Table(name = "BankStmtTxReconContra")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BankStmtTxReconContra.findAll", query = "SELECT b FROM BankStmtTxReconContra b"),
    @NamedQuery(name = "BankStmtTxReconContra.findByBankStmtTxReconContrapk", query = "SELECT b FROM BankStmtTxReconContra b WHERE b.bankStmtTxReconContrapk = :bankStmtTxReconContrapk"),
    @NamedQuery(name = "BankStmtTxReconContra.findByAuditDate", query = "SELECT b FROM BankStmtTxReconContra b WHERE b.auditDate = :auditDate"),
    @NamedQuery(name = "BankStmtTxReconContra.findByUserId", query = "SELECT b FROM BankStmtTxReconContra b WHERE b.userId = :userId")})
public class BankStmtTxReconContra implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "BankStmtTxReconContra_pk")
    private Integer bankStmtTxReconContrapk;
    @Basic(optional = false)
    @NotNull
    @Column(name = "AuditDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date auditDate;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "UserId")
    private String userId;
    @JoinColumn(name = "BankStmtTxRecon_fk", referencedColumnName = "BankStmtTxRecon_pk")
    @ManyToOne(optional = false)
    private BankStmtTxRecon bankStmtTxReconfk;
    @JoinColumn(name = "OrigBankStmtTxReconToContra_fk", referencedColumnName = "BankStmtTxRecon_pk")
    @ManyToOne(optional = false)
    private BankStmtTxRecon origBankStmtTxReconToContrafk;

    public BankStmtTxReconContra() {
    }

    public BankStmtTxReconContra(Integer bankStmtTxReconContrapk) {
        this.bankStmtTxReconContrapk = bankStmtTxReconContrapk;
    }

    public BankStmtTxReconContra(Integer bankStmtTxReconContrapk, Date auditDate, String userId) {
        this.bankStmtTxReconContrapk = bankStmtTxReconContrapk;
        this.auditDate = auditDate;
        this.userId = userId;
    }

    public Integer getBankStmtTxReconContrapk() {
        return bankStmtTxReconContrapk;
    }

    public void setBankStmtTxReconContrapk(Integer bankStmtTxReconContrapk) {
        this.bankStmtTxReconContrapk = bankStmtTxReconContrapk;
    }

    public Date getAuditDate() {
        return auditDate;
    }

    public void setAuditDate(Date auditDate) {
        this.auditDate = auditDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public BankStmtTxRecon getBankStmtTxReconfk() {
        return bankStmtTxReconfk;
    }

    public void setBankStmtTxReconfk(BankStmtTxRecon bankStmtTxReconfk) {
        this.bankStmtTxReconfk = bankStmtTxReconfk;
    }

    public BankStmtTxRecon getOrigBankStmtTxReconToContrafk() {
        return origBankStmtTxReconToContrafk;
    }

    public void setOrigBankStmtTxReconToContrafk(BankStmtTxRecon origBankStmtTxReconToContrafk) {
        this.origBankStmtTxReconToContrafk = origBankStmtTxReconToContrafk;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (bankStmtTxReconContrapk != null ? bankStmtTxReconContrapk.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BankStmtTxReconContra)) {
            return false;
        }
        BankStmtTxReconContra other = (BankStmtTxReconContra) object;
        if ((this.bankStmtTxReconContrapk == null && other.bankStmtTxReconContrapk != null) || (this.bankStmtTxReconContrapk != null && !this.bankStmtTxReconContrapk.equals(other.bankStmtTxReconContrapk))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "coza.npda.dcm.smart.surrogate.model.BankStmtTxReconContra[ bankStmtTxReconContrapk=" + bankStmtTxReconContrapk + " ]";
    }
    
}
