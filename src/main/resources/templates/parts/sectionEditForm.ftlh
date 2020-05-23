<div class="mb-3">
    <img id="recipe-main-image" src="<#if sectionCategory.sectionImage??>/img/${sectionCategory.sectionImage}<#else>#</#if>" alt="" />
</div>
<div class="form-group">
    <div class="custom-file">
        <input type="file" name="file" id="customFile" value="<#if sectionCategory.sectionImage??>${sectionCategory.sectionImage}</#if>" />
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
            <#if sectionCategory??>
                <#if sectionCategory.name == category.name>
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
           value="<#if sectionCategory??>${sectionCategory.name}</#if>"/>
</div>

<div class="form-group">
    <input type="text" class="form-control" name="order" placeholder="Порядковый номер"
           value="<#if sectionCategory??>${sectionCategory.sectionOrder}</#if>"/>
</div>

<input type="hidden" name="id" value="<#if sectionCategory??>${sectionCategory.id}</#if>" />

<div class="form-group">
    <button type="submit" class="btn btn-primary"><#if sectionCategory??>Обновить раздел<#else>Сохранить раздел</#if></button>
</div>