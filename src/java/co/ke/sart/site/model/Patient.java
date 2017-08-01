/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.ke.sart.site.model;

import co.ke.sart.site.utils.Gender;
import co.ke.sart.site.utils.MaritalStatus;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 *
 * @author CMaundu
 */
public class Patient extends Record {

    private LocalDate dateOfBirth;

    private String docNumber;
    private String docType;
    private String surname;
    private String forename;
    private String middleNames;
    private Gender gender;
    private String patientNumber;
    private MaritalStatus maritalStatus;
    private String patientContact;
    private String patientAddress;

// <editor-fold defaultstate="collapsed" desc=" Class Properties ">
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public Date getDateOfBirthDate() {
        return Date.valueOf(dateOfBirth);
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        if (dateOfBirth != null) {
            this.dateOfBirth = dateOfBirth.toLocalDate();
        }
    }

    public String getDocNumber() {
        return docNumber;
    }

    public void setDocNumber(String docNumber) {
        this.docNumber = docNumber;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
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

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getPatientNumber() {
        return patientNumber;
    }

    public void setPatientNumber(String patientNumber) {
        this.patientNumber = patientNumber;
    }

    public MaritalStatus getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(MaritalStatus maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getPatientContact() {
        return patientContact;
    }

    public void setPatientContact(String patientContact) {
        this.patientContact = patientContact;
    }

    public String getPatientAddress() {
        return patientAddress;
    }

    public void setPatientAddress(String patientAddress) {
        this.patientAddress = patientAddress;
    }

// </editor-fold>
    public String getAge() {
        String ageStr = "(Unknown)";
        if (this.dateOfBirth != null) {
            Period age = Period.between(this.dateOfBirth, LocalDate.now());
            if (age.getYears() > 2) {
                ageStr = age.getYears() + " Years";
            } else if (age.toTotalMonths() > 2) {
                ageStr = age.toTotalMonths() + " Months";
            } else {
                long days = ChronoUnit.DAYS.between(this.dateOfBirth, LocalDate.now());
                ageStr = days + " Days";
            }
        }
        return ageStr;
    }

    public String getIdentity() {
        String identity = "(None)";
        if (!this.docNumber.isEmpty()) {
            identity = this.getDocNumber();
        }
        if (!this.docType.isEmpty()) {
            identity += " - " + this.getDocType();
        }
        return identity;
    }

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
    public String getDoBFormattedDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return getDateOfBirth().format(formatter);
    }
}
