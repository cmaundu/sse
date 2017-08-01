<%--@elvariable id="prescriptions" type="java.util.List<co.ke.sart.site.entity.Prescription>"--%>
<%--@elvariable id="prescription" type="co.ke.sart.site.entity.Prescription>"--%>
<c:if test="${not empty diagnosises}">
    <div class="sart-approval-req">
        <table class="drugs">
            <tr><th>Date</th><th>ICD Code</th><th>Details</th><th>Site</th><th>Note</th></tr>
                    <c:forEach items="${diagnosises}" var="diagnosis">
                <tr>
                    <td>${diagnosis.getCreatedDateTime()}</td>
                    <td>${diagnosis.icdCode}</td>
                    <td>${diagnosis.description}</td>
                    <td>${diagnosis.site}</td>
                    <td>${diagnosis.note}</td>                 
                </tr>
            </c:forEach>
        </table>
    </div>
</c:if>