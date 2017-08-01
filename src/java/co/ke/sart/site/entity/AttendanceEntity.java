/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.ke.sart.site.entity;

import co.ke.sart.site.utils.AttendanceStatus;
import co.ke.sart.site.utils.AttendanceType;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name = "sart_attendance")
public class AttendanceEntity extends RecordEntity implements Serializable {

    private Timestamp closeDate;
    private int patientID;
    private String attNumber;
    private int attDoc;
    private int paymentTypeID;
    private int closedBy;
    @Enumerated(EnumType.STRING)
    private AttendanceStatus attStatus;
    private int attType;
    private String insuranceNumber;

    public Timestamp getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(Timestamp closeDate) {
        this.closeDate = closeDate;
    }

    public void setCloseDate(LocalDateTime closeDate) {
        this.setCloseDate((this.getCloseDate() == null) ? null : Timestamp.valueOf(closeDate));
    }

    public int getPatientID() {
        return patientID;
    }

    public void setPatientID(int patientID) {
        this.patientID = patientID;
    }

    public int getAttDoc() {
        return attDoc;
    }

    public void setAttDoc(int attDoc) {
        this.attDoc = attDoc;
    }

    public int getPaymentTypeID() {
        return paymentTypeID;
    }

    public void setPaymentTypeID(int paymentTypeID) {
        this.paymentTypeID = paymentTypeID;
    }

    public int getClosedBy() {
        return closedBy;
    }

    public void setClosedBy(int closedBy) {
        this.closedBy = closedBy;
    }

    public AttendanceStatus getAttStatus() {
        return attStatus;
    }

    public void setAttStatus(AttendanceStatus attStatus) {
        this.attStatus = attStatus;
    }

    public int getAttType() {
        return attType;
    }

    public void setAttType(int attType) {
        this.attType = attType;
    }

    public String getInsuranceNumber() {
        return insuranceNumber;
    }

    public void setInsuranceNumber(String insuranceNumber) {
        this.insuranceNumber = insuranceNumber;
    }

    public String getAttNumber() {
        return attNumber;
    }

    public void setAttNumber(String attNumber) {
        this.attNumber = attNumber;
    }

}
