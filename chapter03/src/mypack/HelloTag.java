package mypack;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * Created by haifei on 2017/9/20.
 */
public class HelloTag extends TagSupport {


    /**
     * 当JSP解析器遇到hello标签的结束标志时，调用此方法
     */
    @Override
    public int doEndTag() throws JspException {

        try {
            pageContext.getOut().print("hello");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return EVAL_PAGE;
    }
}
