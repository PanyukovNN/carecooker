<#import "parts/common.ftlh" as c>

<@c.page>
    <div class="form-group mt-4">
        <form method="post" enctype="multipart/form-data">
            <#include "parts/recipeEdit.ftlh" />
        </form>

        <div class="mt-3">
            <#if successfullyUpdated??=true>
                Рецепт успешно обновлен!
            </#if>
        </div>
    </div>
</@c.page>
