package com.atyyx.SpringMVC.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * SpringMVC支持ant风格的路径
 * 在@RequestMapping注解的value属性值中设置一些特殊字符
 * ？ 任意的单个字符（但是不抱愧？）
 * *：任意个数的任意字符（不包括？和/）
 * ** 任意层数的任意目录，注意使用方式只能**写在双斜线当中，并且前后不能够在有其他字符
 * 7.@RequestMapping注解使用路径占位符
 * 传统的方式：/deleteUser?id=1
 * rest：  /user/delete/1
 * 需要在@RequestMapping注解的value属性中所设置的路径中，使用{xxx}的方式表示路径中的数据
 * 在通过使用@PathVariable注解，将占位符所表示的值和控制器方法的形参进行绑定
 */
@Controller
public class HelloController {

    /**
     * /被解析成服务器的绝对路径
     * @return
     */
    @RequestMapping("/")
    public String protal()
    {
        // 将逻辑视图返回
        return "index";
    }

    @RequestMapping("/hello")
    public String hello()
    {
        return "success";
    }


}
