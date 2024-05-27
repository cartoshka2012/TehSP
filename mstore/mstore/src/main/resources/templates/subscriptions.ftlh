<#import "blocks/common.ftlh" as t>
<@t.page>
    <h3>${userChannel.username}</h3>
    <div>${type}</div>
    <ul class="list-group">
        <#list users as user>
            <li class="list-group-item">
            </li>
        </#list>
    </ul>
</@t.page>