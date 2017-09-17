package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by 18394 on 2017/9/17.
 */
public class HTTPServer {

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
