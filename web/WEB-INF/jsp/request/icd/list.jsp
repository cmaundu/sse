<%--@elvariable id="icdList" type="java.util.List<co.ke.sart.site.entity.ICD>"--%>
<div class="icd-item icd-title">
    Select Diagnosis to add
</div>   
<c:forEach items="${icdList}" var="icd">
    <div class="icd-item">
        <a href="javascript:addicd('${icd.code}', '${icd.shortDescription}')">${icd.shortDescription}</a>
    </div>
</c:forEach>

