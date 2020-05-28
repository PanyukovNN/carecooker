<div class="recipe-container">
    <div class="recipe-title">
        <h1 class="recipe-title-header">
            ${recipe.name}
        </h1>
    </div>

    <div class="recipe-meta mt-3">
        <div class="recipe-meta-wrap meta-delimiter">
            <div class="svg clock-svg"></div>
            <div class="recipe-meta_prep-time">
                ${recipe.cookTime}
            </div>
        </div>
<#--        <span class="recipe-meta-wrap meta-delimiter">-->
<#--            <div class="recipe-meta-heading">Сложность</div>-->
<#--            <div class="recipe-meta_serving mt-1">${recipe.complexity}</div>-->
<#--        </span>-->
        <div class="recipe-meta-wrap">
            <div class="svg serving-svg"></div>
            <div class="recipe-meta_serving">
                ${recipe.serving}
            </div>
        </div>
    </div>

    <div class="recipe-content mt-3">
        <div class="recipe-info-block">
            <#if recipe.mainImage??>
                <div class="recipe-main-image mt-3"
                     style="background-image: url('/img/${recipe.mainImage}');">
                        <div style="padding-top: 52%;"></div>
                </div>
                <#else>
                <div class="recipe-main-image mb-5"></div>
            </#if>

            <div class="recipe-description-text my-4">
                <p>
                    ${recipe.description}
                </p>
            </div>

            <div class="recipe-ingredient-list-wrap my-4">
                <h3 class="recipe-ingredients__heading">Ингедиенты</h3>
                <ul class="recipe-ingredients__list mt-3">
                    <#list recipe.ingredients as ingedient>
                        <li class="recipe-ingredients__list-item">${ingedient}</li>
                    </#list>
                </ul>
            </div>
            <div class="recipe-method-wrap my-4">
                <h3 class="recipe-method__heading">Способ приготовления</h3>
                <div class="recipe-method__list my-4">
                    <#list recipe.methodSteps as step>
<#--                        <li class="recipe-method__list-item my-5">-->
                        <div class="recipe-method__list-item-text my-5">
                        <span style="font-weight: bold;">${step?index + 1}.</span>
                        <span>
                            ${step}
                        </span>
                        </div>
<#--                        </li>-->
                    </#list>
                </div>
            </div>
            <div class="recipe-categories-wrap my-4 pb-3">
                <h3 class="recipe-categories__heading">Категории</h3>
                <#list recipe.categories as category>
                    <form class="category-item rounded-lg my-1 mr-1" onclick="this.submit();" method="post" action="/category">
                        <input type="hidden" name="category" class="form-control" value="${category.name}">
                        <div>${category.name}</div>
                    </form>
                </#list>
            </div>

            <div class="recipe-button-block">
                <a href="/recipe/edit?id=${recipe.id}">
                    <button type="button" class="btn btn-primary mt-4">Редактировать рецепт</button>
                </a>

                <a href="/recipe/delete?id=${recipe.id}"
                   onclick="return confirm('Вы действительно хотите удалить рецепт?');">
                    <button type="button" class="btn btn-danger mt-4">Удалить рецепт</button>
                </a>
            </div>
        </div>
    </div>

</div>