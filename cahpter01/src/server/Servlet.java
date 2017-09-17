package server;

import java.io.OutputStream;

/**
 * Created by haifei on 2017/9/17.
 */
public interface Servlet {

    public void init() throws Exception;

    public void service(byte[] requestBuffer, OutputStream outputStream) throws Exception;
}
