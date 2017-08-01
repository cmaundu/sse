/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.ke.sart.site.controller;

import co.ke.sart.config.annotation.WebController;
import co.ke.sart.site.entity.Diagnosis;
import co.ke.sart.site.entity.Prescription;
import co.ke.sart.site.entity.Radiology;
import co.ke.sart.site.entity.UserPrincipal;
import co.ke.sart.site.form.ChargeForm;
import co.ke.sart.site.form.DiagnosisForm;
import co.ke.sart.site.form.DischargeForm;
import co.ke.sart.site.form.LabTestForm;
import co.ke.sart.site.form.ObservationForm;
import co.ke.sart.site.form.PatientForm;
import co.ke.sart.site.form.PaymentForm;
import co.ke.sart.site.form.PrescriptionForm;
import co.ke.sart.site.form.ProcedureForm;
import co.ke.sart.site.form.RadiologyForm;
import co.ke.sart.site.form.VitalForm;
import co.ke.sart.site.model.Patient;
import co.ke.sart.site.model.Request;
import co.ke.sart.site.repository.VitalRepository;
import co.ke.sart.site.service.RequestService;
import co.ke.sart.site.utils.FormAction;
import co.ke.sart.site.utils.RecordStatus;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@WebController
@RequestMapping("request")
public class RequestController {

    @Autowired
    RequestService requestService;

    @RequestMapping(value = {"{attendanceID}/{requestID}/charge/add/{chargeID}"}, method = RequestMethod.GET)
    public String addCharge(Model model, @PathVariable("attendanceID") int attendanceID, @PathVariable("requestID") int requestID, @PathVariable("chargeID") int chargeID) {

        ChargeForm chargeForm = new ChargeForm();

        chargeForm.setAttendanceID(attendanceID);
        chargeForm.setRequestID(requestID);
        chargeForm.setRowID(chargeID);
        chargeForm = requestService.prepareChargeForm(chargeForm);
        model.addAttribute("chargeForm", chargeForm);


        return "request/charge/add";
    }
    
    @RequestMapping(value = {"{attendanceID}/{requestID}/charge/add", "{attendanceID}/{requestID}/charge/edit"}, method = RequestMethod.GET)
    public String editCharge(Model model, @PathVariable("attendanceID") int attendanceID, @PathVariable("requestID") int requestID) {

        ChargeForm chargeForm = new ChargeForm();
        chargeForm.setAttendanceID(attendanceID);
        chargeForm.setRequestID(requestID);
        chargeForm = requestService.prepareChargeForm(chargeForm);
        model.addAttribute("chargeForm", chargeForm);

        return "request/charge/add";
    }    

    @RequestMapping(value = {"{attendanceID}/{requestID}/charge/add"}, method = RequestMethod.POST)
    public ModelAndView saveCharge(Principal principal, @Valid @ModelAttribute("chargeForm") ChargeForm chargeForm, BindingResult result) {
        if (result.hasErrors()) {
            return new ModelAndView(chargeForm.getAttendanceID() + "/" + chargeForm.getRequestID() + "/charge/add");
        }
        this.requestService.saveCharge(chargeForm, (UserPrincipal) principal);

        return new ModelAndView("redirect:/attendance/view/" + chargeForm.getAttendanceID());

    }

    @RequestMapping(value = {"{attendanceID}/{requestID}/vital/add/{rowID}"}, method = RequestMethod.GET)
    public String addVital(Model model, @PathVariable("attendanceID") int attendanceID,
            @PathVariable("requestID") int requestID, @PathVariable("rowID") int rowID) {
        VitalForm vitalForm;
        if (rowID == 0) {
            vitalForm = new VitalForm();
        } else {
            vitalForm = new VitalForm(this.requestService.getVitalByrowID(rowID));
        }
        vitalForm.setAttendanceID(attendanceID);
        vitalForm.setRequestID(requestID);
        model.addAttribute("vitalForm", vitalForm);

        return "request/vital/add";
    }

    @RequestMapping(value = {"{attendanceID}/{requestID}/vital/add/{rowID}"}, method = RequestMethod.POST)
    public ModelAndView saveVital(Principal principal, @Valid @ModelAttribute("vitalForm") VitalForm vitalForm, BindingResult result) {
        if (result.hasErrors()) {
            return new ModelAndView(vitalForm.getAttendanceID() + "/" + vitalForm.getRequestID() + "/charge/add/" + vitalForm.getVital().getRowID());
        }
        this.requestService.saveVital(vitalForm, (UserPrincipal) principal);

        return new ModelAndView("redirect:/attendance/view/" + vitalForm.getAttendanceID());

    }

