<%--@elvariable id="labTestForm" type="co.ke.sart.site.form.LabTestForm"--%>
<%--@elvariable id="validationErrors" type="java.util.Set<javax.validation.ConstraintViolation>"--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/jsp/base.jspf" %>
<c:url value="/request/${radiologyForm.getAttendanceID()}/${radiologyForm.getRequestID()}/radiology/fulfil" var="posturl" />
<c:url value="/attendance/view/${radiologyForm.attendanceID}" var="cancelurl" />
<c:set var="formtitle">
    <spring:message code="sart.title.radiology" /> - Submit Results
</c:set>
<mt:modal_master cancleurl="${cancelurl}" modaltitle="${formtitle}">
    <jsp:attribute name="modal_content">
        <form:form modelAttribute="radiologyForm"  method="post" action="${posturl}" >
            <form:hidden path="requestID" />
            <form:hidden path="attendanceID" />
            <c:if test="${validationErrors != null}">
                <div class="errors">
                    <ul>
                        <c:forEach items="${validationErrors}" var="error">
                            <li><c:out value="${error.message}" /></li>
                            </c:forEach>
                    </ul>
                </div>
            </c:if>
            <div class="sart-approval-req">
                <table class="drugs">
                    <tr><th>Name</th><th>Note</th><th>Site</th><th>Result</th><th>Result Note</th></tr>
                    <tbody id="req-list-body"> 
                        <c:forEach items="${radiologyForm.radiologyList}" var="radiology"  varStatus="status">
                            <form:hidden path="radiologyList[${status.index}].rowID"/>
                            <form:hidden path="radiologyList[${status.index}].lovTypeLovID"/>
                            <tr>
                                <td><form:input type="text"  readonly="true" path="radiologyList[${status.index}].lovTypeVal"/></td>
                                <td><form:input type="text"  readonly="true" path="radiologyList[${status.index}].note"/></td>
                                 <td><form:input type="text"  readonly="true" path="radiologyList[${status.index}].site"/></td>
                                <td><form:textarea rows="3" class="allowinput"  path="radiologyList[${status.index}].resultText"/></td>
                                <td><form:textarea rows="3"  class="allowinput" path="radiologyList[${status.index}].resultNote"/></td>
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