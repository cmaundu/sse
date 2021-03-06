
<%@ include file="/WEB-INF/jsp/base.jspf" %>
<sec:authentication var="principal" property="principal" />

<%@attribute name="title" required="false" rtexprvalue="true" %>
<%@attribute name="content" fragment="true" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="content-type" content="text/html">
        <link href="<c:url value="/resources/css/styles.css" />" rel="stylesheet" />
        <link href="<c:url value="/resources/css/AdminLTE.min.css" />" rel="stylesheet" />
        <link href="<c:url value="/resources/css/bootstrap.min.css" />" rel="stylesheet" />
        <link rel="icon" type="image/ico"  href="<c:url value="/resources/images/hosi.ico" />" />
        <script src="<c:url value="/resources/scripts/formscript.js" />" ></script>
              
        <title><spring:message code="sart.title" /></title>
    </head>
   <body>
        <div class="topbar">
          <div class="raphaicon"> <img src="<c:url value="/resources/images/hospital.png" />" /> </div>
          <div class="titletextright"> <spring:message code="sart.title.top" /> </div>
          <div class="titletextleft"> ${title} Welcome ${pageContext.request.userPrincipal} | <a href="<c:url value="/logout"/>">Logout</a> </div>
        </div>
<div class="mainbody">
  <div class="sidebarmain"> 
    <a href="<c:url value="/dashboard" />" class="nodeco">
        <div class="sidemenuitem">
          <div class="sidemenuimg"> <img src="<c:url value="/resources/images/icon_three_dot.png" />" /> </div>
          <div class="sidemenutxt"> <spring:message code="sart.title.dashboard" /> </div>
        </div>
    </a>

    <a href="<c:url value="/patient/" />" class="nodeco">
        <div class="sidemenuitem">
          <div class="sidemenuimg"> <img src="<c:url value="/resources/images/icon_three_dot.png" />" /> </div>
          <div class="sidemenutxt"> <spring:message code="sart.title.patient" /> </div>
        </div>
    </a> 

    <a href="<c:url value="/attendance" />" class="nodeco">
        <div class="sidemenuitem">
          <div class="sidemenuimg"> <img src="<c:url value="/resources/images/icon_three_dot.png" />" /> </div>
          <div class="sidemenutxt"> <spring:message code="sart.title.attendance" /> </div>
        </div>
    </a> 

    <a href="reports" class="nodeco">
        <div class="sidemenuitem">
          <div class="sidemenuimg"> <img src="<c:url value="/resources/images/icon_three_dot.png" />" /> </div>
          <div class="sidemenutxt"><spring:message code="sart.title.reports" /></div>
        </div>
    </a> 

    <a href="<c:url value="/pharmacy" />" class="nodeco">
        <div class="sidemenuitem">
          <div class="sidemenuimg"> <img src="<c:url value="/resources/images/icon_three_dot.png" />" /> </div>
          <div class="sidemenutxt"> <spring:message code="sart.title.pharmacy" /> </div>
        </div>
    </a> 

    <a href="<c:url value="/billing" />" class="nodeco">
        <div class="sidemenuitem">
          <div class="sidemenuimg"> <img src="<c:url value="/resources/images/icon_three_dot.png" />" /> </div>
          <div class="sidemenutxt"> <spring:message code="sart.title.billing" /> </div>
        </div>
    </a>

    <a href="<c:url value="/administration" />" class="nodeco">
        <div class="sidemenuitem">
          <div class="sidemenuimg"> <img src="<c:url value="/resources/images/icon_three_dot.png" />" /> </div>
          <div class="sidemenutxt">Administration</div>
        </div>
    </a>  

    </div>
    <div class="sart-work-area" id="work-area" name="work-area"> 
        <jsp:invoke fragment="content"></jsp:invoke>
    </div>
</div>
    </body>
</html>
