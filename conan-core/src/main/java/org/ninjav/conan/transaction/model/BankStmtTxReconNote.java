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
@Table(name = "BankStmtTxReconNote")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BankStmtTxReconNote.findAll", query = "SELECT b FROM BankStmtTxReconNote b"),
    @NamedQuery(name = "BankStmtTxReconNote.findByBankStmtTxReconNotepk", query = "SELECT b FROM BankStmtTxReconNote b WHERE b.bankStmtTxReconNotepk = :bankStmtTxReconNotepk"),
    @NamedQuery(name = "BankStmtTxReconNote.findByNote", query = "SELECT b FROM BankStmtTxReconNote b WHERE b.note = :note"),
    @NamedQuery(name = "BankStmtTxReconNote.findByAuditDate", query = "SELECT b FROM BankStmtTxReconNote b WHERE b.auditDate = :auditDate"),
    @NamedQuery(name = "BankStmtTxReconNote.findByUserId", query = "SELECT b FROM BankStmtTxReconNote b WHERE b.userId = :userId")})
public class BankStmtTxReconNote implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "BankStmtTxReconNote_pk")
    private Integer bankStmtTxReconNotepk;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "Note")
    private String note;
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

    public BankStmtTxReconNote() {
    }

    public BankStmtTxReconNote(Integer bankStmtTxReconNotepk) {
        this.bankStmtTxReconNotepk = bankStmtTxReconNotepk;
    }

    public BankStmtTxReconNote(Integer bankStmtTxReconNotepk, String note, Date auditDate, String userId) {
        this.bankStmtTxReconNotepk = bankStmtTxReconNotepk;
        this.note = note;
        this.auditDate = auditDate;
        this.userId = userId;
    }

    public Integer getBankStmtTxReconNotepk() {
        return bankStmtTxReconNotepk;
    }

    public void setBankStmtTxReconNotepk(Integer bankStmtTxReconNotepk) {
        this.bankStmtTxReconNotepk = bankStmtTxReconNotepk;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (bankStmtTxReconNotepk != null ? bankStmtTxReconNotepk.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BankStmtTxReconNote)) {
            return false;
        }
        BankStmtTxReconNote other = (BankStmtTxReconNote) object;
        if ((this.bankStmtTxReconNotepk == null && other.bankStmtTxReconNotepk != null) || (this.bankStmtTxReconNotepk != null && !this.bankStmtTxReconNotepk.equals(other.bankStmtTxReconNotepk))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "coza.npda.dcm.smart.surrogate.model.BankStmtTxReconNote[ bankStmtTxReconNotepk=" + bankStmtTxReconNotepk + " ]";
    }
    
}
