<div class="modal-content modal-print-content">
     <%@include file="invoiceheader.jsp" %>
     
      <c:choose>
        <c:when test="${not empty procedures}">
            <div class="print-sub-title">Details of Procedure Request.</div>
            <table class="sart-print-table">
                <tbody>
                    <tr><th>Date</th><th>Name</th><th>Note</th></tr>
                            <c:forEach items="${procedures}" var="procedure">
                        <tr><td>${procedure.getCreatedDateTime()}</td><td>${procedure.lovTypeVal}</td><td>${procedure.note}</td></tr>
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
            <div class="print-sub-title">No Procedure Request Recorded.</div>
        </c:otherwise>
    </c:choose> 
</div>
