package co.ke.sart.site.entity;

import co.ke.sart.site.interfaces.ICharges;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "sart_procedure")
public class Procedure extends RecordEntity implements Serializable, ICharges {

    private int requestID;
    private int attendanceID;
    private String note;
    private int lovTypeLovID;
    @Column(name = "result")
    private String resultText;
    private String resultNote;
    private double chargeAmount;
    
    @Transient
    private String lovTypeVal;    

    public int getRequestID() {
        return requestID;
    }

    public void setRequestID(int requestID) {
        this.requestID = requestID;
    }

    public int getAttendanceID() {
        return attendanceID;
    }

    public void setAttendanceID(int attendanceID) {
        this.attendanceID = attendanceID;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getLovTypeLovID() {
        return lovTypeLovID;
    }

    public void setLovTypeLovID(int lovTypeLovID) {
        this.lovTypeLovID = lovTypeLovID;
    }

    public String getResultText() {
        return resultText;
    }

    public void setResultText(String resultText) {
        this.resultText = resultText;
    }

    public String getResultNote() {
        return resultNote;
    }

    public void setResultNote(String resultNote) {
        this.resultNote = resultNote;
    }

    public double getChargeAmount() {
        return chargeAmount;
    }

    public void setChargeAmount(double chargeAmount) {
        this.chargeAmount = chargeAmount;
    }

    public String getLovTypeVal() {
        return lovTypeVal;
    }

    public void setLovTypeVal(String lovTypeVal) {
        this.lovTypeVal = lovTypeVal;
    }

}
