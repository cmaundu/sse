<%--@elvariable id="prescriptions" type="java.util.List<co.ke.sart.site.entity.Prescription>"--%>
<%--@elvariable id="prescription" type="co.ke.sart.site.entity.Prescription>"--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/jsp/base.jspf" %>
<c:url value="/request/${prescriptionForm.getAttendanceID()}/${prescriptionForm.getRequestID()}/prescription/edit" var="posturl" />
<c:url value="/attendance/view/${prescriptionForm.getAttendanceID()}" var="cancelurl" />
<c:set var="formtitle">
    <spring:message code="sart.title.prescription" /> - Edit
</c:set>
<mt:modal_master cancleurl="${cancelurl}" modaltitle="${formtitle}">
    <jsp:attribute name="modal_content">
        <form:form modelAttribute="prescriptionForm"  method="post" action="${posturl}" >
            <form:hidden  path="prescriptionList" />
            <form:hidden path="requestID" />
            <form:hidden path="attendanceID" />
            <div class="req-list-values">
                <table class="req-list-table">
                    <tbody id="req-list-body">
                        <tr><th>Drug</th><th>Dose</th><th>qty</th><th>Dispensed</th><th>Comment</th><th></th></tr>

                        <c:forEach items="${prescriptionForm.prescriptionList}" var="prescriptionItem" varStatus="status">
                            <c:url value="/request/${prescriptionForm.getAttendanceID()}/${prescriptionForm.getRequestID()}/prescription/add/removeitem/${prescriptionItem.rowID}" var="removeitemurl" />
                            <tr>
                                <td><form:hidden path="prescriptionList[${status.index}].drugDef"/><form:hidden path="prescriptionList[${status.index}].rowID"/> 
                                    ${prescriptionItem.drugDef.drugName}</td> 
                                <td><form:input path="prescriptionList[${status.index}].dose" /></td>
                                <td><form:input type="number" path="prescriptionList[${status.index}].quantity" /></td>
                                <td><form:checkbox disabled="true"  path="prescriptionList[${status.index}].dispensed" /></td>
                                <td><form:textarea rows="2" path="prescriptionList[${status.index}].note" /></td>
                                <td class="sart-table-button"><input type="submit" formaction="${removeitemurl}" value="Remove" />
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>  
            <div class="sart_add_button_div req-form-button-bottom-bar">
                <input type="submit" name="userrole-submit" id="userrole-submit" class="sart_add_buttons" value="Save" />
                <a  href="${cancelurl}"><input type="button" value="Cancel"  class="sart_add_buttons" /></a>
            </div> 
        </form:form>
    </jsp:attribute>
</mt:modal_master>