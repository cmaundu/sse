<%--@elvariable id="chargeForm" type="co.ke.sart.site.form.ChargeForm"--%>
<%--@elvariable id="validationErrors" type="java.util.Set<javax.validation.ConstraintViolation>"--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/jsp/base.jspf" %>
<c:url value="/request/${chargeForm.getAttendanceID()}/${chargeForm.getRequestID()}/charge/add" var="posturl" />
<c:url value="/attendance/view/${chargeForm.getAttendanceID()}" var="cancelurl" />
<c:set var="formtitle">
    <spring:message code="sart.title.charge" />
</c:set>

<mt:model_master_slim cancleurl="${cancelurl}" modaltitle="${formtitle}">

        <jsp:attribute name="modal_content">
            <form:form modelAttribute="chargeForm"  method="post" action="${posturl}" >
                <div class="box-body"> 
                <form:hidden path="rowID" />
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
                    <label for="typeInput">Charge Type *</label>
                    <form:select path="requestSubType" id="typeInput" class="form-control">
                        <c:forEach items="${chargeForm.getRequestSubTypeLOVs()}" var="lov">
                            <form:option value="${lov.getLovID()}">${lov.getLovVal()}</form:option>
                        </c:forEach>
                    </form:select>
                    <form:errors path="requestSubType" cssClass="errors" />
                </div>            
                <div class="form-group">
                    <label for="amountCharged">Amount (Kes)</label>
                    <form:input class="form-control" type="number" id="amountCharged"   path="amountCharged" />
                    <form:errors path="amountCharged" cssClass="errors" />             
                </div>              

                <div class="form-group">
                    <label for="note">Notes</label>
                    <form:textarea class="form-control" type="text" id="note" rows="3"  path="note" />
                    <form:errors path="note" cssClass="errors" />             
                </div>  
            </div>
            <div class="box-footer">
                <button type="submit" class="btn btn-primary">Save</button>                
                <button  type="button" onclick="javascript:location.href = '${cancelurl}'" class="btn btn-default">Cancel</button>
            </div>                    
        </form:form>
    </jsp:attribute>
</mt:model_master_slim>