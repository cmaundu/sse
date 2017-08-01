/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.ke.sart.site.controller;

import co.ke.sart.config.annotation.WebController;
import co.ke.sart.site.entity.PatientEntity;
import co.ke.sart.site.entity.UserPrincipal;
import co.ke.sart.site.form.PatientForm;
import co.ke.sart.site.model.Patient;
import co.ke.sart.site.service.AttendanceService;
import co.ke.sart.site.service.PatientService;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.validation.constraints.NotNull;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;


@WebController
@RequestMapping("patient")
public class PatientController {

    @Autowired
    PatientService patientService;
    
    @Autowired
    AttendanceService attendanceService;

    @RequestMapping(value = {"*","**","/**", "list"}, method = RequestMethod.GET)
    public String list(Model model, SearchForm form) {
        List<Patient> patients = patientService.getPatients();
        model.addAttribute("patients", patients);

        return "patient/list";
    }

    @RequestMapping(value = "search")
    public String search(Model model, @Valid SearchForm form, Errors errors, Pageable pageable) {
        if (form == null || errors.hasErrors()) {
            model.addAttribute("searchPerformed", false);
        } else if  (form.getQuery() == null || form.getQuery().isEmpty()){
            
        }
        else {
            model.addAttribute("searchPerformed", true);
            model.addAttribute("patients", this.patientService.search(
                    form.getQuery(), false, pageable)
            );
        }

        return "patient/search";
    }

    @RequestMapping(value = "add")
    public String create(Model model) {
        model.addAttribute("patientForm", new PatientForm());

        return "patient/add";
    }
    
  

    @RequestMapping(value = {"add","edit"}, method = RequestMethod.POST)
    public ModelAndView create(Principal principal, @Valid @ModelAttribute("patientForm") PatientForm patientForm, BindingResult result) {

        if (result.hasErrors()) {
            System.out.println("Error has been reported");
            result.getAllErrors().stream().forEach(p -> System.out.println(p.getCode()+" - "+p.getObjectName()+p.toString()));
            return new ModelAndView("patient/add");
        }
        this.patientService.savePatient(patientForm, (UserPrincipal)principal);
        
        return new ModelAndView("redirect:/patient/view/"+patientForm.getRowID());
        //return new ModelAndView("patient/list");
    }
    
    @RequestMapping(value = "edit/{rowID}")
    public String edit(Model model, @PathVariable("rowID") int rowID) {
        
        Patient patient = this.patientService.getPatient(rowID);
        PatientForm patForm = new PatientForm();
        if (patient != null) {
            this.patientService.convert(patient, patForm);
        }
        model.addAttribute("patientForm", patForm);

        return "patient/add";
    }    
    
    @RequestMapping(value = "view/{rowID}")
    public String view(Model model, Pageable page, @PathVariable("rowID") int rowID) {
        Patient patient = this.patientService.getPatient(rowID);
        model.addAttribute("patient",patient);
        
        model.addAttribute("attendances",attendanceService.getAttendanceByPatientID(rowID, page));
        
        return "patient/view";
    }  

    public static class SearchForm {

        @NotNull(message = "{validate.ticket.search.query}")
        private String query;

        public String getQuery() {
            return query;
        }

        public void setQuery(String query) {
            this.query = query;
        }

    }

}
