<div class="form-group">
    <input type="text" class="form-control" name="name" placeholder="Название блюда"
           value="<#if recipe??>${recipe.name}</#if>"/>
</div>
<div class="form-group">
    <textarea class="form-control" name="description" placeholder="Описание"><#if recipe??>${recipe.description}</#if></textarea>
</div>
<div class="form-group">
    <div class="custom-file">
        <input type="file" name="file" id="customFile"
               value="<#if recipe??>${recipe.mainImage}</#if>">
        <label class="custom-file-label" for="customFile">Выберите изображение блюда</label>
    </div>
</div>
<div class="form-group">
    <input type="text" class="form-control" name="cookTime" placeholder="Время приготовления"
           value="<#if recipe??>${recipe.cookTime}</#if>">
</div>
<div class="form-group">
    <input type="text" class="form-control" name="serving" placeholder="Количество порций"
           value="<#if recipe??>${recipe.serving}</#if>">
</div>
<div class="form-group">
    <textarea class="form-control" name="ingredients" placeholder="Ингредиенты"><#if recipe??><#list recipe.ingredients as ingredient>
${ingredient}</#list></#if></textarea>
</div>
<div class="form-group">
    <textarea class="form-control" name="method" placeholder="Способ приготвления"><#if recipe??>${recipe.method}</#if></textarea>
</div>
<div class="form-group">
    <textarea class="form-control" name="categories" placeholder="Категории"><#if recipe??><#list recipe.categories as category>
${category}</#list></#if></textarea>
</div>
<input type="hidden" name="id" value="<#if recipe??>${recipe.id}</#if>" />
<div class="form-group">
    <button type="submit" class="btn btn-primary">Сохранить рецепт</button>
</div>