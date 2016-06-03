/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ninjav.conan.transaction.model;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Alan.Pickard
 */
@Entity
@Table(name = "BankStmtTxRecon")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BankStmtTxRecon.findAll", query = "SELECT b FROM BankStmtTxRecon b"),
    @NamedQuery(name = "BankStmtTxRecon.findByBankStmtTxReconpk", query = "SELECT b FROM BankStmtTxRecon b WHERE b.bankStmtTxReconpk = :bankStmtTxReconpk"),
    @NamedQuery(name = "BankStmtTxRecon.findByAmount", query = "SELECT b FROM BankStmtTxRecon b WHERE b.amount = :amount")})
public class BankStmtTxRecon implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "BankStmtTxRecon_pk")
    private Integer bankStmtTxReconpk;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Amount")
    private int amount;
    @JoinColumn(name = "BankStmtTx_fk", referencedColumnName = "BankStmtTx_pk")
    @ManyToOne(optional = false)
    private BankStmtTx bankStmtTxfk;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bankStmtTxReconfk")
    private Set<BankStmtTxReconNote> bankStmtTxReconNoteSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bankStmtTxReconfk")
    private Set<BankStmtTxReconContra> bankStmtTxReconContraSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bankStmtTxReconfk")
    private Set<BankStmtTxReconAudit> bankStmtTxReconAuditSet;

    public BankStmtTxRecon() {
    }

    public BankStmtTxRecon(Integer bankStmtTxReconpk) {
        this.bankStmtTxReconpk = bankStmtTxReconpk;
    }

    public BankStmtTxRecon(Integer bankStmtTxReconpk, int amount) {
        this.bankStmtTxReconpk = bankStmtTxReconpk;
        this.amount = amount;
    }

    public Integer getBankStmtTxReconpk() {
        return bankStmtTxReconpk;
    }

    public void setBankStmtTxReconpk(Integer bankStmtTxReconpk) {
        this.bankStmtTxReconpk = bankStmtTxReconpk;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public BankStmtTx getBankStmtTxfk() {
        return bankStmtTxfk;
    }

    public void setBankStmtTxfk(BankStmtTx bankStmtTxfk) {
        this.bankStmtTxfk = bankStmtTxfk;
    }

    @XmlTransient
    public Set<BankStmtTxReconNote> getBankStmtTxReconNoteSet() {
        return bankStmtTxReconNoteSet;
    }

    public void setBankStmtTxReconNoteSet(Set<BankStmtTxReconNote> bankStmtTxReconNoteSet) {
        this.bankStmtTxReconNoteSet = bankStmtTxReconNoteSet;
    }

    @XmlTransient
    public Set<BankStmtTxReconContra> getBankStmtTxReconContraSet() {
        return bankStmtTxReconContraSet;
    }

    public void setBankStmtTxReconContraSet(Set<BankStmtTxReconContra> bankStmtTxReconContraSet) {
        this.bankStmtTxReconContraSet = bankStmtTxReconContraSet;
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
        hash += (bankStmtTxReconpk != null ? bankStmtTxReconpk.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BankStmtTxRecon)) {
            return false;
        }
        BankStmtTxRecon other = (BankStmtTxRecon) object;
        if ((this.bankStmtTxReconpk == null && other.bankStmtTxReconpk != null) || (this.bankStmtTxReconpk != null && !this.bankStmtTxReconpk.equals(other.bankStmtTxReconpk))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "coza.npda.dcm.smart.surrogate.model.BankStmtTxRecon[ bankStmtTxReconpk=" + bankStmtTxReconpk + " ]";
    }

}
