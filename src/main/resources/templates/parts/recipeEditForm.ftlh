<form method="post" enctype="multipart/form-data">
    <div class="recipe-edit-from-title">
        <input type="text" class="recipe-edit-from-title-input" name="name" placeholder="Название блюда"
               onfocus="this.placeholder = ''"
               value="<#if recipe??>${recipe.name}</#if>"/>
    </div>


    <div class="form-group">
        <div class="recipe-edit-from-image">
            <label for="customFile">

                <#if recipe??>
                    <div class="recipe-main-image" id="recipe-main-image"
                         style="background-image:url('/img/${recipe.mainImage}');">
                        <div style="padding-top: 52%;"></div>
                    </div>
                <#else>
                    <div class="recipe-main-image" id="recipe-main-image"
                         style="background-image:url('../../static/img_placeholder.svg');">
                        <div style="padding-top: 52%;"></div>
                    </div>
                </#if>
            </label>
            <input type="file" name="file" id="customFile" value="<#if recipe??>${recipe.mainImage}</#if>"/>
        </div>

<#--        <div class="custom-file">-->
<#--            <input type="file" name="file" id="customFile" value="<#if recipe??>${recipe.mainImage}</#if>" />-->
<#--            <label class="custom-file-label" for="customFile">Выберите изображение блюда</label>-->
<#--        </div>-->
        <script>
            function readURL(input) {
                if (input.files && input.files[0]) {
                    var reader = new FileReader();
                    reader.onload = function (e) {
                        $('#recipe-main-image').attr('style', "background-image: url(" + e.target.result) + ");";
                    }
                    reader.readAsDataURL(input.files[0]);
                }
            }

            $("#customFile").change(function(){
                readURL(this);
            });
        </script>
    </div>

    <div class="form-group">
        <textarea class="form-control" name="description" rows="7" placeholder="Описание"><#if recipe??>${recipe.description}</#if></textarea>
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
        <input type="text" class="form-control" name="complexity" placeholder="Сложность"
               value="<#if recipe??>${recipe.complexity}</#if>">
    </div>

    <div class="form-group">
        <textarea class="form-control" name="ingredients" rows="7" placeholder="Ингредиенты"><#if recipe??><#list recipe.ingredients as ingredient>
${ingredient}</#list></#if></textarea>
    </div>

    <div class="form-group">
        <textarea class="form-control" name="method" rows="7" placeholder="Способ приготвления"><#if recipe??>${recipe.method}</#if></textarea>
    </div>

    <div class="form-group">
        <textarea class="form-control" name="categories" placeholder="Категории"><#if recipe??><#list recipe.categories as category>
${category.name}</#list></#if></textarea>
    </div>

    <input type="hidden" name="id" value="<#if recipe??>${recipe.id}</#if>" />

    <div class="form-group">
        <button type="submit" class="btn btn-primary"><#if recipe??>Обновить рецепт<#else>Сохранить рецепт</#if></button>
    </div>
</form>