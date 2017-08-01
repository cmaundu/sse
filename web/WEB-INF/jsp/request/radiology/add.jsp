<%--@elvariable id="radiologyForm" type="co.ke.sart.site.form.RadiologyForm"--%>
<%--@elvariable id="validationErrors" type="java.util.Set<javax.validation.ConstraintViolation>"--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/jsp/base.jspf" %>
<c:url value="/request/${radiologyForm.getAttendanceID()}/${radiologyForm.getRequestID()}/radiology/add" var="posturl" />
<c:url value="/request/${radiologyForm.getAttendanceID()}/${radiologyForm.getRequestID()}/radiology/add/additem" var="additem" />
<c:url value="/request/${radiologyForm.getAttendanceID()}/${radiologyForm.getRequestID()}/radilogy/add/clearitems" var="clearitems" />
<c:url value="/attendance/view/${radiologyForm.getAttendanceID()}" var="cancelurl" />
<c:set var="formtitle">
    <spring:message code="sart.title.radiology" /> - Request
</c:set>
<mt:modal_master cancleurl="${cancelurl}" modaltitle="${formtitle}">
    <jsp:attribute name="modal_content">
        <form:form modelAttribute="radiologyForm"  method="post" action="${posturl}" >
            <form:hidden  path="radiologyList" />
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

            <div class="req-form-button-top-bar">
                <input type="submit" name="userrole-submit" id="userrole-submit" formaction="${additem}" class="sart_add_buttons" value="Add" />
                <!--input type="submit" name="userrole-submit" id="userrole-submit" formaction="${clearitems}" class="sart_add_buttons" value="Clear All" /-->
            </div>


            <div class="req-form-inputs">
                <div class="sart-req-small-input">
                    Radiology Request *<br>
                    <form:hidden path="radiologyDefLOVs"/>
                    <form:select path="radiology.lovTypeVal" >
                        <c:forEach items="${radiologyForm.getRadiologyDefLOVs()}" var="lov">
                            <form:option value="${lov.getLovID()}||${lov.getLovVal()}">${lov.getLovVal()}</form:option>
                        </c:forEach>
                    </form:select>
                </div>
                <div class="sart-req-small-input">
                    Site*<br>
                    <form:input type="text"   path="radiology.site" /><br />
                    <form:errors path="radiology.site" cssClass="errors" /><br />
                </div>
                <div class="sart-req-small-input">
                    Note<br>
                    <form:input type="test"   path="radiology.note" /><br />
                    <form:errors path="radiology.note" cssClass="errors" /><br />
                </div>
            </div>   
            <div class="req-list-values">
                <table class="req-list-table">
                    <tbody id="req-list-body">
                        <tr><th>Name</th><th>Site</th><th>Note</th></tr>

                        <c:forEach items="${radiologyForm.radiologyList}" var="radiologyItem" varStatus="status">
                            <c:url value="/request/${radiologyForm.getAttendanceID()}/${radiologyForm.getRequestID()}/radiology/add/removeitem/${radiologyItem.rowID}" var="removeitemurl" />
                            <tr>
                                <td><form:hidden path="radiologyList[${status.index}].rowID"/>
                                    <form:hidden path="radiologyList[${status.index}].lovTypeLovID"/>
                                    <form:input readonly="true" path="radiologyList[${status.index}].lovTypeVal" />
                                </td> 
                                <td><form:input path="radiologyList[${status.index}].site" /></td>
                                <td><form:textarea rows="1" path="radiologyList[${status.index}].note" /></td>

                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>

            <div class="sart_add_button_div req-form-button-bottom-bar">
                <input type="submit" name="userrole-submit" id="userrole-submit" class="sart_add_buttons" value="Save" />
                <a  href="${cancelurl}"><input type="button" value="Cancel"  class="sart_add_buttons" /></a>
            </div>  

        </form:form>

    </jsp:attribute>
</mt:modal_master>