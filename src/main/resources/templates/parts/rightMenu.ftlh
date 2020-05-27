<div class="right-menu rounded-lg shadow-sm mt-3">
    <h6 class="recipe-find-categories__heading">Выберите категорию для поиска</h6>
    <#list categories as category>
        <form class="category-item rounded-lg my-1 mr-1" onclick="this.submit();" method="post" action="/category">
            <input type="hidden" name="category" class="form-control" value="${category.name}">
            <div>${category.name}</div>
        </form>
    </#list>
</div>