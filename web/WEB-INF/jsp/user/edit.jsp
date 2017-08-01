<%--@elvariable id="patientForm" type="co.ke.sart.site.form.PatientForm"--%>
<%--@elvariable id="validationErrors" type="java.util.Set<javax.validation.ConstraintViolation>"--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/jsp/base.jspf" %>
<c:url value="/administration/user/add" var="posturl" />
<c:url value="/administration/user" var="cancelurl" />
<c:set var="formtitle">
    <spring:message code="sart.title.user" /> - Edit User
</c:set>
<mt:modal_master cancleurl="${cancelurl}" modaltitle="${formtitle}">
    <jsp:attribute name="modal_content">
        <form:form modelAttribute="userForm"  method="post" action="${posturl}" >
            <form:hidden path="formAction" />
            <form:hidden path="user.rowID" />
            <form:hidden path="roles" />
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
                    Employee Number<br />
                    <form:input readonly="true" type="text"   path="user.ekNumber" /><br /><br />
                    User Name<br />
                    <form:input readonly="true" type="text"   path="user.username" /><br />
                    <form:errors readonly="true" path="user.username" cssClass="errors" /><br />
                    Status<br />
                    <form:select path="user.recordStatus" >
                        <option value="0" disabled>Select Status</option>
                        <form:option value="ACTIVE">Active</form:option>
                        <form:option value="INACTIVE">Disabled</form:option>
                        <form:option value="SUSPENDED">Suspended</form:option>
                    </form:select><br /><br />

                    Role<br />
                    <form:select path="user.role.rowID">
                        <option value="None" disabled>Select Role</option>
                        <c:forEach items="${userForm.roles}" var="role">
                            <form:option value="${role.rowID}">${role.roleDesc}</form:option>
                        </c:forEach>
                    </form:select>                  
                 </div>
                    
                <div class="sart-req-small-input">
                    Surname<br />
                    <form:input type="text"   path="user.surname" /><br />
                    <form:errors path="user.surname" cssClass="errors" /><br />
                    First Name<br />
                    <form:input type="text"   path="user.forename" /><br />
                    <form:errors path="user.forename" cssClass="errors" /><br />
                    Other Names<br />
                    <form:input type="text"   path="user.middleNames" /><br /><br />
                </div>
                <div class="sart-req-small-input">
                    ID #<br />
                    <form:input type="text"   path="user.docNumber" /><br />
                    <form:errors path="user.docNumber" cssClass="errors" /><br />
                    ID Type<br />
                    <form:input type="text"   path="user.docType" /><br /><br />
                </div>                 
              
                <div class="sart-req-small-input">                    
                    Gender<br />
                    <form:select path="user.gender" >
                        <option value="Unspecified" disabled>--Select Gender--</option>
                        <form:option value="Male">Male</form:option>
                        <form:option value="Female">Female</form:option>
                    </form:select><br /><br />
                </div>                 
              
            </div>
            <div class="sart_add_button_div req-form-button-bottom-bar">
                <input type="submit" name="userrole-submit" id="userrole-submit" class="sart_add_buttons" value="Save" />
                <a  href="${cancelurl}"><input type="button" value="Cancel"  class="sart_add_buttons" /></a>
            </div>                    
        </form:form>
    </jsp:attribute>
</mt:modal_master>