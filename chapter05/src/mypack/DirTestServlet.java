package mypack;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

/**
 * Created by haifei on 2017/9/29.
 */
public class DirTestServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ServletContext context = getServletContext();

        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        Enumeration eu = context.getAttributeNames();
        while (eu.hasMoreElements()) {
            String attributeName = (String) eu.nextElement();
            out.println("<br>" + attributeName + ": " + context.getAttribute(attributeName));
        }

        out.close();

        File workDir = (File) context.getAttribute("javax.servlet.context.tempdir");

        FileOutputStream fileOut = new FileOutputStream(workDir + "/temp.txt");
        fileOut.write("Hello World".getBytes());
        fileOut.close();
    }
}
