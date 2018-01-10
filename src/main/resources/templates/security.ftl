<#macro authorize ifAnyGranted>
<#assign authorized = false>
<#list Session["SPRING_SECURITY_CONTEXT"].authentication.authorities as authority>
<#if authority == ifAnyGranted>
<#assign authorized = true>
</#if>
</#list>
<#if authorized>
<#nested>
</#if>
</#macro>