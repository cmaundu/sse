<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/base.jspf" %>
<spring:message code="sart.title" var="sartTitle" />
<c:url value="/patient/search" var="searchUrl" />
<mt:master>
    <jsp:attribute name="content">
        <div class="att-screen-main">
            <div class="sart-att-request-list">
                <span class="sart-title">Open Attendance List</span>
                <c:choose>
                    <c:when test="${attendances.size() == 0}">
                        <i><spring:message code="message.patientView.noAttendances" /></i><br /><br />
                    </c:when>
                    <c:otherwise>

                        <table class="sart-att-req-table">
                            <tr><th></th><th>Visit #</th><th>Attendance Type</th><th>Payment Mode</th><th>Status</th><th>Insurance #</th><th>Doctor</th><th>Date</th><th>Created By</th></tr>
                                    <c:forEach items="${attendances}" var="attendance">
                                <tr>
                                    <td class="icon-button"><a href="<c:url value="/attendance/edit/${attendance.getRowID()}" />" class="icon-button"><image src=" <c:url value="/resources/images/edit_icon.png" />" /></a></td>
                                    <td><a href="<c:url value="/attendance/view/${attendance.getRowID()}" />"> ${attendance.attNumber}</a></td>
                                    <td>${attendance.getAttTypeName()}</td>
                                    <td>${attendance.getPaymentMode().getName()}</td>
                                    <td class="sart-att-status-${attendance.getAttStatus()}">${attendance.getAttStatus()}</td>
                                    <td>${attendance.getInsuranceNumber()}</td>
                                    <td>Not Assigned</td>
                                    <td>${attendance.getCreatedDateTime()}</td>
                                    <td>${attendance.createdByUser.getFullNames()}</td>
                                </tr>
                            </c:forEach>
                        </table>
                    </c:otherwise>
                </c:choose>
            </div>           
        </div>
    </jsp:attribute>
</mt:master>





