/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.ke.sart.site.entity;

import co.ke.sart.site.interfaces.ICharges;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "sart_lab_test")
public class LabTest extends RecordEntity implements Serializable, ICharges {

    private int requestID;
    private int attendanceID;
    private String note;
    private int labTypeLovID;
    @Column(name="result")
    private String resultText;
    private String resultNote;
    private double chargeAmount;
    
    @Transient
    private String labTypeLovVal;
    
    
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

    public int getLabTypeLovID() {
        return labTypeLovID;
    }

    public void setLabTypeLovID(int labTypeLovID) {
        this.labTypeLovID = labTypeLovID;
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

    public String getResultText() {
        return resultText;
    }

    public void setResultText(String resultText) {
        this.resultText = resultText;
    }

    @Override
    @Transient
    public int getLovTypeLovID() {
        return this.labTypeLovID;
    }

    public String getLabTypeLovVal() {
        return labTypeLovVal;
    }

    public void setLabTypeLovVal(String labTypeLovVal) {
        this.labTypeLovVal = labTypeLovVal;
    }

}
