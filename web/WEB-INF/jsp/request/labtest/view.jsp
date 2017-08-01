<%--@elvariable id="labTests" type="java.util.List<co.ke.sart.site.entity.LabTest>"--%>
<%--@elvariable id="labTest" type="co.ke.sart.site.entity.LabTest>"--%>
<c:if test="${not empty labtests}">
    <div class="sart-approval-req">
        <table class="drugs">
            <tr><th>Name</th><th>Note</th><th>Result</th><th>Result Note</th></tr>
                    <c:forEach items="${labtests}" var="labtest">
                <tr>
                    <td>${labtest.labTypeLovVal}</td>
                    <td>${labtest.note}</td>
                    <td>${labtest.resultText}</td>
                    <td>${labtest.resultNote}</td>
                </tr>
            </c:forEach>
        </table>
    </div>
</c:if>