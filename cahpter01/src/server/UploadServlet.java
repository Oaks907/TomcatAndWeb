package server;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.StringReader;

/**
 * Created by haifei on 2017/9/18.
 */
public class UploadServlet implements Servlet {

    @Override
    public void init() throws Exception {
        System.out.println("UploadServlet is inited");
    }

    @Override
    public void service(byte[] requestBuffer, OutputStream outputStream) throws Exception {
        String request = new String(requestBuffer);

        //获取HTTP中文件部分的头
        String headerOfRequest = request.substring(request.indexOf("\r\n") + 2, request.indexOf("\r\n\r\n"));

        BufferedReader br = new BufferedReader(new StringReader(headerOfRequest));
        String data = null;
        //获取boundary
        String boundary = null;
        while ((data = br.readLine()) != null) {
            if (data.indexOf("Content-Type") != -1) {
                boundary = data.substring(data.indexOf("boundary=") + 9, data.length()) + "\r\n";
                break;
            }
        }

        if (boundary == null){
            outputStream.write("HTTP/1.1 200 OK\r\n".getBytes());
            outputStream.write("Content-Type:text/html\r\n\r\n".getBytes());
            outputStream.write("Uploading is failed".getBytes());
            return;
        }

        //第一个boundary出现的位置
        int index1OfBoundary = request.indexOf(boundary);
        //第二个boundary出现的位置
        int index2OfBoundary = request.indexOf(boundary, index1OfBoundary + boundary.length());
        //第三个
        int index3OfBoundary = request.indexOf(boundary, index2OfBoundary + boundary.length());
        //文件部分的正文部分的开始前的位置
        int beforeOfFilePart = request.indexOf("\r\n\r\n", index2OfBoundary) + 3;
        //文件部分的正文部分的结束后的位置
        int afterOfFilePart = index3OfBoundary - 4;

        //文件部分的头的第一行结束后的位置
        int afterOfFilePartLine1 = request.indexOf("\r\n", index2OfBoundary + boundary.length());
        //文件部分的头的第二行
        String head2OfFilePart = request.substring(index2OfBoundary + boundary.length(), afterOfFilePartLine1);
        //上传文件的名字
        String fileName = head2OfFilePart.substring(head2OfFilePart.indexOf("\\") + 1, head2OfFilePart.length() - 1);
        //文件部分的正文部分之前字符串字节长度
        int len1 = request.substring(0, beforeOfFilePart + 1).getBytes().length;
        //文件部分的正文部分之后的字符串的字节长度
        int len2 = request.substring(afterOfFilePart, request.length()).getBytes().length;
        //文件部分的正文部分的字节长度
        int fileLen = requestBuffer.length - len1 - len2;

        //将文件正文部分保存到系统中
        FileOutputStream f = new FileOutputStream("server\\root\\" + fileName);
        f.write(requestBuffer,len1, fileLen);
        f.close();

        //创建并发送HTTP响应
        outputStream.write("HTTP/1.1 200 OK\r\n".getBytes());
        outputStream.write("Content-Type:text/html\r\n\r\n".getBytes());
        outputStream.write("<html><head><title>HelloWorld</title></head><body>".getBytes());
        outputStream.write(new String("<h1>Uploading is finished.<br></h1>").getBytes());
        outputStream.write(new String("<h1>FileName:"+fileName+"<br></h1>").getBytes());
        outputStream.write(new String("<h1>FileSize:"+fileLen+"<br></h1></body><head>").getBytes());

    }
}













