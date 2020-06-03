<div class="site-header">
    <h1>Care Cooker</h1>
    <div class="section-overlay"></div>
</div>
<nav class="navbar navbar-expand-lg static-top bg-light navbar-light border-bottom py-1">

    <div class="collapse navbar-collapse order-1 order-md-0 dual-collapse2" id="navbarSupportedContent">
        <ul class="navbar-nav">
            <li class="nav-item">
                <a class="nav-link font-weight-bold" href="/">Разделы</a>
            </li>
            <li class="nav-item">
                <a class="nav-link font-weight-bold" href="/recipe/all">Все рецепты</a>
            </li>
            <li class="nav-item ml-3">
                <a class="nav-link font-weight-bold" href="/recipe/add">Добавить рецепт</a>
            </li>
            <li class="nav-item ml-3">
                <a class="nav-link font-weight-bold" href="/section/list">Редактировать разделы</a>
            </li>
            <li class="nav-item ml-3">
                <a class="nav-link font-weight-bold" href="/registration">Добавить администратора</a>
            </li>
        </ul>
    </div>
    <div class="search-form navbar-collapse collapse order-3 dual-collapse2">
        <form class="form-inline my-2 my-lg-0" method="post" action="/filter">
            <input class="form-control mr-sm-1" name="filter" type="search" placeholder="Поиск по сайту" aria-label="Search"
                   value="<#if filter??>${filter}</#if>">
            <button class="btn btn-outline-secondary my-2 my-sm-0" type="submit">Найти</button>
        </form>
    </div>
</nav>