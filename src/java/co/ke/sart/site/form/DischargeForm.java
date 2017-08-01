package co.ke.sart.site.form;

import co.ke.sart.site.entity.ListOfValue;
import java.util.List;

public class DischargeForm {
    private int attendanceID;
    private int requestID;
    private String note;
    private List<ListOfValue> dischargeLOVs;
    private int lovTypeLovID;

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

    public List<ListOfValue> getDischargeLOVs() {
        return dischargeLOVs;
    }

    public void setDischargeLOVs(List<ListOfValue> dischargeLOVs) {
        this.dischargeLOVs = dischargeLOVs;
    }

    public int getLovTypeLovID() {
        return lovTypeLovID;
    }

    public void setLovTypeLovID(int lovTypeLovID) {
        this.lovTypeLovID = lovTypeLovID;
    }


    
    
}
