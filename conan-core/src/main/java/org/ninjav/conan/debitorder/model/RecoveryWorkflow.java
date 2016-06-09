package org.ninjav.conan.debitorder.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by ninjav on 6/9/16.
 */
@Entity
public class RecoveryWorkflow implements Serializable {

    @Id
    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @MapsId("debitOrderTransactionId")
    protected DebitOrder debitOrder;

    @Basic(optional = false)
    private Status status;

    public DebitOrder getDebitOrder() {
        return debitOrder;
    }

    public void setDebitOrder(DebitOrder debitOrder) {
        this.debitOrder = debitOrder;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RecoveryWorkflow that = (RecoveryWorkflow) o;

        return !(debitOrder != null ? !debitOrder.equals(that.debitOrder) : that.debitOrder != null);
    }

    @Override
    public int hashCode() {
        return debitOrder != null ? debitOrder.hashCode() : 0;
    }

    public enum Status {
        NEW,
        HANDED_OVER,
        RECOVERED,
        WRITTEN_OFF
    }
}
