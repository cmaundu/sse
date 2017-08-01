/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.ke.sart.site.service;

import co.ke.sart.site.entity.ChargeMatrix;
import co.ke.sart.site.interfaces.ICharges;
import co.ke.sart.site.model.Attendance;
import co.ke.sart.site.repository.ChargeMatrixRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChargesService {

    @Autowired
    ChargeMatrixRepository chargeMatrixRepository;

    @Autowired
    AttendanceService attendanceService;

    public List<ChargeMatrix> getChargeMatrix(String lovType, List<Integer> lovTypeIDs, int paymentMode) {
        List<ChargeMatrix> matrices = chargeMatrixRepository.findByLovTypeAndLovTypeIDInAndPaymentMode(lovType, lovTypeIDs, paymentMode);

        List<Integer> missingLovTypes = lovTypeIDs.stream().filter(p -> matrices.stream().noneMatch(e -> e.getLovTypeID() == p)).collect(Collectors.toList());
        if (missingLovTypes.size() > 0) {
            List<ChargeMatrix> defaultMatrices = chargeMatrixRepository.findByLovTypeAndLovTypeIDInAndPaymentMode(lovType, missingLovTypes, 0);

            matrices.addAll(defaultMatrices);
        }

        return matrices;
    }

    public int getPaymentType(int attendanceID) {
        int paymentType = 0;
        Attendance attendance = attendanceService.getAttendance(attendanceID);
        if (attendance != null) {
            paymentType = attendance.getPaymentTypeID();
        }
        return paymentType;
    }

    public List<ICharges> addCharges(List<ICharges> items, int attendanceID, String lovType) {
        int paymentType = getPaymentType(attendanceID);

        if (items != null) {
            List<Integer> lovs = items.stream().map(e -> e.getLovTypeLovID()).collect(Collectors.toList());

            System.out.println("Type: " + lovType + " Lovs :" + lovs.toString() + "PaymentType: " + paymentType);
            List<ChargeMatrix> chargeMatrices = getChargeMatrix(lovType, lovs, paymentType);

            System.out.println(chargeMatrices);

            items.forEach(p -> {
                Optional<Double> amount = chargeMatrices.stream()
                        .filter(c -> c.getLovTypeID() == p.getLovTypeLovID() && c.getPaymentMode() == paymentType)
                        .findFirst().map(c -> c.getAmount());

                if (!amount.isPresent()) {
                    amount = chargeMatrices.stream()
                            .filter(c -> c.getLovTypeID() == p.getLovTypeLovID() && c.getPaymentMode() == 0)
                            .findFirst().map(c -> c.getAmount());
                }
                p.setChargeAmount(amount.isPresent() ? amount.get() : 0);
                System.out.println(amount);
            });

        }

        return items;
    }

    private <E> List<E> toList(Iterable<E> i) {
        List<E> list = new ArrayList<>();
        i.forEach(list::add);
        return list;
    }
}
