<%--@elvariable id="prescriptions" type="java.util.List<co.ke.sart.site.entity.Prescription>"--%>
<%--@elvariable id="prescription" type="co.ke.sart.site.entity.Prescription>"--%>
<c:if test="${not empty observations}">
    <div class="sart-approval-req">
        <table class="drugs">
            <tr><th>Date</th><th>Complaint</th><th>Provisional Diagnosis</th><th>Remarks</th></tr>
                    <c:forEach items="${observations}" var="observation">
                <tr>
                    <td>${observation.getCreatedDateTime()}</td>
                    <td>${observation.complaint}</td>
                    <td>${observation.provisionalDiagnosis}</td>
                    <td>${observation.remark}</td>                  
                </tr>
            </c:forEach>
        </table>
    </div>
</c:if>