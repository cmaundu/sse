/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.ke.sart.site.controller;

import co.ke.sart.config.annotation.WebController;
import co.ke.sart.site.entity.PaymentSummary;
import co.ke.sart.site.service.ReportService;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@WebController
@RequestMapping("reports")
public class ReportController {
    
    @Autowired
    ReportService reportService;
    
    @RequestMapping(value = {"*","**","/**", "list"}, method = RequestMethod.GET)
    public String list(Model model) {
        
        Map<String,Object> pays = reportService.getPayments();
        model.addAllAttributes(pays);

        return "report/daily_summary";
    }
    
}
