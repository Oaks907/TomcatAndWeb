package mypack;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by haifei on 2017/9/23.
 */
public class ContextTesterServlet extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ServletContext servletContext = getServletContext();

        //设置HTTP响应的正文的 MIME 及字符编码
        resp.setContentType("text/html;charset=UTF-8");

        PrintWriter out = resp.getWriter();
        out.print("<html><head><title>FontServlet</title></head></html>");
        out.print("<body>");
        out.print("<br> Email: " + servletContext.getInitParameter("emailOfwebmaster"));
        out.print("<br>Path:" + servletContext.getRealPath("/WEB-INF"));
        out.print("<br>MimeType:" + servletContext.getMimeType("/WEB-INF/web.xml"));
        out.print("<br>MajorVersion:" + servletContext.getMajorVersion());
        out.print("<br>ServerInfo:" + servletContext.getServerInfo());
        out.print("<br></body></=html>");

        servletContext.log("这是ContextTesterServlet输出的日志。");
        out.close();
    }
}
