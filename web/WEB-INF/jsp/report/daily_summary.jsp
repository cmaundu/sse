<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/base.jspf" %>
<spring:message code="sart.title" var="sartTitle" />
<mt:master>
    <jsp:attribute name="content">
        <div class="att-screen-main">
            <div class="sart-att-request-list">  
                <span class="sart-title">Daily Summaries</span>
                <table class="sart-report-table">
                    <tbody>
                        <tr>
                            <th>Date</th><th>Total</th>
                                <c:forEach items="${payTypes}" var="payType">
                                <th>${payType}</th>
                                </c:forEach>
                        </tr>
                        <c:forEach items="${dates}" var="date">
                            <tr>
                                <td>${date}</td>                                    
                                <td class="sart-report-total">
                                    <fmt:setLocale value = "en_US"/>
                                    <fmt:formatNumber value = "${summary.getPay(date)}" type = "currency" currencySymbol=""/>
                                </td>
                                <c:forEach items="${payTypes}" var="payType">
                                    <td>
                                        <fmt:setLocale value = "en_US"/>
                                        <fmt:formatNumber value = "${summary.getPay(payType,date)}" type = "currency" maxFractionDigits="0" currencySymbol=""/>
                                    </td>
                                </c:forEach>
                            </tr>
                        </c:forEach>

                </table>
            </div>           
        </div>
    </jsp:attribute>
</mt:master>