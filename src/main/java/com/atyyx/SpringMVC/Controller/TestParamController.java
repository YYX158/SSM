package com.atyyx.SpringMVC.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 获取请求参数的当时
 * 1.通过servletAPI获取
 * 只需要在控制器方法的形参位置上设置HttpServletRequest类型的形参
 * 就可以直接在控制器方法中使用request对象获取请求参数
 * 2.通过控制器方法的形参获取
 * 只需要在控制器方法的形参位置，设置一个形参，要求形参的名字和请求参数的名字要一样
 * 如果形参的名字和请求的名字不一样，可以用@RequestParam
 * 3.@RequestParam的value可以指定一个请求参数的名字
 * required设置是否需要传输value所对应的请求参数，
 * 默认值为true，表示value所对应的请求参数必须传输，否则页面报错
 * 400--Required String paramter  'xxx'is not present
 * 若设置为false，则表示value所对应的请求参数不是必须传输的；若传输，则形参的值为null
 * defaultValue：设置当没有传输value所对应的请求参数的时候，为形参设置的默认值，此时和required的属性值就无关了
 *
 * 4.@RequestHeader:将请求头信息和控制器方法的形参绑定
 * 5.@CookieValue将cookie数据和控制器方法的形参绑定
 */
@Controller
public class TestParamController {

    /**
     * Cookie
     * @param request
     * @return
     */
    @RequestMapping("/param/servletAPI")
    public String getParamByServletAPI(HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        System.out.println("username:"+username+",password:"+password);
        return "success";
    }

    /**
     * HttpSession: 第一次在获取session的时候是通过request.getSession()去获取Session的值
     * 然后这个时候就会去检测我们这时候传来的报文是否携带了名叫JESSIONID的请求报文
     * 如果说没有这个叫做JESSIONID的请求报文，就会在服务器中去创建一个JESSIONID的cookie和一个HttpSession对象
     * 然后把我们的Session对象存储到服务器所维护的map对象中，
     * 这个map集合以JESSIONID这个cookie的值作为键，以创建出来的Session对象作为值。JESSIONID的cookie的值是一个随机序列
     * 然后再把cookie相应到浏览器
     * @param username
     * @param password
     * @param referer
     * @param jsessionId
     * @return
     */
    @RequestMapping("/param")
    public String getParam(@RequestParam(value = "username",required = false,defaultValue = "hello") String username,
                           @RequestParam(value = "password",defaultValue = "123456") String password,
                           @RequestHeader(value = "Accept",defaultValue = "不存在") String referer,
                           @CookieValue("JSESSIONID") String jsessionId)
    {
        System.out.println("referer:"+referer);
        System.out.println("username:"+username+",password:"+password);
        System.out.println("JSESSIONID:"+jsessionId);
        return "success";
    }
}
