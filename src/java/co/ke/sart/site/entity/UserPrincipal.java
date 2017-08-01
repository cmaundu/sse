/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.ke.sart.site.entity;

import co.ke.sart.site.utils.Gender;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

/**
 *
 * @author CMaundu
 */
@Entity
@Table(name = "sart_usr_users"
//,uniqueConstraints = {@UniqueConstraint(name = "username_UNIQUE", columnNames = "username")}
)
public class UserPrincipal extends RecordEntity implements Authentication, Cloneable {

    private Timestamp lastLoginTime;
    //private int userRole;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userRole")    
    private Role role;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String surname;
    private String forename;
    private String middleNames;

    private String username;
    private String ekNumber;
    private String docNumber;
    private String docType;

    private byte[] password;

    @Transient
    private Collection<? extends GrantedAuthority> authorities;

// <editor-fold defaultstate="collapsed" desc=" Properties ">
    public Timestamp getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Timestamp lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }


    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getForename() {
        return forename;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public String getMiddleNames() {
        return middleNames;
    }

    public void setMiddleNames(String middleNames) {
        this.middleNames = middleNames;
    }

    public byte[] getPassword() {
        return password;
    }

    public void setPassword(byte[] password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEkNumber() {
        return ekNumber;
    }

    public void setEkNumber(String ekNumber) {
        this.ekNumber = ekNumber;
    }

    public String getDocNumber() {
        return docNumber;
    }

    public void setDocNumber(String docNumber) {
        this.docNumber = docNumber;
    }

// </editor-fold>
    @Transient
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
         this.authorities = authorities;
    }

    @Override
    @Transient
    public String getName() {
        return this.username;
    }

    @Override
    @Transient
    public Object getPrincipal() {
        return this.username;
    }

    @Override
    @Transient
    public Object getDetails() {
        return this.username;
    }

    @Override
    @Transient
    public Object getCredentials() {
        return this.password;
    }

    @Override
    public int hashCode() {
        return this.username.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof UserPrincipal
                && ((UserPrincipal) other).username.equals(this.username);
    }

    @Override
    @SuppressWarnings("CloneDoesntDeclareCloneNotSupportedException")
    protected UserPrincipal clone() {
        try {
            return (UserPrincipal) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e); // not possible
        }
    }

    @Override
    public String toString() {
        return this.getFullNames() + " (" + this.username + ')';
    }

    @Transient
    public String getFullNames() {
        String fullNames = "";
        if (!this.surname.isEmpty()) {
            fullNames += this.getSurname();
        }
        if (!this.middleNames.isEmpty() || !this.forename.isEmpty()) {
            fullNames += ", ";
        }
        if (!this.forename.isEmpty()) {
            fullNames += this.getForename();
        }
        if (!this.middleNames.isEmpty() || !this.forename.isEmpty()) {
            fullNames += " ";
        }
        if (!this.middleNames.isEmpty()) {
            fullNames += this.getMiddleNames();
        }

        return fullNames;
    }


    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Transient
    public String getLastLoginDateTime() {
        if (this.lastLoginTime != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
            return lastLoginTime.toLocalDateTime().format(formatter);
        }
        return "";
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }
}
