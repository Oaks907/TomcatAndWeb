package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by haifei on 2017/9/17.
 */
public class HTTPClient {

    public static void main(String[] args){
        String uri = "index.htm";
        if (args.length != 0){
            uri = args[0];
        }

        doGet("localhost", 8080, uri);
    }

    public static void doGet(String host, int port, String uri) {
        Socket socket = null;
        try {
            socket = new Socket(host, port);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //创建HTTP请求
        StringBuffer sb = new StringBuffer("GET " + uri + " HTTP/1.1\r\n");
        sb.append("Accept: */*\r\n");
        sb.append("Accept-Language: zh-cn\r\n");
        sb.append("Accept_Encoding: gzip, deflate\r\n]]]]]]]]]]]]]]]]]]]]]]]]]");
        sb.append("User_Agent: HTTPClient\r\n");
        sb.append("Host: localhost:8080\r\n");
        sb.append("Connection: Keep-Alive\r\n\r\n");

        try {
            //发送HTTP请求
            OutputStream socketOut=socket.getOutputStream();
            socketOut.write(sb.toString().getBytes());

            Thread.sleep(2000);

            //接收响应结果
            InputStream socketIn = socket.getInputStream();
            int size = socketIn.available();
            byte[] buffer = new byte[size];
            socketIn.read(buffer);
            System.out.println(new String(buffer)); //打印响应结果
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}












