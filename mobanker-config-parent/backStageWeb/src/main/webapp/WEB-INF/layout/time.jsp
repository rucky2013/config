<%@page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"
isELIgnored="false"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<style>
    * {
        margin: 0;
        padding: 0;
        list-style: none;
        border: none;
    }
    #zzsc {
        width: 920px;
        margin: 100px auto;
    }
</style>
<div id="zzsc">
    <canvas id="canvas" width="920" height="400"></canvas>
</div>