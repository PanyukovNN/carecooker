<#import "pager.ftlh" as p>

<div class="main-list-block">
<#--    <div class="section-top-parent shadow mt-3 mb-4">-->
<#--        <h5>${section.name}</h5>-->
<#--        <div class="section-top-child"-->
<#--             style="<#if section.image??>background-image: url('/img/${section.image}');</#if>">-->
<#--            <div class="section-overlay"></div>-->
<#--        </div>-->
<#--    </div>-->

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
        </#list>
    </div>
    <#if recipes.content?size == 0>
        <div class="alert alert-primary mt-3" role="alert">
            На данной странице нет рецептов
        </div>
    </#if>

    <@p.pager url recipes/>
</div>