package mypack;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by haifei on 2017/9/30.
 */
public class MainServlet extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/html");

        PrintWriter out = resp.getWriter();
        out.println("<html><head><title>MainServlet</title></head>");
        out.println("<body>");

        ServletContext context = getServletContext();
        RequestDispatcher headDispatcher = context.getRequestDispatcher("/header.htm");
        RequestDispatcher greetDispathcer = context.getRequestDispatcher("/greet");
        RequestDispatcher footerDispathcer = context.getRequestDispatcher("/footer.htm");

        headDispatcher.include(req, resp);
        greetDispathcer.include(req, resp);
        footerDispathcer.include(req, resp);

        out.println("</body></html>");
        out.close();
    }
}
