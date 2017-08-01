<%@ include file="/WEB-INF/jsp/base.jspf" %>
<%@attribute name="modal_content" fragment="true" %>
<%@attribute name="cancleurl" required="false" %>
<%@attribute name="modaltitle" required="false" %>
<mt:master>
    <jsp:attribute name="content">
        <div id="sart-modal" class="modal" style="display:block">
            <div class="modal-content modal-slim">
                <div class="modal-header">
                    
                    <c:if test="${not empty modaltitle}">
                        <h4 class="modal-title" id="myModalLabel">${modaltitle}</h4>
                    </c:if>
                    <c:if test="${not empty cancleurl}">
                    <a  class="close" data-dismiss="modal" aria-hidden="true" href="${cancleurl}"  >×</a>   
                    </c:if>
                </div>      
                <div class="req-form box-body">
                    <div class="sart-pop-form" id="sart-admin-form-content" name="sart-admin-form-content">
                        <jsp:invoke fragment="modal_content"></jsp:invoke>
                        </div>

                    </div>
                    <div class="modal-footer">
                        <h4>Sart Frameworks</h4>
                    </div>
                </div>
            </div>
    </jsp:attribute>
</mt:master>