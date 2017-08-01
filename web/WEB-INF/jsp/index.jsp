
<%@ include file="/WEB-INF/jsp/base.jspf" %>
<spring:message code="sart.title" var="sartTitle" />
<mt:master title="${sart.title}">
    <jsp:attribute name="content">
        <div class="att-screen-main">
            <table>
                <c:forEach items="${patients}" var="pat">
                    <tr><td class="icon-button"><a href="javascript:editForm('PATIENT',${pat.getRowID()});" class="icon-button"><image src=" <c:url value="/resources/images/edit_icon.png" />" /></a></td>
                        <td><a href="<c:url value="/patient/details?RowID=${pat.getRowID()}" />"> ${pat.getRowID()}</a></td>

                        <td>${pat.getFullNames()}</td>
                        <td>${pat.getIdentity()}</td>
                        <td>${pat.getAge()}</td>
                        <td>${pat.getGender()}</td>
                        <td>${pat.getPatientContact()}</td>
                        <td>${pat.getPatientAddress()}</td>
                        <td>${pat.getCreated()}</td>
                        <td>${pat.getRecordStatus()}</td>
                    </tr>
                </c:forEach>
            </table>            
        </div>
    </jsp:attribute>
</mt:master>


