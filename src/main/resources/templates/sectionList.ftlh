<#import "parts/common.ftlh" as c>

<@c.page>
    <#list sectionCategories as sectionCategory>
        <a href="/section/edit?id=${sectionCategory.id}">
            <div class="section-header-parent shadow mt-3">
                <h5>${sectionCategory.sectionName}</h5>
                <div class="section-header" style="<#if sectionCategory.sectionImage??>background-image: url('/img/${sectionCategory.sectionImage}');</#if>"></div>
            </div>
        </a>
    </#list>

    <a href="/section/add">
        <button type="button" class="btn btn-primary mt-4">Добавить раздел</button>
    </a>
</@c.page>