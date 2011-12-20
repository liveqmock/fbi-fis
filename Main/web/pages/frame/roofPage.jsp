<%@ page contentType="text/html; charset=GBK" %>
<%@ page import="pub.platform.security.OperatorManager" %>
<%@ page import="pub.platform.form.config.SystemAttributeNames" %>
<%@ page import="pub.platform.db.ConnectionManager" %>
<%@ page import="pub.platform.db.DatabaseConnection" %>
<%@ page import="pub.platform.db.RecordSet" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%
    OperatorManager om = (OperatorManager) session.getAttribute(SystemAttributeNames.USER_INFO_NAME);
    String contextPath = request.getContextPath();

    if (om == null) {
        om = new OperatorManager();
        session.setAttribute(SystemAttributeNames.USER_INFO_NAME, om);
    }

    String username = "";
    String deptname = "";
    String operid = "";

    String rolesall = null;

    if (om != null) {
        if (om.getOperator() != null) {
            username = om.getOperator().getOpername();
            operid = om.getOperator().getOperid();
            if (om.getOperator().getPtDeptBean() != null)
                deptname = om.getOperator().getPtDeptBean().getDeptname();

            //��ɫ
            List roles = new ArrayList();
            DatabaseConnection conn = ConnectionManager.getInstance().get();
            RecordSet rs = conn.executeQuery("select a.roledesc from ptoperrole b right join ptrole a on b.roleid = a.roleid  where b.operid='" + operid + "'");
            while (rs.next()) {
                roles.add(rs.getString("roledesc"));
            }
            ConnectionManager.getInstance().release();
            rolesall = " ";
            for (int i = 0; i < roles.size(); i++) {
                rolesall += roles.get(i) + " ";
            }

        }

    }
%>
<html>
<head>
    <META http-equiv="Content-Type" content="text/html; charset=gb2312">
    <LINK href="<%=contextPath%>/css/ccb.css" type="text/css" rel="stylesheet">
    <LINK href="<%=contextPath%>/css/diytabbar.css" type="text/css" rel="stylesheet">
    <script type="text/javascript">

        function Relogin() {
            parent.window.reload = "true";
            parent.window.location.replace("<%=contextPath%>/pages/security/logout.jsp");
        }
        function changepwd() {
            var sfeature = "dialogwidth:400px; dialogheight:200px;center:yes;help:no;resizable:no;scroll:no;status:no";
            window.showModalDialog("<%=contextPath%>/UI/system/deptUser/Passwordedit.jsp", "test", sfeature);
        }
        function goFirst() {
            /*parent.document.getElementById("divid").value = "a1";
            <%--parent.document.getElementById("url").value = "<%=contextPath%>/pages/frame/trackMisc.xhtml";--%>
            parent.document.getElementById("btnAddTabbar").click();*/
        <%--parent.document.getElementsByTagName("iframe")[2].src = "<%=contextPath%>/pages/frame/trackMisc.xhtml";--%>
        <%--parent.document.getElementsByTagName("iframe")[1].contentWindow.document.getElementsByTagName("iframe")[0].contentWindow.document.getElementById("contentframe").src="<%=contextPath%>/pages/frame/trackMisc.xhtml";--%>
        <%--parent.window.workFrame.location.replace("<%=contextPath%>/pages/frame/trackMisc.xhtml");--%>
        }
        /*var dividary = new Array('a1','a2','a3','a4');
        function tabbarclk(obj) {
            var active = obj.getAttribute("active");
            if (active == 'false') {
                setclass(obj.getAttribute("id"));
                obj.setAttribute("active","true");
                obj.setAttribute("className","tabs-item-active");
                //�л�������tree
                parent.document.getElementById("accordItemid").value = obj.getAttribute("id");
                parent.document.getElementById("btnExchangeAccd").click();
            }
        }*/
        var dividary = new Array('bizlayout','syslayout','helplayout','verlayout');
        function tabbarclk(obj) {
            var active = obj.getAttribute("active");
            if (active == 'false') {
                setclass(obj.getAttribute("id"));
                obj.setAttribute("active","true");
                obj.setAttribute("className","tabs-item-active");
                //�л�������tree
                parent.document.getElementById("accordItemid").value = obj.getAttribute("id");
                parent.document.getElementById("btnExchangeAccd").click();
            }
        }
        function setclass(activeid) {
            for (var i = 0; i < dividary.length; i++) {
                if (dividary[i] != activeid) {
                    document.getElementById(dividary[i]).setAttribute("className","tabs-item");
                    document.getElementById(dividary[i]).setAttribute("active","false");
                }
            }
        }
    </script>

    <style type="text/css">
        html,body {
            background-color: #FFF;
            /*background: url(../../images/roofHeader.jpg) repeat-x scroll left bottom;*/
            color: #7387A0;
            /*color:#FFF;*/
            margin: 0px;
            padding: 0px;
            overflow: auto;
        }

        div#nifty1 {
            margin: 0 1px; /*background: #9BD1FA;*/
            /*background: #7A8FA8;*/
            /*background: #0F67A1;*/
            /*background: #B54936;*/
            /*background: #FF6600;*/
            /*background: #999999;*/
            background: #7387A0;
        }

        div#nifty2 {
            margin: 0 1px;
            background: #7387A0;
        }

        div#nifty3 {
            margin: 0 1px;
            background: #7387A0;
        }

        div#nifty4 {
            margin: 0 1px;
            background: #7387A0;
        }

        div#nifty5 {
            margin: 0 1px;
            background: #7387A0;
        }

        b.rtop, b.rbottom {
            display: block;
            background: #FFF
        }

        b.rtop b, b.rbottom b {
            display: block;
            height: 1px;
            overflow: hidden;
            background: #7387A0
        }

        b.r1 {
            margin: 0 5px
        }

        b.r2 {
            margin: 0 3px
        }

        b.r3 {
            margin: 0 2px
        }

        b.rtop b.r4, b.rbottom b.r4 {
            margin: 0 1px;
            height: 2px
        }

        .tdbgcolor1 {
            background: url(../../images/bgcolorhead1.png) repeat-x scroll left bottom;
        }

        .tdbgcolor2 {
            background: url(../../images/bgcolorhead2.png) repeat-x scroll left bottom;
        }

        .tabbarbg1 {
            background: url(../../images/diytabbar/bgx.png) repeat-x scroll left bottom;
            width: 100px;
            height: 30px;
        }


    </style>


