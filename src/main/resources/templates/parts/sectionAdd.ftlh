<div class="form-group">
    <input type="text" class="form-control" name="name" placeholder="Название раздела"
           value="<#if section??>${section.name}</#if>"/>
</div>

<div class="form-group">
    <textarea class="form-control" name="categories" placeholder="Категории"><#if recipe??><#list recipe.categories as category>
            ${category.name}</#list></#if></textarea>
</div>

<input type="hidden" name="id" value="<#if section??>${section.id}</#if>" />

<div class="form-group">
    <button type="submit" class="btn btn-primary"><#if section??>Раздел рецепт<#else>Сохранить раздел</#if></button>
</div>