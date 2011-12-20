<%@ page contentType="text/html; charset=GBK" %>
<html>
<head>
    <title></title>
    <meta content='text/html; charset=GBK' http-equiv="Content-Type"/>
    <link rel="stylesheet" type="text/css" href="../../dhtmlx/dhtmlxTabbar/codebase/dhtmlxtabbar.css"/>
    <script src="../../dhtmlx/dhtmlxTabbar/codebase/dhtmlxcommon.js" type="text/javascript"></script>
    <script src="../../dhtmlx/dhtmlxTabbar/codebase/dhtmlxtabbar.js" type="text/javascript"></script>
    <!--<script src="../../dhtmlx/dhtmlxTabbar/codebase/dhtmlxtabbar_start.js" type="text/javascript"></script>-->
</head>
<body style="margin:0px;padding:0px;overflow: auto;">
<div id="a_tabbar" style="margin:0px;width:100%;height: 100%;">
</div>
<script type="text/javascript">
    tabbar = new dhtmlXTabBar("a_tabbar", "top");
    tabbar.setSkin("modern");
    tabbar.setImagePath("../../dhtmlx/dhtmlxTabbar/codebase/imgs/");
    tabbar.setHrefMode("iframes-on-demand");
    tabbar.setSkinColors("#FCFBFC", "#F4F3EE");
    tabbar.addTab("a1", "系统", "100px");
    tabbar.addTab("a2", "帮助", "100px");
    tabbar.setContentHref("a1", "tabContent.jsp");
    tabbar.setContentHref("a2", "");
    tabbar.setTabActive("a1");
</script>
</body>
</html>