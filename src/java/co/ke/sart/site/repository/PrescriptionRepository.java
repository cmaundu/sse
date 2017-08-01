package co.ke.sart.site.repository;

import co.ke.sart.site.entity.Prescription;
import co.ke.sart.site.entity.Procedure;
import java.util.Collection;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface PrescriptionRepository extends CrudRepository<Prescription, Integer> {

    List<Prescription> findByRequestID(int requestID);

    List<Prescription> findByAttendanceID(int attendanceID);
    

}
