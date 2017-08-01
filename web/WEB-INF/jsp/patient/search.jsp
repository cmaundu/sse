<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/base.jspf" %>
<spring:message code="sart.title" var="sartTitle" />
<mt:master>
    <jsp:attribute name="content">
        <div class="att-screen-main">
            <div class="sart-att-request-list">
                <span class="sart-title">Patient List</span>
                <form:form method="post"  modelAttribute="searchForm">
                    <div>
                        <a href="<c:url value="/patient/add" />" class="btn btn-result" data-toggle="modal" data-target="#myModal"><i class="fa fa-plus"></i>New Patient</a>
                        <span>
                            <form:input class="sart-search-input" path="query" size="40" />
                            <input type="submit" class="sart-search-button" value="Search" />
                        </span>
                    </div>
                </form:form>  
                <table class="sart-att-req-table">

        <c:if test="${searchPerformed}">
            <c:choose>
                <c:when test="${patients.numberOfElements == 0}">
                    <i><spring:message code="message.patientSearch.none" /></i>
                </c:when>
                <c:otherwise>
                    Total Pages: <c:forEach begin="1" end="${patients.totalPages}" var="i">
                        <c:choose>
                            <c:when test="${(i - 1) == patients.number}">${i}</c:when>
                            <c:otherwise><a href="<c:url value="/patient/search">
                                       <c:param name="query" value="${searchForm.query}" />
                                       <c:param name="paging.page" value="${i}" />
                                       <c:param name="paging.size" value="10" />
                                   </c:url>">${i}</a></c:otherwise>
                        </c:choose>&nbsp;
                    </c:forEach><br />
                    <table class="sart-att-req-table">
                        <tr><th></th><th>Patient #</th><th>Name</th><th>ID</th><th>Age</th><th>Gender</th><th>Contact</th><th>Address</th><th>Created</th></tr>
              <c:forEach items="${patients.content}" var="pat">
                        <tr><td class="icon-button"><a href="<c:url value="/patient/edit/${pat.getEntity().getRowID()}" />" class="icon-button"><image src=" <c:url value="/resources/images/edit_icon.png" />" /></a></td>
                            <td><a href="<c:url value="/patient/view/${pat.getEntity().getRowID()}" />"> ${pat.getEntity().getPatientNumber()}</a></td>
                            <td>${pat.getEntity().getFullNames()}</td>
                            <td>${pat.getEntity().getIdentity()}</td>
                            <td>${pat.getEntity().getAge()}</td>
                            <td>${pat.getEntity().getGender()}</td>
                            <td>${pat.getEntity().getPatientContact()}</td>
                            <td>${pat.getEntity().getPatientAddress()}</td>
                            <td>${pat.getEntity().getCreatedDate()}</td>
                        </tr>
                    </c:forEach>                          

                    </table>
                </c:otherwise>
            </c:choose>
        </c:if>   
                </table>
            </div>           
        </div>

    </jsp:attribute>
</mt:master>