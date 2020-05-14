<#import "pager.ftlh" as p>

<nav class="mt-3" aria-label="breadcrumb">
    <ol class="breadcrumb" >
        <#if category??>
            <li class="breadcrumb-item">${category.name}</li>
            <#else>
            <li class="breadcrumb-item">Все рецепты</li>
        </#if>
        <li class="breadcrumb-item active" aria-current="page">Страница ${recipes.getNumber() + 1}</li>
    </ol>
</nav>
<div class="card-columns pt-1">
    <#list recipes.content as recipe>
        <a href="/recipe/${recipe.id}">
            <div class="card mb-3 shadow rounded zoom">
                <#if recipe.mainImage??>
                    <img src="/img/${recipe.mainImage}" class="card-img-top">
                </#if>
                <div class="card-body mask">
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