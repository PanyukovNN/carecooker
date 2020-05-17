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