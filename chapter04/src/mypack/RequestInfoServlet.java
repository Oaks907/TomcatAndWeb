package mypack;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

/**
 * Created by haifei on 2017/9/22.
 */
public class RequestInfoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //设置HTTP响应正文的格式
        resp.setContentType("text/html;charset=GB2312");

        PrintWriter out = resp.getWriter();
        out.println("<html><head><title>RequestInfo</title></head></html>");
        out.println("<body>");
        //打印服务器端的IP地址
        out.println("<br>LocalAddr:" + req.getLocalAddr());
        //打印服务端的主机名
        out.println("<br>LocalName:"+ req.getLocalName());
        //打印服务端的FTP端口号
        out.println("<br>LocalPort:" + req.getLocalPort());
        //打印服务端与客户端的通信协议以及版本号
        out.println("<br>Protocol:" + req.getProtocol());
        //打印客户端的IP地址
        out.println("<br>RemoteAddr:" + req.getRemoteAddr());
        //打印客户端的主机名
        out.println("<br>RemoteHost:" + req.getRemoteHost());
        //打印客户端的FTP端口号
        out.println("<br>RemotePort" + req.getRemotePort());

        //打印HTTP请求方式
        out.println("<br>Method:" + req.getMethod());
        //打印HTTP的请求URL
        out.println("<br>URI:" + req.getRequestURL());
        //打印客户端所请求访问的web应用的URL入口
        out.println("<br>ContextPath:" + req.getContextPath());
        //打印HTTP请求中查询字符串
        out.println("<br>QueryString:" + req.getQueryString());

        /*打印HTTP请求头*/
        out.println("<br>***打印HTTP请求头****");
        Enumeration eu = req.getHeaderNames();
        while (eu.hasMoreElements()){
            String headName= (String) eu.nextElement();
            out.println("<br>" + headName + ":" + req.getHeader(headName));
        }
        out.println("<br>但因HTTP请求头结束***<br>");
        out.println("<br>username:" + req.getParameter("username"));
        out.println("</body></html>");

        out.close();
    }
}

























