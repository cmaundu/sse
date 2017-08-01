/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.ke.sart.site.repository;

import co.ke.sart.site.entity.DrugDef;
import co.ke.sart.site.entity.ICD;
import co.ke.sart.site.entity.ListOfValue;
import co.ke.sart.site.entity.Prescription;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author CMaundu
 */
public interface DrugDefRepository extends CrudRepository<DrugDef, Integer> {

    List<DrugDef> findFirst200ByDrugName(String drugName);

    List<DrugDef> findFirst200ByDrugNameContainingIgnoreCase(String drugName);

    List<DrugDef> findByRowIDIn(Collection<Integer> rowIDs);
}
