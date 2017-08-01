package co.ke.sart.site.service;

import co.ke.sart.site.entity.Charge;
import co.ke.sart.site.entity.LabTest;
import co.ke.sart.site.entity.ListOfValue;
import co.ke.sart.site.entity.Payment;
import co.ke.sart.site.entity.Prescription;
import co.ke.sart.site.entity.PrintInfo;
import co.ke.sart.site.entity.Procedure;
import co.ke.sart.site.entity.Radiology;
import co.ke.sart.site.entity.RequestEntity;
import co.ke.sart.site.entity.UserPrincipal;
import co.ke.sart.site.model.Attendance;
import co.ke.sart.site.model.Patient;
import co.ke.sart.site.repository.AttendanceRepository;
import co.ke.sart.site.repository.ChargeRepository;
import co.ke.sart.site.repository.LabTestRepository;
import co.ke.sart.site.repository.ListOfValueRepository;
import co.ke.sart.site.repository.PatientRepository;
import co.ke.sart.site.repository.PaymentRepository;
import co.ke.sart.site.repository.PrescriptionRepository;
import co.ke.sart.site.repository.PrintInfoRepository;
import co.ke.sart.site.repository.ProcedureRepository;
import co.ke.sart.site.repository.RadiologyRepository;
import co.ke.sart.site.repository.RequestRepository;
import co.ke.sart.site.repository.UserRepository;
import co.ke.sart.site.utils.PrintType;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrintService {

    @Autowired
    PrintInfoRepository printInfoRepository;

    @Autowired
    AttendanceService attendanceService;

    @Autowired
    PatientService patientService;

    @Autowired
    LabTestRepository labTestRepository;

    @Autowired
    RequestService requestService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RadiologyRepository radiologyRepository;

    @Autowired
    PrescriptionRepository prescriptionRepository;

    @Autowired
    ListOfValueRepository listOfValueRepository;

    @Autowired
    ProcedureRepository procedureRepository;

    @Autowired
    RequestRepository requestRepository;
    
    @Autowired
    PaymentRepository paymentRepository;

    public Map<String, Object> prepareInvoiceHeader(int attendanceID, UserPrincipal user, PrintType printType) {
        Map<String, Object> modelMap = new TreeMap<>();
        Attendance attendance = this.attendanceService.getAttendance(attendanceID);
        modelMap.put("attendance", attendance);

        Patient patient = this.patientService.getPatient(attendance.getPatientID());
        modelMap.put("patient", patient);

        PrintInfo printInfo;
        List<PrintInfo> printInfoList = this.printInfoRepository.findByAttendanceIDAndPrintType(attendanceID, printType);
        if (printInfoList.size() > 0) {
            printInfo = printInfoList.get(0);
        } else {
            printInfo = new PrintInfo();
            printInfo.setAttendanceID(attendanceID);
            printInfo.setPrintType(printType);
            printInfo.setCreated(Timestamp.valueOf(LocalDateTime.now()));
            printInfo.setCreatedBy(user.getRowID());
            printInfo.setUpdated(Timestamp.valueOf(LocalDateTime.now()));
            printInfo.setUpdatedBy(user.getRowID());
            this.printInfoRepository.save(printInfo);
            printInfo.setSerialNumber(String.format("%d/%d", printInfo.getRowID(), LocalDate.now().getYear()));
            this.printInfoRepository.save(printInfo);
        }
        modelMap.put("printInfo", printInfo);

        if (printType == PrintType.Prescription || printType == PrintType.Invoice) {
            List<Prescription> prescriptions = this.prescriptionRepository.findByAttendanceID(attendanceID);

            if (prescriptions != null && !prescriptions.isEmpty()) {
                modelMap.put("prescriptions", prescriptions);
                int userID = prescriptions.get(prescriptions.size() - 1).getCreatedBy();
                UserPrincipal userP = this.userRepository.findOne(userID);
                modelMap.put("user", userP);
            }
        }

        if (printType == PrintType.Radiology || printType == PrintType.Invoice) {
            List<Radiology> radiologies = this.radiologyRepository.findByAttendanceID(attendanceID);
            if (radiologies != null && !radiologies.isEmpty()) {

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
                modelMap.put("radiologies", radiologies);
                int userID = radiologies.get(radiologies.size() - 1).getCreatedBy();
                UserPrincipal userP = this.userRepository.findOne(userID);
                modelMap.put("user", userP);
            }
        }

        if (printType == PrintType.Procedure || printType == PrintType.Invoice) {
            List<Procedure> procedures = this.procedureRepository.findByAttendanceID(attendanceID);

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
                modelMap.put("procedures", procedures);
                int userID = procedures.get(procedures.size() - 1).getCreatedBy();
                UserPrincipal userP = this.userRepository.findOne(userID);
                modelMap.put("user", userP);

            }
        }

        if (printType == PrintType.Laboratory || printType == PrintType.Invoice) {
            List<LabTest> labtests = this.labTestRepository.findByAttendanceID(attendanceID);
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
                modelMap.put("labtests", labtests);
                int userID = labtests.get(labtests.size() - 1).getCreatedBy();
                UserPrincipal userP = this.userRepository.findOne(userID);
                modelMap.put("user", userP);
            }
        }

        if (printType == PrintType.Invoice) {
            List<RequestEntity> charges = this.requestRepository.findByAttendanceIDAndRequestType(attendanceID, 15);

            if (charges != null && charges.size() > 0) {
                List<ListOfValue> labtestLovs = this.listOfValueRepository.findByLovType("CHARGE");
                charges = charges.stream().map(p -> {
                    for (ListOfValue lov : labtestLovs) {
                        if (lov.getLovID() == p.getRequestSubType()) {
                            p.setDescription(lov.getLovVal());
                            break;
                        }
                    }
                    return p;
                }).collect(Collectors.toList());
                modelMap.put("charges", charges);
            }
        }
        
        if (printType == PrintType.Invoice) {
            List<Payment> payments = this.paymentRepository.findByAttendanceID(attendanceID);

            if (payments != null && payments.size() > 0) {
                List<ListOfValue> lovs = this.listOfValueRepository.findByLovType("PAYMENT_METHOD");
                payments = payments.stream().map(p -> {
                    for (ListOfValue lov : lovs) {
                        if (lov.getLovID() == p.getPaymentLovTypeID()) {
                            p.setPaymentLovTypeVal(lov.getLovVal());
                            break;
                        }
                    }
                    return p;
                }).collect(Collectors.toList());
                modelMap.put("payments", payments);
            }
        }        

        return modelMap;
    }
}