</head>
<body leftmargin="0" topmargin="0" rightmargin="0" bottommargin="0" onunload="Relogin()">
<input id="hhidOperatorID" type="hidden" value="<%=operid%>">
<table width="100%" cellpadding="0" cellspacing="0" style="margin:0px;padding:0px;">
    <tr width="100%" height="25px">
        <td width="10%" rowspan="2">
            &nbsp;&nbsp;
            <img src="../../images/hfccrm.gif" height="45px">
        </td>
        <td width="30%">
            <img src="../../images/systitle.jpg" height="25px">
        </td>
        <td style="height:25px;text-align:right">
            <span>����,<%=username%>! </span>
            <span onclick="changepwd()"
                  onMouseOver="this.style.cursor='hand'">|&nbsp;&nbsp;�޸�����</span>
             <%--<span onclick="goFirst() "
                   onMouseOver="this.style.cursor='hand'">|&nbsp;&nbsp;����ҳ</span>--%>
             <span onclick="Relogin()"
                   onMouseOver="this.style.cursor='hand'">|  �˳� &nbsp;&nbsp;</span>
        </td>
    </tr>
    <tr height="25px">
        <td colspan="2" width="100%" style="height: 100%;">
            <div onclick="tabbarclk(this);" active="true" id="bizlayout" class="tabs-item-active" style="float:left;width:80px;">
                <span style="width:100%;">ҵ�����</span>
            </div>
            <div style="float:left;width:2px;"></div>
            <div onclick="tabbarclk(this);" active="false" id="syslayout" class="tabs-item" style="float:left;width:80px;">
                <span style="width:100%;">ϵͳ����</span>
            </div>
            <div style="float:left;width:2px;"></div>
            <div onclick="tabbarclk(this);" active="false" id="helplayout" class="tabs-item" style="float:left;width:80px;">
                <span style="width:100%;">ϵͳ����</span>
            </div>
            <div style="float:left;width:2px;"></div>
            <div onclick="tabbarclk(this);" active="false" id="verlayout" class="tabs-item" style="float:left;width:80px;">
                <span style="width:100%;">�汾����</span>
            </div>
            <%--<%=" " + deptname + " | " + operid + " | <" + rolesall + ">" %>--%>
        </td>
    </tr>
    <tr>
        <td width="100%" style="height:4px;background-color: #3169AD;" colspan="3"></td>
    </tr>
</table>


</body>
</html>