<#import "parts/common.ftlh" as c>

<@c.page>
    <h2 class="mt-3">${recipe.name}</h2>

    <div class="recipe-main-info border-bottom">
        <div class="recipe-leading-info">
            <#if recipe.mainImage??>
                <img src="/img/${recipe.mainImage}" class="card-img-top">
            </#if>
        </div>
        <div class="recipe-description-text mt-3 border-bottom">
            <p>
                ${recipe.description}
            </p>
        </div>
        <div class="recipe-meta-block ml-3">
            <div class="recipe-meta-wrap">
                <p class="recipe-meta-heading">Время приготовления</p>
                <p class="recipe-meta_prep-time mt-1">${recipe.cookTime}</p>
            </div>
            <div class="recipe-meta-wrap">
                <p class="recipe-meta-heading mt-3">Количество порций</p>
                <p class="recipe-meta_serving mt-1">${recipe.serving}</p>
            </div>
        </div>
    </div>

    <div class="recipe-ingredient-list-wrap mt-4 border-bottom">
        <h3 class="recipe-ingredients__heading">Ингедиенты</h3>
        <ul class="recipe-ingredients__list">
            <#list recipe.ingredients as ingedient>
                <li class="recipe-ingredients__list-item">${ingedient}</li>
            </#list>
        </ul>
    </div>

    <div class="recipe-method-wrap mt-4 border-bottom">
        <h3 class="recipe-method__heading">Способ приготовления</h3>
        <ol class="recipe-method__list mt-4">
            <#list recipe.methodSteps as step>
                <li class="recipe-method__list-item">
                    <p class="recipe-method__list-item-text">
                        ${step}
                    </p>
                </li>
            </#list>
        </ol>
    </div>
</@c.page>
