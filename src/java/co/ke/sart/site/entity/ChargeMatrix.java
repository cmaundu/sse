/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.ke.sart.site.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "sart_charge_matrix")
public class ChargeMatrix  extends RecordEntity implements Serializable{

    private String lovType;
    private int lovTypeID;
    private int paymentMode;
    private double amount;

    public String getLovType() {
        return lovType;
    }

    public void setLovType(String lovType) {
        this.lovType = lovType;
    }

    public int getLovTypeID() {
        return lovTypeID;
    }

    public void setLovTypeID(int lovTypeID) {
        this.lovTypeID = lovTypeID;
    }

    public int getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(int paymentMode) {
        this.paymentMode = paymentMode;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

}
