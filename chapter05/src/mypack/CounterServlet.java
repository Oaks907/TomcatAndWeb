package mypack;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by haifei on 2017/9/28.
 */
public class CounterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ServletContext context = getServletContext();

        Counter counter = new Counter(1);

        if (counter == null) {
            counter = new Counter(1);
            context.setAttribute("counter", counter);
        }

        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.println("<hmtl><head><title>CountServlet></title></head>");
        out.println("<body>");

        String imageLink = "<img src='image?count=" + 12 +"'";
        out.println(counter.getCount());
        out.println("欢迎光临本站。您是第 " + imageLink + " 位访问者。");
        out.println("</body></html>");

        counter.add(1);
        out.println(counter.getCount());
        out.close();
    }
}
