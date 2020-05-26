<#import "parts/common.ftlh" as c>

<@c.page>
    <#list sections as section>
        <a href="/section/edit?id=${section.id}">
            <div class="section-header-parent shadow mt-3">
                <h5>${section.sectionName}</h5>
                <div class="section-header"
                     style="<#if section.image??>background-image: url('/img/${section.image}');</#if>"></div>
            </div>
        </a>
    </#list>

    <a href="/section/add">
        <button type="button" class="btn btn-primary mt-4">Добавить раздел</button>
    </a>
</@c.page>