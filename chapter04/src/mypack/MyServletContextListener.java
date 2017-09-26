package mypack;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.*;

/**
 * Created by haifei on 2017/9/26.
 */
public class MyServletContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        System.out.println("helloapp application isInitialized.");

        ServletContext context = servletContextEvent.getServletContext();

        BufferedReader reader = new BufferedReader(new InputStreamReader(context.getResourceAsStream("/count/count.txt")));
        try {
            int count = Integer.parseInt(reader.readLine());
            reader.close();
            Counter counter = new Counter(count);
            context.setAttribute("counter", counter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        System.out.println("helloapp application is Destroyed.");

        ServletContext context = servletContextEvent.getServletContext();

        Counter counter = (Counter) context.getAttribute("counter");

        if (counter != null){
            try {
                String filePath = context.getRealPath("/count");
                filePath += "/count.txt";
                PrintWriter pw = new PrintWriter(filePath);
                pw.println(counter.getCount());
                pw.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
