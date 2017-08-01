/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.ke.sart.site.repository;

import co.ke.sart.site.entity.Prescription;
import co.ke.sart.site.entity.Radiology;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author CMaundu
 */
public interface RadiologyRepository extends CrudRepository<Radiology, Integer> {

    List<Radiology> findByRequestID(int requestID);

    List<Radiology> findByAttendanceID(int attendanceID);
}
