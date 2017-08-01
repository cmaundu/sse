/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.ke.sart.site.form;

import co.ke.sart.site.utils.AttendanceStatus;
import co.ke.sart.site.utils.AttendanceType;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;


public class AttendanceNewForm {
    private int rowID;
    private int patientID;
    private int attDoc;
    private int paymentTypeID;
    @Enumerated(EnumType.STRING)
    private AttendanceStatus attStatus;
    private int attType;
    private String insuranceNumber;

    public int getRowID() {
        return rowID;
    }

    public void setRowID(int rowID) {
        this.rowID = rowID;
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
    
    
}
