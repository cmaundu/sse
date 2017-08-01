package co.ke.sart.site.entity;

import co.ke.sart.site.interfaces.ICharges;
import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "sart_prescription")
public class Prescription extends RecordEntity implements Serializable, ICharges {

    private int attendanceID;
    private int requestID;
    //private int drugID;
    private String note;
    private String dose;
    private int quantity;
    private double chargeAmount;
    private boolean dispensed;
    private int quantitydispensed;
    private int dispensedby;
    private Timestamp dispenseddate;
    private String dispenseNote;
    
    @Transient
    private String drugName;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "drugID")
    private DrugDef drugDef;

    @Override
    @Transient
    public int getLovTypeLovID() {
        return getDrugDef().getRowID();
    }


    public int getAttendanceID() {
        return attendanceID;
    }

    public void setAttendanceID(int attendanceID) {
        this.attendanceID = attendanceID;
    }

    public int getRequestID() {
        return requestID;
    }

    public void setRequestID(int requestID) {
        this.requestID = requestID;
    }

    //public int getDrugID() {
    //    return drugID;
    //}
    //public void setDrugID(int drugID) {
    //    this.drugID = drugID;
    //}
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDose() {
        return dose;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getChargeAmount() {
        return chargeAmount;
    }

    @Override
    public void setChargeAmount(double chargeAmount) {
        this.chargeAmount = chargeAmount;
    }

    public boolean isDispensed() {
        return dispensed;
    }

    public void setDispensed(boolean dispensed) {
        this.dispensed = dispensed;
    }

    public int getQuantitydispensed() {
        return quantitydispensed;
    }

    public void setQuantitydispensed(int quantitydispensed) {
        this.quantitydispensed = quantitydispensed;
    }

    public int getDispensedby() {
        return dispensedby;
    }

    public void setDispensedby(int dispensedby) {
        this.dispensedby = dispensedby;
    }

    public Timestamp getDispenseddate() {
        return dispenseddate;
    }

    public void setDispenseddate(Timestamp dispenseddate) {
        this.dispenseddate = dispenseddate;
    }

    public String getDispenseNote() {
        return dispenseNote;
    }

    public void setDispenseNote(String dispenseNote) {
        this.dispenseNote = dispenseNote;
    }


    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }


    public DrugDef getDrugDef() {
        return drugDef;
    }

    public void setDrugDef(DrugDef drugDef) {
        this.drugDef = drugDef;
    }

}
