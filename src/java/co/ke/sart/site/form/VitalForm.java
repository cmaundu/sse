/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.ke.sart.site.form;

import co.ke.sart.site.entity.Vital;

public class VitalForm {

    private Vital vital;
    private int attendanceID;
    private int requestID;

    public VitalForm() {
        vital = new Vital();
    }

    public VitalForm(Vital vital) {
        if (vital != null) {
            this.vital = vital;
        } else {
            this.vital = new Vital();
        }
    }

    public Vital getVital() {
        return vital;
    }

    public void setVital(Vital vital) {
        this.vital = vital;
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

}
