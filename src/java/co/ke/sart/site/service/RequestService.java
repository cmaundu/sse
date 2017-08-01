/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.ke.sart.site.service;

import co.ke.sart.site.entity.Charge;
import co.ke.sart.site.entity.ChargeMatrix;
import co.ke.sart.site.entity.Diagnosis;
import co.ke.sart.site.entity.DrugDef;
import co.ke.sart.site.entity.ICD;
import co.ke.sart.site.entity.LabTest;
import co.ke.sart.site.entity.ListOfValue;
import co.ke.sart.site.entity.Observation;
import co.ke.sart.site.entity.Payment;
import co.ke.sart.site.entity.PaymentMode;
import co.ke.sart.site.entity.Prescription;
import co.ke.sart.site.entity.Procedure;
import co.ke.sart.site.entity.Radiology;
import co.ke.sart.site.entity.RequestEntity;
import co.ke.sart.site.entity.UserPrincipal;
import co.ke.sart.site.entity.Vital;
import co.ke.sart.site.form.ChargeForm;
import co.ke.sart.site.form.DiagnosisForm;
import co.ke.sart.site.form.DischargeForm;
import co.ke.sart.site.form.LabTestForm;
import co.ke.sart.site.form.ObservationForm;
import co.ke.sart.site.form.PaymentForm;
import co.ke.sart.site.form.PrescriptionForm;
import co.ke.sart.site.form.ProcedureForm;
import co.ke.sart.site.form.RadiologyForm;
import co.ke.sart.site.form.VitalForm;
import co.ke.sart.site.interfaces.ICharges;
import co.ke.sart.site.model.Attendance;
import co.ke.sart.site.model.Request;
import co.ke.sart.site.repository.ChargeMatrixRepository;
import co.ke.sart.site.repository.ChargeRepository;
import co.ke.sart.site.repository.DiagnosisRepository;
import co.ke.sart.site.repository.DrugDefRepository;
import co.ke.sart.site.repository.ICDRepository;
import co.ke.sart.site.repository.LabTestRepository;
import co.ke.sart.site.repository.ListOfValueRepository;
import co.ke.sart.site.repository.ObservationRepository;
import co.ke.sart.site.repository.PaymentRepository;
import co.ke.sart.site.repository.PrescriptionRepository;
import co.ke.sart.site.repository.ProcedureRepository;
import co.ke.sart.site.repository.RadiologyRepository;
import co.ke.sart.site.repository.RequestRepository;
import co.ke.sart.site.repository.UserRepository;
import co.ke.sart.site.repository.VitalRepository;
import co.ke.sart.site.utils.AttendanceStatus;
import co.ke.sart.site.utils.FormAction;
import co.ke.sart.site.utils.RecordStatus;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RequestService {

    @Autowired
    ChargeRepository chargeRepository;

    @Autowired
    ChargeMatrixRepository chargeMatrixRepository;

    @Autowired
    RequestRepository requestRepository;

    @Autowired
    ListOfValueRepository listOfValueRepository;

    @Autowired
    VitalRepository vitalRepository;

    @Autowired
    ObservationRepository observationRepository;

    @Autowired
    RadiologyRepository radiologyRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ICDRepository icdRepository;

    @Autowired
    DiagnosisRepository diagnosisRepository;

    @Autowired
    LabTestRepository labTestRepository;

    @Autowired
    ProcedureRepository procedureRepository;

    @Autowired
    PrescriptionRepository prescriptionRepository;

    @Autowired
    DrugDefRepository drugDefRepository;

    @Autowired
    ChargesService chargeService;

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    AttendanceService attendanceService;

    public ChargeForm prepareChargeForm(ChargeForm chargeForm) {
        chargeForm.setRequestSubTypeLOVs(listOfValueRepository.findByLovTypeOrderByLovVal("CHARGE"));
        System.out.println("Request ID: " + chargeForm.getRequestID());
        if (chargeForm.getRequestID() > 0) {
            Charge charge = new Charge();
            List<Charge> charges = this.chargeRepository.findByRequestID(chargeForm.getRequestID());
            if (!charges.isEmpty()) {
                charge = charges.get(0);
                System.out.println("Amount: " + charge);
                chargeForm.setNote(charge.getNote());
                chargeForm.setAmountCharged(charge.getAmount());
            }
            RequestEntity entity = this.requestRepository.findOne(chargeForm.getRequestID());
            chargeForm.setRequestSubType(entity.getRequestSubType());
        }
        return chargeForm;
    }

    public void saveCharge(ChargeForm chargeForm, UserPrincipal user) {
        RequestEntity entity = new RequestEntity();
        Charge charge = new Charge();
        if (chargeForm.getRequestID() > 0) {
            entity = this.requestRepository.findOne(chargeForm.getRequestID());

            List<Charge> charges = this.chargeRepository.findByRequestID(chargeForm.getRequestID());
            if (!charges.isEmpty()) {
                charge = charges.get(0);
            }

            List<Payment> requestPayments = this.paymentRepository.findByRequestID(chargeForm.getRequestID());
            double totalPayments = requestPayments.stream().map(p -> p.getPaidAmount()).reduce(Double.valueOf(0), Double::sum);

            entity.setAmountCharged(chargeForm.getAmountCharged());

            if (entity.getAmountCharged() <= totalPayments) {
                entity.setPaid(true);
            }
        } else {
            int requestType = this.listOfValueRepository.findByLovTypeAndLovName("REQUEST", "Charge")
                    .stream()
                    .findFirst()
                    .map(e -> e.getLovID())
                    .get();
            System.out.println(requestType);

            entity.setRequestType(requestType);
            entity.setCreated(Timestamp.valueOf(LocalDateTime.now()));

            entity.setCreatedBy(user.getRowID());

            entity.setChargeable(true);
            entity.setRecordStatus(RecordStatus.ACTIVE);

            charge.setRecordStatus(RecordStatus.ACTIVE);
            charge.setCreated(Timestamp.valueOf(LocalDateTime.now()));
            charge.setCreatedBy(user.getRowID());

            entity.setPaid(false);

        }
        entity.setUpdated(Timestamp.valueOf(LocalDateTime.now()));
        entity.setUpdatedBy(user.getRowID());

        entity.setAttendanceID(chargeForm.getAttendanceID());
        entity.setNote(chargeForm.getNote());

        entity.setAmountCharged(chargeForm.getAmountCharged());
        entity.setRequestSubType(chargeForm.getRequestSubType());
        this.requestRepository.save(entity);

        charge.setAttendanceID(entity.getAttendanceID());
        charge.setRequestID(entity.getRowID());

        charge.setAmount(entity.getAmountCharged());
        charge.setNote(entity.getNote());
        charge.setUpdatedBy(user.getRowID());
        charge.setUpdated(Timestamp.valueOf(LocalDateTime.now()));
        this.chargeRepository.save(charge);
    }

    public ObservationForm prepareObservationForm(ObservationForm observationForm) {
        if (observationForm.getRequestID() > 0) {
            Observation observation = new Observation();
            List<Observation> observations = this.observationRepository.findByRequestID(observationForm.getRequestID());
            if (!observations.isEmpty()) {
                observation = observations.get(0);
                observationForm.setObservation(observation);
            }
        }
        return observationForm;
    }

    public void saveObservation(ObservationForm observationForm, UserPrincipal user) {

        RequestEntity entity = new RequestEntity();
        Observation observation = observationForm.getObservation();

        if (observationForm.getObservation().getRowID() > 0) {
            entity = this.requestRepository.findOne(observationForm.getRequestID());
            observation = this.observationRepository.findOne(observationForm.getObservation().getRowID());

        } else {
            entity.setAttendanceID(observationForm.getAttendanceID());

            entity.setRecordStatus(RecordStatus.ACTIVE);
            int requestType = this.listOfValueRepository.findByLovTypeAndLovName("REQUEST", "Examination")
                    .stream()
                    .findFirst()
                    .map(e -> e.getLovID())
                    .get();
            entity.setRequestType(requestType);
            entity.setPaid(true);
            entity.setCreated(Timestamp.valueOf(LocalDateTime.now()));
            entity.setCreatedBy(user.getRowID());

            observation.setCreated(Timestamp.valueOf(LocalDateTime.now()));
            observation.setCreatedBy(user.getRowID());
            observation.setRecordStatus(RecordStatus.ACTIVE);

        }
        entity.setNote(("Complaint: " + observationForm.getObservation().getComplaint() + ", History: " + observationForm.getObservation().getProvisionalDiagnosis()
                + ", Remarks: " + observationForm.getObservation().getRemark()));
        if (entity.getNote().length() > 200) {
            entity.setNote(entity.getNote().substring(0, 198));
        }
        entity.setUpdated(Timestamp.valueOf(LocalDateTime.now()));
        entity.setUpdatedBy(user.getRowID());
        entity.setChargeable(false);
        this.requestRepository.save(entity);
        this.attendanceService.updateAttendance(entity.getAttendanceID(), AttendanceStatus.Active);
        observation.setAttendanceID(entity.getAttendanceID());
        observation.setRequestID(entity.getRowID());
        observation.setComplaint(observationForm.getObservation().getComplaint());
        observation.setProvisionalDiagnosis(observationForm.getObservation().getProvisionalDiagnosis());
        observation.setRemark(observationForm.getObservation().getRemark());
        observation.setUpdated(Timestamp.valueOf(LocalDateTime.now()));
        observation.setUpdatedBy(user.getRowID());
        this.observationRepository.save(observation);
    }

    public List<Request> getAllRequests(int attendanceID) {
        List<RequestEntity> requestEntities = this.requestRepository.findByAttendanceIDAndRecordStatusOrderByRowIDDesc(attendanceID, RecordStatus.ACTIVE);
        return requestEntities.stream().map(p -> this.convert(p))
                .collect(Collectors.toList());
    }

    public Request getRequest(int requestID) {
        Request request = null;
        RequestEntity requestEntity = this.requestRepository.findOne(requestID);
        if (requestEntity != null) {
            request = this.convert(requestEntity);
        }

        return request;
    }

    public List<ChargeMatrix> getChargeMatrix(String lovType, List<Integer> lovTypeIDs, int paymentMode) {
        return chargeMatrixRepository.findByLovTypeAndLovTypeIDInAndPaymentMode(lovType, lovTypeIDs, paymentMode);
    }

    public List<Request> getRequestForDisplay(int attendanceID) {
        List<Request> requests = this.getAllRequests(attendanceID);

        List<ListOfValue> lovs = this.listOfValueRepository.findByLovType("REQUEST");
        List<ListOfValue> subLovs = this.listOfValueRepository.findByLovParentName("REQUEST");
        List<UserPrincipal> users = new ArrayList<>();
        this.userRepository.findAll().forEach(u -> users.add(u));

        requests = requests.stream().map(p -> {
            for (ListOfValue lov : lovs) {
                if (lov.getLovID() == p.getRequestType()) {
                    p.setTitle(lov.getLovVal());
                    break;
                }
            }
            return p;
        }).map(p -> {
            for (ListOfValue lov : subLovs) {
                if (lov.getLovID() == p.getRequestSubType()) {
                    p.setTitle(p.getTitle() + ": " + lov.getLovVal());
                    break;
                }
            }
            return p;
        }).map(p -> {
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

        requests.forEach(r -> {
            if (r.getRequestType() == 3 && r.getDescription() != null && !r.getDescription().isEmpty()) {
                r.setTitle(r.getTitle() + ": " + r.getDescription());
            }
        });

        return requests;
    }

    public Request getSingleRequestForDisplay(int requestID) {

        Request request = this.getRequest(requestID);
        if (request != null) {
            List<ListOfValue> lovs = this.listOfValueRepository.findByLovTypeAndLovID("REQUEST", request.getRequestType());
            if (lovs != null && lovs.size() > 0) {
                request.setTitle(lovs.get(0).getLovVal());
            }
            request.setCreatedByUser(this.userRepository.findOne(request.getCreatedBy()));
            request.setUpdatedByUser(this.userRepository.findOne(request.getUpdatedBy()));
        }
        return request;
    }

    public Map<String, Object> getRequestDetailsForDisplay(int requestID) {
        Map<String, Object> modelAttributes = new HashMap<>();

        Request request = this.getSingleRequestForDisplay(requestID);
        modelAttributes.put("request", request);

        List<Payment> payments = this.paymentRepository.findByRequestID(requestID);
        if (payments != null) {
            List<ListOfValue> paymentLovs = this.listOfValueRepository.findByLovType("PAYMENT_METHOD");
            payments = payments.stream().map(p -> {
                for (ListOfValue lov : paymentLovs) {
                    if (lov.getLovID() == p.getPaymentLovTypeID()) {
                        p.setPaymentLovTypeVal(lov.getLovVal());
                        break;
                    }
                }
                return p;
            }).collect(Collectors.toList());
            modelAttributes.put("payments", payments);
        }

        List<LabTest> labtests = this.labTestRepository.findByRequestID(requestID);
        if (labtests != null && labtests.size() > 0) {
            List<ListOfValue> labtestLovs = this.listOfValueRepository.findByLovType("LAB_TEST");
            labtests = labtests.stream().map(p -> {
                for (ListOfValue lov : labtestLovs) {
                    if (lov.getLovID() == p.getLabTypeLovID()) {
                        p.setLabTypeLovVal(lov.getLovVal());
                        break;
                    }
                }
                return p;
            }).collect(Collectors.toList());
            modelAttributes.put("labtests", labtests);
        }

        List<Radiology> radiologies = this.radiologyRepository.findByRequestID(requestID);
        if (radiologies != null && radiologies.size() > 0) {
            List<ListOfValue> radLovs = this.listOfValueRepository.findByLovType("RADIOLOGY_DEF");
            radiologies = radiologies.stream().map(p -> {
                for (ListOfValue lov : radLovs) {
                    if (lov.getLovID() == p.getLovTypeLovID()) {
                        p.setLovTypeVal(lov.getLovVal());
                        break;
                    }
                }
                return p;
            }).collect(Collectors.toList());
            modelAttributes.put("radiologies", radiologies);
        }
        List<Procedure> procedures = this.procedureRepository.findByRequestID(requestID);
        if (procedures != null && procedures.size() > 0) {
            List<ListOfValue> procLovs = this.listOfValueRepository.findByLovType("PROCEDURE_DEF");
            procedures = procedures.stream().map(p -> {
                for (ListOfValue lov : procLovs) {
                    if (lov.getLovID() == p.getLovTypeLovID()) {
                        p.setLovTypeVal(lov.getLovVal());
                        break;
                    }
                }
                return p;
            }).collect(Collectors.toList());
            modelAttributes.put("procedures", procedures);
        }

        List<Prescription> prescriptions = this.prescriptionRepository.findByRequestID(requestID);
        if (prescriptions != null && prescriptions.size() > 0) {
            modelAttributes.put("prescriptions", prescriptions);
        }
        List<Vital> reqvitals = this.vitalRepository.findByRequestID(requestID);
        List<Vital> vitals = this.vitalRepository.findByAttendanceID(request.getAttendanceID());
        if (reqvitals != null && reqvitals.size() > 0) {
            modelAttributes.put("vitals", vitals);
        }

        List<Observation> observations = this.observationRepository.findByAttendanceID(request.getAttendanceID());
        List<Observation> reqobs = this.observationRepository.findByRequestID(requestID);
        if (reqobs != null && reqobs.size() > 0) {
            modelAttributes.put("observations", observations);
        }

        List<Diagnosis> rqdx = this.diagnosisRepository.findByRequestID(requestID);
        List<Diagnosis> diagnosises = this.diagnosisRepository.findByAttendanceID(request.getAttendanceID());

        if (rqdx != null && rqdx.size() > 0) {
            modelAttributes.put("diagnosises", diagnosises);
        }

        modelAttributes = this.getDiagnosises(requestID, modelAttributes);

        return modelAttributes;
    }

    public Map<String, Object> getDiagnosises(int requestID, Map<String, Object> modelAttributes) {
        List<Diagnosis> reqdiags = this.diagnosisRepository.findByRequestID(requestID);
        if (reqdiags != null) {
            modelAttributes.put("diagnosises", reqdiags);
        }
        return modelAttributes;
    }

    private <E> List<E> toList(Iterable<E> i) {
        List<E> list = new ArrayList<>();
        i.forEach(list::add);
        return list;
    }

    public void saveRequest(Request request) {
        RequestEntity requestEntity = this.requestRepository.save(this.convert(request));
        request.setRowID(requestEntity.getRowID());
    }

    public Request convert(RequestEntity requestEntity) {
        Request request = new Request();
        RecordService.convert(requestEntity, request);
        request.setAmountCharged(requestEntity.getAmountCharged());
        request.setApprovalID(requestEntity.getApprovalID());
        request.setApprovalStatus(requestEntity.getApprovalStatus());
        request.setChargeable(requestEntity.isChargeable());
        request.setAttendanceID(requestEntity.getAttendanceID());
        request.setDescription(requestEntity.getDescription());
        request.setFulfilled(requestEntity.isFulfilled());
        request.setFulfilmentID(requestEntity.getFulfilmentID());
        request.setNote(requestEntity.getNote());
        request.setPaid(requestEntity.isPaid());
        request.setPatientID(requestEntity.getPatientID());
        request.setRequestSubType(requestEntity.getRequestSubType());
        request.setRequestType(requestEntity.getRequestType());
        return request;
    }

    public RequestEntity convert(Request request) {
        RequestEntity requestEntity = new RequestEntity();
        RecordService.convert(request, requestEntity);
        requestEntity.setAmountCharged(request.getAmountCharged());
        requestEntity.setApprovalID(request.getApprovalID());
        requestEntity.setApprovalStatus(request.getApprovalStatus());
        requestEntity.setChargeable(request.isChargeable());
        requestEntity.setAttendanceID(request.getAttendanceID());
        requestEntity.setDescription(request.getDescription());
        requestEntity.setFulfilled(request.isFulfilled());
        requestEntity.setFulfilmentID(request.getFulfilmentID());
        requestEntity.setNote(request.getNote());
        requestEntity.setPaid(request.isPaid());
        requestEntity.setPatientID(request.getPatientID());
        requestEntity.setRequestSubType(request.getRequestSubType());
        requestEntity.setRequestType(request.getRequestType());
        return requestEntity;
    }

    public Vital getVitalByrowID(int rowID) {
        return this.vitalRepository.findOne(rowID);
    }

    public Observation getObservationByrowID(int rowID) {
        return this.observationRepository.findOne(rowID);
    }

    public void saveVital(VitalForm vitalForm, UserPrincipal user) {

        Request request = new Request();
        request.setAttendanceID(vitalForm.getAttendanceID());
        request.setNote(vitalForm.getVital().getNote());
        request.setPaid(true);
        request.setRecordStatus(RecordStatus.ACTIVE);
        int requestType = this.listOfValueRepository.findByLovTypeAndLovName("REQUEST", "Vitals")
                .stream()
                .findFirst()
                .map(e -> e.getLovID())
                .get();
        request.setRequestType(requestType);
        request.setCreated(LocalDateTime.now());
        request.setUpdated(LocalDateTime.now());
        request.setCreatedBy(user.getRowID());
        request.setUpdatedBy(user.getRowID());
        request.setChargeable(false);
        this.saveRequest(request);
        this.attendanceService.updateAttendance(request.getAttendanceID(), AttendanceStatus.Active);
        Vital vital = vitalForm.getVital();
        vital.setAttendanceID(request.getAttendanceID());
        vital.setRequestID(request.getRowID());
        vital.setRecordStatus(RecordStatus.ACTIVE);
        vital.setCreated(Timestamp.valueOf(LocalDateTime.now()));
        vital.setUpdated(Timestamp.valueOf(LocalDateTime.now()));
        vital.setCreatedBy(user.getRowID());
        vital.setUpdatedBy(user.getRowID());
        this.vitalRepository.save(vital);
    }

    public List<ICD> getMatchICDs(String description) {
        return this.icdRepository.findFirst200ByShortDescriptionContainingIgnoreCase(description);
    }

    public List<DrugDef> getMatchDrugs(String searchString) {
        return this.drugDefRepository.findFirst200ByDrugNameContainingIgnoreCase(searchString);
    }

    public void createDiagnosis(Diagnosis diagnosis, UserPrincipal user) {

        diagnosis.setCreated(Timestamp.valueOf(LocalDateTime.now()));
        diagnosis.setUpdated(Timestamp.valueOf(LocalDateTime.now()));
        diagnosis.setCreatedBy(user.getRowID());
        diagnosis.setUpdatedBy(user.getRowID());
        diagnosis.setRecordStatus(RecordStatus.ACTIVE);
        this.diagnosisRepository.save(diagnosis);
    }

    public void saveDiagnosis(DiagnosisForm diagnosisForm, UserPrincipal user) {
        Request request = new Request();
        request.setAttendanceID(diagnosisForm.getAttendanceID());

        request.setNote(diagnosisForm.getDiagnosisList().stream().map(d -> d.getDescription()).collect(Collectors.joining()));

        if (request.getNote().length() > 200) {
            request.setNote(request.getNote().substring(0, 194) + " ...");
        }

        request.setPaid(true);
        request.setRecordStatus(RecordStatus.ACTIVE);
        int requestType = this.listOfValueRepository.findByLovTypeAndLovName("REQUEST", "Diagnosis")
                .stream()
                .findFirst()
                .map(e -> e.getLovID())
                .get();
        request.setRequestType(requestType);
        request.setCreated(LocalDateTime.now());
        request.setUpdated(LocalDateTime.now());
        request.setCreatedBy(user.getRowID());
        request.setUpdatedBy(user.getRowID());
        request.setChargeable(false);
        this.saveRequest(request);
this.attendanceService.updateAttendance(request.getAttendanceID(), AttendanceStatus.Active);
        diagnosisForm.getDiagnosisList().stream().forEach(d -> {
            d.setAttendanceID(diagnosisForm.getAttendanceID());
            d.setRequestID(request.getRowID());
            createDiagnosis(d, user);
        });
    }

    public LabTestForm prepareLabTestForm(int attendanceID, int requestID) {
        LabTestForm labTestForm = new LabTestForm();
        labTestForm.setAttendanceID(attendanceID);
        labTestForm.setRequestID(requestID);
        List<LabTest> labTestList = null;
        labTestForm.setLabTestLOVs(this.listOfValueRepository.findByLovType("LAB_TEST"));
        if (requestID > 0) {
            labTestList = this.labTestRepository.findByRequestID(requestID);
            Request request = this.getRequest(requestID);
            if (request != null) {
                labTestForm.setNote(request.getNote());
            }
        }
        if (labTestList == null) {
            labTestList = new ArrayList<>();
        } else {
            if (labTestForm.getSelectedLovs() != null) {
                labTestList.stream().forEach(p -> labTestForm.getSelectedLovs().add(p.getLabTypeLovID()));
            }
            List<ListOfValue> labtestLovs = this.listOfValueRepository.findByLovType("LAB_TEST");
            labTestList = labTestList.stream().map(p -> {
                for (ListOfValue lov : labtestLovs) {
                    if (lov.getLovID() == p.getLabTypeLovID()) {
                        p.setLabTypeLovVal(lov.getLovVal());
                        break;
                    }
                }
                return p;
            }).collect(Collectors.toList());

        }

        labTestForm.setLabTestList(labTestList);

        return labTestForm;
    }

    /**
     * public void saveDiagnosis(DiagnosisForm diagnosisForm, UserPrincipal
     * user) { Request request = new Request();
     * request.setAttendanceID(diagnosisForm.getAttendanceID());
     *
     * request.setNote(diagnosisForm.getDiagnosisList().stream().map(d ->
     * d.getDescription()).collect(Collectors.joining()));
     *
     * if (request.getNote().length() > 200) {
     * request.setNote(request.getNote().substring(0, 194) + " ..."); }
     *
     * request.setPaid(true); request.setRecordStatus(RecordStatus.ACTIVE); int
     * requestType =
     * this.listOfValueRepository.findByLovTypeAndLovName("REQUEST",
     * "Diagnosis") .stream() .findFirst() .map(e -> e.getLovID()) .get();
     * request.setRequestType(requestType);
     * request.setCreated(LocalDateTime.now());
     * request.setUpdated(LocalDateTime.now());
     * request.setCreatedBy(user.getRowID());
     * request.setUpdatedBy(user.getRowID()); request.setChargeable(false);
     * this.saveRequest(request);
     *
     * diagnosisForm.getDiagnosisList().stream().forEach(d -> createDiagnosis(d,
     * user)); }*
     */
    public List<LabTest> convertToLabTests(List<Integer> selectedLovs, List<LabTest> tests) {
        if (tests == null) {
            tests = new ArrayList<>();
        }
        List<ListOfValue> lovs = this.listOfValueRepository.findByLovType("LAB_TEST");

        List<Integer> oldLovs = tests.stream().map(p -> p.getLabTypeLovID()).collect(Collectors.toList());

        List<Integer> newLovs = selectedLovs.stream().filter(p -> !oldLovs.contains(p)).collect(Collectors.toList());

        List<LabTest> newLabs = newLovs.stream().map(p -> {
            LabTest lab = new LabTest();
            lab.setLabTypeLovID(p);
            return lab;
        }).collect(Collectors.toList());

        tests.addAll(newLabs);

        return tests;
    }

    public void saveLabTestFulfil(LabTestForm labTestForm, UserPrincipal user) {
        List<LabTest> labTests = this.labTestRepository.findByRequestID(labTestForm.getRequestID());
        labTests = labTests.stream().map((LabTest lb) -> {
            labTestForm.getLabTestList().stream().filter((lt) -> (lt.getRowID() == lb.getRowID())).forEach((lt) -> {
                lb.setResultNote(lt.getResultNote());
                lb.setResultText(lt.getResultText());

                lb.setUpdated(Timestamp.from(Instant.now()));
                lb.setUpdatedBy(user.getRowID());

            });
            return lb;
        }).collect(Collectors.toList());

        labTests.forEach(labtest -> {
            this.labTestRepository.save(labtest);
        });

        Request request = this.getRequest(labTestForm.getRequestID());
        request.setFulfilled(true);
        this.saveRequest(request);
        //this.requestRepository.save(request);
    }

    public void saveLabTest(LabTestForm labTestForm, UserPrincipal user) {
        Request request = null;
        if (labTestForm.getRequestID() > 0) {
            request = getRequest(labTestForm.getRequestID());
        } else {
            request = new Request();
        }
        List<LabTest> oldTests = this.labTestRepository.findByRequestID(labTestForm.getRequestID());

        List<LabTest> labTests = convertToLabTests(labTestForm.getSelectedLovs(), oldTests);

        List<ListOfValue> testlovs = this.listOfValueRepository.findByLovType("LAB_TEST");

        for (int i = 0; i < testlovs.size(); i++) {
            int labRowID = testlovs.get(i).getRowID();

            for (LabTest lb : labTests) {
                if (lb.getLabTypeLovID() == testlovs.get(i).getLovID()) {
                    lb.setNote(labTestForm.getLabNotes().get(i));
                    continue;
                }
            }
        }

        List<ICharges> icharge = new ArrayList<>();
        icharge.addAll(labTests);

        this.chargeService.addCharges(icharge, labTestForm.getAttendanceID(), "LAB_TEST");
        System.out.println("Attendance ID is: " + labTestForm.getAttendanceID());

        request.setAttendanceID(labTestForm.getAttendanceID());

        request.setNote(labTestForm.getNote() != null ? labTestForm.getNote() : "");
        request.setDescription(labTestForm.getSelectedLovs().size() + " tests requested.");

        if (request.getNote().length() > 200) {
            request.setNote(request.getNote().substring(0, 194) + " ...");
        }

        request.setPaid(false);
        request.setRecordStatus(RecordStatus.ACTIVE);
        int requestType = this.listOfValueRepository.findByLovTypeAndLovName("REQUEST", "Lab Investigation")
                .stream()
                .findFirst()
                .map(e -> e.getLovID())
                .get();
        request.setRequestType(requestType);
        request.setCreated(LocalDateTime.now());
        request.setUpdated(LocalDateTime.now());
        request.setCreatedBy(user.getRowID());
        request.setUpdatedBy(user.getRowID());
        request.setChargeable(true);
        request.setAmountCharged(labTests.stream().map(e -> e.getChargeAmount()).reduce(Double.valueOf(0), Double::sum));
        this.saveRequest(request);
this.attendanceService.updateAttendance(request.getAttendanceID(), AttendanceStatus.Active);
        for (LabTest labTest : labTests) {
            labTest.setRequestID(request.getRowID());
            labTest.setAttendanceID(request.getAttendanceID());
            createLabTest(labTest, user);
        }
    }

    public void createLabTest(LabTest labTest, UserPrincipal user) {
        if (labTest.getRowID() > 0) {
            labTest.setUpdated(Timestamp.valueOf(LocalDateTime.now()));
            labTest.setUpdatedBy(user.getRowID());
        } else {
            labTest.setCreated(Timestamp.valueOf(LocalDateTime.now()));
            labTest.setUpdated(Timestamp.valueOf(LocalDateTime.now()));
            labTest.setCreatedBy(user.getRowID());
            labTest.setUpdatedBy(user.getRowID());
            labTest.setRecordStatus(RecordStatus.ACTIVE);
        }
        this.labTestRepository.save(labTest);
    }

    public ProcedureForm prepareProcedureForm(int attendanceID, int requestID) {
        ProcedureForm procedureForm = new ProcedureForm();
        procedureForm.setAttendanceID(attendanceID);
        procedureForm.setRequestID(requestID);
        List<Procedure> procedures = null;
        procedureForm.setProcedureDefLOVs(this.listOfValueRepository.findByLovType("PROCEDURE_DEF"));
        if (requestID > 0) {
            procedures = this.procedureRepository.findByRequestID(requestID);
            if (procedures != null && procedures.size() > 0) {
                List<ListOfValue> procLovs = this.listOfValueRepository.findByLovType("PROCEDURE_DEF");
                procedures = procedures.stream().map(p -> {
                    for (ListOfValue lov : procLovs) {
                        if (lov.getLovID() == p.getLovTypeLovID()) {
                            p.setLovTypeVal(lov.getLovVal());
                            break;
                        }
                    }
                    return p;
                }).collect(Collectors.toList());

            }
            Request request = this.getRequest(requestID);
            if (request != null) {
                procedureForm.setNote(request.getNote());
            }
        } else {
            if (procedures == null) {
                procedures = new ArrayList<>();
            } else {
                procedures.stream().forEach(p -> procedureForm.getSelectedLovs().add(p.getLovTypeLovID()));
            }
        }
        procedureForm.setProcedureList(procedures);

        return procedureForm;
    }

    public void saveProcedure(ProcedureForm procedureForm, UserPrincipal user) {
        Request request = null;
        if (procedureForm.getRequestID() > 0) {
            request = getRequest(procedureForm.getRequestID());
        } else {
            request = new Request();
        }
        List<Procedure> oldProcedures = this.procedureRepository.findByRequestID(procedureForm.getRequestID());

        List<Procedure> procedures = convertToProcedures(procedureForm.getSelectedLovs(), oldProcedures);

        List<ListOfValue> procLovs = this.listOfValueRepository.findByLovType("PROCEDURE_DEF");

        for (int i = 0; i < procLovs.size(); i++) {
            int labRowID = procLovs.get(i).getRowID();

            for (Procedure lb : procedures) {
                if (lb.getLovTypeLovID() == procLovs.get(i).getLovID()) {
                    lb.setNote(procedureForm.getProcNotes().get(i));
                    continue;
                }
            }
        }

        List<ICharges> icharge = new ArrayList<>();
        icharge.addAll(procedures);

        this.chargeService.addCharges(icharge, procedureForm.getAttendanceID(), "PROCEDURE_DEF");
        System.out.println("Attendance ID is: " + procedureForm.getAttendanceID());

        request.setAttendanceID(procedureForm.getAttendanceID());

        request.setNote(procedureForm.getNote() != null ? procedureForm.getNote() : "");
        request.setDescription(procedureForm.getSelectedLovs().size() + " tests requested.");

        if (request.getNote().length() > 200) {
            request.setNote(request.getNote().substring(0, 194) + " ...");
        }

        request.setPaid(false);
        request.setRecordStatus(RecordStatus.ACTIVE);
        int requestType = this.listOfValueRepository.findByLovTypeAndLovName("REQUEST", "Procedure")
                .stream()
                .findFirst()
                .map(e -> e.getLovID())
                .get();
        request.setRequestType(requestType);
        request.setCreated(LocalDateTime.now());
        request.setUpdated(LocalDateTime.now());
        request.setCreatedBy(user.getRowID());
        request.setUpdatedBy(user.getRowID());
        request.setChargeable(true);
        request.setAmountCharged(procedures.stream().map(e -> e.getChargeAmount()).reduce(Double.valueOf(0), Double::sum));
        this.saveRequest(request);
this.attendanceService.updateAttendance(request.getAttendanceID(), AttendanceStatus.Active);
        for (Procedure procedure : procedures) {
            procedure.setRequestID(request.getRowID());
            procedure.setAttendanceID(request.getAttendanceID());
            this.createProcedure(procedure, user);
        }
    }

    public void saveFulfilProcedure(ProcedureForm procedureForm, UserPrincipal user) {
        Request request = getRequest(procedureForm.getRequestID());
        List<Procedure> procedures = this.procedureRepository.findByRequestID(procedureForm.getRequestID());

        procedures = procedures.stream().map((Procedure p) -> {
            procedureForm.getProcedureList().stream().filter(op -> op.getRowID() == p.getRowID()).forEach(op -> {
                p.setResultText(op.getResultText());
                p.setResultNote(op.getResultNote());
                p.setUpdated(Timestamp.from(Instant.now()));
                p.setUpdatedBy(user.getRowID());
            });
            return p;
        }).collect(Collectors.toList());

        procedures.forEach(p -> {
            this.procedureRepository.save(p);
        });

        request.setFulfilled(true);
        this.saveRequest(request);
        this.attendanceService.updateAttendance(request.getAttendanceID(), AttendanceStatus.Active);
    }

    public List<Procedure> convertToProcedures(List<Integer> selectedLovs, List<Procedure> procedures) {
        if (procedures == null) {
            procedures = new ArrayList<>();
        }
        List<ListOfValue> lovs = this.listOfValueRepository.findByLovType("PROCEDURE_DEF");

        List<Integer> oldLovs = procedures.stream().map(p -> p.getLovTypeLovID()).collect(Collectors.toList());

        List<Integer> newLovs = selectedLovs.stream().filter(p -> !oldLovs.contains(p)).collect(Collectors.toList());

        List<Procedure> newProcs = newLovs.stream().map(p -> {
            Procedure proc = new Procedure();
            proc.setLovTypeLovID(p);
            return proc;
        }).collect(Collectors.toList());

        procedures.addAll(newProcs);

        return procedures;
    }

    public void createProcedure(Procedure procedure, UserPrincipal user) {
        if (procedure.getRowID() > 0) {
            procedure.setUpdated(Timestamp.valueOf(LocalDateTime.now()));
            procedure.setUpdatedBy(user.getRowID());
        } else {
            procedure.setCreated(Timestamp.valueOf(LocalDateTime.now()));
            procedure.setUpdated(Timestamp.valueOf(LocalDateTime.now()));
            procedure.setCreatedBy(user.getRowID());
            procedure.setUpdatedBy(user.getRowID());
            procedure.setRecordStatus(RecordStatus.ACTIVE);
        }
        this.procedureRepository.save(procedure);
    }

    public PrescriptionForm preparePrescriptionForm(int attendanceID, int requestID) {
        PrescriptionForm prescriptionForm = new PrescriptionForm();
        prescriptionForm.setAttendanceID(attendanceID);
        prescriptionForm.setRequestID(requestID);

        prescriptionForm.setPrescriptionList(this.prescriptionRepository.findByRequestID(requestID));

        return prescriptionForm;
    }

    public void savePrescriptionChanges(PrescriptionForm prescriptionForm, UserPrincipal user) {
        Request request = getRequest(prescriptionForm.getRequestID());
        List<Prescription> prescriptions = this.prescriptionRepository.findByRequestID(prescriptionForm.getRequestID());

        List<ICharges> icharge = new ArrayList<>();
        icharge.addAll(prescriptions);

        this.chargeService.addCharges(icharge, prescriptionForm.getAttendanceID(), "PRESCRIPTION");

        prescriptions = prescriptions.stream().map((Prescription p) -> {
            prescriptionForm.getPrescriptionList().stream().filter(up -> up.getRowID() == p.getRowID()).forEach(up -> {
                p.setNote(up.getNote());
                p.setQuantity(up.getQuantity());
                p.setDose(up.getDose());
                p.setUpdated(Timestamp.from(Instant.now()));
                p.setUpdatedBy(user.getRowID());
                p.setChargeAmount(p.getQuantity() * p.getChargeAmount());
            });
            return p;
        }).collect(Collectors.toList());

        prescriptions.forEach(p -> {
            this.prescriptionRepository.save(p);
        });
        request.setUpdated(Timestamp.from(Instant.now()));
        request.setUpdatedBy(user.getRowID());

        request.setAmountCharged(prescriptions.stream().mapToDouble(p -> p.getChargeAmount()).sum());

        this.saveRequest(request);

    }

    public void savePrescriptionDispense(PrescriptionForm prescriptionForm, UserPrincipal user) {
        Request request = getRequest(prescriptionForm.getRequestID());
        List<Prescription> prescriptions = this.prescriptionRepository.findByRequestID(prescriptionForm.getRequestID());

        prescriptions = prescriptions.stream().map((Prescription p) -> {
            prescriptionForm.getPrescriptionList().stream().filter(up -> up.getRowID() == p.getRowID()).forEach(up -> {
                p.setDispensed(up.isDispensed());
                p.setDispenseNote(up.getDispenseNote());
                p.setQuantitydispensed(up.getQuantitydispensed());
                p.setDispenseddate(Timestamp.from(Instant.now()));
                p.setDispensedby(user.getRowID());
            });
            return p;
        }).collect(Collectors.toList());

        prescriptions.forEach(p -> {
            this.prescriptionRepository.save(p);
        });
        request.setUpdated(Timestamp.from(Instant.now()));
        request.setUpdatedBy(user.getRowID());

        request.setFulfilled(true);

        this.saveRequest(request);

    }

    public void savePrescription(PrescriptionForm prescriptionForm, UserPrincipal user) {
        Request request = null;
        if (prescriptionForm.getRequestID() > 0) {
            request = getRequest(prescriptionForm.getRequestID());
        } else {
            request = new Request();
        }

        List<Prescription> oldPrescriptions = this.prescriptionRepository.findByRequestID(prescriptionForm.getRequestID());

        List<ICharges> icharge = new ArrayList<>();
        icharge.addAll(prescriptionForm.getPrescriptionList());

        this.chargeService.addCharges(icharge, prescriptionForm.getAttendanceID(), "PRESCRIPTION");
        request.setAttendanceID(prescriptionForm.getAttendanceID());
        request.setPaid(false);
        request.setRecordStatus(RecordStatus.ACTIVE);
        int requestType = this.listOfValueRepository.findByLovTypeAndLovName("REQUEST", "Prescription")
                .stream()
                .findFirst()
                .map(e -> e.getLovID())
                .get();
        request.setRequestType(requestType);
        request.setCreated(LocalDateTime.now());
        request.setUpdated(LocalDateTime.now());
        request.setCreatedBy(user.getRowID());
        request.setUpdatedBy(user.getRowID());
        request.setAmountCharged(prescriptionForm.getPrescriptionList().stream().map(e -> e.getChargeAmount()).reduce(Double.valueOf(0), Double::sum));
        if (request.getAmountCharged() > 0) {
            request.setAmountCharged(100000);
        }
        request.setChargeable(true);
        this.saveRequest(request);
this.attendanceService.updateAttendance(request.getAttendanceID(), AttendanceStatus.Active);
        for (Prescription prescription : prescriptionForm.getPrescriptionList()) {
            prescription.setRequestID(request.getRowID());
            prescription.setAttendanceID(request.getAttendanceID());
            this.createPrescription(prescription, user);
        }
    }

    public void createPrescription(Prescription prescription, UserPrincipal user) {
        if (prescription.getRowID() > 0) {
            prescription.setUpdated(Timestamp.valueOf(LocalDateTime.now()));
            prescription.setUpdatedBy(user.getRowID());
        } else {
            prescription.setCreated(Timestamp.valueOf(LocalDateTime.now()));
            prescription.setUpdated(Timestamp.valueOf(LocalDateTime.now()));
            prescription.setCreatedBy(user.getRowID());
            prescription.setUpdatedBy(user.getRowID());
            prescription.setRecordStatus(RecordStatus.ACTIVE);
        }
        this.prescriptionRepository.save(prescription);
    }

    public RadiologyForm prepareRadiologyForm(int attendanceID, int requestID) {
        RadiologyForm radiologyForm = new RadiologyForm();
        radiologyForm.setAttendanceID(attendanceID);
        radiologyForm.setRequestID(requestID);
        List<Radiology> radiologyList = null;
        radiologyForm.setRadiologyDefLOVs(this.listOfValueRepository.findByLovType("RADIOLOGY_DEF"));
        if (requestID > 0) {
            radiologyList = this.radiologyRepository.findByRequestID(requestID);

            if (radiologyList != null && radiologyList.size() > 0) {
                List<ListOfValue> radLovs = this.listOfValueRepository.findByLovType("RADIOLOGY_DEF");
                radiologyList = radiologyList.stream().map(p -> {
                    for (ListOfValue lov : radLovs) {
                        if (lov.getLovID() == p.getLovTypeLovID()) {
                            p.setLovTypeVal(lov.getLovVal());
                            break;
                        }
                    }
                    return p;
                }).collect(Collectors.toList());
            }
            Request request = this.getRequest(requestID);
            if (request != null) {
                radiologyForm.setHistory(request.getNote());
                radiologyForm.setLmp(request.getDescription());
            }
        }

        if (radiologyList == null) {
            radiologyList = new ArrayList<>();
        }

        radiologyForm.setRadiologyList(radiologyList);

        return radiologyForm;
    }

    public void saveRadiology(RadiologyForm radiologyForm, UserPrincipal user) {
        Request request = null;
        if (radiologyForm.getRequestID() > 0) {
            request = getRequest(radiologyForm.getRequestID());
            request.setUpdated(LocalDateTime.now());
            request.setUpdatedBy(user.getRowID());
        } else {
            request = new Request();

            List<ICharges> icharge = new ArrayList<>();
            icharge.addAll(radiologyForm.getRadiologyList());

            this.chargeService.addCharges(icharge, radiologyForm.getAttendanceID(), "RADIOLOGY");
            request.setAttendanceID(radiologyForm.getAttendanceID());
            request.setPaid(false);
            request.setRecordStatus(RecordStatus.ACTIVE);
            int requestType = this.listOfValueRepository.findByLovTypeAndLovName("REQUEST", "Radiology")
                    .stream()
                    .findFirst()
                    .map(e -> e.getLovID())
                    .get();
            request.setRequestType(requestType);
            request.setCreated(LocalDateTime.now());
            request.setUpdated(LocalDateTime.now());
            request.setCreatedBy(user.getRowID());
            request.setUpdatedBy(user.getRowID());
            request.setAmountCharged(radiologyForm.getRadiologyList().stream().map(e -> e.getChargeAmount()).reduce(Double.valueOf(0), Double::sum));
            request.setChargeable(true);
        }
        this.saveRequest(request);
        this.attendanceService.updateAttendance(request.getAttendanceID(), AttendanceStatus.Active);
        for (Radiology radiology : radiologyForm.getRadiologyList()) {
            radiology.setRequestID(request.getRowID());
            radiology.setAttendanceID(request.getAttendanceID());
            this.createRadiology(radiology, user);
        }
    }

    public void saveFulfilRadiology(RadiologyForm radiologyForm, UserPrincipal user) {
        Request request = getRequest(radiologyForm.getRequestID());
        request.setUpdated(LocalDateTime.now());
        request.setUpdatedBy(user.getRowID());
        request.setFulfilled(true);

        List<Radiology> radiologies = this.radiologyRepository.findByRequestID(radiologyForm.getRequestID());

        radiologies = radiologies.stream().map((Radiology p) -> {
            radiologyForm.getRadiologyList().stream().filter(up -> up.getRowID() == p.getRowID()).forEach(up -> {
                p.setResultText(up.getResultText());
                p.setResultNote(up.getResultNote());
                p.setUpdated(Timestamp.from(Instant.now()));
                p.setUpdatedBy(user.getRowID());
            });
            return p;
        }).collect(Collectors.toList());

        radiologies.forEach(p -> {
            this.radiologyRepository.save(p);
        });

        this.saveRequest(request);

    }

    public void createRadiology(Radiology radiology, UserPrincipal user) {
        if (radiology.getRowID() > 0) {
            radiology.setUpdated(Timestamp.valueOf(LocalDateTime.now()));
            radiology.setUpdatedBy(user.getRowID());
        } else {
            radiology.setCreated(Timestamp.valueOf(LocalDateTime.now()));
            radiology.setUpdated(Timestamp.valueOf(LocalDateTime.now()));
            radiology.setCreatedBy(user.getRowID());
            radiology.setUpdatedBy(user.getRowID());
            radiology.setRecordStatus(RecordStatus.ACTIVE);
        }
        this.radiologyRepository.save(radiology);
    }

    public PaymentForm preparePaymentForm(int attendanceID, int requestID) {
        PaymentForm paymentForm = new PaymentForm();
        paymentForm.setPayment(new Payment());
        paymentForm.setAttendanceID(attendanceID);
        paymentForm.setRequestID(requestID);

        Request request = this.getRequest(requestID);
        System.out.println("Request ID: " + requestID);
        paymentForm.setTotalCharges(attendanceID);

        List<Payment> requestPayments = this.paymentRepository.findByRequestID(paymentForm.getRequestID());
        double totalPayments = requestPayments.stream().map(p -> p.getPaidAmount()).reduce(Double.valueOf(0), Double::sum);

        paymentForm.getPayment().setPaidAmount(request.getAmountCharged() - totalPayments);
        paymentForm.getPayment().setCreditDueDate(Date.valueOf(LocalDate.now()));

        Attendance attendance = this.attendanceService.getAttendance(attendanceID);
        PaymentMode paymentMode = this.attendanceService.getPaymentMode(attendance.getPaymentTypeID());

        paymentForm.setPaymentType(paymentMode.getName());

        paymentForm.setPaymentLovTypeLOVs(this.listOfValueRepository.findByLovTypeOrderByLovVal("PAYMENT_METHOD"));

        if (paymentMode.getName().contains("Insurance")) {
            paymentForm.getPayment().setPaymentLovTypeID(3);
        } else {
            paymentForm.getPayment().setPaymentLovTypeID(1);
        }

        return paymentForm;
    }

    public void savePayment(PaymentForm paymentForm, UserPrincipal user) {
        Request request = getRequest(paymentForm.getRequestID());
        Payment payment = paymentForm.getPayment();

        if (payment.getRowID() > 0) {
            payment.setUpdated(Timestamp.valueOf(LocalDateTime.now()));
            payment.setUpdatedBy(user.getRowID());
        } else {
            payment.setRequestID(paymentForm.getRequestID());
            payment.setAttendanceID(paymentForm.getAttendanceID());
            payment.setCreated(Timestamp.valueOf(LocalDateTime.now()));
            payment.setUpdated(Timestamp.valueOf(LocalDateTime.now()));
            payment.setCreatedBy(user.getRowID());
            payment.setUpdatedBy(user.getRowID());
            payment.setRecordStatus(RecordStatus.ACTIVE);
        }
        this.paymentRepository.save(payment);

        List<Payment> requestPayments = this.paymentRepository.findByRequestID(paymentForm.getRequestID());
        double totalPayments = requestPayments.stream().map(p -> p.getPaidAmount()).reduce(Double.valueOf(0), Double::sum);

        if (request.getAmountCharged() <= totalPayments) {
            request.setPaid(true);
            this.saveRequest(request);
        }
        this.attendanceService.updateAttendance(request.getAttendanceID(), AttendanceStatus.Active);

    }

    public DischargeForm prepareDischargeForm(int requestID, int attendanceID, FormAction formAction) {
        DischargeForm dischargeForm = new DischargeForm();

        dischargeForm.setAttendanceID(attendanceID);
        dischargeForm.setRequestID(requestID);

        dischargeForm.setDischargeLOVs(this.listOfValueRepository.findByLovTypeOrderByLovVal("DISCHARGE"));

        if (requestID > 0 && formAction == FormAction.EDIT) {
            RequestEntity request = this.requestRepository.findOne(requestID);
            dischargeForm.setLovTypeLovID(request.getRequestSubType());
            dischargeForm.setNote(request.getNote());
        }

        return dischargeForm;
    }

    public void saveDischargeForm(DischargeForm dischargeForm, UserPrincipal user) {
        int requestID = dischargeForm.getRequestID();
        RequestEntity entity = new RequestEntity();
        if (requestID > 0) {
            entity = this.requestRepository.findOne(requestID);

        } else {
            entity.setAttendanceID(dischargeForm.getAttendanceID());
            entity.setCreated(Timestamp.valueOf(LocalDateTime.now()));
            entity.setCreatedBy(user.getRowID());
            int requestType = this.listOfValueRepository.findByLovTypeAndLovName("REQUEST", "Discharge")
                    .stream()
                    .findFirst()
                    .map(e -> e.getLovID())
                    .get();
            entity.setRequestType(requestType);
        }
        entity.setRecordStatus(RecordStatus.ACTIVE);
        entity.setDescription(dischargeForm.getDischargeLOVs().stream().filter(p -> p.getLovID() == dischargeForm.getLovTypeLovID()).findFirst()
                .map(p -> p.getLovVal()).orElse(""));
        entity.setNote(dischargeForm.getNote());
        entity.setUpdated(Timestamp.valueOf(LocalDateTime.now()));
        entity.setUpdatedBy(user.getRowID());
        this.requestRepository.save(entity);
        this.attendanceService.updateAttendance(entity.getAttendanceID(), AttendanceStatus.Discharged);

    }
}
