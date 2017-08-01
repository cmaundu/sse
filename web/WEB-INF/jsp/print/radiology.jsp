<div class="modal-content modal-print-content">
    <%@include file="invoiceheader.jsp" %>

    <c:choose>
        <c:when test="${not empty radiologies}">
            <div class="print-sub-title">Details of Radiology Request.</div>
            <table class="sart-print-table">
                <tbody>
                    <tr><th>Date</th><th>Name</th><th>Site</th><th>Note</th></tr>
                            <c:forEach items="${radiologies}" var="radiology">
                        <tr><td>${radiology.getCreatedDateTime()}</td><td>${radiology.lovTypeVal}</td> <td>${radiology.site}</td><td>${radiology.note}</td></tr>
                    </c:forEach>
                </tbody>
            </table>

            <div class="sart-sign">
                <table class="sart-sign-line">
                    <tbody><tr><td><div class="sart-sign-line sart-left-half">${user.getFullNames()}</div></td>
                            <td><div class="sart-sign-line sart-left-half">Date</div></td></tr>
                    </tbody></table>
            </div> 
        </c:when>
        <c:otherwise>
            <div class="print-sub-title">No Radiology Request Recorded.</div>
        </c:otherwise>
    </c:choose> 
</div>
