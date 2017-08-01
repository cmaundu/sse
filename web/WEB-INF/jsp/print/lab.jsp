<div class="modal-content modal-print-content">
     <%@include file="invoiceheader.jsp" %>
     
<c:choose>
        <c:when test="${not empty labtests}">
            <div class="print-sub-title">Details of Laboratory Test Request.</div>
            <table class="sart-print-table">
                <tbody>
                    <tr><th>Date</th><th>Name</th><th>Note</th></tr>
                            <c:forEach items="${labtests}" var="labtest">
                        <tr><td>${labtest.getCreatedDateTime()}</td><td>${labtest.labTypeLovVal}</td><td>${labtest.note}</td></tr>
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
            <div class="print-sub-title">No Laboratory Test Request Recorded.</div>
        </c:otherwise>
    </c:choose> 
</div>
