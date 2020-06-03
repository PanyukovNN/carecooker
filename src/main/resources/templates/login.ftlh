<#import "parts/common.ftlh" as c>

<@c.page>
    <div class="login-form-block">
        <form method="post" class="login-form border" action="/login">
            <div class="mb-5">
                <h4>Вход</h4>
            </div>
            <div class="form-group">
                <label>Логин</label>
                <input type="text" class="form-control" name="username"/>
            </div>
            <div class="form-group">
                <label>Пароль</label>
                <input type="password" class="form-control" name="password"/>
            </div>
            <input type="hidden" name="_csrf" value="${_csrf.token}" />
            <div class="form-group mt-5">
                <button type="submit" class="login-from-submit-btn btn btn-primary">Войти</button>
            </div>
        </form>
    </div>
</@c.page>
