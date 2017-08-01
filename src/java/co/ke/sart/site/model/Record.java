/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.ke.sart.site.model;

import co.ke.sart.site.entity.UserPrincipal;
import co.ke.sart.site.utils.RecordStatus;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Record {

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

    private int rowID;
    private int createdBy;
    private int updatedBy;
    private int deletedBy;
    private LocalDateTime created;
    private LocalDateTime updated;
    private LocalDateTime deleted;
    private RecordStatus recordStatus;
    
    private UserPrincipal createdByUser;
    private UserPrincipal updatedByUser;

    
    
    // <editor-fold defaultstate="collapsed" desc=" Class Properties ">
    public int getRowID() {
        return rowID;
    }

    public void setRowID(int rowID) {
        this.rowID = rowID;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public int getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(int updatedBy) {
        this.updatedBy = updatedBy;
    }

    public int getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(int deletedBy) {
        this.deletedBy = deletedBy;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public Timestamp getCreatedTimestamp() {
        return Timestamp.valueOf(created);
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public void setCreated(Timestamp created) {
        if (created != null) {
            this.created = created.toLocalDateTime();
        }
    }

    public LocalDateTime getUpdated() {
        return updated;
    }

    public Timestamp getUpdatedTimestamp() {
        return Timestamp.valueOf(this.updated);
    }

    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }

    public void setUpdated(Timestamp updated) {
        if (updated != null) {
            this.updated = updated.toLocalDateTime();
        }
    }

    public LocalDateTime getDeleted() {
        return deleted;
    }

    public Timestamp getDeletedTimestamp() {
        if (this.deleted != null)
        return Timestamp.valueOf(this.deleted);
        else
            return null;
    }

    public void setDeleted(LocalDateTime deleted) {
        this.deleted = deleted;
    }

    public void setDeleted(Timestamp deleted) {
        if (deleted != null) {
            this.deleted = deleted.toLocalDateTime();
        }
    }

    public RecordStatus getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(RecordStatus recordStatus) {
        this.recordStatus = recordStatus;
    }

    public String getCreatedDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return getCreated().format(formatter);
    }
    
    public String getCreatedDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        return getCreated().format(formatter);
    }    
    
    public String getUpdatedDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        return getUpdated().format(formatter);
    }     
// </editor-fold>
}
