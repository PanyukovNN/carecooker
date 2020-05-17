<nav class="navbar navbar-expand-lg static-top navbar-light bg-light border-bottom py-1">
    <a class="navbar-brand" href="/">CareCooker</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item active ml-3">
                <a class="nav-link" href="/">Все рецепты<span class="sr-only">(current)</span></a>
            </li>
            <li class="nav-item ml-3">
                <a class="nav-link" href="/recipe/add">Добавить рецепт</a>
            </li>
<#--            <li class="nav-item ml-3">-->
<#--                <a class="nav-link" href="/section/add">Добавить раздел</a>-->
<#--            </li>-->
            <li>
                <form class="form-inline" method="post" action="/filter">
                    <input class="form-control mr-sm-2 ml-3" name="filter"  type="search" placeholder="Введите название" aria-label="Search"
                           value="<#if filter??>${filter}</#if>">
                    <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Найти</button>
                </form>
            </li>
        </ul>
    </div>
</nav>