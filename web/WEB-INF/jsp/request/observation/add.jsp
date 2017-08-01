<%--@elvariable id="observationForm" type="co.ke.sart.site.form.ObservationForm"--%>
<%--@elvariable id="validationErrors" type="java.util.Set<javax.validation.ConstraintViolation>"--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/jsp/base.jspf" %>
<c:url value="/request/${observationForm.getAttendanceID()}/${observationForm.getRequestID()}/observation/add" var="posturl" />
<c:url value="/attendance/view/${observationForm.attendanceID}" var="cancelurl" />
<c:set var="formtitle">
    <spring:message code="sart.title.observation" />
</c:set>

<mt:model_master_slim cancleurl="${cancelurl}" modaltitle="${formtitle}">
    <jsp:attribute name="modal_content">
        <form:form modelAttribute="observationForm"  method="post" action="${posturl}" >
            <div class="box-body">
                <form:hidden path="observation.rowID" />
                <form:hidden path="attendanceID" />
                <form:hidden path="requestID" />            
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
                <div class="form-group">
                    <label for="Complaint">Complaint</label>
                    <form:textarea class="form-control" type="text" id="Complaint" rows="3"  path="observation.complaint" />
                    <form:errors path="observation.complaint" cssClass="errors" />             
                </div>     

                <div class="form-group">
                    <label for="provisionalDiagnosis">Patient History</label>
                    <form:textarea class="form-control" type="text" id="provisionalDiagnosis" rows="3"  path="observation.provisionalDiagnosis" />
                    <form:errors path="observation.provisionalDiagnosis" cssClass="errors" />             
                </div>              

                <div class="form-group">
                    <label for="Complaint">Remarks</label>
                    <form:textarea class="form-control" type="text" id="Remarks" rows="3"  path="observation.remark" />
                    <form:errors path="observation.remark" cssClass="errors" />             
                </div>       
            </div>
            <div class="box-footer">
                <button  type="button" onclick="javascript:location.href = '${cancelurl}'" class="btn btn-default">Cancel</button>
                <button type="submit" class="btn btn-primary">Save</button>
            </div>              

        </form:form>
    </jsp:attribute>
</mt:model_master_slim>