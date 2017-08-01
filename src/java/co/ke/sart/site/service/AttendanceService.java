/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.ke.sart.site.service;

import co.ke.sart.site.entity.AttendanceEntity;
import co.ke.sart.site.entity.ChargeMatrix;
import co.ke.sart.site.entity.EAttendance;
import co.ke.sart.site.entity.ListOfValue;
import co.ke.sart.site.entity.PatientEntity;
import co.ke.sart.site.entity.PaymentMode;
import co.ke.sart.site.entity.UserPrincipal;
import co.ke.sart.site.form.AttendanceForm;
import co.ke.sart.site.form.AttendanceNewForm;
import co.ke.sart.site.form.ChargeForm;
import co.ke.sart.site.model.Attendance;
import co.ke.sart.site.model.Record;
import co.ke.sart.site.repository.AttendanceERepository;
import co.ke.sart.site.repository.AttendanceRepository;
import co.ke.sart.site.repository.ChargeMatrixRepository;
import co.ke.sart.site.repository.ListOfValueRepository;
import co.ke.sart.site.repository.PatientRepository;
import co.ke.sart.site.repository.PaymentModeRepository;
import co.ke.sart.site.utils.AttendanceStatus;
import co.ke.sart.site.utils.FormAction;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 *
 * @author CMaundu
 */
@Service
public class AttendanceService extends RecordService {

    @Autowired
    PaymentModeRepository paymentModeRepository;

    @Autowired
    AttendanceRepository attendanceRepository;

    @Autowired
    ListOfValueRepository listOfValueRepository;

    @Autowired
    ChargeMatrixRepository chargeMatrixRepository;

    @Autowired
    PatientService patientService;

    @Autowired
    RequestService requestService;

    @Autowired
    AttendanceERepository attendanceERepository;

    public List<Attendance> retriveOpenAttendances() {

        List<AttendanceEntity> attlist = this.attendanceRepository.findByAttStatus(AttendanceStatus.New);
        attlist.addAll(this.attendanceRepository.findByAttStatus(AttendanceStatus.Active));
        attlist = attlist.stream().sorted((a, b) -> Integer.compare(a.getRowID(), b.getRowID())).collect(Collectors.toList());

        List<Attendance> returnList = attlist.stream().map(p -> this.addPaymentMode(this.convert(p))).collect(Collectors.toList());

        List<Record> records = new ArrayList<>();

        returnList.forEach(p -> records.add(p));

        this.addUserDetails(records);

        returnList = addAttendanceType(returnList);
        return returnList;

    }

    public AttendanceForm prepareAttendanceForm(int rowID, int patientID, FormAction formAction) {
        AttendanceForm attendanceForm = new AttendanceForm();
        attendanceForm.setFormAction(formAction);
        if (formAction == FormAction.EDIT && rowID > 0) {
            AttendanceEntity entity = this.attendanceRepository.findOne(rowID);
            attendanceForm.setRowID(entity.getRowID());
            attendanceForm.setAttDoc(entity.getAttDoc());
            attendanceForm.setAttType(entity.getAttType());
            attendanceForm.setPatientID(entity.getPatientID());
            attendanceForm.setInsuranceNumber(entity.getInsuranceNumber());
            attendanceForm.setPaymentTypeID(entity.getPaymentTypeID());
        } else {
            attendanceForm.setPatientID(patientID);
        }
        attendanceForm.setPatient(this.patientService.getPatient(attendanceForm.getPatientID()));

        attendanceForm.setAttendanceTypes(this.getAttendanceTypes());
        attendanceForm.setPaymentModes(this.getAllPaymentModes());

        return attendanceForm;
    }

    public void updateAttendance(int attendanceID, AttendanceStatus attendanceStatus) {
        AttendanceEntity entity = this.attendanceRepository.findOne(attendanceID);
        entity.setAttStatus(attendanceStatus);
        this.attendanceRepository.save(entity);
    }

