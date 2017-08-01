<%--@elvariable id="labTestForm" type="co.ke.sart.site.form.LabTestForm"--%>
<%--@elvariable id="validationErrors" type="java.util.Set<javax.validation.ConstraintViolation>"--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/jsp/base.jspf" %>
<c:url value="/request/${labTestForm.getAttendanceID()}/${labTestForm.getRequestID()}/labtest/add/" var="posturl" />
<c:url value="/attendance/view/${labTestForm.attendanceID}" var="cancelurl" />
<c:set var="formtitle">
    <spring:message code="sart.title.labtest" />
</c:set>
<mt:modal_master cancleurl="${cancelurl}" modaltitle="${formtitle}">
    <jsp:attribute name="modal_content">
        <form:form modelAttribute="labTestForm"  method="post" action="${posturl}" >
            <form:hidden  path="labTestLOVs" />
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
            <div class="sart-multi-col-form">
                <c:forEach items="${labTestForm.labTestLOVs}" var="lov"  varStatus="status">
                    <div class="sart_chk_fields">
                        <form:checkbox path="selectedLovs" value="${lov.lovID}" label="${lov.lovVal}" onclick="javascript:showhidenotes(this);" />
                        <div class="sart-req-small-input sart-req-shrink-height" id="divprocnotes"   style="display: none;">
                            <form:input path="labNotes[${status.index}]" title="Additional Instructions" />
                        </div>

                    </div>
                </c:forEach>                            
            </div>
            <div class="sart_add_button_div req-form-button-bottom-bar">
                <input type="submit" name="userrole-submit" id="userrole-submit" class="sart_add_buttons" value="Save" />
                <a  href="${cancelurl}"><input type="button" value="Cancel"  class="sart_add_buttons" /></a>
            </div>                    
        </form:form>
    </jsp:attribute>
</mt:modal_master>