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
@Table(name = "sart_usr_roles")
public class Role extends RecordEntity implements Serializable {

    private String roleDesc;
    private String roleName;
    private long viewPermissions;
    private long addPermissions;
    private long editPermissions;
    private long deletePermissions;

    public String getRoleDesc() {
        return roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }

    public long getViewPermissions() {
        return viewPermissions;
    }

    public void setViewPermissions(long viewPermissions) {
        this.viewPermissions = viewPermissions;
    }

    public long getAddPermissions() {
        return addPermissions;
    }

    public void setAddPermissions(long addPermissions) {
        this.addPermissions = addPermissions;
    }

    public long getEditPermissions() {
        return editPermissions;
    }

    public void setEditPermissions(long editPermissions) {
        this.editPermissions = editPermissions;
    }

    public long getDeletePermissions() {
        return deletePermissions;
    }

    public void setDeletePermissions(long deletePermissions) {
        this.deletePermissions = deletePermissions;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
    
    

}
