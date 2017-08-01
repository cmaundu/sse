<%--@elvariable id="procedureForm" type="co.ke.sart.site.form.ProcedureForm"--%>
<%--@elvariable id="validationErrors" type="java.util.Set<javax.validation.ConstraintViolation>"--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/jsp/base.jspf" %>
<c:url value="/request/${procedureForm.getAttendanceID()}/${procedureForm.getRequestID()}/procedure/fulfil" var="posturl" />
<c:url value="/attendance/view/${procedureForm.attendanceID}" var="cancelurl" />
<c:set var="formtitle">
    <spring:message code="sart.title.procedure" /> - Fill Results
</c:set>
<mt:modal_master cancleurl="${cancelurl}" modaltitle="${formtitle}">
    <jsp:attribute name="modal_content">
        <form:form modelAttribute="procedureForm"  method="post" action="${posturl}" >
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
                    <tr><th>Name</th><th>Note</th><th>Fulfilment</th><th>Fulfilment Note</th></tr>
                    <tbody id="req-list-body"> 
                        <c:forEach items="${procedureForm.procedureList}" var="procedure"  varStatus="status">
                            <form:hidden path="procedureList[${status.index}].rowID"/>
                            <form:hidden path="procedureList[${status.index}].lovTypeLovID"/>
                            <tr>
                                <td><form:input type="text"  readonly="true" path="procedureList[${status.index}].lovTypeVal"/></td>
                                <td><form:input type="text"  readonly="true" path="procedureList[${status.index}].note"/></td>
                                <td><form:textarea rows="2" class="allowinput"  path="procedureList[${status.index}].resultText"/></td>
                                <td><form:textarea rows="2"  class="allowinput" path="procedureList[${status.index}].resultNote"/></td>
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