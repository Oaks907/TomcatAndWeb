package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by haifei on 2017/9/17.
 *
 */
public class HTTPServer1 {
    private static Map servletCache = new HashMap();

    public static void main(String[] args) {
        int port;
        ServerSocket serverSocket;

        try {
            port = Integer.parseInt(args[0]);
        } catch (Exception e) {
            System.out.println("port = 8080 (默认)");
            port = 8080;
        }

        try {
            serverSocket = new ServerSocket(port);
            System.out.println("服务器正在监听端口: " + serverSocket.getLocalPort());

            while (true) {
                try {
                    final Socket socket = serverSocket.accept();
                    System.out.println("建立一个与客户的一个新的TCP连接，该客户的地址为：" + socket.getInetAddress() + ":" + socket.getPort());
                    service(socket);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 响应用户的HTTP请求
     *
     * @param socket
     * @throws Exception
     */
    public static void service(Socket socket) throws Exception {

        //读取HTTP请求信息
        InputStream socketIn = socket.getInputStream();//获取输入流
        Thread.sleep(500);
        int size = socketIn.available();
        byte[] requestBuffer = new byte[size];
        socketIn.read(requestBuffer);
        String request = new String(requestBuffer);
        System.out.println(request);    //打印请求数据


        //解析请求数据
        //获取请求的第一行
        String firstLineOfRequest = request.substring(0, request.indexOf("\r\n"));
        //解析HTTP请求的第一行
        String[] parts = firstLineOfRequest.split(" ");
        String uri = parts[1]; //获得HTTP请求中的uri

        //如果请求对象包含servlet，则动态调用Servlet对象的service方法
        if (uri.indexOf("servlet") != -1) {
            String servletName = null;
            if (uri.indexOf("?") != -1) {
                servletName = uri.substring(uri.indexOf("servlet/") + 8, uri.indexOf("?"));
            } else {
                servletName = uri.substring(uri.indexOf("servlet/" + 8, uri.length()));
            }

            Servlet servlet = (Servlet) servletCache.get(servletName);
            if (servlet == null) {
                servlet = (Servlet) Class.forName("server." + servletName).newInstance();
                servlet.init();
                servletCache.put(servletName, servlet);
            }

            servlet.service(requestBuffer, socket.getOutputStream());

            Thread.sleep(1000);
            socket.close();
            return;
        }

        //决定HTTP响应正文的类型
        String contentType;
        if (uri.indexOf("html") != -1 || uri.indexOf("htm") != -1) {
            contentType = "text/html";
        } else if (uri.indexOf("jpg") != -1 || uri.indexOf("jepg") != -1) {
            contentType = "image/jpeg";
        } else if (uri.indexOf("gif") != -1) {
            contentType = "image/gif";
        } else {
            contentType = "application/octet-stream";
        }

        //创建HTTP响应结果
        //HTTP响应第一行
        String responseFirstLine = "HTTP/1.1 200 OK\r\n";
        String responseHeader = "Content=Type:" + contentType + "\r\n\r\n";
        InputStream in = HTTPServer1.class.getResourceAsStream("root/" + uri);

        OutputStream socketOut = socket.getOutputStream();
        socketOut.write(responseFirstLine.getBytes());
        socketOut.write(responseHeader.getBytes());
        //发送正文
        int len = 0;
        byte[] buffer = new byte[128];
        while ((len = in.read(buffer)) != -1) {
            socketOut.write(buffer, 0, len);
        }

        Thread.sleep(1000);
        socket.close();
    }
}
/*
POST /servlet/UploadServlet HTTP/1.1
        Host: localhost:8080
        User-Agent: Mozilla/5.0 (Windows NT 10.0; WOW64; rv:55.0) Gecko/20100101 Firefox/55.0
        Accept: text/html,application/xhtml+xml,application/xml;q=0.9,**//*
/
/*;q=0.8
Accept-Language: zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3
Accept-Encoding: gzip, deflate
Content-Type: multipart/form-data; boundary=---------------------------254192976419214
Content-Length: 401
Referer: http://localhost:8080/hello6.htm
Cookie: _ga=GA1.1.151271314.1490961834; Idea-33fbc508=87f8bcbc-8575-43a5-a7cb-c572ae87c841
Connection: keep-alive
Upgrade-Insecure-Requests: 1/

-----------------------------254192976419214
Content-Disposition: form-data; name="filedata"; filename="FromClient.txt"
Content-Type: text/plain

Data1 in FromClient.txt
Data2 in FromClient.txt
Data3 in FromClient.txt
Data4 in FromClient.txt
-----------------------------254192976419214
Content-Disposition: form-data; name="submit"

upload
-----------------------------254192976419214--
*/
