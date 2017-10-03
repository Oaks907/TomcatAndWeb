<%--
  Created by IntelliJ IDEA.
  User: haifei
  Date: 2017/10/2
  Time: 16:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.sql.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>dbaccess.jsp</title>
</head>
<body>
    <%
        try {
            Connection con;
            Statement stmt;
            ResultSet rs;
            //加载驱动程序，下面的代码是加载Mysql的驱动程序
            Class.forName("com.mysql.jdbc.Driver");
            //用适当的驱动程序去连接数据库
            String dbUrl = "jdbc:mysql://localhost:3306/BookDB?useUnicode=true&characterEncoding=UTF-8";
            String dbUser = "root";
            String dbPwd = "password";

            //建立数据库连接
            con = java.sql.DriverManager.getConnection(dbUrl, dbUser, dbPwd);
            //创建一个SQL声明
            stmt = con.createStatement();
            //增加新记录
            stmt.executeUpdate("INSERT into books (id, name, title, price) VALUES ('999', 'Tom', 'Tomcat Bible', 44.5)");

            rs = stmt.executeQuery("SELECT id, name, title, price from books");

            out.println("<table border=1 width=400>");
            while (rs.next()){
                String col1 = rs.getString(1);
                String col2 = rs.getString(2);
                String col3 = rs.getString(3);
                float col4 = rs.getFloat(4);

               out.println("<tr><td>" + col1 + "</td><td>" + col2 + "</td><td>" + col3 + "</td><td>" + col4 + "</td></tr>");
            }
            out.println("</table>");

            stmt.executeUpdate("DELETE FROM books where id='999'");

            rs.close();
            stmt.close();
            con.close();
        } catch (Exception e) {
            out.println(e.getMessage());
        }
    %>
</body>
</html>