    @RequestMapping(value = {"{attendanceID}/{requestID}/observation/add", "{attendanceID}/{requestID}/observation/edit"}, method = RequestMethod.GET)
    public String addObservation(Model model,
            @PathVariable("attendanceID") int attendanceID, @PathVariable("requestID") int requestID) {
        ObservationForm observationForm = new ObservationForm();
        observationForm.setRequestID(requestID);
        observationForm.setAttendanceID(attendanceID);

        model.addAttribute("observationForm", this.requestService.prepareObservationForm(observationForm));

        return "request/observation/add";
    }

    @RequestMapping(value = {"{attendanceID}/{requestID}/observation/add"}, method = RequestMethod.POST)
    public ModelAndView saveObservation(Principal principal, @Valid @ModelAttribute("observationForm") ObservationForm observationForm, BindingResult result) {
        if (result.hasErrors()) {
            return new ModelAndView(observationForm.getAttendanceID() + "/" + observationForm.getRequestID() + "/charge/add");
        }
        this.requestService.saveObservation(observationForm, (UserPrincipal) principal);

        return new ModelAndView("redirect:/attendance/view/" + observationForm.getAttendanceID());

    }

    @RequestMapping(value = {"{attendanceID}/{requestID}/diagnosis/add"}, method = RequestMethod.GET)
    public String addDiagnosis(Model model, @PathVariable("attendanceID") int attendanceID, @PathVariable("requestID") int requestID) {
        DiagnosisForm diagnosisForm = new DiagnosisForm();

        diagnosisForm.setAttendanceID(attendanceID);
        diagnosisForm.setRequestID(requestID);
        model.addAttribute("diagnosisForm", diagnosisForm);

        return "request/diagnosis/add";
    }

    @RequestMapping(value = {"/listicd/{searchString}"}, method = RequestMethod.GET)
    public String searchICD(Model model, @PathVariable("searchString") String searchString) {
        model.addAttribute("icdList", this.requestService.getMatchICDs(searchString));
        return "request/icd/list";
    }

    @RequestMapping(value = {"{attendanceID}/{requestID}/diagnosis/add/additem"}, method = RequestMethod.POST)
    public String diagnosisAddItem(@Valid @ModelAttribute("diagnosisForm") DiagnosisForm diagnosisForm, @PathVariable("attendanceID") int attendanceID, @PathVariable("requestID") int requestID) {

        Diagnosis diagnosis = diagnosisForm.getDiagnosis();
        diagnosis.setAttendanceID(attendanceID);
        diagnosis.setRequestID(requestID);
        diagnosis.setTempID(Math.round(Math.random() * 1000));
        diagnosisForm.getDiagnosisList().add(diagnosis);
        diagnosisForm.setDiagnosis(new Diagnosis());

        return "request/diagnosis/add";
    }

    @RequestMapping(value = {"{attendanceID}/{requestID}/diagnosis/add/removeitem/{position}"}, method = RequestMethod.POST)
    public String diagnosisRemoveItem(@Valid @ModelAttribute("diagnosisForm") DiagnosisForm diagnosisForm,
            @PathVariable("attendanceID") int attendanceID, @PathVariable("requestID") int requestID, @PathVariable("position") int position) {

        diagnosisForm.getDiagnosisList().remove(position);

        return "request/diagnosis/add";
    }

    @RequestMapping(value = {"{attendanceID}/{requestID}/diagnosis/add"}, method = RequestMethod.POST)
    public ModelAndView saveDiagnosis(Principal principal, @Valid @ModelAttribute("diagnosisForm") DiagnosisForm diagnosisForm, BindingResult result) {
        if (result.hasErrors()) {
            return new ModelAndView("request/diagnosis/add");
        } else if (diagnosisForm.getDiagnosisList() == null || diagnosisForm.getDiagnosisList().isEmpty()) {
            ObjectError error = new ObjectError("Diagnosis", "No Diagnosis added.");
            result.addError(error);
            ModelAndView rxView = new ModelAndView("request/diagnosis/add");
            rxView.addObject("validationErrors", result.getAllErrors());

            return rxView;
        }
        this.requestService.saveDiagnosis(diagnosisForm, (UserPrincipal) principal);

        return new ModelAndView("redirect:/attendance/view/" + diagnosisForm.getAttendanceID());

    }

