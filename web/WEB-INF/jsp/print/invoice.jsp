<div class="modal-content modal-print-content">
    <%@include file="invoiceheader.jsp" %>
    <c:set var="grandtotal" value="0" />
    <c:if test="${not empty charges}">
        <div class="sart-print-section">
            <div class="sart-print-sub-header">
                Consultation and Other Charges
            </div>  
            <div class="sart-print-header">
                <table class="sart-print-table">
                    <tbody>
                        <tr>
                            <th>Date</th><th>Item</th><th>Note</th><th class="th-number">Sub Total</th>
                        </tr>
                        <tr>
                            <c:set var="subtotal" value="0" />
                            <c:forEach items="${charges}" var="charge">
                                <c:set var="subtotal" value="${subtotal + charge.amountCharged}" />
                            <tr>
                                <td>${charge.getCreatedDateTime()}</td>
                                <td>${charge.description}</td>
                                <td>${charge.note}</td>
                                <td class="number">${charge.amountCharged}</td>
                            </tr>
                        </c:forEach>
                        <c:set var="grandtotal" value="${subtotal + grandtotal}" />
                        </tr>
                        <tr class="sart-print-total"><td colspan="3">Total</td><td class="number">${subtotal}</td></tr>
                    </tbody>
                </table>
            </div>

        </div>                       
    </c:if>        

    <c:if test="${not empty labtests}">
        <div class="sart-print-section">
            <div class="sart-print-sub-header">
                Laboratory Test Charges
            </div>  
            <div class="sart-print-header">
                <table class="sart-print-table">
                    <tbody>
                        <tr>
                            <th>Date</th><th>Item</th><th class="th-number">Sub Total</th>
                        </tr>
                        <tr>
                            <c:set var="subtotal" value="0" />
                            <c:forEach items="${labtests}" var="labtest">
                                <c:set var="subtotal" value="${subtotal + labtest.chargeAmount}" />
                            <tr>
                                <td>${labtest.getCreatedDateTime()}</td>
                                <td>${labtest.labTypeLovVal}</td>
                                <td class="number">${labtest.chargeAmount}</td>
                            </tr>
                        </c:forEach>
                        <c:set var="grandtotal" value="${subtotal + grandtotal}" />
                        </tr>
                        <tr class="sart-print-total"><td colspan="2">Total</td><td class="number">${subtotal}</td></tr>
                    </tbody>
                </table>
            </div>

        </div>                       
    </c:if>    

    <c:if test="${not empty prescriptions}">
        <div class="sart-print-section">
            <div class="sart-print-sub-header">
                Pharmacy Charges
            </div>  
            <div class="sart-print-header">
                <table class="sart-print-table">
                    <tbody>
                        <tr>
                            <th>Date</th><th>Name</th><th>Dose</th><th class="th-number">@</th><th class="th-number">Qty</th><th class="th-number">Sub Total</th>
                        </tr>
                        <tr>
                            <c:set var="subtotal" value="0" />
                            <c:forEach items="${prescriptions}" var="prescription">
                                <c:set var="subtotal" value="${subtotal + prescription.chargeAmount}" />
                            <tr>
                                <td>${prescription.getCreatedDateTime()}</td>
                                <td>${prescription.drugDef.drugName}</td>
                                <td>${prescription.dose}</td>
                                <td class="number">
                                    <c:if test="${prescription.quantity >0}">
                                        ${prescription.chargeAmount / prescription.quantity}
                                    </c:if> 
                                </td>
                                <td class="number">        
                                    <c:if test="${prescription.quantity >0}">
                                        ${prescription.quantity}
                                    </c:if>
                                </td>
                                <td class="number">${prescription.chargeAmount}</td>
                            </tr>
                        </c:forEach>
                        <c:set var="grandtotal" value="${subtotal + grandtotal}" />
                        </tr>
                        <tr class="sart-print-total"><td colspan="5">Total</td><td class="number">${subtotal}</td></tr>
                    </tbody>
                </table>
            </div>

        </div>                       
    </c:if>

    <c:if test="${not empty procedures}">
        <div class="sart-print-section">
            <div class="sart-print-sub-header">
                Procedure Charges
            </div>  
            <div class="sart-print-header">
                <table class="sart-print-table">
                    <tbody>
                        <tr>
                            <th>Date</th><th>Name</th><th class="th-number">Sub Total</th>
                        </tr>
                        <tr>
                            <c:set var="subtotal" value="0" />
                            <c:forEach items="${procedures}" var="procedure">
                                <c:set var="subtotal" value="${subtotal + procedure.chargeAmount}" />
                            <tr>
                                <td>${procedure.getCreatedDateTime()}</td>
                                <td>${procedure.lovTypeVal}</td>
                                <td class="number">${procedure.chargeAmount}</td>
                            </tr>
                        </c:forEach>
                        <c:set var="grandtotal" value="${subtotal + grandtotal}" />
                        </tr>
                        <tr class="sart-print-total"><td colspan="2">Total</td><td class="number">${subtotal}</td></tr>
                    </tbody>
                </table>
            </div>

        </div>                       
    </c:if>    

    <c:if test="${not empty radiologies}">
        <div class="sart-print-section">
            <div class="sart-print-sub-header">
                Radiology Charges
            </div>  
            <div class="sart-print-header">
                <table class="sart-print-table">
                    <tbody>
                        <tr>
                            <th>Date</th><th>Name</th><th>Site</th><th class="th-number">Sub Total</th>
                        </tr>
                        <tr>
                            <c:set var="subtotal" value="0" />
                            <c:forEach items="${radiologies}" var="radiology">
                                <c:set var="subtotal" value="${subtotal + radiology.chargeAmount}" />
                            <tr>
                                <td>${radiology.getCreatedDateTime()}</td>
                                <td>${radiology.lovTypeVal}</td>
                                <td>${radiology.site}</td>
                                <td class="number">${radiology.chargeAmount}</td>
                            </tr>
                        </c:forEach>
                        <c:set var="grandtotal" value="${subtotal + grandtotal}" />
                        </tr>
                        <tr class="sart-print-total"><td colspan="3">Total</td><td class="number">${subtotal}</td></tr>
                    </tbody>
                </table>
            </div>

        </div>                       
    </c:if>    
    <c:set var="subtotal" value="0" />
    <c:if test="${not empty payments}">
        <div class="sart-print-section">
            <div class="sart-print-sub-header">
                Payments
            </div>  
            <div class="sart-print-header">
                <table class="sart-print-table">
                    <tbody>
                        <tr>
                            <th>Date</th><th>Method</th><th>Transaction ID</th><th class="th-number">Amount (Kes)</th>
                        </tr>
                        <tr>

                            <c:forEach items="${payments}" var="payment">
                                <c:set var="subtotal" value="${subtotal + payment.paidAmount}" />
                            <tr>
                                <td>${payment.getCreatedDateTime()}</td>
                                <td>${payment.paymentLovTypeVal}</td>
                                <td>${payment.receiptNumber}</td>
                                <td class="number">${payment.paidAmount}</td>
                            </tr>
                        </c:forEach>
                        </tr>
                        <tr class="sart-print-total"><td colspan="3">Total</td><td class="number">${subtotal}</td></tr>
                    </tbody>
                </table>
            </div>

        </div>                       
    </c:if>   

    <div class="sart-print-grand-section  sart-print-header">
        <table class="sart-grand-total sart-print-table">
            <tbody>
                <tr class="sart-print-total"><td>Grand Total</td><td class="number">${grandtotal}</td></tr>
                <tr><td></td><td class="number"></td></tr>
                <tr class="sart-print-total"><td>Balance</td><td class="number">${grandtotal-subtotal}</td></tr>
            </tbody>
        </table>
    </div>
</div>
