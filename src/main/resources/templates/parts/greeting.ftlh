<div class="greeting-block">
    <div class="greeting-block__title">
        <h1 class="greeting-header">
            <#if recipesAll??>
                Рецепты
            <#else>
                <#if section??>${section.name}<#else>Разделы</#if>
            </#if>

        </h1>
    </div>
    <div class="greeting-block__description">
        Ищите рецепты, выбирая категорию блюда, его подкатегорию, кухню или меню. А в дополнительных фильтрах можно искать по нужному (или ненужному) ингредиенту: просто начните писать его название и сайт подберет соответствующий.
    </div>
</div>