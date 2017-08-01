/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.ke.sart.site.repository;

import co.ke.sart.site.entity.Payment;
import co.ke.sart.site.entity.Permission;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author CMaundu
 */
public interface PermissionRepository extends CrudRepository<Permission, Integer> {
    
}
