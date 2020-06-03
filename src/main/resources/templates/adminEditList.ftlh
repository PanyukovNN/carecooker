<#import "parts/common.ftlh" as c>

<@c.page>
    <h1 class="greeting-header mt-3">
        Список администраторов
    </h1>

    <table class="table">
    <thead>
        <tr>
            <th scope="col">#</th>
            <th scope="col">Имя</th>
        </tr>
    </thead>
        <tbody>
            <#list admins as admin>
                <tr>
                    <th scope="row">${admin?index + 1}</th>
                    <td>${admin.username}</td>
                </tr>
            </#list>
        </tbody>
    </table>

    <a href="/admin/registration">
        <button type="button" class="btn btn-primary">Добавить администратора</button>
    </a>
</@c.page>