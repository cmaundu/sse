/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.ke.sart.site.form;

import co.ke.sart.site.entity.ListOfValue;
import co.ke.sart.site.entity.Procedure;
import co.ke.sart.site.entity.Radiology;
import java.util.List;

public class RadiologyForm {

    private List<ListOfValue> radiologyDefLOVs;
    private List<Radiology> radiologyList;
    private Radiology radiology;
    private int attendanceID;
    private int requestID;
    private String lmp;
    private String history;
    

    public List<ListOfValue> getRadiologyDefLOVs() {
        return radiologyDefLOVs;
    }

    public void setRadiologyDefLOVs(List<ListOfValue> radiologyDefLOVs) {
        this.radiologyDefLOVs = radiologyDefLOVs;
    }

    public List<Radiology> getRadiologyList() {
        return radiologyList;
    }

    public void setRadiologyList(List<Radiology> radiologyList) {
        this.radiologyList = radiologyList;
    }

    public Radiology getRadiology() {
        return radiology;
    }

    public void setRadiology(Radiology radiology) {
        this.radiology = radiology;
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

    public String getLmp() {
        return lmp;
    }

    public void setLmp(String lmp) {
        this.lmp = lmp;
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }
    
    
}
