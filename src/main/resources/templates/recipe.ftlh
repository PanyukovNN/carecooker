<#import "parts/common.ftlh" as c>

<@c.page>
    <div class="recipe-main-block">
        <div class="recipe-info">
            <h2 class="mt-3">${recipe.name}</h2>
            <#if recipe.mainImage??>
                <div class="recipe-main-image mt-4"
                     style="background-image: url('/img/${recipe.mainImage}');">
                        <div style="padding-top: 56.25%;"></div>
                </div>
                <#else>
                <div class="mb-5"></div>
            </#if>
            <div class="recipe-description-text mt-3 border-bottom">
                <p>
                    ${recipe.description}
                </p>
            </div>
            <div class="recipe-meta mt-3 border-bottom">
                <div class="recipe-meta-wrap">
                    <p class="recipe-meta-heading">Время приготовления</p>
                    <p class="recipe-meta_prep-time mt-1">${recipe.cookTime}</p>
                </div>
                <div class="recipe-meta-wrap">
                    <p class="recipe-meta-heading mt-3">Количество порций</p>
                    <p class="recipe-meta_serving mt-1">${recipe.serving}</p>
                </div>
            </div>
            <div class="recipe-ingredient-list-wrap mt-3 border-bottom">
                <h3 class="recipe-ingredients__heading">Ингедиенты</h3>
                <ul class="recipe-ingredients__list mt-3">
                    <#list recipe.ingredients as ingedient>
                        <li class="recipe-ingredients__list-item">${ingedient}</li>
                    </#list>
                </ul>
            </div>
            <div class="recipe-method-wrap mt-3 border-bottom">
                <h3 class="recipe-method__heading">Способ приготовления</h3>
                <ol class="recipe-method__list mt-3">
                    <#list recipe.methodSteps as step>
                        <li class="recipe-method__list-item">
                            <p class="recipe-method__list-item-text">
                                ${step}
                            </p>
                        </li>
                    </#list>
                </ol>
            </div>
            <div class="recipe-categories-wrap mt-3 pb-3 border-bottom">
                <h3 class="recipe-categories__heading">Категории</h3>
                <#list recipe.categories as category>
                    <a class="recipe-category-link" href="/?category=${category.name}">
                        <div class="recipe-category-item rounded-lg mt-1 mr-1">${category.name}</div>
                    </a>
                </#list>
            </div>

            <a href="/recipe/edit?id=${recipe.id}">
                <button type="button" class="btn btn-primary mt-4">Редактировать рецепт</button>
            </a>

            <a href="/recipe/delete?id=${recipe.id}"
               onclick="return confirm('Вы действительно хотите удалить рецепт?');">
                <button type="button" class="btn btn-danger mt-4">Удалить рецепт</button>
            </a>
        </div>

        <div class="recipe-right-menu rounded-lg shadow-sm">
            <h6 class="recipe-find-categories__heading">Выберите категорию для поиска</h6>
            <#list categories as category>
                <a class="recipe-category-link" href="/?category=${category.name}">
                    <div class="recipe-category-item rounded-lg mt-1 mr-1">${category.name}</div>
                </a>
            </#list>
        </div>
    </div>
</@c.page>
