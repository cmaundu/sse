<%--@elvariable id="prescriptionForm" type="co.ke.sart.site.form.PrescriptionForm"--%>
<%--@elvariable id="validationErrors" type="java.util.Set<javax.validation.ConstraintViolation>"--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/jsp/base.jspf" %>
<c:url value="/request/${prescriptionForm.getAttendanceID()}/${prescriptionForm.getRequestID()}/prescription/add" var="posturl" />
<c:url value="/request/${prescriptionForm.getAttendanceID()}/${prescriptionForm.getRequestID()}/prescription/add/additem" var="additem" />
<c:url value="/request/${prescriptionForm.getAttendanceID()}/${prescriptionForm.getRequestID()}/prescription/add/clearitems" var="clearitems" />
<c:url value="/attendance/view/${prescriptionForm.getAttendanceID()}" var="cancelurl" />
<c:url value="/request/searchdrug/" var="searchStr" />
<c:set var="formtitle">
    <spring:message code="sart.title.prescription" />
</c:set>
<mt:modal_master cancleurl="${cancelurl}" modaltitle="${formtitle}">
    <jsp:attribute name="modal_content">
        <form:form modelAttribute="prescriptionForm"  method="post" action="${posturl}" >
            <form:hidden  path="prescriptionList" />
            <c:if test="${validationErrors != null}">
                
                <div class="form-errors">
                    <h3 class="form-error-header">Errors</h3>
                    <ul>
                        <c:forEach items="${validationErrors}" var="error">
                            <li><c:out value="${error.getDefaultMessage()}" /></li>
                            </c:forEach>
                    </ul>
                </div>
            </c:if>
            <div class="req-form-button-top-bar">
                <input type="submit" name="userrole-submit" id="userrole-submit" formaction="${additem}" class="sart_add_buttons" value="Add" />
                <!--input type="submit" name="userrole-submit" id="userrole-submit" formaction="${clearitems}" class="sart_add_buttons" value="Clear All" /-->
            </div>


            <div class="req-form-inputs">
                <div class="sart-req-small-input">
                    Prescription *<br>
                    <form:input type="text" id="prescription" path="prescription.drugName"  onkeyup="javascript:searchdrug(this.value,'${searchStr}');" autocomplete="off" />
                    <form:hidden  path="prescription.drugDef.rowID" id="drugID" />
                    <div class="sart-float-popup" id="diag-drug-lookup">
                    </div>
                </div>
                <div class="sart-req-small-input">
                    Dose*<br>
                    <form:input type="text"   path="prescription.dose" /><br />
                    <form:errors path="prescription.dose" cssClass="errors" /><br />
                </div>
                <div class="sart-req-small-input">
                    Quantity<br>
                    <form:input type="number"   path="prescription.quantity" /><br />
                    <form:errors path="prescription.quantity" cssClass="errors" /><br />
                </div>
            </div>  

            <div class="req-list-values">
                <table class="req-list-table">
                    <tbody id="req-list-body">
                        <tr><th>Name</th><th>Dose</th><th>Quantity</th><th>Comment</th><th></th></tr>

                        <c:forEach items="${prescriptionForm.prescriptionList}" var="prescriptionItem" varStatus="status">
                            <c:url value="/request/${prescriptionForm.getAttendanceID()}/${prescriptionForm.getRequestID()}/prescription/add/removeitem/${prescriptionItem.rowID}" var="removeitemurl" />
                            <tr>
                                <td><form:hidden path="prescriptionList[${status.index}].drugDef.rowID"/>
                                    <form:hidden path="prescriptionList[${status.index}].rowID"/> 
                                    <form:input readonly="true" path="prescriptionList[${status.index}].drugName" /></td> 
                                <td><form:input path="prescriptionList[${status.index}].dose" /></td>
                                <td><form:input path="prescriptionList[${status.index}].quantity" /></td>
                                <td><form:textarea rows="1" path="prescriptionList[${status.index}].note" /></td>
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