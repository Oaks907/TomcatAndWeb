package mypack;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by haifei on 2017/9/28.
 */
public class Cookie1Servlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie cookie = null;

        resp.setContentType("text/plain");
        PrintWriter out = resp.getWriter();

        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                out.println("Cookie name:" + cookies[i].getName());
                out.println("Cookie value:" + cookies[i].getValue());
               if (cookies[i].getName().equals("username")){
                   cookie = cookies[i];
               }
            }
        } else {
            out.println("No cookie.");
        }

        if (cookie == null) {
            cookie = new Cookie("username", "Tom");
            cookie.setMaxAge(60 * 60);
            resp.addCookie(cookie);
        } else if (cookie.getValue().equals("Tom")){
            cookie.setValue("Jack");
            resp.addCookie(cookie);
        } else if (cookie.getValue().equals("Jack")) {
            cookie.setValue("Tom");
            resp.addCookie(cookie);
        }
    }
}
