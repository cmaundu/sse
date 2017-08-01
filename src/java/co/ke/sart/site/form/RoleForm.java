/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.ke.sart.site.form;

import co.ke.sart.site.entity.Permission;
import co.ke.sart.site.entity.Role;
import co.ke.sart.site.utils.FormAction;
import java.util.List;

public class RoleForm {

    private List<Permission> permissions;
    private Role role;
    private FormAction formAction;
    private List<Integer> viewPermissions;
    private List<Integer> addPermissions;
    private List<Integer> editPermissions;
    private List<Integer> deletePermissions;

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public FormAction getFormAction() {
        return formAction;
    }

    public void setFormAction(FormAction formAction) {
        this.formAction = formAction;
    }

    public List<Integer> getViewPermissions() {
        return viewPermissions;
    }

    public void setViewPermissions(List<Integer> viewPermissions) {
        this.viewPermissions = viewPermissions;
    }

    public List<Integer> getAddPermissions() {
        return addPermissions;
    }

    public void setAddPermissions(List<Integer> addPermissions) {
        this.addPermissions = addPermissions;
    }

    public List<Integer> getEditPermissions() {
        return editPermissions;
    }

    public void setEditPermissions(List<Integer> editPermissions) {
        this.editPermissions = editPermissions;
    }

    public List<Integer> getDeletePermissions() {
        return deletePermissions;
    }

    public void setDeletePermissions(List<Integer> deletePermissions) {
        this.deletePermissions = deletePermissions;
    }

}
