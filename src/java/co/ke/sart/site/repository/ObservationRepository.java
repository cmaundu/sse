/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.ke.sart.site.repository;

import co.ke.sart.site.entity.LabTest;
import co.ke.sart.site.entity.Observation;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author CMaundu
 */
public interface ObservationRepository extends CrudRepository<Observation, Integer> {

    List<Observation> findByRequestID(int requestID);

    List<Observation> findByAttendanceID(int attendanceID);
}
