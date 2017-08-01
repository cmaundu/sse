
<%@ include file="/WEB-INF/jsp/base.jspf" %>
<spring:message code="sart.title" var="sartTitle" />
<mt:plain_master title="${sart.title}">
    <jsp:body>
        <jsp:doBody />
    </jsp:body>
</mt:plain_master>
