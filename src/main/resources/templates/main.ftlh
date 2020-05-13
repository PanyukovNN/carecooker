<#import "parts/common.ftlh" as c>

<@c.page>
    <div class="card-columns mt-4">
        <#list recipes as recipe>
            <a href="/recipe?id=${recipe.id}">
                <div class="card my-3">
                    <#if recipe.mainImage??>
                        <img src="/img/${recipe.mainImage}" class="card-img-top">
                    </#if>
                    <div class="m-2">
                        <h4>${recipe.name}</h4>
                        <span>${recipe.description}</span>
                    </div>
                </div>
            </a>
        <#else>
            No recipe
        </#list>
    </div>
</@c.page>
