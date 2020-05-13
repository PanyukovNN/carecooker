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
    <div class="mt-5">
        <ul class="pagination justify-content-center">
            <li class="page-item">
                <#if page.getNumber() gt 0>
                    <a class="page-link" href="${url}?page=${page.getNumber() - 1}" aria-label="Previous">
                        <span aria-hidden="true">&laquo;</span>
                        <span class="sr-only">Previous</span>
                    </a>
                <#else>
                    <a class="page-link" href="#" aria-label="Previous">
                        <span aria-hidden="true">&laquo;</span>
                        <span class="sr-only">Previous</span>
                    </a>
                </#if>
            </li>

            <#list body as p>
                <#if (p - 1) == page.getNumber()>
                    <li class="page-item active">
                        <a class="page-link" href="#" tabindex="-1" style="background-color: #35adc1; border-color: #35adc1;">${p}</a>
                    </li>
                <#elseif p == -1>
                    <li class="page-item disabled">
                        <a class="page-link" href="#" tabindex="-1">...</a>
                    </li>
                <#else>
                    <li class="page-item">
                        <a class="page-link" href="${url}?page=${p - 1}" tabindex="-1">${p}</a>
                    </li>
                </#if>
            </#list>

            <li class="page-item">
                <#if page.getNumber() lt page.getTotalPages() - 1>
                    <a class="page-link" href="${url}?page=${page.getNumber() + 1}" aria-label="Next">
                        <span aria-hidden="true">&raquo;</span>
                        <span class="sr-only">Next</span>
                    </a>
                <#else>
                    <a class="page-link" href="#" aria-label="Next">
                        <span aria-hidden="true">&raquo;</span>
                        <span class="sr-only">Next</span>
                    </a>
                </#if>
            </li>
        </ul>

<#--        <ul class="pagination">-->
<#--            <li class="page-item disabled">-->
<#--                <a class="page-link" href="#" tabindex="-1">Элементов на странице</a>-->
<#--            </li>-->
<#--            <#list [3, 9, 15] as c>-->
<#--                <#if c == page.getSize()>-->
<#--                    <li class="page-item active">-->
<#--                        <a class="page-link" href="#" tabindex="-1">${c}</a>-->
<#--                    </li>-->
<#--                <#else>-->
<#--                    <li class="page-item">-->
<#--                        <a class="page-link" href="${url}?page=${page.getNumber()}&size=${c}" tabindex="-1">${c}</a>-->
<#--                    </li>-->
<#--                </#if>-->
<#--            </#list>-->
<#--        </ul>-->
    </div>
</#macro>