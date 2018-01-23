<#macro header title="PrivateBox">

<!DOCTYPE html>
<html>
<head>


<title>${title}</title>

<!-- 引入jQuery -->
<script src="${ctx}/js/jquery-3.2.1.js" type="text/javascript" charset="utf-8"></script>

<script src="${ctx}/js/jquery.serializeObject.js" type="text/javascript" charset="utf-8"></script>

<script src="${ctx}/js/jquery.dateFormat.js" type="text/javascript" charset="utf-8"></script>

<script src="${ctx}/js/jquery.sizeFormat.js" type="text/javascript" charset="utf-8"></script>

<!--<script src="${ctx}/js/jquery.sha512File.js" type="text/javascript" charset="utf-8"></script>

<script src="${ctx}/js/md5.js" type="text/javascript" charset="utf-8"></script>
-->

<script src="${ctx}/js/sha512.js" type="text/javascript" charset="utf-8"></script>

<script src="${ctx}/js/lib-typedarrays-min.js" type="text/javascript" charset="utf-8"></script>






<!-- WebUpload js--!>
<script src="${ctx}/js/webuploader.min.js" type="text/javascript" charset="utf-8"></script>
<!-- WebUpload csss--!>
<link rel="stylesheet" type="text/css" href="${ctx}/style/webuploader.css">
<link rel="stylesheet" type="text/css" href="${ctx}/style/bootstrap.css">
<!-- 引入EasyUI -->
<link id="easyuiTheme" rel="stylesheet"
	  href="${ctx}/style/default/easyui.css" type="text/css">
<link rel="stylesheet" href="${ctx}/style/icon.css"
	  type="text/css">

<script type="text/javascript" src="${ctx}/js/jquery.easyui.min.js"
		charset="utf-8"></script>
<script type="text/javascript"
		src="${ctx}/js/easyui-lang-zh_CN.js" charset="utf-8"></script>
 <#nested>  

</head>
<body>
</#macro>  
<#include "security.ftl" />
<#import "spring.ftl" as spring/>
<#assign ctx = request.contextPath />
<#macro footer>  

<#nested>  
</body>
</html>
</#macro>  