    @RequestMapping(value = {"{attendanceID}/{requestID}/labtest/add"}, method = RequestMethod.GET)
    public String addLabTest(Model model, @PathVariable("attendanceID") int attendanceID, @PathVariable("requestID") int requestID) {
        LabTestForm labTestForm = this.requestService.prepareLabTestForm(attendanceID, requestID);

        model.addAttribute("labTestForm", labTestForm);

        return "request/labtest/add";
    }

    @RequestMapping(value = {"{attendanceID}/{requestID}/labtest/fulfil"}, method = RequestMethod.GET)
    public String fulfilLabTest(Model model, @PathVariable("attendanceID") int attendanceID, @PathVariable("requestID") int requestID) {
        LabTestForm labTestForm = this.requestService.prepareLabTestForm(attendanceID, requestID);

        model.addAttribute("labTestForm", labTestForm);

        return "request/labtest/fulfil";
    }

    @RequestMapping(value = {"{attendanceID}/{requestID}/labtest/add"}, method = RequestMethod.POST)
    public ModelAndView saveLabTest(Principal principal, @Valid @ModelAttribute("labTestForm") LabTestForm labTestForm, BindingResult result) {
        if (result.hasErrors()) {
            return new ModelAndView(labTestForm.getAttendanceID() + "/" + labTestForm.getRequestID() + "/labtest/add");
        } else if (labTestForm.getSelectedLovs().isEmpty()) {
            ObjectError error = new ObjectError("Lab Test", "No laboratory test selected. Please select one or several, then save.");
            result.addError(error);
            System.out.println("--notes--" + labTestForm.getLabNotes());
            ModelAndView rxView = new ModelAndView("request/labtest/add");
            rxView.addObject("validationErrors", result.getAllErrors());

            return rxView;
        }

        this.requestService.saveLabTest(labTestForm, (UserPrincipal) principal);

        return new ModelAndView("redirect:/attendance/view/" + labTestForm.getAttendanceID());

    }

    @RequestMapping(value = {"{attendanceID}/{requestID}/labtest/fulfil"}, method = RequestMethod.POST)
    public ModelAndView saveFulfilLabTest(Principal principal, @Valid @ModelAttribute("labTestForm") LabTestForm labTestForm, BindingResult result) {
        if (result.hasErrors()) {
            return new ModelAndView(labTestForm.getAttendanceID() + "/" + labTestForm.getRequestID() + "/labtest/fulfil");
        }
        this.requestService.saveLabTestFulfil(labTestForm, (UserPrincipal) principal);

        return new ModelAndView("redirect:/attendance/view/" + labTestForm.getAttendanceID());

    }

    @RequestMapping(value = {"{attendanceID}/{requestID}/procedure/add"}, method = RequestMethod.GET)
    public String addProcedure(Model model, @PathVariable("attendanceID") int attendanceID, @PathVariable("requestID") int requestID) {
        ProcedureForm procedureForm = this.requestService.prepareProcedureForm(attendanceID, requestID);

        model.addAttribute("procedureForm", procedureForm);

        return "request/procedure/add";
    }

    @RequestMapping(value = {"{attendanceID}/{requestID}/procedure/fulfil"}, method = RequestMethod.GET)
    public String fulfilProcedure(Model model, @PathVariable("attendanceID") int attendanceID, @PathVariable("requestID") int requestID) {
        ProcedureForm procedureForm = this.requestService.prepareProcedureForm(attendanceID, requestID);

        model.addAttribute("procedureForm", procedureForm);

        return "request/procedure/fulfil";
    }

    @RequestMapping(value = {"{attendanceID}/{requestID}/procedure/fulfil"}, method = RequestMethod.POST)
    public ModelAndView saveFulfilProcedure(Principal principal, @Valid @ModelAttribute("procedureForm") ProcedureForm procedureForm, BindingResult result) {
        if (result.hasErrors()) {
            return new ModelAndView(procedureForm.getAttendanceID() + "/" + procedureForm.getRequestID() + "/procedure/fulfil");
        }
        this.requestService.saveFulfilProcedure(procedureForm, (UserPrincipal) principal);

        return new ModelAndView("redirect:/attendance/view/" + procedureForm.getAttendanceID());

    }

