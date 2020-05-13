<#import "parts/common.ftlh" as c>

<@c.page>
    <div class="card-columns mt-4">
        <#list recipes as recipe>
            <a href="/recipe?id=${recipe.id}">
                <div class="card mb-3 shadow rounded">
                    <#if recipe.mainImage??>
                        <img src="/img/${recipe.mainImage}" class="card-img-top">
                    </#if>
                    <div class="card-body">
                        <h5 class="card-title mt-n2">${recipe.name}</h5>
                        <p class="card-text">${recipe.shortDescription}</p>
<#--                            <p class="card-text"><small class="text-muted">Last updated 3 mins ago</small></p>-->
                    </div>
                </div>
            </a>
        <#else>
            No recipe
        </#list>
    </div>
</@c.page>
