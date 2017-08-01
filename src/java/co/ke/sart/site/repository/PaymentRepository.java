/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.ke.sart.site.repository;

import co.ke.sart.site.entity.Diagnosis;
import co.ke.sart.site.entity.Payment;
import co.ke.sart.site.entity.PaymentSummary;
import java.util.List;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author CMaundu
 */
public interface PaymentRepository extends CrudRepository<Payment, Integer> {
    List<Payment> findByRequestID(int requestID);

    List<Payment> findByAttendanceID(int attendanceID);
    
   
}
