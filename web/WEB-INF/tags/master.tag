
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
    <!--    
    <aside class="main-sidebar">
        <section class="sidebar">
            <div class="user-panel">

            </div>
            <ul class="sidebar-menu" data-widget="tree">
                <li class="header">MAIN NAVIGATION</li>
                <li class="active treeview">
                    <a href="#">
                        <i class="fa fa-dashboard"></i> <span>Dashboard</span>
                        <span class="pull-right-container">
                            <i class="fa fa-angle-left pull-right"></i>
                        </span>
                    </a>
                    <ul class="treeview-menu">
                        <li class="active"><a href="index.html"><i class="fa fa-circle-o"></i> Dashboard v1</a></li>
                        <li><a href="index2.html"><i class="fa fa-circle-o"></i> Dashboard v2</a></li>
                    </ul>
                </li>
            </ul>
        </section>
    </aside>
    -->
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

            <a href="<c:url value="/attendance/" />" class="nodeco">
                <div class="sidemenuitem">
                    <div class="sidemenuimg"> <img src="<c:url value="/resources/images/icon_three_dot.png" />" /> </div>
                    <div class="sidemenutxt"> <spring:message code="sart.title.attendance.matter" /> </div>
                </div>
            </a> 

            <a href="<c:url value="/reports/" />"  class="nodeco">
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

            <a href="<c:url value="/administration/" />" class="nodeco">
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
    <div class="modal" id="request-modal-form" style="display:none" ></div>

 

</body>
</html>
