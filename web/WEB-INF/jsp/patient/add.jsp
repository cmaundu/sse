<%--@elvariable id="patientForm" type="co.ke.sart.site.form.PatientForm"--%>
<%--@elvariable id="validationErrors" type="java.util.Set<javax.validation.ConstraintViolation>"--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/jsp/base.jspf" %>
<c:url value="/patient/add" var="posturl" />
<mt:modal_master>
    <jsp:attribute name="modal_content">
        <form:form modelAttribute="patientForm"  method="post" action="${posturl}" >
            <c:if test="${validationErrors != null}">
                <div class="errors">12
                    <ul>
                        <c:forEach items="${validationErrors}" var="error">
                            <li><c:out value="${error.message}" /></li>
                            </c:forEach>
                    </ul>
                </div>
            </c:if>
            <div class="req-form-inputs">
                <div class="sart-req-small-input">
                    <form:hidden path="rowID" />
                    Patient #<br />
                    <form:input path="patientNumber" readonly="true" />
                </div>
                <div class="sart-req-small-input">
                    Surname<br />
                    <form:input type="text"   path="surname" /><br />
                    <form:errors path="surname" cssClass="errors" /><br />
                    First Name<br />
                    <form:input type="text"   path="forename" /><br />
                    <form:errors path="forename" cssClass="errors" /><br />
                    Other Names<br />
                    <form:input type="text"   path="middleNames" /><br /><br />
                </div>
                <div class="sart-req-small-input">
                    ID #<br />
                    <form:input type="text"   path="docNumber" /><br />
                    <form:errors path="docNumber" cssClass="errors" /><br />
                    ID Type<br />
                    <form:input type="text"   path="docType" /><br /><br />
                </div>                 
                <div class="sart-req-small-input">                    
                    Date of Birth<br />
                    <form:input type="date" path="dateOfBirth" />
                </div>                 
                <div class="sart-req-small-input">                    
                    Gender<br />
                    <form:select path="gender" >
                        <option value="0" disabled>--Select Gender--</option>
                        <form:option value="Male">Male</form:option>
                        <form:option value="Female">Female</form:option>
                    </form:select><br /><br />
                </div>                 
                <div class="sart-req-small-input"> 
                    Marital Status<br />
                    <form:select path="maritalStatus">
                        <option value="0" disabled>Select Status</option>
                        <form:option value="Married">Married</form:option>
                        <form:option value="Single">Single</form:option>
                        <form:option value="Separated">Separated</form:option>
                        <form:option value="Divorced">Divorced</form:option>
                    </form:select>                  
                </div>                 
                <div class="sart-req-small-input">
                    Contact/Mobile #<br />
                    <form:input type="text" path="patientContact" /><br /><br />
                </div>  
                <div class="sart-req-small-input">
                    Address<br />
                    <form:input type="text" path="patientAddress" /><br /><br />
                </div>                 
            </div>
            <div class="sart_add_button_div req-form-button-bottom-bar">
                <input type="submit" name="userrole-submit" id="userrole-submit" class="sart_add_buttons" value="Save" />
                <a class="sart_add_buttons" href="<c:url value="/patient/list" />"><input type="button" value="Cancel"  class="sart_add_buttons" /></a>
            </div>                    
        </form:form>
    </jsp:attribute>
</mt:modal_master>