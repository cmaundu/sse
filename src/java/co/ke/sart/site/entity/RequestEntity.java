/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.ke.sart.site.entity;

import co.ke.sart.site.utils.ApprovalStatus;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

/**
 *
 * @author CMaundu
 */
@Entity
@Table(name = "sart_request")
public class RequestEntity  extends RecordEntity implements Serializable{

    private boolean chargeable;
    private boolean paid;
    private boolean fulfilled;
    private double amountCharged;
    private int attendanceID;
    private int patientID;
    private int requestType;
    private int requestSubType;
    private int approvalID;
    private int fulfilmentID;
    private String note;
    private String description;
    @Enumerated(EnumType.STRING)
    private ApprovalStatus approvalStatus;

    
    public boolean isChargeable() {
        return chargeable;
    }

    public void setChargeable(boolean chargeable) {
        this.chargeable = chargeable;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public boolean isFulfilled() {
        return fulfilled;
    }

    public void setFulfilled(boolean fulfilled) {
        this.fulfilled = fulfilled;
    }

    public double getAmountCharged() {
        return amountCharged;
    }

    public void setAmountCharged(double amountCharged) {
        this.amountCharged = amountCharged;
    }

    public int getAttendanceID() {
        return attendanceID;
    }

    public void setAttendanceID(int attendanceID) {
        this.attendanceID = attendanceID;
    }

    public int getPatientID() {
        return patientID;
    }

    public void setPatientID(int patientID) {
        this.patientID = patientID;
    }

    public int getRequestType() {
        return requestType;
    }

    public void setRequestType(int requestType) {
        this.requestType = requestType;
    }

    public int getApprovalID() {
        return approvalID;
    }

    public void setApprovalID(int approvalID) {
        this.approvalID = approvalID;
    }

    public int getFulfilmentID() {
        return fulfilmentID;
    }

    public void setFulfilmentID(int fulfilmentID) {
        this.fulfilmentID = fulfilmentID;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ApprovalStatus getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(ApprovalStatus approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public int getRequestSubType() {
        return requestSubType;
    }

    public void setRequestSubType(int requestSubType) {
        this.requestSubType = requestSubType;
    }
    
    
    
}
