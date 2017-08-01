/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.ke.sart.site.form;

import co.ke.sart.site.entity.ListOfValue;
import co.ke.sart.site.entity.PatientEntity;
import co.ke.sart.site.entity.PaymentMode;
import co.ke.sart.site.model.Patient;
import co.ke.sart.site.utils.AttendanceStatus;
import co.ke.sart.site.utils.FormAction;
import java.util.List;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 *
 * @author CMaundu
 */
public class AttendanceForm {

    private int rowID;
    private int patientID;
    private int attDoc;
    private int paymentTypeID;
    private int attStatus;
    private int attType;
    private String insuranceNumber;
    private Patient patient;
    
    private List<PaymentMode> paymentModes;
    private List<ListOfValue> attendanceTypes;

    private FormAction formAction;

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

    public int getAttStatus() {
        return attStatus;
    }

    public void setAttStatus(int attStatus) {
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

    public FormAction getFormAction() {
        return formAction;
    }

    public void setFormAction(FormAction formAction) {
        this.formAction = formAction;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public List<PaymentMode> getPaymentModes() {
        return paymentModes;
    }

    public void setPaymentModes(List<PaymentMode> paymentModes) {
        this.paymentModes = paymentModes;
    }

    public List<ListOfValue> getAttendanceTypes() {
        return attendanceTypes;
    }

    public void setAttendanceTypes(List<ListOfValue> attendanceTypes) {
        this.attendanceTypes = attendanceTypes;
    }

}
