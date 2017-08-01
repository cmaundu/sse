/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.ke.sart.site.entity;

import java.io.Serializable;
import java.sql.Date;
import javax.persistence.Entity;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "sart_payment")
public class Payment extends RecordEntity implements Serializable {

    private int requestID;
    private int attendanceID;
    private String note;
    private double paidAmount;
    private int paymentLovTypeID;
    private String receiptNumber;
    @DateTimeFormat(pattern = "yyy/MM/dd")
    private Date creditDueDate;

    @Transient
    private String paymentLovTypeVal;

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

    public double getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(double paidAmount) {
        this.paidAmount = paidAmount;
    }

    public int getPaymentLovTypeID() {
        return paymentLovTypeID;
    }

    public void setPaymentLovTypeID(int paymentLovTypeID) {
        this.paymentLovTypeID = paymentLovTypeID;
    }

    public String getReceiptNumber() {
        return receiptNumber;
    }

    public void setReceiptNumber(String receiptNumber) {
        this.receiptNumber = receiptNumber;
    }

    public Date getCreditDueDate() {
        return creditDueDate;
    }

    public void setCreditDueDate(Date creditDueDate) {
        this.creditDueDate = creditDueDate;
    }

    public String getPaymentLovTypeVal() {
        return paymentLovTypeVal;
    }

    public void setPaymentLovTypeVal(String paymentLovTypeVal) {
        this.paymentLovTypeVal = paymentLovTypeVal;
    }

}
