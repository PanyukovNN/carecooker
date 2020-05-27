<nav class="navbar navbar-expand-lg static-top navbar-light border-bottom py-1">
    <a class="navbar-brand ml-3 mr-auto" href="/">CareCooker</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse order-1 order-md-0 dual-collapse2" id="navbarSupportedContent">
        <ul class="navbar-nav ml-auto mr-auto">
            <li class="nav-item ml-3">
                <a class="nav-link font-weight-bold" href="/">Разделы</a>
            </li>
            <li class="nav-item ml-3">
                <a class="nav-link font-weight-bold" href="/recipe/add">Добавить рецепт</a>
            </li>
            <li class="nav-item ml-3">
                <a class="nav-link font-weight-bold" href="/section/list">Редактировать разделы</a>
            </li>
        </ul>
    </div>
    <div class="navbar-collapse collapse w-50 order-3 dual-collapse2">
        <form class="form-inline my-2 my-lg-0 ml-auto" method="post" action="/filter">
            <input class="form-control mr-sm-2" name="filter" type="search" placeholder="Введите название" aria-label="Search"
                   value="<#if filter??>${filter}</#if>">
            <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Найти</button>
        </form>
    </div>
</nav>