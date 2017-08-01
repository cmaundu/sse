/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.ke.sart.site.repository;

import co.ke.sart.site.entity.ICD;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface ICDRepository extends CrudRepository<ICD, Integer> {

    List<ICD> findFirst200ByShortDescription(String shortDescription);

    List<ICD> findFirst200ByShortDescriptionContainingIgnoreCase(String shortDescription);

}
