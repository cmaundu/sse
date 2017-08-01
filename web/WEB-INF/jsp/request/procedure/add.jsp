<%--@elvariable id="procedureForm" type="co.ke.sart.site.form.ProcedureForm"--%>
<%--@elvariable id="validationErrors" type="java.util.Set<javax.validation.ConstraintViolation>"--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/jsp/base.jspf" %>
<c:url value="/request/${procedureForm.getAttendanceID()}/${procedureForm.getRequestID()}/procedure/add/" var="posturl" />
<c:url value="/attendance/view/${procedureForm.attendanceID}" var="cancelurl" />
<c:set var="formtitle">
    <spring:message code="sart.title.procedure" />
</c:set>
<mt:modal_master cancleurl="${cancelurl}" modaltitle="${formtitle}">
    <jsp:attribute name="modal_content">
        <form:form modelAttribute="procedureForm"  method="post" action="${posturl}" >
            <form:hidden  path="procedureDefLOVs" />
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
            <div class="sart-multi-col-form-proc">
                <c:forEach items="${procedureForm.procedureDefLOVs}" var="lov"  varStatus="status">
                    <div class="sart_chk_fields">
                        <form:checkbox path="selectedLovs" value="${lov.lovID}" label="${lov.lovVal}" onclick="javascript:showhidenotes(this);" />
                        <div class="sart-req-small-input sart-req-shrink-height" id="divprocnotes"   style="display: none;">
                            <form:input path="procNotes[${status.index}]" title="Additional Instructions" />
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