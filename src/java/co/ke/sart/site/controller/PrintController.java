package co.ke.sart.site.controller;

import co.ke.sart.config.annotation.WebController;
import co.ke.sart.site.entity.UserPrincipal;
import co.ke.sart.site.service.PrintService;
import co.ke.sart.site.utils.PrintType;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@WebController
@RequestMapping("print")
public class PrintController {

    @Autowired
    PrintService printService;
    
    @RequestMapping(value = "lab/{attendanceID}")
    public String printLabInformation(Model model, @PathVariable("attendanceID") int attendanceID, Principal user) {
        model.addAllAttributes(this.printService.prepareInvoiceHeader(attendanceID, (UserPrincipal)user, PrintType.Laboratory));
        
        return "print/lab";
    }
    
    @RequestMapping(value = "prescription/{attendanceID}")
    public String printRxInformation(Model model, @PathVariable("attendanceID") int attendanceID, Principal user) {
        model.addAllAttributes(this.printService.prepareInvoiceHeader(attendanceID, (UserPrincipal)user, PrintType.Prescription));
        
        return "print/rx";
    }    
    
    @RequestMapping(value = "radiology/{attendanceID}")
    public String printRadiologyInformation(Model model, @PathVariable("attendanceID") int attendanceID, Principal user) {
        model.addAllAttributes(this.printService.prepareInvoiceHeader(attendanceID, (UserPrincipal)user, PrintType.Radiology));
        
        return "print/radiology";
    }  

    @RequestMapping(value = "procedure/{attendanceID}")
    public String printProcedureInformation(Model model, @PathVariable("attendanceID") int attendanceID, Principal user) {
        model.addAllAttributes(this.printService.prepareInvoiceHeader(attendanceID, (UserPrincipal)user, PrintType.Procedure));
        
        return "print/procedure";
    }   

    @RequestMapping(value = "invoice/{attendanceID}")
    public String printInvoiceInformation(Model model, @PathVariable("attendanceID") int attendanceID, Principal user) {
        model.addAllAttributes(this.printService.prepareInvoiceHeader(attendanceID, (UserPrincipal)user, PrintType.Invoice));
        
        return "print/invoice";
    }     
}

