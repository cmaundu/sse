/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.ke.sart.site.service;

import co.ke.sart.site.entity.PatientEntity;
import co.ke.sart.site.entity.RecordEntity;
import co.ke.sart.site.entity.UserPrincipal;
import co.ke.sart.site.form.PatientForm;
import co.ke.sart.site.model.Patient;
import co.ke.sart.site.repository.PatientRepository;
import co.ke.sart.site.repository.SearchResult;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 *
 * @author CMaundu
 */
@Service
public class PatientService {

    @Autowired
    PatientRepository patientRepository;

    @Transactional
    public List<PatientEntity> getPatientEntities() {
        return this.toList(this.patientRepository.findAll());
    }

    @Transactional
    public List<Patient> getPatients() {
        RecordEntity re = new PatientEntity();
        List<Patient> list = new ArrayList<>();
        this.patientRepository.findAll()
                .forEach(p -> list.add(this.convert(p)));

        List result = list.stream().sorted((o1, o2) -> Integer.compare(o2.getRowID(), o1.getRowID())).
                collect(Collectors.toList());

        return result;
    }

    @Transactional
    public Patient getPatient(int rowID) {
        PatientEntity patEntity = this.patientRepository.findOne(rowID);
        return this.convert(patEntity);
    }

    private <E> List<E> toList(Iterable<E> i) {
        List<E> list = new ArrayList<>();
        i.forEach(list::add);
        return list;
    }

    @Transactional
    public void savePatient(PatientEntity patient) {
        this.patientRepository.save(patient);
    }

    @Transactional
    public void savePatient(Patient patient) {
        PatientEntity patEntity = this.convert(patient);
        int rowID = patEntity.getRowID();
        this.patientRepository.save(patEntity);
        if (rowID == 0) {
            patEntity.setPatientNumber(String.format("RM-%06d", patEntity.getRowID()));
            this.patientRepository.save(patEntity);
            patient.setRowID(patEntity.getRowID());
        }
    }

    @Transactional
    public void savePatient(PatientForm patForm, UserPrincipal user) {
        Patient patient = new Patient();

        System.out.println("Updated Date -1 : " + patient.getUpdated());
        if (patForm.getRowID() > 0) {
            patient = this.getPatient(patForm.getRowID());
            patient.setUpdatedBy(user.getRowID());
            patient.setUpdated(LocalDateTime.now());
        } else {
            patient.setUpdatedBy(user.getRowID());
            patient.setUpdated(LocalDateTime.now());
            patient.setCreatedBy(user.getRowID());
            patient.setCreated(LocalDateTime.now());
        }
        System.out.println("Updated Date 0.5 : " + patient.getUpdated());
        this.convert(patForm, patient);
        System.out.println("Updated Date 0 : " + patient.getUpdated());
        this.savePatient(patient);
        if (patForm.getRowID() == 0) {
            patForm.setRowID(patient.getRowID());
        }

    }

    @Transactional
    public Page<SearchResult<Patient>> search(String query, boolean useBooleanMode,
            Pageable pageable) {
        Page<PatientEntity> lresult = this.patientRepository.findByDocNumberContainingOrSurnameContainingOrForenameContainingOrPatientNumberContainingOrPatientContactContainingAllIgnoreCaseOrderByRowIDDesc(query, query, query, query, query, pageable);
        //Page<SearchResult<PatientEntity>> sresult = this.patientRepository.search(query, useBooleanMode, pageable);
        List<SearchResult<Patient>> list = new ArrayList<>();
        lresult
                .getContent()
                .forEach(p -> list.add(new SearchResult<>(this.convert(p), 1)));
        return new PageImpl<>(list, pageable, lresult.getTotalPages());
    }
    
    

    public void convert(Patient pat, PatientForm patForm) {
        patForm.setRowID(pat.getRowID());
        patForm.setDateOfBirth(pat.getDateOfBirth());
        patForm.setDocNumber(pat.getDocNumber());
        patForm.setDocType(pat.getDocType());
        patForm.setForename(pat.getForename());
        patForm.setGender(pat.getGender());
        patForm.setMaritalStatus(pat.getMaritalStatus());
        patForm.setMiddleNames(pat.getMiddleNames());
        patForm.setPatientAddress(pat.getPatientAddress());
        patForm.setPatientContact(pat.getPatientContact());
        patForm.setPatientNumber(pat.getPatientNumber());
        patForm.setSurname(pat.getSurname());
    }

    public void convert(PatientForm patForm, Patient pat) {
        //Patient pat = new Patient();
        pat.setRowID(patForm.getRowID());
        pat.setDateOfBirth(patForm.getDateOfBirth());
        pat.setDocNumber(patForm.getDocNumber());
        pat.setDocType(patForm.getDocType());
        pat.setForename(patForm.getForename());
        pat.setGender(patForm.getGender());
        pat.setMaritalStatus(patForm.getMaritalStatus());
        pat.setMiddleNames(patForm.getMiddleNames());
        pat.setPatientAddress(patForm.getPatientAddress());
        pat.setPatientContact(patForm.getPatientContact());
        pat.setPatientNumber(patForm.getPatientNumber());
        pat.setSurname(patForm.getSurname());
    }

    public Patient convert(PatientEntity patEntity) {
        Patient pat = new Patient();
        RecordService.convert(patEntity, pat);

        pat.setDateOfBirth(patEntity.getDateOfBirth());
        pat.setDocNumber(patEntity.getDocNumber());
        pat.setDocType(patEntity.getDocType());
        pat.setForename(patEntity.getForename());
        pat.setGender(patEntity.getGender());
        pat.setMaritalStatus(patEntity.getMaritalStatus());
        pat.setMiddleNames(patEntity.getMiddleNames());
        pat.setPatientAddress(patEntity.getPatientAddress());
        pat.setPatientContact(patEntity.getPatientContact());
        pat.setPatientNumber(patEntity.getPatientNumber());
        pat.setSurname(patEntity.getSurname());

        return pat;
    }

    public PatientEntity convert(Patient pat) {
        PatientEntity patEntity = new PatientEntity();
        RecordService.convert(pat, patEntity);

        patEntity.setDateOfBirth(pat.getDateOfBirthDate());
        patEntity.setDocNumber(pat.getDocNumber());
        patEntity.setDocType(pat.getDocType());
        patEntity.setForename(pat.getForename());
        patEntity.setGender(pat.getGender());
        patEntity.setMaritalStatus(pat.getMaritalStatus());
        patEntity.setMiddleNames(pat.getMiddleNames());
        patEntity.setPatientAddress(pat.getPatientAddress());
        patEntity.setPatientContact(pat.getPatientContact());
        patEntity.setPatientNumber(pat.getPatientNumber());
        patEntity.setSurname(pat.getSurname());

        return patEntity;
    }
}
