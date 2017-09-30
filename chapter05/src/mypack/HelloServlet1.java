package mypack;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by haifei on 2017/9/30.
 */
public class HelloServlet1 extends HttpServlet {

    private String username = null;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        username = req.getParameter("username");

        if (username != null) {
            username = new String(username.getBytes("ISO-8859-1"), "GB2312");
        }

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        resp.setContentType("text/html;charset=UTF-8");

        PrintWriter out = resp.getWriter();
        out.println("<html><head><title>HelloServlet</title>");
        out.println("<body>");
        out.println("name: " + username);
        out.println("</body></html>");

        out.close();
    }
}
