<%--@elvariable id="radilogies" type="java.util.List<co.ke.sart.site.entity.Radiology>"--%>
<%--@elvariable id="radiology" type="co.ke.sart.site.entity.Radiology>"--%>
<c:if test="${not empty radiologies}">
    <div class="sart-approval-req">
        <table class="drugs">
            <tr><th>Name</th><th>Note</th><th>Site</th><th>Result</th><th>Result Note</th></tr>
                    <c:forEach items="${radiologies}" var="radiology">
                <tr>
                    <td>${radiology.lovTypeVal}</td>
                    <td>${radiology.note}</td>
                    <td>${radiology.site}</td>
                    <td>${radiology.resultText}</td>
                    <td>${radiology.resultNote}</td>
                </tr>
            </c:forEach>
        </table>
    </div>
</c:if>