    @RequestMapping(value = {"{attendanceID}/{requestID}/procedure/add"}, method = RequestMethod.POST)
    public ModelAndView saveProcedure(Principal principal, @Valid @ModelAttribute("procedureForm") ProcedureForm procedureForm, BindingResult result) {
        if (result.hasErrors()) {
            return new ModelAndView(procedureForm.getAttendanceID() + "/" + procedureForm.getRequestID() + "/procedure/add");
        } else if (procedureForm.getSelectedLovs().isEmpty()) {
            ObjectError error = new ObjectError("Procedures", "No Procedure selected. Please select one or several, then save.");
            result.addError(error);
            ModelAndView rxView = new ModelAndView("request/procedure/add");
            rxView.addObject("validationErrors", result.getAllErrors());

            return rxView;
        }

        // procedureForm.getSelectedLovs()
        this.requestService.saveProcedure(procedureForm, (UserPrincipal) principal);

        return new ModelAndView("redirect:/attendance/view/" + procedureForm.getAttendanceID());

    }

    @RequestMapping(value = {"{attendanceID}/{requestID}/prescription/add"}, method = RequestMethod.GET)
    public String addPrescription(Model model, @PathVariable("attendanceID") int attendanceID, @PathVariable("requestID") int requestID) {
        PrescriptionForm prescriptionForm = new PrescriptionForm();

        prescriptionForm.setAttendanceID(attendanceID);
        prescriptionForm.setRequestID(requestID);
        model.addAttribute("prescriptionForm", prescriptionForm);

        return "request/prescription/add";
    }

    @RequestMapping(value = {"{attendanceID}/{requestID}/prescription/edit"}, method = RequestMethod.GET)
    public String editPrescription(Model model, @PathVariable("attendanceID") int attendanceID, @PathVariable("requestID") int requestID) {
        PrescriptionForm prescriptionForm = new PrescriptionForm();

        prescriptionForm.setAttendanceID(attendanceID);
        prescriptionForm.setRequestID(requestID);
        model.addAttribute("prescriptionForm", this.requestService.preparePrescriptionForm(attendanceID, requestID));

        return "request/prescription/edit";
    }

    @RequestMapping(value = {"{attendanceID}/{requestID}/prescription/dispense"}, method = RequestMethod.GET)
    public String dispensePrescription(Model model, @PathVariable("attendanceID") int attendanceID, @PathVariable("requestID") int requestID) {
        PrescriptionForm prescriptionForm = new PrescriptionForm();

        prescriptionForm.setAttendanceID(attendanceID);
        prescriptionForm.setRequestID(requestID);
        model.addAttribute("prescriptionForm", this.requestService.preparePrescriptionForm(attendanceID, requestID));

        return "request/prescription/dispense";
    }

    @RequestMapping(value = {"{attendanceID}/{requestID}/prescription/dispense"}, method = RequestMethod.POST)
    public ModelAndView savePrescriptionDispense(Principal principal, @Valid @ModelAttribute("prescriptionForm") PrescriptionForm prescriptionForm, BindingResult result) {
        if (result.hasErrors()) {
            return new ModelAndView(prescriptionForm.getAttendanceID() + "/" + prescriptionForm.getRequestID() + "/prescription/dispense");
        }

        this.requestService.savePrescriptionDispense(prescriptionForm, (UserPrincipal) principal);

        return new ModelAndView("redirect:/attendance/view/" + prescriptionForm.getAttendanceID());
    }

    @RequestMapping(value = {"{attendanceID}/{requestID}/prescription/edit"}, method = RequestMethod.POST)
    public ModelAndView savePrescriptionChanges(Principal principal, @Valid @ModelAttribute("prescriptionForm") PrescriptionForm prescriptionForm, BindingResult result) {
        if (result.hasErrors()) {
            return new ModelAndView(prescriptionForm.getAttendanceID() + "/" + prescriptionForm.getRequestID() + "/prescription/edit");
        }
        this.requestService.savePrescriptionChanges(prescriptionForm, (UserPrincipal) principal);

        return new ModelAndView("redirect:/attendance/view/" + prescriptionForm.getAttendanceID());
    }

    @RequestMapping(value = {"/searchdrug/{searchStr}"}, method = RequestMethod.GET)
    public String searchDrug(Model model, @PathVariable("searchStr") String searchString) {
        model.addAttribute("drugList", this.requestService.getMatchDrugs(searchString));
        return "request/drug/list";
    }

