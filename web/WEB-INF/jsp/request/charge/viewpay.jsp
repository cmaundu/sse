<%--@elvariable id="payments" type="java.util.List<co.ke.sart.site.entity.Payment>"--%>
<%--@elvariable id="payment" type="co.ke.sart.site.entity.Paymen>"--%>
<c:if test="${not empty payments}">
    <div class="sart-approval-req">
        <table class="drugs">
            <tr><th>Payment Method</th><th>Amount</th><th>Date</th><th>Transaction ID</th><th>Notes</th></tr>
                    <c:forEach items="${payments}" var="payment">
                <tr>
                    <td>${payment.paymentLovTypeVal}</td>
                    <td>${payment.paidAmount}</td>
                    <td>${payment.getCreatedDate()}</td>
                    <td>${payment.receiptNumber}</td>
                    <td>${payment.note}</td>
                </tr>
            </c:forEach>
        </table>
    </div>
</c:if>