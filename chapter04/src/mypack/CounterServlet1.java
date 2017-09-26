package mypack;

import javax.servlet.*;
import java.io.*;

/**
 * Created by haifei on 2017/9/26.
 */
public class CounterServlet1 extends GenericServlet {

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {

        //获取ServletContext的引用
        ServletContext servletContext = getServletContext();

        //由servletContext中读取counter属性
        Counter counter = (Counter) servletContext.getAttribute("counter");

        if (counter == null) {
            counter = new Counter(1);
            servletContext.setAttribute("counter", counter);
        }

        servletResponse.setContentType("text/html;charset=GB2312");
        PrintWriter out = servletResponse.getWriter();
        out.println("<html><head><title>CounterServlet</title><head/>");
        out.println("<body>");
        out.println("<h1>欢迎光临本站。你是第 " + counter.getCount() + " 位访问者。</h1>");
        out.print("</html>");

        counter.add(1);
        out.close();
    }

    @Override
    public void init() throws ServletException {
        System.out.println("CounterServlet1 is Initialized.");

        ServletContext context = getServletContext();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(context.getResourceAsStream("/count/count.txt")));
            int count = Integer.parseInt(reader.readLine());
            reader.close();

            //创建计数器对象，将计数器对象保存在Web应用范围内
            Counter counter = new Counter(count);
            context.setAttribute("counter", counter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void destroy() {
        System.out.println("CounterServlet1 is destory.");

        ServletContext context = getServletContext();

        Counter counter = (Counter) context.getAttribute("counter");

        if (counter != null) {
            try {
                String filePath = context.getRealPath("/count");
                filePath += "count.txt";
                PrintWriter pw = new PrintWriter(filePath);
                pw.println(counter.getCount());
                pw.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}























