<%--@elvariable id="drugList" type="java.util.List<co.ke.sart.site.entity.DrugDef>"--%>
<div class="icd-item icd-title">
    Select Drug to add
</div>   
<c:forEach items="${drugList}" var="drug">
    <div class="icd-item">
        <a href="javascript:selectdrug(${drug.rowID}, '${drug.drugName}')">${drug.drugName}</a>
    </div>
</c:forEach>

