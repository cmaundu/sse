<%--@elvariable id="diagnosisForm" type="co.ke.sart.site.form.DiagnosisForm"--%>
<%--@elvariable id="validationErrors" type="java.util.Set<javax.validation.ConstraintViolation>"--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/jsp/base.jspf" %>
<c:url value="/request/${diagnosisForm.getAttendanceID()}/${diagnosisForm.getRequestID()}/diagnosis/add" var="posturl" />
<c:url value="/request/${diagnosisForm.getAttendanceID()}/${diagnosisForm.getRequestID()}/diagnosis/add/additem" var="additem" />
<c:url value="/request/${diagnosisForm.getAttendanceID()}/${diagnosisForm.getRequestID()}/diagnosis/add/clearitems" var="clearitems" />
<c:url value="/attendance/view/${diagnosisForm.getAttendanceID()}" var="cancelurl" />
<c:url value="/request/listicd/" var="searchicd" />
<c:set var="formtitle">
    <spring:message code="sart.title.diagnosis" />
</c:set>
<mt:modal_master  cancleurl="${cancelurl}" modaltitle="${formtitle}">
    <jsp:attribute name="modal_content">
        <form:form modelAttribute="diagnosisForm"  method="post" action="${posturl}" >
            <form:hidden  path="diagnosisList" />
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
                    Diagnosis *<br>
                    <form:input type="text" id="description" path="diagnosis.description"  onkeyup="javascript:searchicd(this.value,'${searchicd}');" autocomplete="off" />
                    <form:hidden  path="diagnosis.icdCode" id="diag_code" />
                    <div class="sart-float-popup" id="diag-icd-lookup">
                    </div>
                </div>
                <div class="sart-req-small-input">
                    Diagnosis Site<br>
                    <form:input type="text"   path="diagnosis.site" /><br />
                    <form:errors path="diagnosis.site" cssClass="errors" /><br />
                </div>
                <div class="sart-req-small-input">
                    Diagnosis Comment<br>
                    <form:textarea type="text" rows="2"  path="diagnosis.note" /><br />
                    <form:errors path="diagnosis.note" cssClass="errors" /><br />
                </div>
            </div>   
            <div class="req-list-values">
                <table class="req-list-table req-form-table">
                    <tbody id="req-list-body">
                        <tr><th>Code</th><th>Diagnosis</th><th>Site</th><th>Comment</th><th></th></tr>

                        <c:forEach items="${diagnosisForm.diagnosisList}" var="diagnosisItem" varStatus="status">
                            <c:url value="/request/${diagnosisForm.getAttendanceID()}/${diagnosisForm.getRequestID()}/diagnosis/add/removeitem/${status.index}" var="removeitemurl" />
                            <tr>
                                <td><form:hidden path="diagnosisList[${status.index}].tempID"/>

                                    <form:input readonly="true" path="diagnosisList[${status.index}].icdCode" /></td> 
                                <td><form:input readonly="true" path="diagnosisList[${status.index}].description" /></td>
                                <td><form:input path="diagnosisList[${status.index}].site" /></td>
                                <td><form:textarea rows="1" path="diagnosisList[${status.index}].note" /></td>
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