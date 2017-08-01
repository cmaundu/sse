/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.ke.sart.site.entity;

import co.ke.sart.site.interfaces.ICharges;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "sart_radiology")
public class Radiology extends RecordEntity implements Serializable, ICharges {

    private int attendanceID;
    private int requestID;
    private String note;
    private String site;
    private int lovTypeLovID;
    private double fulfilled;
    private int fulfilmentID;
    private double chargeAmount;
    
    @Transient
    private String lovTypeVal;
    
    private String LMP;
    private String history;
    
    @Column(name="result")
    private String resultText;
    private String resultNote;    

    
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public int getLovTypeLovID() {
        return lovTypeLovID;
    }

    public void setLovTypeLovID(int lovTypeLovID) {
        this.lovTypeLovID = lovTypeLovID;
    }

    public double getFulfilled() {
        return fulfilled;
    }

    public void setFulfilled(double fulfilled) {
        this.fulfilled = fulfilled;
    }

    public int getFulfilmentID() {
        return fulfilmentID;
    }

    public void setFulfilmentID(int fulfilmentID) {
        this.fulfilmentID = fulfilmentID;
    }

    public double getChargeAmount() {
        return chargeAmount;
    }

    public void setChargeAmount(double chargeAmount) {
        this.chargeAmount = chargeAmount;
    }

    public String getLMP() {
        return LMP;
    }

    public void setLMP(String LMP) {
        this.LMP = LMP;
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public String getLovTypeVal() {
        return lovTypeVal;
    }

    public void setLovTypeVal(String lovTypeVal) {
        this.lovTypeVal = lovTypeVal;
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



}
