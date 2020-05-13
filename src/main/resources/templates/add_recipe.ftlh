<#import "parts/common.ftlh" as c>

<@c.page>
    <div class="form-group mt-3">
        <form method="post" enctype="multipart/form-data">
            <div class="form-group">
                <input type="text" class="form-control" name="name" placeholder="Название блюда" />
            </div>
            <div class="form-group">
                <textarea class="form-control" name="description" placeholder="Описание"></textarea>
            </div>
            <div class="form-group">
                <div class="custom-file">
                    <input type="file" name="file" id="customFile">
                    <label class="custom-file-label" for="customFile">Выберите изображение блюда</label>
                </div>
            </div>
            <div class="form-group">
                <input type="text" class="form-control" name="cookTime" placeholder="Время приготовления">
            </div>
            <div class="form-group">
                <input type="text" class="form-control" name="serving" placeholder="Количество порций">
            </div>
            <div class="form-group">
                <textarea class="form-control" name="ingredients" placeholder="Ингредиенты"></textarea>
            </div>
            <div class="form-group">
                <textarea class="form-control" name="method" placeholder="Способ приготвления"></textarea>
            </div>
            <div class="form-group">
                <textarea class="form-control" name="categories" placeholder="Категории"></textarea>
            </div>
            <div class="form-group">
                <button type="submit" class="btn btn-primary">Сохранить рецепт</button>
            </div>
        </form>

        <div class="mt-3">
            <#if successfully_added??=true>
                Successfully added!
            </#if>
        </div>
    </div>
</@c.page>