    @Transactional
    public void SaveAttendance(AttendanceForm attendanceForm, UserPrincipal user) {
        AttendanceEntity entity = new AttendanceEntity();
        if (attendanceForm.getRowID() > 0) {
            entity = this.attendanceRepository.findOne(attendanceForm.getRowID());
            entity.setUpdated(Timestamp.valueOf(LocalDateTime.now()));
            entity.setUpdatedBy(user.getRowID());
        } else {
            entity.setPatientID(attendanceForm.getPatientID());
            entity.setUpdated(Timestamp.valueOf(LocalDateTime.now()));
            entity.setUpdatedBy(user.getRowID());
            entity.setCreated(Timestamp.valueOf(LocalDateTime.now()));
            entity.setCreatedBy(user.getRowID());
        }
        entity.setAttType(attendanceForm.getAttType());
        entity.setInsuranceNumber(attendanceForm.getInsuranceNumber());
        entity.setPaymentTypeID(attendanceForm.getPaymentTypeID());
        entity.setAttStatus(AttendanceStatus.New);
        this.attendanceRepository.save(entity);
        attendanceForm.setRowID(entity.getRowID());
        Optional<String> sVal = attendanceForm.getAttendanceTypes().stream().filter(p -> p.getLovID() == attendanceForm.getAttType())
                .map(p -> p.getLovVal()).findFirst();
        String attendanceType = sVal.isPresent() ? sVal.get() : "";
        this.saveAttendanceNumber(attendanceForm.getRowID(), attendanceForm.getPatientID());
        if (attendanceForm.getFormAction() == FormAction.NEW) {
            this.saveAttendanceCharge(attendanceForm.getRowID(), user, attendanceForm.getAttType(), attendanceForm.getPaymentTypeID(), attendanceType);
        }
    }

    public void saveAttendanceNumber(int attendanceID, int patientID) {
        List<AttendanceEntity> entities = this.attendanceRepository.findFirst2ByPatientIDOrderByRowIDDesc(patientID);
        PatientEntity patEntity = this.patientService.patientRepository.findOne(patientID);
        AttendanceEntity entity;
        int num = 0;

        if (entities.size() == 0) {

        } else {
            entity = entities.get(0);
            if (entities.size() == 1) {
                entity.setAttNumber(patEntity.getPatientNumber() + "-001");
            } else {
                AttendanceEntity entityLast = entities.get(1);
                String lastNumber = entityLast.getAttNumber();
                lastNumber = (lastNumber == null || lastNumber.isEmpty() ) ? "-" : lastNumber;
                int lastIndex = (lastNumber != null && lastNumber.contains("-"))? lastNumber.lastIndexOf("-"):0;

                if (lastIndex > 5) {
                    lastNumber = lastNumber.substring(lastIndex + 1);
                    try {
                        num = Integer.parseInt(lastNumber) + 1;
                    } catch (NumberFormatException | NullPointerException e) {
                        num = entityLast.getRowID() + 1;
                    }
                }
                if (num == 0) {
                    num = entity.getRowID();
                }
                entity.setAttNumber(patEntity.getPatientNumber() + String.format("-%03d", num));
                this.attendanceRepository.save(entity);
            }
        }

    }

    public void saveAttendanceCharge(int attendanceID, UserPrincipal user, int attType, int paymentTypeID, String attendanceType) {
        Optional<ChargeMatrix> chargeMatrix = this.chargeMatrixRepository
                .findByLovTypeAndLovTypeIDAndPaymentModeOrderByRowIDDesc("ATTENDANCE_TYPE", attType, paymentTypeID)
                .stream()
                .findFirst();

        if (chargeMatrix.isPresent()) {
            ChargeForm chargeForm = new ChargeForm();
            chargeForm.setAttendanceID(attendanceID);
            chargeForm.setAmountCharged(chargeMatrix.get().getAmount());
            chargeForm.setDescription("Consultation Charges - " + attendanceType);
            chargeForm.setNote("Consultation Charges - " + attendanceType);
            chargeForm.setRequestSubType(1);
            chargeForm.setRequestType(1);
            requestService.saveCharge(chargeForm, user);
        }

    }

