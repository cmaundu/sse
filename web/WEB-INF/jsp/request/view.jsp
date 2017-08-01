<%--@elvariable id="request" type="co.ke.sart.site.model.Request"--%>

<div class="modal-content">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true" onclick="javascript:closerequestview();">×</button>
        <h4 class="modal-title" id="myModalLabel">${request.title} View</h4>
    </div>      
    <div class="modal-body">
        <div class="sart-approval-req">
            <table class="sart-att-req-table drugs">
                <tr><th>Created</th><th>Created By</th><th>Updated</th><th>Updated By</th><th colspan="2" class="number">Amount (Kes)</th></tr>
                <tr>
                    <td>${request.getCreatedDateTime()}</td>
                    <td>${request.getCreatedByUser().fullNames}</td>
                    <td>${request.getUpdatedDateTime()}</td>
                    <td>${request.updatedByUser.fullNames}</td>                    
                    <td class="number">
                        <fmt:setLocale value = "en_US"/>
                        <fmt:formatNumber value = "${request.amountCharged}" type = "currency" currencySymbol=""/>
                    </td>
                    <td><c:choose>
                            <c:when test="${(request.amountCharged ==0 || !request.chargeable)}" >
                                No Charges
                            </c:when>
                            <c:when test="${request.paid}" >
                                Fully Paid
                            </c:when>
                            <c:otherwise>
                                Not Paid
                            </c:otherwise>
                        </c:choose></td>
                </tr>

            </table>
        </div>
        <%@include file="prescription/view.jsp" %>
        <%@include file="diagnosis/view.jsp" %>
        <%@include file="labtest/view.jsp" %>
        <%@include file="vital/view.jsp" %>
        <%@include file="radiology/view.jsp" %>
        <%@include file="procedure/view.jsp" %>
        <%@include file="observation/view.jsp" %>
        <%@include file="charge/viewpay.jsp" %>    


        <div class="sart_add_button_div">
            <input type="submit" value="Close" class="sart_add_buttons" onclick="javascript:closerequestview();">
        </div> 
    </div>
    <div class="modal-footer">
        <h4><spring:message code="sart.modal.footer.title" /></h4>
    </div>
</div>