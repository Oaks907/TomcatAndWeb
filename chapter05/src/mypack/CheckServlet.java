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
 * Created by haifei on 2017/9/29.
 */
public class CheckServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String message = null;
        if (username == null){
            message = "Please input username.";
        } else {
            message = "Hello, " + username;
        }
        req.setAttribute("message", message);

        ServletContext context = getServletContext();
        RequestDispatcher dispatcher = context.getRequestDispatcher("/output");

        PrintWriter out = resp.getWriter();
        out.println("Output form checkServlet before forwarding request.");

        dispatcher.forward(req, resp);

        out.println("Output from checkServlet after forwarding request.");
        System.out.println("Output from CheckServlet after forwarding request.");
    }
}