    public List<PaymentMode> getAllPaymentModes() {
        return this.toList(this.paymentModeRepository.findAll());
    }

    public List<ListOfValue> getAttendanceTypes() {
        return this.listOfValueRepository.findByLovType("ATTENDANCE_TYPE");
    }

    public PaymentMode getPaymentMode(int paymentID) {
        return this.paymentModeRepository.findOne(paymentID);
    }

    private Attendance addPaymentMode(Attendance att) {
        att.setPaymentMode(this.getPaymentMode(att.getPaymentTypeID()));
        return att;
    }

    @Transactional
    public Page<Attendance> getAttendanceByPatientID(int rowID, Pageable pageable) {
        Page<AttendanceEntity> attlist = this.attendanceRepository.getByPatientIDOrderByRowIDDesc(rowID, pageable);

        List<Attendance> returnList = attlist.getContent().stream().map(p -> this.addPaymentMode(this.convert(p))).collect(Collectors.toList());

        returnList = addAttendanceType(returnList);

        return new PageImpl<Attendance>(returnList, pageable, attlist.getTotalElements());
    }

    public List<ListOfValue> getLovByType(String lovType) {
        return this.listOfValueRepository.findByLovTypeOrderByLovVal(lovType);
    }

    public List<Attendance> addAttendanceType(List<Attendance> attendances) {
        if (attendances != null) {
            List<ListOfValue> lovs = getLovByType("ATTENDANCE_TYPE");
            attendances = attendances.stream().map(e -> {
                Optional<ListOfValue> lov = lovs.stream().filter(p -> p.getLovID() == e.getAttType()).findFirst();
                if (lov.isPresent()) {
                    e.setAttTypeName(lov.get().getLovVal());
                }
                return e;
            }).collect(Collectors.toList());
        }
        return attendances;
    }

    public Attendance addAttendanceType(Attendance attendance) {
        if (attendance != null) {
            List<ListOfValue> lovs = getLovByType("ATTENDANCE_TYPE");
            ListOfValue lov = lovs.stream().filter(p -> p.getLovID() == attendance.getAttType()).findFirst().get();
            if (lov != null) {
                attendance.setAttTypeName(lov.getLovVal());
            }
        }
        return attendance;
    }

    public Attendance getAttendance(int rowID) {
        AttendanceEntity attendanceEntity = this.attendanceRepository.findOne(rowID);
        System.out.println("Attendance Patient ID : " + attendanceEntity.getPatientID());
        return this.addAttendanceType(this.convert(attendanceEntity));
    }

    @Transactional
    public Attendance convert(AttendanceEntity attendanceEntity) {
        Attendance attendance = new Attendance();
        RecordService.convert(attendanceEntity, attendance);
        attendance.setAttDoc(attendanceEntity.getAttDoc());
        attendance.setAttStatus(attendanceEntity.getAttStatus());
        attendance.setAttType(attendanceEntity.getAttType());
        attendance.setCloseDate(attendanceEntity.getCloseDate());
        attendance.setClosedBy(attendanceEntity.getClosedBy());
        attendance.setInsuranceNumber(attendanceEntity.getInsuranceNumber());
        attendance.setPatientID(attendanceEntity.getPatientID());
        attendance.setPaymentTypeID(attendanceEntity.getPaymentTypeID());
        attendance.setAttNumber(attendanceEntity.getAttNumber());

        return attendance;
    }

    @Transactional
    public void convert(Attendance attendance, AttendanceNewForm attendanceNewForm) {
        attendanceNewForm.setRowID(attendance.getRowID());
        attendanceNewForm.setAttDoc(attendance.getAttDoc());
        attendanceNewForm.setAttStatus(attendance.getAttStatus());
        attendanceNewForm.setAttType(attendance.getAttType());
        attendanceNewForm.setInsuranceNumber(attendance.getInsuranceNumber());
        attendanceNewForm.setPatientID(attendance.getPatientID());
        attendanceNewForm.setPaymentTypeID(attendance.getPaymentTypeID());
    }

