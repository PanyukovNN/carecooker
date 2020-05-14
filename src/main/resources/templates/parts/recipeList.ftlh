<#import "pager.ftlh" as p>

<nav class="mt-3" aria-label="breadcrumb">
    <ol class="breadcrumb" >
        <li class="breadcrumb-item"><a href="#">Главная</a></li>
        <li class="breadcrumb-item active" aria-current="page">Страница ${recipes.getNumber() + 1}</li>
    </ol>
</nav>
<div class="card-columns pt-1">
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