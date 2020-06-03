<#import "parts/common.ftlh" as c>

<@c.page>
    <div class="login-form-block">
        <form method="post" class="login-form border" action="/admin/registration">
            <div class="mb-5">
                <h4>Добавить администратора</h4>
            </div>
            <div class="form-group">
                <label>Логин</label>
                <input type="text" class="form-control" name="username"/>
            </div>
            <div class="form-group mb-5">
                <label>Пароль</label>
                <input type="password" class="form-control" name="password"/>
            </div>

            <#if adminExists??>
                <div class="alert alert-primary mt-4" role="alert">
                    Администратор с таким именем уже существует
                </div>
            </#if>
            <input type="hidden" name="_csrf" th:value="${_csrf.token}" />
            <div class="form-group">
                <button type="submit" class="login-from-submit-btn btn btn-primary">Зарегистрировать админа</button>
            </div>
        </form>
    </div>
</@c.page>
