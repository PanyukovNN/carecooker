<#import "parts/common.ftlh" as c>

<@c.page>
    <form method="get" action="/" class="form-inline">
        <input type="hidden" name="filter" class="form-control" value="${category!}">
    </form>
    <#include "parts/recipeList.ftlh" />
</@c.page>
