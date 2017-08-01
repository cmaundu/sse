/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.ke.sart.site.repository;

import co.ke.sart.site.entity.AttendanceEntity;
import co.ke.sart.site.entity.EAttendance;
import co.ke.sart.site.utils.AttendanceStatus;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author CMaunduEAttendance
 */
public interface AttendanceERepository extends CrudRepository<EAttendance, Integer> {

    List<EAttendance> findByAttStatus(AttendanceStatus attendanceStatus);
}
