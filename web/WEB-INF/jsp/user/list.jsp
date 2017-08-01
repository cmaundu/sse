<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/base.jspf" %>
<spring:message code="sart.title" var="sartTitle" />
<mt:master>
    <jsp:attribute name="content">
        <div class="att-screen-main">
            <div class="sart-att-request-list">  
                 <span class="sart-title">User List</span>
                <a href="<c:url value="/administration/user/add" />" class="btn btn-result" data-toggle="modal" data-target="#myModal"><i class="fa fa-plus"></i>New User</a>
                <a href="<c:url value="/administration/roles" />" class="btn btn-view" data-toggle="modal" data-target="#myModal"><i class="fa fa-plus"></i>Roles</a>
                <table class="sart-att-req-table">
                    <tr><th></th><th></th><th>User Name</th><th>Roles</th><th>Status</th><th>Full Names</th><th>Emp #</th><th>Last Login</th><th>Created On</th></tr>

                    <c:forEach items="${users}" var="item">
                        <tr><td class="icon-button"><a href="<c:url value="/administration/user/edit/${item.getRowID()}" />" class="icon-button"><image src=" <c:url value="/resources/images/edit_icon.png" />" /></a></td>
                            <td class="icon-button"><a href="<c:url value="/administration/user/passwordedit/${item.getRowID()}" />" class="icon-button"><image src=" <c:url value="/resources/images/password_reset.png" />" /></a></td>
                            <td>${item.username}</td>
                            <td>${item.role.roleDesc}</td>
                            <td>${item.recordStatus}</td>
                            <td>${item.getFullNames()}</td>
                            <td>${item.ekNumber}</td>
                            <td>${item.getLastLoginDateTime()}</td>
                            <td>${item.getCreatedDateTime()}</td></tr>
                        </c:forEach>
                </table>
            </div>           
        </div>
    </jsp:attribute>
</mt:master>