    @Transactional
    public void convert(AttendanceNewForm attendanceNewForm, Attendance attendance) {
        attendance.setRowID(attendanceNewForm.getRowID());
        attendance.setAttDoc(attendanceNewForm.getAttDoc());
        attendance.setAttStatus(attendanceNewForm.getAttStatus());
        attendance.setAttType(attendanceNewForm.getAttType());
        attendance.setInsuranceNumber(attendanceNewForm.getInsuranceNumber());
        attendance.setPatientID(attendanceNewForm.getPatientID());
        attendance.setPaymentTypeID(attendanceNewForm.getPaymentTypeID());
    }

    @Transactional
    public AttendanceEntity convert(Attendance attendance) {
        AttendanceEntity attendanceEntity = new AttendanceEntity();
        RecordService.convert(attendance, attendanceEntity);
        attendanceEntity.setAttDoc(attendance.getAttDoc());
        attendanceEntity.setAttStatus(attendance.getAttStatus());
        attendanceEntity.setAttType(attendance.getAttType());
        attendanceEntity.setCloseDate(attendance.getCloseDate());
        attendanceEntity.setClosedBy(attendance.getClosedBy());
        attendanceEntity.setInsuranceNumber(attendance.getInsuranceNumber());
        attendanceEntity.setPatientID(attendance.getPatientID());
        attendanceEntity.setPaymentTypeID(attendance.getPaymentTypeID());
        attendanceEntity.setAttNumber(attendance.getAttNumber());
        return attendanceEntity;
    }

    @Transactional
    public void SaveAttendance(AttendanceNewForm attendanceNewForm, UserPrincipal user) {
        Attendance att = new Attendance();
        if (attendanceNewForm.getRowID() > 0) {
            att = this.convert(this.attendanceRepository.findOne(attendanceNewForm.getRowID()));
            att.setUpdated(LocalDateTime.now());
            att.setUpdatedBy(user.getRowID());
        } else {
            att.setCreated(LocalDateTime.now());
            att.setCreatedBy(user.getRowID());
            att.setUpdated(LocalDateTime.now());
            att.setUpdatedBy(user.getRowID());
        }
        this.convert(attendanceNewForm, att);
        this.saveAttendance(att);
        saveAttendanceCharge(att, user);
        attendanceNewForm.setRowID(att.getRowID());
    }

    @Transactional
    public void saveAttendance(Attendance attendance) {
        AttendanceEntity attendanceEntity = this.convert(attendance);
        int rowID = attendanceEntity.getRowID();
        this.attendanceRepository.save(attendanceEntity);
        attendance.setRowID(attendanceEntity.getRowID());

    }

    @Transactional
    public void saveAttendanceCharge(Attendance attendance, UserPrincipal user) {
        Optional<ChargeMatrix> chargeMatrix = this.chargeMatrixRepository
                .findByLovTypeAndLovTypeIDAndPaymentModeOrderByRowIDDesc("ATTENDANCE_TYPE", attendance.getAttType(), attendance.getPaymentTypeID())
                .stream()
                .findFirst();

        if (chargeMatrix.isPresent()) {
            this.addAttendanceType(attendance);
            ChargeForm chargeForm = new ChargeForm();
            chargeForm.setAttendanceID(attendance.getRowID());
            chargeForm.setAmountCharged(chargeMatrix.get().getAmount());
            chargeForm.setDescription("Consultation Charges - " + attendance.getAttTypeName());
            chargeForm.setNote("Consultation Charges - " + attendance.getAttTypeName());
            chargeForm.setRequestSubType(1);
            chargeForm.setRequestType(1);
            requestService.saveCharge(chargeForm, user);
        }

    }

    private <E> List<E> toList(Iterable<E> i) {
        List<E> list = new ArrayList<>();
        i.forEach(list::add);
        return list;
    }
}
