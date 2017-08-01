/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.ke.sart.site.form;

import co.ke.sart.site.entity.LabTest;
import co.ke.sart.site.entity.ListOfValue;
import java.util.List;

/**
 *
 * @author CMaundu
 */
public class LabTestForm {

    private List<ListOfValue> labTestLOVs;
    private List<LabTest> labTestList;
    private int attendanceID;
    private int requestID;
    private String note;
    private List<Integer> selectedLovs;
    private List<String> labNotes;
    

    public List<ListOfValue> getLabTestLOVs() {
        return labTestLOVs;
    }

    public void setLabTestLOVs(List<ListOfValue> labTestLOVs) {
        this.labTestLOVs = labTestLOVs;
    }

    public List<LabTest> getLabTestList() {
        return labTestList;
    }

    public void setLabTestList(List<LabTest> labTest) {
        this.labTestList = labTest;
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

    public List<String> getLabNotes() {
        return labNotes;
    }

    public void setLabNotes(List<String> labNotes) {
        this.labNotes = labNotes;
    }
    
    
}
