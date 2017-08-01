/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.ke.sart.site.form;

import co.ke.sart.site.entity.Observation;

/**
 *
 * @author CMaundu
 */
public class ObservationForm {

    private Observation observation;
    private int attendanceID;
    private int requestID;
    
    public ObservationForm(){
        observation = new Observation();
    }
    
    public ObservationForm(Observation observation){
        this.observation = observation;
    }

    public Observation getObservation() {
        return observation;
    }

    public void setObservation(Observation observation) {
        this.observation = observation;
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
