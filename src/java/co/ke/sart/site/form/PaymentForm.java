
package co.ke.sart.site.form;

import co.ke.sart.site.entity.ListOfValue;
import co.ke.sart.site.entity.Payment;
import java.util.List;


public class PaymentForm {
    private int attendanceID;
    private int requestID;
    private Payment payment;
    private double totalCharges;
    
    private String paymentType;
    
    private List<ListOfValue> paymentLovTypeLOVs;

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

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public double getTotalCharges() {
        return totalCharges;
    }

    public void setTotalCharges(double totalCharges) {
        this.totalCharges = totalCharges;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public List<ListOfValue> getPaymentLovTypeLOVs() {
        return paymentLovTypeLOVs;
    }

    public void setPaymentLovTypeLOVs(List<ListOfValue> paymentLovTypeLOVs) {
        this.paymentLovTypeLOVs = paymentLovTypeLOVs;
    }
    
    
}
