<table class="sart-att-req-table">
    <tbody>
        <tr><th></th><th></th><th>Type</th><th>Created</th><th>Created By</th><th colspan="2" class="number">Amount (Kes)</th><th></th><th>Additional Info</th></tr>
    </tbody>
    <c:url value="/resources/images/edit_icon.png" var="editIcon" />
    <c:forEach items="${requests}" var="request">
        <c:url value="/request/view/${request.rowID}" var="viewurl" />
        <c:url value="/request/${request.attendanceID}/${request.rowID}/payment/add" var="payurl" />
        <c:url value="/request/${request.attendanceID}/${request.rowID}/labtest/fulfil" var="labfulfil" />
        <c:url value="/request/${request.attendanceID}/${request.rowID}/radiology/fulfil" var="radfulfil" />
        <c:url value="/request/${request.attendanceID}/${request.rowID}/procedure/fulfil" var="procfulfil" />
        <c:url value="/request/${request.attendanceID}/${request.rowID}/prescription/edit" var="editrx" />
        <c:url value="/request/${request.attendanceID}/${request.rowID}/prescription/dispense" var="dispenserx" />
        <c:url value="/resources/images/request_${request.requestType}.png" var="requestimg" />

        <c:url value="/request/${request.attendanceID}/${request.rowID}/charge/edit" var="editcharge" />  
        <c:url value="/request/${request.attendanceID}/${request.rowID}/observation/edit" var="editobsrv" />          

        <tr><td class="sart-req-icons">
                <a href="javascript:showrequestview(${request.rowID},'${viewurl}');">
                    <div class="sart_request_item_img">
                        <img class="sart_req_img sart_img" src="${requestimg}">
                    </div></a></td>
            <td class="icon-button">
                <c:choose>
                    <c:when test="${request.requestType == 7}">
                        <a href="${editrx}" class="icon-button">
                            <image src=" <c:url value="/resources/images/edit_icon.png" />" />
                        </a>           
                    </c:when>
                    <c:when test="${request.requestType == 15}">
                        <a href="${editcharge}" class="icon-button">
                            <image src=" <c:url value="/resources/images/edit_icon.png" />" />
                        </a>           
                    </c:when>  
                    <c:when test="${request.requestType == 5}">
                        <a href="${editobsrv}" class="icon-button">
                            <image src=" <c:url value="/resources/images/edit_icon.png" />" />
                        </a>           
                    </c:when>  

                    <c:otherwise>
                        <a href="#" class="icon-button">
                            <image src=" <c:url value="/resources/images/readonly.png" />" />
                        </a>

                    </c:otherwise>
                </c:choose>
            </td>
            <td>${request.title}</td>
            <td>${request.getCreatedDateTime()}</td>
            <td>${request.getCreatedByUser().fullNames}</td>
            <c:choose>
                <c:when test="${request.requestType == 3 || request.requestType == 14 || request.requestType == 5 || request.requestType == 2}">
                    <td class="sart-td-moreinfo" colspan="9" >${request.note}</td>
                </c:when>
                <c:otherwise>
                    <td class="number">
                        <fmt:setLocale value = "en_US"/>
                        <fmt:formatNumber value = "${request.amountCharged}" type = "currency" currencySymbol=""/>
                    </td>
                    <td>
                        <c:choose>
                            <c:when test="${(request.amountCharged ==0 || !request.chargeable)}" >
                                No Charges
                            </c:when>
                            <c:when test="${request.paid}" >
                                Fully Paid
                            </c:when>
                            <c:otherwise>
                                <a href="${payurl}" class="btn btn-pay" ><i class="fa fa-plus"></i>Pay</a>
                            </c:otherwise>
                        </c:choose></td>
                    <td>
                        <c:choose>
                            <c:when test="${((request.amountCharged ==0 || !request.chargeable) || request.paid)}">
                                <c:choose>
                                    <c:when test="${!request.fulfilled}">
                                        <c:choose>
                                            <c:when test="${request.requestType == 11}">
                                                <a href="${labfulfil}" class="btn btn-result" ><i class="fa fa-plus"></i> Submit Results</a>
                                            </c:when>
                                            <c:when test="${request.requestType == 9}">
                                                <a href="${radfulfil}" class="btn btn-result" ><i class="fa fa-plus"></i> Submit Results</a>
                                            </c:when>   
                                            <c:when test="${request.requestType == 8}">
                                                <a href="${procfulfil}" class="btn btn-result" ><i class="fa fa-plus"></i> Submit Results</a>
                                            </c:when>                             
                                            <c:when test="${request.requestType == 7}">
                                                <a href="${dispenserx}" class="btn btn-result" ><i class="fa fa-plus"></i> Dispense</a>
                                            </c:when> 
                                        </c:choose>                            
                                    </c:when>
                                    <c:otherwise>
                                        <a href="javascript:showrequestview(${request.rowID},'${viewurl}');" class="btn btn-view" ><i class="fa fa-plus"></i> View Results</a>
                                    </c:otherwise>
                                </c:choose>
                            </c:when>                      
                        </c:choose>
                    </td><td class="sart-td-moreinfo">${request.note}</td>

                </c:otherwise>
            </c:choose>

        </tr>
    </c:forEach>
</table>