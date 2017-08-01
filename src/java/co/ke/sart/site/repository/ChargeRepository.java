/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.ke.sart.site.repository;

import co.ke.sart.site.entity.Charge;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author CMaundu
 */
public interface ChargeRepository extends CrudRepository<Charge, Integer>  {
    List<Charge> findByRequestID(int requestID);
    List<Charge> findByAttendanceID(int attendanceID);
}
