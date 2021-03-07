<%--
  Created by IntelliJ IDEA.
  User: 22945
  Date: 2020-8-11
  Time: 12:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
        <%
    String basePath = request.getScheme() + "://" +
            request.getServerName() + ":" + request.getServerPort() +
            request.getContextPath() + "/";
%>

    <html>
    <head>
        <base href="<%=basePath%>">
    <title>Title</title>
        $.ajax({
            url:"",
            data:{

            },
            type:"post",
            dataType:"json",
            success:function (data) {

            }
        })




        String createTime= DateTimeUtil.getSysTime();
        String createBy=((User)req.getSession().getAttribute("user")).getName();
</head>
<body>


</body>
</html>
