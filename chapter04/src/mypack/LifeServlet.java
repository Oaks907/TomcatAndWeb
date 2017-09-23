package mypack;

import javax.servlet.*;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by haifei on 2017/9/23.
 */
public class LifeServlet extends GenericServlet {

    private int initVar = 0;
    private int serviceVar = 0;
    private int destoryVar = 0;
    private String name;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        name = config.getServletName();
        initVar++;
        System.out.println(name + ">init():Servlet 被初始化了" + initVar + "次");
    }

    @Override
    public void destroy() {
        super.destroy();
        destoryVar++;
        System.out.println(name + ">destory():Servlet 被销毁了" + destoryVar + "次");
    }

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        serviceVar++;
        System.out.println(name + ">service():Servlet共响应了:" + serviceVar + "次");

        String content1 = "初始化次数:" + initVar;
        String content2 = "响应用户的服务次数:" + serviceVar;
        String content3 = "销毁次数:" + destoryVar;

        servletResponse.setContentType("text/html;charset=UTF-8");

        PrintWriter out = servletResponse.getWriter();
        out.print("<html><head><title>LiftServlet></title></head>");
        out.print("<body>");
        out.print("<h1>" + content1 + "</h1>");
        out.print("<h1>" + content2 + "</h1>");
        out.print("<h1>" + content3 + "</h1>");
        out.print("</html>");
    }


}


















