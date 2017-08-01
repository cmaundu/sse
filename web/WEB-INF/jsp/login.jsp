<%--@elvariable id="loginFailed" type="java.lang.Boolean"--%>
<%--@elvariable id="validationErrors" type="java.util.Set<javax.validation.ConstraintViolation>"--%>


<%@ include file="/WEB-INF/jsp/base.jspf" %>
<spring:message code="sart.title" var="sartTitle" />
<spring:message code="title.login" var="loginTitle" />
<mt:plain_master title="${sart.title}">
    <jsp:attribute name="content">
        <div class="login-div">
            <c:if test="${param.containsKey('loginFailed')}">
                <b class="errors"><spring:message code="error.login.failed" /></b><br />
            </c:if><c:if test="${param.containsKey('loggedOut')}">
                <i><spring:message code="message.login.loggedOut" /></i><br /><br />
            </c:if>
            <spring:message code="message.login.instruction" /><br /><br />
            <form:form method="post" modelAttribute="loginForm" autocomplete="off">

                <form:label path="username"><spring:message code="field.login.username" /></form:label><br />
                <form:input path="username" autocomplete="off" /><br />
                <form:label path="password"><spring:message code="field.login.password" /></form:label><br />
                <form:password path="password" autocomplete="off" /><br />
                <input type="submit" value="<spring:message code="field.login.submit" />" />

            </form:form>
        </div>
    </jsp:attribute>
</mt:plain_master>


