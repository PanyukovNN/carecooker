<#import "parts/common.ftlh" as c>

<@c.page>
    <form method="post" action="category" class="form-inline">
        <input type="hidden" name="category" class="form-control" value="${category!}">
    </form>
    <#include "parts/recipeList.ftlh" />
</@c.page>
