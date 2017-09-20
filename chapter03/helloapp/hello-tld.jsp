<%--
  Created by IntelliJ IDEA.
  User: haifei
  Date: 2017/9/20
  Time: 8:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/mytaglib" prefix="mm"%>
<html>
<head>
    <title>mytaglib</title>
</head>
<body>
    <b><mm:hello/> : <%= request.getAttribute("USER")%></b>
</body>
</html>
