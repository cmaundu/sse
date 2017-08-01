/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.ke.sart.site.entity;

import co.ke.sart.site.utils.AttendanceStatus;
import co.ke.sart.site.utils.RecordStatus;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "sart_attendance")
public class EAttendance implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int rowID;

    private Timestamp created;
    private Timestamp updated;
    private Timestamp deleted;

    /**
     * int createdBy; int updatedBy;*
     */
    private int deletedBy;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "createdBy")
    private UserPrincipal createdByUser;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "updatedBy")
    private UserPrincipal updatedByUser;

    @Enumerated(EnumType.STRING)
    private RecordStatus recordStatus;

    private Timestamp closeDate;
    /**
     * private int patientID;*
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "patientID")
    private PatientEntity patient;

    private String attNumber;
    private int attDoc;
    private int paymentTypeID;
    private int closedBy;
    @Enumerated(EnumType.STRING)
    private AttendanceStatus attStatus;
    private int attType;
    private String insuranceNumber;

    public Timestamp getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(Timestamp closeDate) {
        this.closeDate = closeDate;
    }

    public PatientEntity getPatient() {
        return patient;
    }

    public void setPatient(PatientEntity patient) {
        this.patient = patient;
    }

    public String getAttNumber() {
        return attNumber;
    }

    public void setAttNumber(String attNumber) {
        this.attNumber = attNumber;
    }

    public int getAttDoc() {
        return attDoc;
    }

    public void setAttDoc(int attDoc) {
        this.attDoc = attDoc;
    }

    public int getPaymentTypeID() {
        return paymentTypeID;
    }

    public void setPaymentTypeID(int paymentTypeID) {
        this.paymentTypeID = paymentTypeID;
    }

    public int getClosedBy() {
        return closedBy;
    }

    public void setClosedBy(int closedBy) {
        this.closedBy = closedBy;
    }

    public AttendanceStatus getAttStatus() {
        return attStatus;
    }

    public void setAttStatus(AttendanceStatus attStatus) {
        this.attStatus = attStatus;
    }

    public int getAttType() {
        return attType;
    }

    public void setAttType(int attType) {
        this.attType = attType;
    }

    public String getInsuranceNumber() {
        return insuranceNumber;
    }

    public void setInsuranceNumber(String insuranceNumber) {
        this.insuranceNumber = insuranceNumber;
    }

    @Transient
    public String getCreatedDateTime() {
        if (this.getCreated() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
            return getCreated().toLocalDateTime().format(formatter);
        }
        return "";
    }

    @Transient
    public String getUpdatedDateTime() {
        if (this.getUpdated() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
            return getUpdated().toLocalDateTime().format(formatter);
        }
        return "";
    }

    public int getRowID() {
        return rowID;
    }

    public void setRowID(int rowID) {
        this.rowID = rowID;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public Timestamp getUpdated() {
        return updated;
    }

    public void setUpdated(Timestamp updated) {
        this.updated = updated;
    }

    public Timestamp getDeleted() {
        return deleted;
    }

    public void setDeleted(Timestamp deleted) {
        this.deleted = deleted;
    }

    public int getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(int deletedBy) {
        this.deletedBy = deletedBy;
    }

    public UserPrincipal getCreatedByUser() {
        return createdByUser;
    }

    public void setCreatedByUser(UserPrincipal createdByUser) {
        this.createdByUser = createdByUser;
    }

    public UserPrincipal getUpdatedByUser() {
        return updatedByUser;
    }

    public void setUpdatedByUser(UserPrincipal updatedByUser) {
        this.updatedByUser = updatedByUser;
    }

    public RecordStatus getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(RecordStatus recordStatus) {
        this.recordStatus = recordStatus;
    }

}
