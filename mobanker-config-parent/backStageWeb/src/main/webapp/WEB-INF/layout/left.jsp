<%@page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"
	isELIgnored="false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />


<a class="menu-toggler" id="menu-toggler" href="#">
	<span class="menu-text"></span>
</a>


<%--<input type="hidden" name="menuId" id="menuId" value="${pageContext.request.parameterMap['menuId'][0]}">--%>
<%--<input type="hidden"  name="parentMenuId" id="parentMenuId" value="${pageContext.request.parameterMap['parentId'][0]}">--%>


<div class="sidebar" id="sidebar">
	<script type="text/javascript">
		try{ace.settings.check('sidebar' , 'fixed')}catch(e){}
	</script>

	<div class="sidebar-shortcuts" id="sidebar-shortcuts">
	</div><!-- #sidebar-shortcuts -->




	<ul class="nav nav-list">
		<li id="menu-home">
			<a href="${ctx}/home"> 
				<i class="glyphicon glyphicon-home"></i>
				<span class="menu-text"> 首页</span>
			</a>
		</li>
		<c:if test="${!empty menus}">
			<c:forEach items="${ menus}" var="menu"  >
				<li id="menu${ menu.menuId }">
					<a href="#" class="dropdown-toggle">
						<i class="icon-dashboard"></i>
						<span class="menu-text">${menu.displayName }</span>
						<b class="arrow icon-angle-down"></b>
					</a>

					<c:if test="${!empty menu.subMenus}">
						<ul class="submenu">
						<c:forEach items="${ menu.subMenus}" var="subMenu"  >
							<li id="submenu${subMenu.menuId}"><a href="${ctx}${subMenu.url }"><i class="icon-double-angle-right"></i>${subMenu.displayName }</a></li>
						</c:forEach>
						</ul>
					</c:if>
				</li>
			</c:forEach>
		</c:if>

		<%--<li id="menu-1" class="active open">--%>
			<%--<a href="#" class="dropdown-toggle">--%>
				<%--<i class="icon-desktop"></i>--%>
				<%--<span class="menu-text">系统变量</span>--%>
				<%--<b class="arrow icon-angle-down"></b>--%>
			<%--</a>--%>
			<%--<ul class="submenu">--%>
				<%--<li>--%>
					<%--<a href="${ctx}/system/getSysVars?pageSize=10" >--%>
						<%--<i class="icon-double-angle-right"></i>--%>
						<%--系统参数配置--%>
					<%--</a>--%>
				<%--</li>--%>

				<%--<li >--%>
					<%--<a href="${ctx}/system/getConfigurations?pageSize=10"  >--%>
						<%--<i class="icon-double-angle-right"></i>--%>
						<%--渠道配置--%>
					<%--</a>--%>
				<%--</li>--%>

				<%--<li >--%>
					<%--<a href="${ctx}/system/getCaptchaUseConfigurations?pageSize=10"  >--%>
						<%--<i class="icon-double-angle-right"></i>--%>
						<%--验证码配置--%>
					<%--</a>--%>
				<%--</li>--%>
			<%--</ul>--%>
		<%--</li>--%>

		<%--<li class="active open">--%>
			<%--<a href="#" class="dropdown-toggle">--%>
				<%--<i class="icon-ban-circle"></i>--%>
				<%--<span class="menu-text"> IP黑白名单 </span>--%>

				<%--<b class="arrow icon-angle-down"></b>--%>
			<%--</a>--%>

			<%--<ul class="submenu">--%>
				<%--<li  >--%>
					<%--<a href="${ctx}/system/getIplists" >--%>
						<%--<i class="icon-double-angle-right"></i>--%>
						<%--IP名单列表--%>
					<%--</a>--%>
				<%--</li>--%>

				<%--<li  >--%>
					<%--<a href="${ctx}/system/addIp" >--%>
						<%--<i class="icon-double-angle-right"></i>--%>
						<%--添加IP到黑名单--%>
					<%--</a>--%>
				<%--</li>--%>

				<%--<li  >--%>
					<%--<a href="${ctx}/system/addWhiteIp" >--%>
						<%--<i class="icon-double-angle-right"></i>--%>
						<%--添加IP到白名单--%>
					<%--</a>--%>
				<%--</li>--%>

			<%--</ul>--%>
		<%--</li>--%>
	</ul><!-- /.nav-list -->

	<div class="sidebar-collapse" id="sidebar-collapse">
		<i class="icon-double-angle-left" data-icon1="icon-double-angle-left" data-icon2="icon-double-angle-right"></i>
	</div>

	<script type="text/javascript">
		try{ace.settings.check('sidebar' , 'collapsed')}catch(e){}


		$('ul.submenu').children().click(function(){
			//绑定li单击事件，添加active样式，移出同级li的active样式
			var liss = $('ul.submenu').children();
			for(i = 0 ; i < liss.length ; i ++){
				$(liss[i]).attr('class','');
			}
			$(this).attr('class','active');
		});

	</script>
</div>