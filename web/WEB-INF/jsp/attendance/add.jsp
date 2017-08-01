<%--@elvariable id="attendanceForm" type="co.ke.sart.site.form.AttendanceForm"--%>
<%--@elvariable id="patient" type="co.ke.sart.site.model.Patient"--%>
<%--@elvariable id="paymentModes" type="co.ke.sart.site.enity.PaymentMode"--%>
<%--@elvariable id="validationErrors" type="java.util.Set<javax.validation.ConstraintViolation>"--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/jsp/base.jspf" %>
<c:url value="/attendance/add/${attendanceForm.patientID}" var="posturl" />
<c:url value="/patient/view/${attendanceForm.patientID}" var="cancelurl" />

<c:set var="formtitle">
    <spring:message code="sart.title.attendance" /> - New Attendance
</c:set>
<mt:model_master_slim cancleurl="${cancelurl}" modaltitle="${formtitle}">
    <jsp:attribute name="modal_content">
        <form:form modelAttribute="attendanceForm"  method="post" >
            <form:hidden path="rowID" />
            <form:hidden path="patientID" />
            <form:hidden path="paymentModes" />   
            <form:hidden path="attendanceTypes" />  
            <form:hidden path="patient.rowID" />
            <form:hidden path="patient.patientNumber" />
            <form:hidden path="patient.gender" />
            <form:hidden path="patient.patientContact" />
            <form:hidden path="patient.surname" />
            <form:hidden path="patient.forename" />
            <form:hidden path="patient.middleNames" />
            <form:hidden path="patient.dateOfBirth" />
            <form:hidden path="formAction" />
            <div class="sart_pat_top_bar sart-att-patient-info">
                <div class="patdisplay">
                    (${attendanceForm.patient.getPatientNumber()}) ${attendanceForm.patient.getFullNames()}, Age: ${attendanceForm.patient.getAge()}, Gender: ${attendanceForm.patient.getGender()} , Contact: ${attendanceForm.patient.getPatientContact()} 
                </div>
            </div>    
            <div class="box-body"> 

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
                    <label for="attType">Attendance Type *</label>
                    <form:select path="attType" id="attType" class="form-control">
                        <c:forEach items="${attendanceForm.attendanceTypes}" var="lov">
                            <form:option value="${lov.lovID}">${lov.lovVal}</form:option>
                        </c:forEach>
                    </form:select>
                    <form:errors path="attType" cssClass="errors" />
                </div>

                <div class="form-group">
                    <label for="paymentTypeID">Payment Mode *</label>
                    <form:select path="paymentTypeID" id="paymentTypeID" class="form-control">
                        <c:forEach items="${attendanceForm.paymentModes}" var="lov">
                            <form:option value="${lov.rowID}">${lov.name}</form:option>
                        </c:forEach>
                    </form:select>
                    <form:errors path="paymentTypeID" cssClass="errors" />
                </div>

                <div class="form-group">
                    <label for="insuranceNumber">Insurance #</label>
                    <form:input class="form-control" type="text" id="insuranceNumber"   path="insuranceNumber" />
                    <form:errors path="insuranceNumber" cssClass="errors" />             
                </div>  
            </div>
            <div class="box-footer">
                <button type="submit" class="btn btn-primary">Save</button>                
                <button  type="button" onclick="javascript:location.href = '${cancelurl}'" class="btn btn-default">Cancel</button>
            </div>                    
        </form:form>
    </jsp:attribute>
</mt:model_master_slim>