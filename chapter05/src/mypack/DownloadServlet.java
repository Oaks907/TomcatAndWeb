package mypack;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by haifei on 2017/9/27.
 */
public class DownloadServlet extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        OutputStream out;   //输出响应正文输出流
        InputStream in; //读取本地文件输入流

        String filename = req.getParameter("filename");

        if (filename == null) {
            out = resp.getOutputStream();
            out.write("Please input filename.".getBytes());
            out.close();
            return;
        }

        //读取本地文件输入流
        in = getServletContext().getResourceAsStream("/store/" + filename);
        int length = in.available();
        resp.setContentType("application/force-download");
        resp.setHeader("Content-length", String.valueOf(length));
        resp.setHeader("Content-Dispatch", "attachement;filename=\"" + filename +"\" ");

        /**把本地文件中的数据发送给客户*/
        out = resp.getOutputStream();
        int bytesRead = 0;
        byte[] buffer = new byte[512];
        while ((bytesRead = in.read(buffer)) != -1) {
            out.write(buffer, 0, bytesRead);
        }

        in.close();
        out.close();
    }
}
