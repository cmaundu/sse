<%--@elvariable id="prescriptions" type="java.util.List<co.ke.sart.site.entity.Prescription>"--%>
<%--@elvariable id="prescription" type="co.ke.sart.site.entity.Prescription>"--%>
<c:if test="${not empty prescriptions}">
    <div class="sart-approval-req">
        <table class="drugs">
            <tr><th>Drug</th><th>Dose</th><th>qty</th><th>Dispensed</th><th>Note</th></tr>
                    <c:forEach items="${prescriptions}" var="prescription">
                <tr>
                    <td>${prescription.drugDef.drugName}</td>
                    <td>${prescription.dose}</td>
                    <td>${prescription.quantity}</td>
                    <c:choose>
                        <c:when test="${prescription.dispensed}">
                            <td><input disabled="true"  readonly type="checkbox"  checked /></td>
                            </c:when>
                            <c:otherwise>
                            <td><input disabled="true"  readonly type="checkbox"   /></td>
                            </c:otherwise>
                        </c:choose>
                    <td>${prescription.note}</td>
                </tr>
            </c:forEach>
        </table>
    </div>
</c:if>