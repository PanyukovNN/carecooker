<#import "parts/common.ftlh" as c>

<@c.page>
    <div class="form-group mt-4">
        <form method="post" enctype="multipart/form-data">
            <#include "parts/recipeEdit.ftlh" />
        </form>

        <div class="mt-3">
            <#if successfullyAdded??=true>
                Рецепт успешно добавлен!
            </#if>
            <#if successfullyUpdated??=true>
                Рецепт успешно обновлён!
            </#if>
        </div>
    </div>
</@c.page>
