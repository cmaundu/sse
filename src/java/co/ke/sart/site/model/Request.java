/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.ke.sart.site.model;

import co.ke.sart.site.entity.Charge;
import co.ke.sart.site.entity.ListOfValue;
import co.ke.sart.site.utils.ApprovalStatus;
import java.util.List;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import org.springframework.stereotype.Service;

@Service
public class Request extends Record {

    private boolean chargeable;
    private boolean paid;
    private boolean fulfilled;
    private double amountCharged;
    private int attendanceID;
    private int patientID;
    private int requestType;
    private int approvalID;
    private int fulfilmentID;
    private String note;
    private String description;
    
    private String title;
    
    @Enumerated(EnumType.STRING)
    private ApprovalStatus approvalStatus;

    private List<Charge> charges;
    
    private ListOfValue requestLOVDetail;
    
    private int requestSubType;
    
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

    public List<Charge> getCharges() {
        return charges;
    }

    public void setCharges(List<Charge> charges) {
        this.charges = charges;
    }

    public ListOfValue getRequestLOVDetail() {
        return requestLOVDetail;
    }

    public void setRequestLOVDetail(ListOfValue requestLOVDetail) {
        this.requestLOVDetail = requestLOVDetail;
    }

    public int getRequestSubType() {
        return requestSubType;
    }

    public void setRequestSubType(int requestSubType) {
        this.requestSubType = requestSubType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
