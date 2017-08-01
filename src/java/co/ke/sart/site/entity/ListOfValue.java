/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.ke.sart.site.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "sart_list_of_value")
public class ListOfValue  implements Serializable  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int rowID;
    private int lovID;
    private String lovType;
    private String lovName;
    private String lovVal;
    private String lovParentName;
    private String lovStatus;

    public int getRowID() {
        return rowID;
    }

    public void setRowID(int rowID) {
        this.rowID = rowID;
    }

    public int getLovID() {
        return lovID;
    }

    public void setLovID(int lovID) {
        this.lovID = lovID;
    }

    public String getLovType() {
        return lovType;
    }

    public void setLovType(String lovType) {
        this.lovType = lovType;
    }

    public String getLovName() {
        return lovName;
    }

    public void setLovName(String lovName) {
        this.lovName = lovName;
    }

    public String getLovVal() {
        return lovVal;
    }

    public void setLovVal(String lovVal) {
        this.lovVal = lovVal;
    }

    public String getLovParentName() {
        return lovParentName;
    }

    public void setLovParentName(String lovParentName) {
        this.lovParentName = lovParentName;
    }

    public String getLovStatus() {
        return lovStatus;
    }

    public void setLovStatus(String lovStatus) {
        this.lovStatus = lovStatus;
    }

}
