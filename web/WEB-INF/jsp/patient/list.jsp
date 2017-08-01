<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/base.jspf" %>
<spring:message code="sart.title" var="sartTitle" />
<c:url value="/patient/search" var="searchUrl" />
<mt:master>
    <jsp:attribute name="content">
        <div class="att-screen-main">
            <div class="sart-att-request-list">
                <span class="sart-title">Patient List</span>
                <form:form method="post"  modelAttribute="searchForm" action="${searchUrl}">
                    <div>
                        <a href="<c:url value="/patient/add" />" class="btn btn-result" data-toggle="modal" data-target="#myModal"><i class="fa fa-plus"></i>New Patient</a>
                        <span>
                            <form:input class="sart-search-input" path="query" size="40" />
                            <input type="submit" class="sart-search-button" value="Search" />
                        </span>
                    </div>
                </form:form>   
                <table class="sart-att-req-table">
                    <tr><th></th><th>Patient #</th><th>Name</th><th>ID</th><th>Age</th><th>Gender</th><th>Contact</th><th>Address</th><th>Created</th></tr>

                    <c:forEach items="${patients}" var="pat">
                        <tr><td class="icon-button"><a href="<c:url value="/patient/edit/${pat.getRowID()}" />" class="icon-button"><image src=" <c:url value="/resources/images/edit_icon.png" />" /></a></td>
                            <td><a href="<c:url value="/patient/view/${pat.getRowID()}" />"> ${pat.getPatientNumber()}</a></td>
                            <td>${pat.getFullNames()}</td>
                            <td>${pat.getIdentity()}</td>
                            <td>${pat.getAge()}</td>
                            <td>${pat.getGender()}</td>
                            <td>${pat.getPatientContact()}</td>
                            <td>${pat.getPatientAddress()}</td>
                            <td>${pat.getCreatedDate()}</td>
                        </tr>
                    </c:forEach>
                </table>
            </div>           
        </div>
    </jsp:attribute>
</mt:master>




