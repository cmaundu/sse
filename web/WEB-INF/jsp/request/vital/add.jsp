<%--@elvariable id="vitalForm" type="co.ke.sart.site.form.VitalForm"--%>
<%--@elvariable id="validationErrors" type="java.util.Set<javax.validation.ConstraintViolation>"--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/jsp/base.jspf" %>
<c:url value="/request/${vitalForm.getAttendanceID()}/${vitalForm.getRequestID()}/vital/add/${vitalForm.vital.rowID}" var="posturl" />
<c:url value="/attendance/view/${vitalForm.attendanceID}" var="cancelurl" />
<c:set var="formtitle">
    <spring:message code="sart.title.vital" /> - New Vital
</c:set>

<mt:model_master_slim cancleurl="${cancelurl}" modaltitle="${formtitle}">
    <jsp:attribute name="modal_content">
        <form:form modelAttribute="vitalForm"  method="post" action="${posturl}" >
            <div class="box-body">            
                <form:hidden path="vital.rowID" />
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
                    <label for="prate">Pulse Rate (/min)</label>
                    <form:input class="form-control" type="number" id="prate"  path="vital.pulseRate" />
                    <form:errors path="vital.pulseRate" cssClass="errors" />             
                </div> 

                <div class="form-group">
                    <label for="bp">Blood Pressure (mm of Hg)</label>
                    <form:input class="form-control" type="text" id="bp"  path="vital.bloodPressure" />
                    <form:errors path="vital.bloodPressure" cssClass="errors" />             
                </div>  

                <div class="form-group">
                    <label for="temp">Temperature (C)</label>
                    <form:input class="form-control" type="number" id="temp"  path="vital.temperature" />
                    <form:errors path="vital.temperature" cssClass="errors" />             
                </div>                 

                <div class="form-group">
                    <label for="resp">Respiration (/min)</label>
                    <form:input class="form-control" type="text" id="resp"  path="vital.respiration" />
                    <form:errors path="vital.respiration" cssClass="errors" />             
                </div>  

                <div class="form-group">
                    <label for="height">Height (CM)</label>
                    <form:input class="form-control" type="number" id="height"  path="vital.height" />
                    <form:errors path="vital.height" cssClass="errors" />             
                </div>                   

                <div class="form-group">
                    <label for="weight">Weight (Kg)</label>
                    <form:input class="form-control" type="number" id="weight"  path="vital.weight" />
                    <form:errors path="vital.weight" cssClass="errors" />             
                </div>  

                <div class="form-group">
                    <label for="typeInput">Notes</label>
                    <form:textarea class="form-control" type="text" rows="2"  path="vital.note" />
                    <form:errors path="vital.note" cssClass="errors" />             
                </div>                 
            </div>
            <div class="box-footer">
                <button type="submit" class="btn btn-primary">Save</button>
                <button  type="button" onclick="javascript:location.href = '${cancelurl}'" class="btn btn-default">Cancel</button>                
            </div>                                     
        </form:form>
    </jsp:attribute>
</mt:model_master_slim>