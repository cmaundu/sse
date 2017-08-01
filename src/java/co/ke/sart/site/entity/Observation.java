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
@Table(name = "sart_observation")
public class Observation extends RecordEntity implements Serializable {

    private int attendanceID;
    private int requestID;
    private String complaint;
    private String provisionalDiagnosis;
    private String remark;

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

    public String getComplaint() {
        return complaint;
    }

    public void setComplaint(String complaint) {
        this.complaint = complaint;
    }

    public String getProvisionalDiagnosis() {
        return provisionalDiagnosis;
    }

    public void setProvisionalDiagnosis(String provisionalDiagnosis) {
        this.provisionalDiagnosis = provisionalDiagnosis;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String Remark) {
        this.remark = Remark;
    }

}
