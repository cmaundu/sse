<%--@elvariable id="attendance" type="co.ke.sart.site.model.Attendance"--%>
<%--@elvariable id="patient" type="co.ke.sart.site.model.patient"--%>
<%--@elvariable id="printInfo" type="co.ke.sart.site.entity.PrintInfo>"--%>
<c:set var="printtitle">
    <spring:message code="sart.title.print.title" />
</c:set>
<div class="sart-print-pat-details">
    <button type="button" class="close close-button" data-dismiss="modal" aria-hidden="true" onclick="javascript:closerequestview();">Cancel</button>
    <button type="button" class="close print-button" data-dismiss="modal" aria-hidden="true" onclick="javascript:window.print();">Print</button>
    <div class="sart-print-title-top">${printtitle}</div>
    <div class="sart-print-title">
        <c:choose>
            <c:when test="${printInfo.printType == PrintType.Laboratory}">
                Laboratory Test Details
            </c:when>
            <c:when test="${printInfo.printType == PrintType.Invoice}">
                <c:if test="${attendance.paymentTypeID == 10}">
                    Cash Receipt/Invoice
                </c:if>
                <c:if test="${attendance.paymentTypeID != 10}">
                    Invoice
                </c:if> 
            </c:when>
            <c:otherwise>
                ${printInfo.printType} Details
            </c:otherwise>
        </c:choose>
        <span class="sart-serail-num">Serial # ${printInfo.serialNumber}</span>
    </div>
    <div class="sart-print-section">
        <div class="sart-print-header">

            <table class="sart-print-table">
                <tbody><tr><td>Patient Number</td><td>: </td><td>${patient.patientNumber}</td></tr>
                    <tr><td>Patient Name</td><td>: </td><td>${patient.getFullNames()}</td></tr>
                    <tr><td>D.o.B</td><td>: </td><td>${patient.getDoBFormattedDate()}</td></tr>
                    <tr><td>Patient ID #</td><td>: </td><td>${patient.getIdentity()}</td></tr>

                    <tr><td>Visit #</td><td>: </td><td>${attendance.attNumber}</td></tr>
                </tbody></table>
        </div>        
    </div>    
</div>