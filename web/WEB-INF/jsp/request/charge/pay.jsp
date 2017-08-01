<%--@elvariable id="paymentForm" type="co.ke.sart.site.form.PaymentForm"--%>
<%--@elvariable id="validationErrors" type="java.util.Set<javax.validation.ConstraintViolation>"--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/jsp/base.jspf" %>
<c:url value="/request/${paymentForm.getAttendanceID()}/${paymentForm.getRequestID()}/payment/add" var="posturl" />
<c:url value="/attendance/view/${paymentForm.getAttendanceID()}" var="cancelurl" />
<c:set var="formtitle">
    <spring:message code="sart.title.charge" /> - Payments
</c:set>
<mt:model_master_slim cancleurl="${cancelurl}" modaltitle="${formtitle}">
    <jsp:attribute name="modal_content">
        <form:form modelAttribute="paymentForm"  method="post" action="${posturl}" >
            <div class="box-body"> 
                <form:hidden path="payment.rowID" />
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
                    <label for="payment">Payment Method</label>
                    <form:select path="payment.paymentLovTypeID" id="payment" class="form-control">
                        <c:forEach items="${paymentForm.getPaymentLovTypeLOVs()}" var="lov">
                            <form:option value="${lov.getLovID()}">${lov.getLovVal()}</form:option>
                        </c:forEach>
                    </form:select>
                    <form:errors path="payment.paymentLovTypeID" cssClass="errors" />
                </div>             

                <div class="form-group">
                    <label for="paidAmount">Amount (Kes)</label>
                    <form:input class="form-control" type="number" id="paidAmount"   path="payment.paidAmount" />
                    <form:errors path="payment.paidAmount" cssClass="errors" />             
                </div>                   

                <div class="form-group">
                    <label for="receiptNumber">Receipt Number/Transaction ID</label>
                    <form:input class="form-control" type="text" id="receiptNumber"  path="payment.receiptNumber" />
                    <form:errors path="payment.receiptNumber" cssClass="errors" />             
                </div>                 

                <div class="form-group">
                    <label for="receiptNumber">Credit Due Date</label>
                    <form:input class="form-control" type="date" id="creditDueDate"  path="payment.creditDueDate" />
                    <form:errors path="payment.creditDueDate" cssClass="errors" />             
                </div>                   

                <div class="form-group">
                    <label for="note">Notes</label>
                    <form:textarea class="form-control" type="text" id="note" rows="3"  path="payment.note" />
                    <form:errors path="payment.note" cssClass="errors" />             
                </div>                 
           </div>
            <div class="box-footer">
                <button type="submit" class="btn btn-primary">Save</button>                
                <button  type="button" onclick="javascript:location.href = '${cancelurl}'" class="btn btn-default">Cancel</button>
            </div>                  
        </form:form>
    </jsp:attribute>
</mt:model_master_slim>