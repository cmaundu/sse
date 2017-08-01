<%--@elvariable id="patientForm" type="co.ke.sart.site.form.PatientForm"--%>
<%--@elvariable id="validationErrors" type="java.util.Set<javax.validation.ConstraintViolation>"--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/jsp/base.jspf" %>
<c:url value="/administration/user/passwordedit" var="posturl" />
<c:url value="/administration/user" var="cancelurl" />
<c:set var="formtitle">
    <spring:message code="sart.title.user" /> - Password Reset
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
                 </div>
                <div class="sart-req-small-input">
                    Password<br />
                    <form:input type="password"   path="password" id="password" />
                </div> 
                <div class="sart-req-small-input">
                    Confirm Password<br />
                    <form:input type="password"   path="confirmPassword" id="cpassword"  />
                </div>                    

            </div>
            <div class="sart_add_button_div req-form-button-bottom-bar">
                <input type="submit" name="userrole-submit" id="userrole-submit" class="sart_add_buttons" value="Save" />
                <a  href="${cancelurl}"><input type="button" value="Cancel"  class="sart_add_buttons" /></a>
            </div>                    
        </form:form>
    </jsp:attribute>
</mt:modal_master>