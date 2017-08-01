/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.ke.sart.site.controller;

import co.ke.sart.config.annotation.WebController;
import co.ke.sart.site.entity.UserPrincipal;
import co.ke.sart.site.form.AttendanceForm;
import co.ke.sart.site.form.AttendanceNewForm;
import co.ke.sart.site.form.PatientForm;
import co.ke.sart.site.model.Attendance;
import co.ke.sart.site.model.Patient;
import co.ke.sart.site.repository.PaymentModeRepository;
import co.ke.sart.site.service.AttendanceService;
import co.ke.sart.site.service.PatientService;
import co.ke.sart.site.service.RequestService;
import co.ke.sart.site.utils.FormAction;
import java.security.Principal;
import javax.validation.Valid;
import org.hibernate.service.spi.InjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@WebController
@RequestMapping("attendance")
public class AttendanceController {

    @Autowired
    AttendanceService attendanceService;

    @Autowired
    PatientService patientService;

    @Autowired
    RequestService requestService;
    
    @RequestMapping(value = {"*","**"})
    public String showAttendances(Model model){
        model.addAttribute("attendances", this.attendanceService.retriveOpenAttendances());
        return "attendance/listopen";
    }

    @RequestMapping(value = "add/{patientID}")
    public String createattendance(Model model, @PathVariable("patientID") int patientID) {
        model.addAttribute("attendanceForm", this.attendanceService.prepareAttendanceForm(0, patientID, FormAction.NEW));
        //model.addAttribute("paymentModes", attendanceService.getAllPaymentModes());
        //model.addAttribute("patient", this.patientService.getPatient(patientID));
        //model.addAttribute("attendanceNewForm", new AttendanceNewForm());
        //model.addAttribute("attendanceTypeLOVs", this.attendanceService.getLovByType("ATTENDANCE_TYPE"));
        return "attendance/add";
    }
    
    @RequestMapping(value = "edit/{rowID}")
    public String editttendance(Model model, @PathVariable("rowID") int rowID) {
        model.addAttribute("attendanceForm", this.attendanceService.prepareAttendanceForm(rowID, 0, FormAction.EDIT));
        //model.addAttribute("paymentModes", attendanceService.getAllPaymentModes());
        //model.addAttribute("patient", this.patientService.getPatient(patientID));
        //model.addAttribute("attendanceNewForm", new AttendanceNewForm());
        //model.addAttribute("attendanceTypeLOVs", this.attendanceService.getLovByType("ATTENDANCE_TYPE"));
        return "attendance/add";
    }    

    @RequestMapping(value = {"add/{patientID}","edit/{patientID}"}, method = RequestMethod.POST)
    public ModelAndView saveattendance(Principal principal, @Valid @ModelAttribute("attendanceForm") AttendanceForm attendanceForm, BindingResult result, @PathVariable("patientID") int patientID) {
        if (result.hasErrors()) {
            System.out.println("There are errors"+result.getAllErrors());
            return new ModelAndView("attendance/add");
        }

        this.attendanceService.SaveAttendance(attendanceForm, (UserPrincipal) principal);
        return new ModelAndView("redirect:/attendance/view/" + attendanceForm.getRowID());
    }

    @RequestMapping(value = "view/{attendanceID}")
    public String viewattendance(Model model, @PathVariable("attendanceID") int attendanceID) {
        Attendance attendance = this.attendanceService.getAttendance(attendanceID);

        model.addAttribute("attendance", attendance);
        model.addAttribute("patient", this.patientService.getPatient(attendance.getPatientID()));
        model.addAttribute("paymentMode", attendanceService.getPaymentMode(attendance.getPaymentTypeID()));
        
        model.addAttribute("requests", this.requestService.getRequestForDisplay(attendanceID));

        return "attendance/view";
    }
}
