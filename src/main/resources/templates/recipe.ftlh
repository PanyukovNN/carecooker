<#import "parts/common.ftlh" as c>

<@c.page>
    <h2 class="mt-3">${recipe.name}</h2>

    <div class="recipe-main-info border-bottom">
        <div class="recipe-leading-info">
            <#if recipe.mainImage??>
                <img src="/img/${recipe.mainImage}" class="card-img-top">
            </#if>

            <div class="recipe-description-text mt-3 border-bottom">
                <p>
                    ${recipe.description}
                </p>
            </div>
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
            <li class="recipe-method__list-item">
                <p class="recipe-method__list-item-text">
                    ${recipe.method}
                </p>
            </li>
<#--                <li class="recipe-method__list-item">-->
<#--                    <p class="recipe-method__list-item-text">Sift the flour and cocoa into a large mixing bowl. Tip in the sugars, baking powder and salt and mix together. Make a well in the centre.</p>-->
<#--                </li>-->
<#--                <li class="recipe-method__list-item">-->
<#--                    <p class="recipe-method__list-item-text">Whisk together the eggs, buttermilk, vanilla extract and hot water. Pour into the well, then quickly use a spatula to blend into the dry ingredients, followed by the melted butter. Fold in most of the chocolate chips. Divide the batter between the paper cases – they should be about three-quarters full. Scatter the rest of the chocolate chips over the top and bake for 18–20 minutes until well risen and cooked.</p>-->
<#--                </li>-->
        </ol>
    </div>
</@c.page>
