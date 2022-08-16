package com.atyyx.SpringMVC.Controller;

import com.atyyx.SpringMVC.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Controller
public class AjaxController {

//    @RequestMapping("/test/ajax")
    // @RequestBody获取请求体参数和方法的形参进行绑定
     @PostMapping("/test/ajax")
    public void testAjax(Integer id, HttpServletResponse response,
                         @RequestBody String postJoson) throws IOException {

        System.out.println("id="+id);
        //getWriter()需要涉及流的操作
        response.getWriter().write("hello,axios");
         System.out.println(postJoson);
    }

    /**
     * 2.使用@RequestBody注解，将json格式的请求参数转换成java对象
     * 要求：
     * ①导入jackjson的依赖
     * ②开启mvc的注解驱动    <mvc:annotation-driven/>
     * ③在处理请求的控制器方法的形参位置，直接设置json格式请求参数转换成java类型的形参
     * 然后用@RequestBody注解表示
     * @param response
     * @throws IOException
     */
    @PostMapping("/test/RequestBody/json")
    public void testRequestBody(HttpServletResponse response,@RequestBody User user) throws IOException
    {
        response.getWriter().write("hello,RequestBody");
        System.out.println(user);

    }

    @PostMapping("/test/RequestBody/jsonMap")
    public void testRequestBodyMap(HttpServletResponse response,@RequestBody Map<String,Object> user) throws IOException
    {
        response.getWriter().write("hello,RequestBodyMap");
        System.out.println(user);

    }

    @ResponseBody
    @RequestMapping("/test/ResponseBody")
    public String testResponseBody()
    {
        return "success";
    }
}
