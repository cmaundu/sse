/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.ke.sart.site.repository;

import co.ke.sart.site.entity.PatientEntity;
import co.ke.sart.site.entity.Payment;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;


/**
 *
 * @author CMaundu
 */
public interface PatientRepository extends CrudRepository<PatientEntity, Integer>, SearchableRepository<PatientEntity> {
    Page<PatientEntity> findByDocNumberContainingOrSurnameContainingOrForenameContainingOrPatientNumberContainingOrPatientContactContainingAllIgnoreCaseOrderByRowIDDesc(String docNumber,String  surname,String forename, String patientNumber, String patientContact, Pageable pageable);
}
