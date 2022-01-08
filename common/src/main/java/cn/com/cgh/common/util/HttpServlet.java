package cn.com.cgh.common.util;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    public static void print(String message,long code, Integer status) {
        HttpServletResponse response = getResponse();
        print(response,message,code,status);
    }
    public static void print(HttpServletResponse response,String message,long code, Integer status) {
        print(response, new R(code,message),status);
    }

    public static void print(Object data, Integer status) {
        HttpServletResponse response = getResponse();
        print(response,data,status);
    }
    /**
     * @param response
     * @param data JSONUtil.toJsonObject(data)
     */
    public static void print(HttpServletResponse response,Object data, Integer status) {
        response.setStatus(status == null ? 200 : status);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        try {
            new ObjectMapper().writeValue(response.getWriter(),data);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        Writer writer = null;
//        try {
//            response.setStatus(status == null ? 200 : status);
//            response.setCharacterEncoding("UTF-8");
//            response.setContentType("application/json;charset=utf-8");
//            writer = response.getWriter();
//            writer.write(JSONObject.toJSONString(data));
//            writer.flush();
//        } catch (IOException e) {
//            log.error("FrontendHttpForwardUtil extend FrontendHttpAbstract.print() :" + e.getMessage());
//        }finally {
//            if (writer != null){
//                try {
//                    writer.close();
//                } catch (IOException e) {
//                }
//            }
//        }
    }
}
