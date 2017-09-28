package mypack;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

/**
 * Created by haifei on 2017/9/28.
 */
public class UploadServlet extends HttpServlet {
    private String filePath; //存放上传文件的目录
    private String tempFilePath;    //存放临时文件的目录

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        filePath = config.getInitParameter("filePath");
        tempFilePath = config.getInitParameter("tempFilePath");
        filePath = getServletContext().getRealPath(filePath);
        tempFilePath = getServletContext().getRealPath(tempFilePath);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain");
        PrintWriter outNet = resp.getWriter();

        try {
            //创建一个基于硬盘的FileItem工厂
            DiskFileItemFactory factory = new DiskFileItemFactory();
            //设置向硬盘写数据时所用的缓冲区的大小，此处为4k
            factory.setSizeThreshold(4 * 1024);
            //设置临时目录
            factory.setRepository(new File(tempFilePath));

            //创建一个文件上传管理器
            ServletFileUpload upload = new ServletFileUpload(factory);
            //设置允许上传的文件的最大尺寸，此处为4M
            upload.setSizeMax(4 * 1024 * 1024);

            List items = upload.parseRequest(req);

            Iterator iter = items.iterator();
            while (iter.hasNext()) {
                FileItem item = (FileItem) iter.next();
                if (item.isFormField()) {
                    processFormField(item, outNet); //处理普通的表单域
                } else {
                    processUploadedFile(item, outNet);
                }
            }
            outNet.close();
        } catch (Exception e){

        }
    }

    private void processFormField(FileItem item, PrintWriter outNet){
        String name = item.getFieldName();
        String value = item.getString();
        outNet.println(name + ":" + value + "\r\n");
    }

    private void processUploadedFile(FileItem item, PrintWriter outNet) throws Exception {
        String fileName = item.getName();
        int index = fileName.lastIndexOf("\\");
        fileName = fileName.substring(index + 1, fileName.length());
        long fileSize = item.getSize();

        if (fileName.equals("") && fileSize == 0){
            return;
        }

        File uploadFile = new File(filePath + "/" + fileName);
        item.write(uploadFile);
        outNet.println(fileName + " is saved.");
        outNet.println("The size of " + fileName + " is " + fileSize + "\r\n");
    }
}













