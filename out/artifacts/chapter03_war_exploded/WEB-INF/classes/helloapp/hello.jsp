<%@taglib uri="/mytaglib" prefix="mm"%>
<html>
    <head>
        <title>helloapp</title>
    </head>

    <body>
        <b>UserName : <%= request.getAttribute("USER")%></b>
    </body>
</html>