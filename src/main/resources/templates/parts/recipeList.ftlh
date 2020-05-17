<#import "pager.ftlh" as p>

<div class="main-recipe-list-block">
    <#if mainPage??>
        <div class="greeting-block shadow my-3 p-3 rounded">
            <h5>Кулинарный сайт</h5>
            <p>Приветствуем вас на нашем кулинарном сайте, здесь собрана большая коллекция интересных и оригинальных кулинарных рецептов от знаменитых шеф-поваров со всего мира. Публикуются простые и быстрые рецепты на каждый день, рецепты здорового питания, а также, интересные и познавательные кулинарные статьи. На сайте имеется справочник продуктов с описанием как обычных, так и экзотических продуктов, из которого вы узнаете как покупать, обрабатывать, хранить и применять различные продукты в кулинарии.</p>
        </div>
    </#if>

    <#if mainPage??>
        <div class="section-head-block shadow mt-3 p-3 rounded">
            <h5>Завтраки</h5>
        </div>
    </#if>
    <div class="row row-cols-1 row-cols-md-3 mt-3">
        <#list recipes.content as recipe>
            <a href="/recipe/${recipe.id}" style="text-decoration: none;">
                <div class="col mb-4">
                    <div class="card shadow rounded zoom">
                        <#if recipe.mainImage??>
                            <img src="/img/${recipe.mainImage}" class="card-img-top">
                        </#if>
                        <div class="card-body mask">
                            <h5 class="card-title mt-n2">${recipe.name}</h5>
        <#--                    <p class="card-text">${recipe.shortDescription}</p>-->
        <#--                    <p class="card-text"><small class="text-muted">Last updated 3 mins ago</small></p>-->
                        </div>
                    </div>
                </div>
            </a>
        <#else>
            На данной странице нет рецептов
        </#list>
    </div>

    <@p.pager url recipes/>
</div>

