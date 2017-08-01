/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.ke.sart.site.repository;

import co.ke.sart.site.entity.Diagnosis;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author CMaundu
 */
public interface DiagnosisRepository extends CrudRepository<Diagnosis, Integer> {

    List<Diagnosis> findByRequestID(int requestID);

    List<Diagnosis> findByAttendanceID(int attendanceID);
}
