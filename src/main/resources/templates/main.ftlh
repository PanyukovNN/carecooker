<#import "parts/common.ftlh" as c>

<@c.page>
    <#include "parts/breadcrumb.ftlh" />
    <div class="content-container">
        <#include "parts/recipeList.ftlh" />
        <#include "parts/rightMenu.ftlh"/>
    </div>

<#--    <form method="post" action="category" class="form-inline">-->
<#--        <input type="hidden" name="category" class="form-control" value="${category!}">-->
<#--    </form>-->
</@c.page>
