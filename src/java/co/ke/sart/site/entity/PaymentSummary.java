package co.ke.sart.site.entity;

import java.io.Serializable;
import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;

@Entity
@NamedStoredProcedureQueries({@NamedStoredProcedureQuery(name = "PaymentSummary.dailySummary", procedureName = "sart_proc_daily_summaries",
         resultClasses = PaymentSummary.class
)})
public class PaymentSummary implements Serializable {

    @Id
    private int rowID;
    private Date paydate;
    @Column(name = "pt_name")
    private String name;
    @Column(name = "totalpay")
    private double pay;

    public Date getPaydate() {
        return paydate;
    }

    public void setPaydate(Date paydate) {
        this.paydate = paydate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPay() {
        return pay;
    }

    public void setPay(double pay) {
        this.pay = pay;
    }

    public int getRowID() {
        return rowID;
    }

    public void setRowID(int rowID) {
        this.rowID = rowID;
    }

}
