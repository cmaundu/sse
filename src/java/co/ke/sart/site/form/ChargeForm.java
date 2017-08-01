/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.ke.sart.site.form;

import co.ke.sart.site.entity.ListOfValue;
import java.util.List;

public class ChargeForm {

    private int rowID;
    private double amountCharged;
    private int attendanceID;
    private int requestID;
    private int requestType;
    private int requestSubType;
    private String note;
    private String description;
    private double discount;
    private List<ListOfValue> requestSubTypeLOVs;

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

    public int getRequestID() {
        return requestID;
    }

    public void setRequestID(int requestID) {
        this.requestID = requestID;
    }

    public int getRequestType() {
        return requestType;
    }

    public void setRequestType(int requestType) {
        this.requestType = requestType;
    }

    public int getRequestSubType() {
        return requestSubType;
    }

    public void setRequestSubType(int requestSubType) {
        this.requestSubType = requestSubType;
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

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public int getRowID() {
        return rowID;
    }

    public void setRowID(int rowID) {
        this.rowID = rowID;
    }

    public List<ListOfValue> getRequestSubTypeLOVs() {
        return requestSubTypeLOVs;
    }

    public void setRequestSubTypeLOVs(List<ListOfValue> requestSubTypeLOVs) {
        this.requestSubTypeLOVs = requestSubTypeLOVs;
    }

}
