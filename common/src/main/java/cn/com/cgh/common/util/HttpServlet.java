package cn.com.cgh.common.util;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

@Slf4j
public class HttpServlet {
    public static HttpServletRequest getRequest(){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return requestAttributes.getRequest();
    }

    public static HttpServletResponse getResponse(){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes)RequestContextHolder.currentRequestAttributes();
        return requestAttributes.getResponse();
    }

    public static void print(String message,long code) {
        HttpServletResponse response = getResponse();
        print(response,message,code);
    }
    public static void print(HttpServletResponse response,String message,long code) {
        print(response, new R(code,message));
    }

    public static void print(Object data) {
        HttpServletResponse response = getResponse();
        print(response,data);
    }
    /**
     * @param response
     * @param data JSONUtil.toJsonObject(data)
     */
    public static void print(HttpServletResponse response,Object data) {
        Writer writer = null;
        try {
            response.setStatus(200);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json;charset=utf-8");
            writer = response.getWriter();
            writer.write(JSONObject.toJSONString(data));
            writer.flush();
        } catch (IOException e) {
            log.error("FrontendHttpForwardUtil extend FrontendHttpAbstract.print() :" + e.getMessage());
        }finally {
            if (writer != null){
                try {
                    writer.close();
                } catch (IOException e) {
                }
            }
        }
    }
}
