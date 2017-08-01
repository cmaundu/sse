<%--@elvariable id="prescriptions" type="java.util.List<co.ke.sart.site.entity.Prescription>"--%>
<%--@elvariable id="prescription" type="co.ke.sart.site.entity.Prescription>"--%>
<c:if test="${not empty vitals}">
    <div class="sart-approval-req">
        <table class="drugs">
            <tr><th>Date</th><th>Pulse Rate</th><th>BP</th><th>Temperature</th><th>Respiration</th><th>Height</th><th>Weight</th><th>Notes</th></tr>
                    <c:forEach items="${vitals}" var="vital">
                <tr>
                    <td>${vital.getCreatedDateTime()}</td>
                    <td>${vital.pulseRate}</td>
                    <td>${vital.bloodPressure}</td>
                    <td>${vital.temperature}</td>
                    <td>${vital.respiration}</td>
                    <td>${vital.height}</td>
                    <td>${vital.weight}</td>
                    <td>${vital.note}</td>                    
                </tr>
            </c:forEach>
        </table>
    </div>
</c:if>