    @RequestMapping(value = {"{attendanceID}/{requestID}/prescription/add/additem"}, method = RequestMethod.POST)
    public String prescriptionAddItem(@Valid @ModelAttribute("prescriptionForm") PrescriptionForm prescriptionForm,
            @PathVariable("attendanceID") int attendanceID, @PathVariable("requestID") int requestID) {

        Prescription prescription = prescriptionForm.getPrescription();
        if (prescription.getRowID() > 0) {

        } else {
            prescription.setAttendanceID(attendanceID);
            prescription.setRequestID(requestID);
            prescriptionForm.getPrescriptionList().add(prescription);
            prescriptionForm.setPrescription(new Prescription());
        }
        return "request/prescription/add";
    }

    @RequestMapping(value = {"{attendanceID}/{requestID}/prescription/add/removeitem/{rowID}"}, method = RequestMethod.POST)
    public String prescriptionRemoveItem(@Valid @ModelAttribute("prescriptionForm") PrescriptionForm prescriptionForm,
            @PathVariable("attendanceID") int attendanceID, @PathVariable("requestID") int requestID, @PathVariable("rowID") int rowID) {

        prescriptionForm.getPrescriptionList().removeIf(p -> p.getRowID() == rowID);

        return "request/prescription/add";
    }

    @RequestMapping(value = {"{attendanceID}/{requestID}/prescription/add"}, method = RequestMethod.POST)
    public ModelAndView savePrescription(Principal principal, @Valid @ModelAttribute("prescriptionForm") PrescriptionForm prescriptionForm, BindingResult result) {
        if (result.hasErrors()) {
            return new ModelAndView(prescriptionForm.getAttendanceID() + "/" + prescriptionForm.getRequestID() + "/prescription/add");
        } else if (prescriptionForm.getPrescriptionList() == null || prescriptionForm.getPrescriptionList().size() == 0) {
            ObjectError error = new ObjectError("prescription", "No prescription added.");
            result.addError(error);
            ModelAndView rxView = new ModelAndView("request/prescription/add");
            rxView.addObject("validationErrors", result.getAllErrors());

            return rxView;
        }
        this.requestService.savePrescription(prescriptionForm, (UserPrincipal) principal);

        return new ModelAndView("redirect:/attendance/view/" + prescriptionForm.getAttendanceID());
    }

    @RequestMapping(value = {"{attendanceID}/{requestID}/radiology/add"}, method = RequestMethod.GET)
    public String addRadiology(Model model, @PathVariable("attendanceID") int attendanceID, @PathVariable("requestID") int requestID) {

        model.addAttribute("radiologyForm", this.requestService.prepareRadiologyForm(attendanceID, requestID));

        return "request/radiology/add";
    }

    @RequestMapping(value = {"{attendanceID}/{requestID}/radiology/fulfil"}, method = RequestMethod.GET)
    public String fulfilRadiology(Model model, @PathVariable("attendanceID") int attendanceID, @PathVariable("requestID") int requestID) {
        LabTestForm labTestForm = this.requestService.prepareLabTestForm(attendanceID, requestID);

        model.addAttribute("radiologyForm", this.requestService.prepareRadiologyForm(attendanceID, requestID));

        return "request/radiology/fulfil";
    }

    @RequestMapping(value = {"{attendanceID}/{requestID}/radiology/fulfil"}, method = RequestMethod.POST)
    public ModelAndView saveFulfilRadiology(Principal principal, @Valid @ModelAttribute("radiologyForm") RadiologyForm radiologyForm, BindingResult result) {
        if (result.hasErrors()) {
            return new ModelAndView(radiologyForm.getAttendanceID() + "/" + radiologyForm.getRequestID() + "/radiology/fulfil");
        }
        this.requestService.saveFulfilRadiology(radiologyForm, (UserPrincipal) principal);

        return new ModelAndView("redirect:/attendance/view/" + radiologyForm.getAttendanceID());

    }

