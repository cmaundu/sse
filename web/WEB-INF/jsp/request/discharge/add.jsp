<%--@elvariable id="dischargeForm" type="co.ke.sart.site.form.DischargeForm"--%>
<%--@elvariable id="validationErrors" type="java.util.Set<javax.validation.ConstraintViolation>"--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/jsp/base.jspf" %>
<c:url value="/request/${dischargeForm.getAttendanceID()}/${dischargeForm.getRequestID()}/discharge/add/" var="posturl" />
<c:url value="/attendance/view/${dischargeForm.getAttendanceID()}" var="cancelurl" />
<c:set var="formtitle">
    <spring:message code="sart.title.discharge" />
</c:set>

<mt:model_master_slim cancleurl="${cancelurl}" modaltitle="${formtitle}">
    <jsp:attribute name="modal_content">
        <form:form modelAttribute="dischargeForm"  method="post" action="${posturl}" >
            <div class="box-body"> 
                <form:hidden path="attendanceID" />
                <form:hidden path="requestID" />
                <form:hidden path="dischargeLOVs" />
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
                    <label for="typeInput">Type *</label>
                    <form:select path="lovTypeLovID" id="typeInput" class="form-control">
                        <c:forEach items="${dischargeForm.dischargeLOVs}" var="lov">
                            <form:option value="${lov.getLovID()}">${lov.getLovVal()}</form:option>
                        </c:forEach>
                    </form:select>
                    <form:errors path="lovTypeLovID" cssClass="errors" />
                </div>

                <div class="form-group">
                    <label for="typeInput">Notes</label>
                    <form:textarea class="form-control" type="text" rows="3"  path="note" />
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