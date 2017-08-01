/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.ke.sart.site.service;

import co.ke.sart.site.entity.RecordEntity;
import co.ke.sart.site.entity.UserPrincipal;
import co.ke.sart.site.model.Record;
import co.ke.sart.site.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author CMaundu
 */
@Service
public class RecordService {
        @Autowired
    UserRepository userRepository;
    public static void convert(RecordEntity fromEntity, Record toRecord){
        toRecord.setRowID(fromEntity.getRowID());
        toRecord.setCreated(fromEntity.getCreated());
        toRecord.setCreatedBy(fromEntity.getCreatedBy());
        toRecord.setDeleted(fromEntity.getDeleted());
        toRecord.setDeletedBy(fromEntity.getDeletedBy());
        toRecord.setUpdated(fromEntity.getUpdated());
        toRecord.setUpdatedBy(fromEntity.getUpdatedBy());
        toRecord.setRecordStatus(fromEntity.getRecordStatus());
    }
    
    
    public static void convert(Record fromRecord, RecordEntity toEntity){
        toEntity.setRowID(fromRecord.getRowID());
        toEntity.setCreated(fromRecord.getCreatedTimestamp());
        toEntity.setCreatedBy(fromRecord.getCreatedBy());
        toEntity.setDeleted(fromRecord.getDeletedTimestamp());
        toEntity.setDeletedBy(fromRecord.getDeletedBy());
        toEntity.setUpdated(fromRecord.getUpdatedTimestamp());
        toEntity.setUpdatedBy(fromRecord.getUpdatedBy());
        toEntity.setRecordStatus(fromRecord.getRecordStatus());
    }    
    
    
        public List<Record> addUserDetails(List<Record> records) {
        List<UserPrincipal> users = new ArrayList<>();
        this.userRepository.findAll().forEach(u -> users.add(u));
        
        records = records.stream().map(p -> {
            for (UserPrincipal user : users) {
                if (user.getRowID() == p.getCreatedBy()) {
                    p.setCreatedByUser(user);
                    break;
                }
            }
            return p;
        }).map(p -> {
            for (UserPrincipal user : users) {
                if (user.getRowID() == p.getUpdatedBy()) {
                    p.setUpdatedByUser(user);
                    break;
                }
            }
            return p;
        }).collect(Collectors.toList());
        
        return records;
    }
}
