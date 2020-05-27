<#macro pager url page>
    <#if page.getTotalPages() gt 5>
        <#assign
        totalPages = page.getTotalPages()
        pageNumber = page.getNumber() + 1

        head = (pageNumber > 4)?then([1, -1], [1, 2, 3])
        tail = (pageNumber < totalPages - 3)?then([-1, totalPages], [totalPages - 2, totalPages - 1, totalPages])
        bodyBefore = (pageNumber > 4 && pageNumber < totalPages - 1)?then([pageNumber - 2, pageNumber - 1], [])
        bodyAfter = (pageNumber > 2 && pageNumber < totalPages - 3)?then([pageNumber + 1, pageNumber + 2], [])

        body = head + bodyBefore + (pageNumber > 3 && pageNumber < totalPages - 2)?then([pageNumber], []) + bodyAfter + tail
        >
    <#else>
        <#assign body = 1..page.getTotalPages()>
    </#if>
    <div class="pager mt-5">
        <ul class="pagination justify-content-center">
            <#if page.getNumber() gt 0>
                <li class="page-item">
                    <a class="page-link" href="${url}?page=${page.getNumber() - 1}" aria-label="Previous">
                        <span aria-hidden="true">&laquo;</span>
                        <span class="sr-only">Previous</span>
                    </a>
                </li>
            </#if>

            <#list body as p>
                <#if (p - 1) == page.getNumber()>
                    <li class="page-item active">
<#--                        <form class="page-link" onclick="this.submit();" method="post" action="page">-->
<#--                            <input type="hidden" name="category" class="form-control" value="${category.name}">-->
<#--                            <div>${category.name}</div>-->
<#--                        </form>-->
                        <a class="page-link selected" href="#" tabindex="-1">${p}</a>
                    </li>
                <#elseif p == -1>
                    <li class="page-item disabled">
                        <a class="page-link" href="#" tabindex="-1">...</a>
                    </li>
                <#else>
                    <li class="page-item">
                        <a class="page-link" href="${url}page=${p - 1}" tabindex="-1">${p}</a>
                    </li>
                </#if>
            </#list>

            <#if page.getNumber() lt page.getTotalPages() - 1>
                <li class="page-item">
                    <a class="page-link" href="${url}page=${page.getNumber() + 1}" aria-label="Next">
                        <span aria-hidden="true">&raquo;</span>
                        <span class="sr-only">Next</span>
                    </a>
                </li>
            </#if>
        </ul>
    </div>
</#macro>