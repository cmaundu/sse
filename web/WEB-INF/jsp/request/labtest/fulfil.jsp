<%--@elvariable id="labTestForm" type="co.ke.sart.site.form.LabTestForm"--%>
<%--@elvariable id="validationErrors" type="java.util.Set<javax.validation.ConstraintViolation>"--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/jsp/base.jspf" %>
<c:url value="/request/${labTestForm.getAttendanceID()}/${labTestForm.getRequestID()}/labtest/fulfil/" var="posturl" />
<c:url value="/attendance/view/${labTestForm.attendanceID}" var="cancelurl" />
<c:set var="formtitle">
    <spring:message code="sart.title.labtest" /> - Results
</c:set>
<mt:modal_master cancleurl="${cancelurl}" modaltitle="${formtitle}">
    <jsp:attribute name="modal_content">
        <form:form modelAttribute="labTestForm"  method="post" action="${posturl}" >
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
                    <tr><th>Name</th><th>Note</th><th>Result</th><th>Result Note</th></tr>
                    <tbody id="req-list-body"> 
                        <c:forEach items="${labTestForm.labTestList}" var="labtest"  varStatus="status">
                            <form:hidden path="labTestList[${status.index}].rowID"/>
                            <form:hidden path="labTestList[${status.index}].labTypeLovID"/>
                            <tr>
                                <td><form:input type="text"  readonly="true" path="labTestList[${status.index}].labTypeLovVal"/></td>
                                <td><form:input type="text"  readonly="true" path="labTestList[${status.index}].note"/></td>
                                <td><form:textarea rows="3" class="allowinput"  path="labTestList[${status.index}].resultText"/></td>
                                <td><form:textarea rows="3"  class="allowinput" path="labTestList[${status.index}].resultNote"/></td>
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