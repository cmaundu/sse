/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.ke.sart.site.model;

import co.ke.sart.site.entity.PaymentSummary;
import java.sql.Date;
import java.util.List;

/**
 *
 * @author CMaundu
 */
public class PaymentSummaryModel {
    private List<PaymentSummary> pays;
    private List<Date> dates;
    private List<String> payTypes;
    
    public double getPay(String payName, Date dateOfPay){
        return pays.stream().filter(p -> p.getName().equals(payName) && p.getPaydate().compareTo(dateOfPay) == 0)
                .mapToDouble(p -> p.getPay()).sum();
    }
    
    public double getPay(Date dateOfPay){
        return pays.stream().filter(p -> p.getPaydate().compareTo(dateOfPay) == 0)
                .mapToDouble(p -> p.getPay()).sum();
    }    
    
    
    
    

    public List<PaymentSummary> getPays() {
        return pays;
    }

    public void setPays(List<PaymentSummary> pays) {
        this.pays = pays;
    }

    public List<Date> getDates() {
        return dates;
    }

    public void setDates(List<Date> dates) {
        this.dates = dates;
    }

    public List<String> getPayTypes() {
        return payTypes;
    }

    public void setPayTypes(List<String> payTypes) {
        this.payTypes = payTypes;
    }
    
    
}
