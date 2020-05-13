<#import "pager.ftlh" as p>

<div class="card-columns pt-4">
    <#list recipes.content as recipe>
        <a href="/recipe?id=${recipe.id}">
            <div class="card mb-3 shadow rounded">
                <#if recipe.mainImage??>
                    <img src="/img/${recipe.mainImage}" class="card-img-top">
                </#if>
                <div class="card-body">
                    <h5 class="card-title mt-n2">${recipe.name}</h5>
                    <p class="card-text">${recipe.shortDescription}</p>
<#--                    <p class="card-text"><small class="text-muted">Last updated 3 mins ago</small></p>-->
                </div>
            </div>
        </a>
    <#else>
        На данной странице нет рецептов
    </#list>
</div>

<@p.pager url recipes/>