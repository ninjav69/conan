/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ninjav.conan.transaction.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Alan.Pickard
 */
@Entity
@Table(name = "BankStmtTxReconAuditStatus")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BankStmtTxReconAuditStatus.findAll", query = "SELECT b FROM BankStmtTxReconAuditStatus b"),
    @NamedQuery(name = "BankStmtTxReconAuditStatus.findByBankStmtTxReconAuditStatuspk", query = "SELECT b FROM BankStmtTxReconAuditStatus b WHERE b.bankStmtTxReconAuditStatuspk = :bankStmtTxReconAuditStatuspk"),
    @NamedQuery(name = "BankStmtTxReconAuditStatus.findByStatus", query = "SELECT b FROM BankStmtTxReconAuditStatus b WHERE b.status = :status")})
public class BankStmtTxReconAuditStatus implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "BankStmtTxReconAuditStatus_pk")
    private Integer bankStmtTxReconAuditStatuspk;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "Status")
    private String status;

    public BankStmtTxReconAuditStatus() {
    }

    public BankStmtTxReconAuditStatus(Integer bankStmtTxReconAuditStatuspk) {
        this.bankStmtTxReconAuditStatuspk = bankStmtTxReconAuditStatuspk;
    }

    public BankStmtTxReconAuditStatus(Integer bankStmtTxReconAuditStatuspk, String status) {
        this.bankStmtTxReconAuditStatuspk = bankStmtTxReconAuditStatuspk;
        this.status = status;
    }

    public Integer getBankStmtTxReconAuditStatuspk() {
        return bankStmtTxReconAuditStatuspk;
    }

    public void setBankStmtTxReconAuditStatuspk(Integer bankStmtTxReconAuditStatuspk) {
        this.bankStmtTxReconAuditStatuspk = bankStmtTxReconAuditStatuspk;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (bankStmtTxReconAuditStatuspk != null ? bankStmtTxReconAuditStatuspk.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BankStmtTxReconAuditStatus)) {
            return false;
        }
        BankStmtTxReconAuditStatus other = (BankStmtTxReconAuditStatus) object;
        if ((this.bankStmtTxReconAuditStatuspk == null && other.bankStmtTxReconAuditStatuspk != null) || (this.bankStmtTxReconAuditStatuspk != null && !this.bankStmtTxReconAuditStatuspk.equals(other.bankStmtTxReconAuditStatuspk))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "coza.npda.dcm.smart.surrogate.model.BankStmtTxReconAuditStatus[ bankStmtTxReconAuditStatuspk=" + bankStmtTxReconAuditStatuspk + " ]";
    }
    
}