    @RequestMapping(value = {"{attendanceID}/{requestID}/radiology/add/additem"}, method = RequestMethod.POST)
    public String radiologyAddItem(@Valid @ModelAttribute("radiologyForm") RadiologyForm radiologyForm,
            @PathVariable("attendanceID") int attendanceID, @PathVariable("requestID") int requestID) {

        Radiology radiology = radiologyForm.getRadiology();
        if (radiology.getRowID() > 0) {

        } else {
            int lovSplitterIndex = radiology.getLovTypeVal().indexOf("||");
            System.out.println("Position: " + lovSplitterIndex);
            if (lovSplitterIndex > 0) {
                String lovTypeID = radiology.getLovTypeVal().substring(0, lovSplitterIndex);
                String lovTypeVal = radiology.getLovTypeVal().substring(lovSplitterIndex + 2, radiology.getLovTypeVal().length());

                radiology.setLovTypeLovID(Integer.parseInt(lovTypeID));
                radiology.setLovTypeVal(lovTypeVal);
            }
            radiology.setAttendanceID(attendanceID);
            radiology.setRequestID(requestID);
            radiologyForm.getRadiologyList().add(radiology);
            radiologyForm.setRadiology(new Radiology());
        }
        return "request/radiology/add";
    }

    @RequestMapping(value = {"{attendanceID}/{requestID}/radiology/add"}, method = RequestMethod.POST)
    public ModelAndView savePrescription(Principal principal, @Valid @ModelAttribute("radiologyForm") RadiologyForm radiologyForm, BindingResult result) {
        if (result.hasErrors()) {
            return new ModelAndView(radiologyForm.getAttendanceID() + "/" + radiologyForm.getRequestID() + "/radiology/add");
        } else if (radiologyForm.getRadiologyList().isEmpty()) {
            ObjectError error = new ObjectError("Radiology", "No Radiology added. Please select one or several, then save.");
            result.addError(error);
            ModelAndView rxView = new ModelAndView("request/radiology/add");
            rxView.addObject("validationErrors", result.getAllErrors());

            return rxView;
        }

        this.requestService.saveRadiology(radiologyForm, (UserPrincipal) principal);

        return new ModelAndView("redirect:/attendance/view/" + radiologyForm.getAttendanceID());
    }

    @RequestMapping(value = {"/view/{requestID}"}, method = RequestMethod.GET)
    public String viewRequest(Model model, @PathVariable("requestID") int requestID) {

        model.addAllAttributes(this.requestService.getRequestDetailsForDisplay(requestID));

        System.out.println("Called: Request ID: " + requestID);
        return "/request/view";
    }

    @RequestMapping(value = {"{attendanceID}/{requestID}/payment/add"}, method = RequestMethod.GET)
    public String addPayment(Model model, @PathVariable("attendanceID") int attendanceID, @PathVariable("requestID") int requestID) {
        PaymentForm paymentForm = this.requestService.preparePaymentForm(attendanceID, requestID);
        model.addAttribute("paymentForm", paymentForm);

        return "request/charge/pay";
    }

    @RequestMapping(value = {"/{attendanceID}/{requestID}/payment/add"}, method = RequestMethod.POST)
    public ModelAndView savePayment(Principal principal, @Valid @ModelAttribute("paymentForm") PaymentForm paymentForm, BindingResult result) {
        if (result.hasErrors()) {
            System.out.println("Error recorded - in payments");
            result.getAllErrors().stream().forEach(e -> System.out.println(e.getDefaultMessage() + " -- " + e.getObjectName()));
            return new ModelAndView("request/charge/pay");
        }
        this.requestService.savePayment(paymentForm, (UserPrincipal) principal);

        return new ModelAndView("redirect:/attendance/view/" + paymentForm.getAttendanceID());
    }

    @RequestMapping(value = {"/{attendanceID}/{requestID}/discharge/add"}, method = RequestMethod.GET)
    public String addDischarge(Model model,@PathVariable("attendanceID") int attendanceID, @PathVariable("requestID") int requestID) {
       model.addAttribute("dischargeForm", this.requestService.prepareDischargeForm(requestID, attendanceID, FormAction.NEW));
       return "request/discharge/add";
    }
    
    @RequestMapping(value = {"/{attendanceID}/{requestID}/discharge/add"}, method = RequestMethod.POST)
    public ModelAndView saveDischarge(Principal principal, @Valid @ModelAttribute("dischargeForm") DischargeForm dischargeForm, BindingResult result) {
       
        if (result.hasErrors()) {
            return new ModelAndView("request/discharge/add");
        }
        this.requestService.saveDischargeForm(dischargeForm, (UserPrincipal) principal);

        return new ModelAndView("redirect:/attendance/view/" + dischargeForm.getAttendanceID());       
    }    
}
