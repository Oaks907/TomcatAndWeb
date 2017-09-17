package server;

import java.io.OutputStream;

/**
 * Created by haifei on 2017/9/17.
 */
public class HelloServlet implements Servlet {

    @Override
    public void init() throws Exception {
        System.out.println("HelloServlet is inited.");
    }

    @Override
    public void service(byte[] requestBuffer, OutputStream outputStream) throws Exception {
        String request = new String(requestBuffer);

        //获取HTTP请求的第一行
        String firstLineOfRequest = request.substring(0, request.indexOf("\r\n"));
        //解析HTTP请求的第一行
        String[] parts = firstLineOfRequest.split(" ");
        String method = parts[0];
        String uri = parts[1];
        System.out.println("uri " + uri);

        //获取请求参数username
        String username = null;
        if (method.equalsIgnoreCase("get") && uri.indexOf("username=") != -1) {
            /*假定uri="servlet/HelloServlet?username=Tom&password=1234"*/
            //parameters="username=Tom&password=1234"
            String parameters = uri.substring(uri.indexOf("?"), uri.length());

            //parts={"username=Tom","password=1234"};
            parts = parameters.split("&");

            parts = parts[0].split("=");
            username = parts[1];
        }

        if (method.equalsIgnoreCase("post")) {
            int locate = request.indexOf("\r\n\r\n");
            //获取响应正文
            String content = request.substring(locate + 4, request.length());
            if (content.indexOf("username=") != -1) {

                parts = content.split("&");
                parts = parts[0].split("=");
                username = parts[1];
            }
        }

        //创建并发送HTTP响应
        outputStream.write("HTTP/1.1 200 OK\r\n".getBytes());
        outputStream.write("Content-Type:text/html\r\n\r\n".getBytes());
        outputStream.write("<html><head><title>HelloWorld</title></head><body>".getBytes());
        outputStream.write(new String("<h1>Hello:" + username + "</h1></body><head>").getBytes());
    }
}















