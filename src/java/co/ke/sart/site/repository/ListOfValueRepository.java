/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.ke.sart.site.repository;

import co.ke.sart.site.entity.Charge;
import co.ke.sart.site.entity.ListOfValue;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author CMaundu
 */
public interface ListOfValueRepository extends CrudRepository<ListOfValue, Integer> {
     List<ListOfValue> findByLovType(String lovType);
     List<ListOfValue> findByLovTypeOrderByLovVal(String lovType);
     List<ListOfValue> findByLovParentName(String lovParentName);
     List<ListOfValue> findByLovTypeAndLovID(String lovType, int lovID);
     List<ListOfValue> findByLovTypeAndLovName(String lovType, String lovName);
     List<ListOfValue> findByLovTypeOrLovParentName(String lovType,String lovParentName);
}
