package co.ke.sart.site.service;

import co.ke.sart.site.entity.PaymentSummary;
import co.ke.sart.site.model.PaymentSummaryModel;
import co.ke.sart.site.repository.PaymentSummaryRepository;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class ReportService {

    @Autowired
    private ApplicationContext appContext;
    //@Autowired
    //PaymentSummaryRepository paymentSummaryRepository;

    public Map<String, Object> getPayments() {
        PaymentSummaryRepository paymentSummaryRepository = appContext.getBean("paymentSummaryRepository", PaymentSummaryRepository.class);
        List<PaymentSummary> pays = paymentSummaryRepository.getDailySummary();
        List<Date> dates = pays.stream().map(p -> p.getPaydate()).distinct().collect(Collectors.toList());
        List<String> payTypes = pays.stream().map(p -> p.getName()).distinct().sorted().collect(Collectors.toList());
        PaymentSummaryModel model = new PaymentSummaryModel();
        model.setDates(dates);
        model.setPayTypes(payTypes);
        model.setPays(pays);
        payTypes.remove("CASH");
        payTypes.add(0, "CASH");
        
        Map<String, Object> returns = new TreeMap<>();
        returns.put("pays", pays);
        returns.put("dates", dates);
        returns.put("payTypes", payTypes);
        returns.put("summary", model);
        return returns;
    }

}
