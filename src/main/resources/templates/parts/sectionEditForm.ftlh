<div class="mb-3">
    <img id="recipe-main-image" src="<#if section.image??>/img/${section.image}<#else>#</#if>" alt="" />
</div>
<div class="form-group">
    <div class="custom-file">
        <input type="file" name="file" id="customFile" value="<#if section.image??>${section.image}</#if>" />
        <label class="custom-file-label" for="customFile">Выберите изображение раздела</label>
    </div>
    <script>
        function readURL(input) {
            if (input.files && input.files[0]) {
                var reader = new FileReader();
                reader.onload = function (e) {
                    $('#recipe-main-image').attr('src', e.target.result);
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
    <label for="exampleFormControlSelect">Выберите родительскую категорию</label>
    <select class="form-control" id="exampleFormControlSelect" name="category">
        <#list categories as category>
            <#if section??>
                <#if section.name == category.name>
                    <option selected>${category.name}</option>
                    <#else>
                    <option>${category.name}</option>
                </#if>
                <#else>
                <option>${category.name}</option>
            </#if>
        </#list>
    </select>
</div>

<div class="form-group">
    <input type="text" class="form-control" name="name" placeholder="Название раздела"
           value="<#if section??>${section.name}</#if>"/>
</div>

<div class="form-group">
    <input type="text" class="form-control" name="position" placeholder="Порядковый номер"
           value="<#if section??>${section.position}</#if>"/>
</div>

<input type="hidden" name="id" value="<#if section??>${section.id}</#if>" />

<div class="form-group">
    <button type="submit" class="btn btn-primary"><#if section??>Обновить раздел<#else>Сохранить раздел</#if></button>
</div>