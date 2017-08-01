/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.ke.sart.site.form;

import co.ke.sart.site.entity.ListOfValue;
import co.ke.sart.site.entity.Procedure;
import java.util.List;

public class ProcedureForm {

    private List<ListOfValue> procedureDefLOVs;
    private List<Procedure> procedureList;
    private int attendanceID;
    private int requestID;
    private String note;
    private List<Integer> selectedLovs;
    private List<String> procNotes;    

    public List<ListOfValue> getProcedureDefLOVs() {
        return procedureDefLOVs;
    }

    public void setProcedureDefLOVs(List<ListOfValue> procedureDefLOVs) {
        this.procedureDefLOVs = procedureDefLOVs;
    }

    public List<Procedure> getProcedureList() {
        return procedureList;
    }

    public void setProcedureList(List<Procedure> procedureList) {
        this.procedureList = procedureList;
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public List<Integer> getSelectedLovs() {
        return selectedLovs;
    }

    public void setSelectedLovs(List<Integer> selectedLovs) {
        this.selectedLovs = selectedLovs;
    }

    public List<String> getProcNotes() {
        return procNotes;
    }

    public void setProcNotes(List<String> procNotes) {
        this.procNotes = procNotes;
    }



    
    
}
