<%--@elvariable id="procedures" type="java.util.List<co.ke.sart.site.entity.Procedure>"--%>
<%--@elvariable id="procedure" type="co.ke.sart.site.entity.Procedure>"--%>
<c:if test="${not empty procedures}">
    <div class="sart-approval-req">
        <table class="drugs">
            <tr><th>Name</th><th>Note</th><th>Result</th><th>Result Note</th></tr>
                    <c:forEach items="${procedures}" var="procedure">
                <tr>
                    <td>${procedure.lovTypeVal}</td>
                    <td>${procedure.note}</td>
                    <td>${procedure.resultText}</td>
                    <td>${procedure.resultNote}</td>
                </tr>
            </c:forEach>
        </table>
    </div>
</c:if>