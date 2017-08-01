/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.ke.sart.site.form;

import co.ke.sart.site.entity.Role;
import co.ke.sart.site.entity.UserPrincipal;
import co.ke.sart.site.utils.FormAction;
import java.util.List;


public class UserForm {
    private FormAction formAction; 
    private UserPrincipal user;
    private List<Role> roles;
    private String password;
    private String confirmPassword;

    public FormAction getFormAction() {
        return formAction;
    }

    public void setFormAction(FormAction formAction) {
        this.formAction = formAction;
    }

    public UserPrincipal getUser() {
        return user;
    }

    public void setUser(UserPrincipal user) {
        this.user = user;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
    
    
}
