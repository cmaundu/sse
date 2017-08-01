
<%@ include file="/WEB-INF/jsp/base.jspf" %>
<spring:message code="sart.title" var="sartTitle" />
<mt:master title="${sart.title}">
    <jsp:attribute name="content">
        <div class="accessdenied">
            Access denied for this task.
        </div>
    </jsp:attribute>
</mt:master>


