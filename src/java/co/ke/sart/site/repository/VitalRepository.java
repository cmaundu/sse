/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.ke.sart.site.repository;

import co.ke.sart.site.entity.Vital;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface VitalRepository extends CrudRepository<Vital, Integer> {

    List<Vital> findByRequestID(int requestID);

    List<Vital> findByAttendanceID(int attendanceID);
    
    List<Vital> findByRowID(int rowID);
}
