/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.ke.sart.site.repository;

import co.ke.sart.site.entity.PatientEntity;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author CMaundu
 */
public class PatientRepositoryImpl implements SearchableRepository<PatientEntity> {
    @PersistenceContext EntityManager entityManager;
    @Override
    public Page<SearchResult<PatientEntity>> search(String query, boolean useBooleanMode, Pageable pageable) {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

        String mode = "IN NATURAL LANGUAGE MODE";
        
        String matchPatient = "MATCH(docNumber, surname, forename, middlenames, patientNumber, patientContact, patientAddress) AGAINST(?1 " + mode + ")";
        String likePatient = "docNumber like %?1% or surname  like %?1% or forename  like %?1% or middlenames  like %?1% or patientNumber  like %?1% or patientContact like %?1% or patientAddress like %?1%";
        
         long total = ((Number)this.entityManager.createNativeQuery(
                "SELECT COUNT(DISTINCT t.rowID) FROM sart_patient t  WHERE " + matchPatient 
        ).setParameter(1, query).getSingleResult()).longValue();
         
@SuppressWarnings("unchecked")
        List<Object[]> results = this.entityManager.createNativeQuery(
                "SELECT DISTINCT t.*, (" + matchPatient +
                        ") AS _ft_scoreColumn " +
                        "FROM sart_patient t WHERE " + matchPatient + " ORDER BY _ft_scoreColumn DESC, rowID DESC",
                "searchResultMapping.patientEntity"
        ).setParameter(1, query)
                .setFirstResult(pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        List<SearchResult<PatientEntity>> list = new ArrayList<>();
        results.forEach(o -> list.add(
                new SearchResult<>((PatientEntity)o[0], (Double)o[1])
        ));

        return new PageImpl<>(list, pageable, total);
    }
    
}
