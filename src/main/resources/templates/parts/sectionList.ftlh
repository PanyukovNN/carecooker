<#import "greeting.ftlh" as g>

<div class="main-list-block">
    <#include "greeting.ftlh" />

    <div class="row row-cols-2" style="display: flex;">
        <#list sections as section>
            <a class="col" href="/section/${section.id}">
                <div class="section-header-parent shadow my-4">
                    <h5>${section.name}</h5>
                    <div class="section-header"
                         style="<#if section.image??>background-image: url('/img/${section.image}');</#if>">
                        <div class="section-overlay"></div>
                    </div>
                </div>
            </a>
        </#list>
    </div>
</div>

