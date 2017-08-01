/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.ke.sart.site.repository;

import co.ke.sart.site.entity.LabTest;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author CMaundu
 */
public interface LabTestRepository extends CrudRepository<LabTest, Integer> {

    List<LabTest> findByRequestID(int requestID);

    List<LabTest> findByAttendanceID(int attendanceID);
}
