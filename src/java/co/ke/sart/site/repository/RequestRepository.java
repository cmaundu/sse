/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.ke.sart.site.repository;

import co.ke.sart.site.entity.RequestEntity;
import co.ke.sart.site.utils.RecordStatus;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author CMaundu
 */
public interface RequestRepository extends CrudRepository<RequestEntity, Integer>  {
    List<RequestEntity> findByAttendanceID(int attendanceID);
    List<RequestEntity> findByAttendanceIDAndRecordStatusOrderByRowIDDesc(int attendanceID, RecordStatus recordStatus);
    List<RequestEntity> findByAttendanceIDAndRequestType(int attendanceID, int requestType);
}
