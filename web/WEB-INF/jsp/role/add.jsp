<%--@elvariable id="patientForm" type="co.ke.sart.site.form.PatientForm"--%>
<%--@elvariable id="validationErrors" type="java.util.Set<javax.validation.ConstraintViolation>"--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/jsp/base.jspf" %>
<c:url value="/administration/role/add" var="posturl" />
<c:url value="/administration/role" var="cancelurl" />
<c:set var="formtitle">
    <spring:message code="sart.title.role" /> - New Role
</c:set>
<mt:modal_master cancleurl="${cancelurl}" modaltitle="${formtitle}">
    <jsp:attribute name="modal_content">
        <form:form modelAttribute="roleForm"  method="post" action="${posturl}" >
            <form:hidden path="formAction" />
            <form:hidden path="role.rowID" />
            <form:hidden path="permissions" />
            <c:if test="${validationErrors != null}">

                <div class="form-errors">
                    <h3 class="form-error-header">Errors</h3>
                    <ul>
                        <c:forEach items="${validationErrors}" var="error">
                            <li><c:out value="${error.getDefaultMessage()}" /></li>
                            </c:forEach>
                    </ul>
                </div>
            </c:if>
            <div class="req-form-inputs">
                <div class="sart-req-small-input">
                    Role Name<br />
                    <form:input type="text"   path="role.roleName" /><br />
                    <form:errors path="role.roleName" cssClass="errors" />
                </div>
                <div class="sart-req-small-input">                    
                    Description<br />
                    <form:input type="text"   path="role.roleDesc" /><br />
                    <form:errors path="role.roleDesc" cssClass="errors" />
                </div>
                <div class="sart-req-small-input">

                </div>                

            </div>
            <div class="req-wide-table-input">
                <table class="sart-user-role req-list-table">                        
                    <tr><th>Permission</th><th>View</th><th>Add</th><th>Edit</th><th>Del</th></tr>
                            <c:forEach items="${roleForm.permissions}" var="pmssn" varStatus="status">
                        <tr><td>${pmssn.desc}</td>
                            <td><form:checkbox path="viewPermissions"  value="${pmssn.rowID}"/> </td>
                            <td><form:checkbox path="addPermissions"  value="${pmssn.rowID}" /></td>
                            <td><form:checkbox path="editPermissions"   value="${pmssn.rowID}" /></td>
                            <td><form:checkbox path="deletePermissions"  value="${pmssn.rowID}" /></td>
                        </tr>
                    </c:forEach>
                </table>   
            </div>
            <div class="sart_add_button_div req-form-button-bottom-bar">
                <input type="submit" name="userrole-submit" id="userrole-submit" class="sart_add_buttons" value="Save" />
                <a  href="${cancelurl}"><input type="button" value="Cancel"  class="sart_add_buttons" /></a>
            </div>                    
        </form:form>
    </jsp:attribute>
</mt:modal_master>