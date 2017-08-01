/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.ke.sart.site.repository;

import co.ke.sart.site.entity.ChargeMatrix;
import java.util.List;
import org.springframework.data.repository.CrudRepository;


public interface ChargeMatrixRepository extends CrudRepository<ChargeMatrix, Integer>  {
    List<ChargeMatrix> findByLovTypeAndLovTypeID(String lovType, int lovTypeID);
    List<ChargeMatrix> findByLovTypeAndLovTypeIDAndPaymentModeOrderByRowIDDesc(String lovType, int lovTypeID, int paymentMode);
    List<ChargeMatrix> findByLovTypeAndLovTypeIDInAndPaymentMode(String lovType, List<Integer> lovTypeIDs, int paymentMode);
}
