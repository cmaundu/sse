/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.ke.sart.site.repository;

import co.ke.sart.site.entity.AttendanceEntity;
import co.ke.sart.site.entity.PaymentMode;
import org.springframework.data.repository.CrudRepository;

public interface PaymentModeRepository extends CrudRepository<PaymentMode, Integer> {
    
}
