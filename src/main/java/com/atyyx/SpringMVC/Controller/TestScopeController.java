package com.atyyx.SpringMVC.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Model/ModelMap/Map本质上都是  org.springframework.validation.support.BindAwareModelMap类型
 * org.springframework.validation.support.BindAwareModelMap  继承于ExtendedModelMap
 * public class BindingAwareModelMap extends ExtendedModelMap
 * public class ExtendedModelMap extends ModelMap implements Model
 * public class ModelMap extends LinkedHashMap<String, Object>
 * public class LinkedHashMap<K,V>
 *     extends HashMap<K,V>
 *     implements Map<K,V>
 */
@Controller
public class TestScopeController {

    @RequestMapping("/test/mav")
    public ModelAndView testMAV()
    {
        /**
         * ModelAndView 包含了Model 和View的功能
         * Model：向请求域中去共享数据
         * View：设置逻辑视图实现页面跳转
         */
        ModelAndView mav = new ModelAndView();
        // 向请求域中去共享数据
        mav.addObject("testRequesScope","hello,ModelAndView");
        // 设置要跳转的视图名称
        mav.setViewName("success");
        return mav;
    }

    @RequestMapping("/test/model")
    public String testModel(Model model)
    {
        //org.springframework.validation.support.BindAwareModelMap
        System.out.println(model.getClass().getName());
        model.addAttribute("testRequesScope","hello,Model");
        return "success";
    }

    @RequestMapping("/test/modelMap")
    public String testModelMap(ModelMap modelMap)
    {
        //org.springframework.validation.support.BindAwareModelMap
        System.out.println(modelMap.getClass().getName());
        modelMap.addAttribute("testRequesScope","hello,ModelMap");
        return "success";
    }

    @RequestMapping("/test/map")
    public String testMap(Map<String,Object> map)
    {
        //org.springframework.validation.support.BindAwareModelMap
        System.out.println(map.getClass().getName());
        map.put("testRequesScope","hello,ModelMap");
        return "success";
    }

    /**
     * session中的数据只跟我们浏览器的开启和关闭有关
     * session的钝化：当我们的服务器关闭了以后，session中的数据会被保存到（钝化到）我们本地的一个磁盘文件中，钝化到tomcat中有一个work目录
     * 就是用来存储我们session钝化以后的文件的，还有jsp所翻译的servlet文件。
     * 当我们的服务器重新加载了以后，就可以将本地的钝化文件中的数据重新加载到session中，这就是session的活化。
     *
     * ---钝化的本质其实是一个序列化的过程，所以我们的java代码要想实现钝化，就必须实现序列化的接口
     * @param session
     * @return
     */
    @RequestMapping("/test/session")
    public String testSession(HttpSession session)
    {
        session.setAttribute("testRequesScope","hello,session");
        return "success";
    }

    /**
     * 应用域中的数据只跟我们的服务器是否关闭有关
     * @param session
     * @return
     */
    @RequestMapping("/test/application")
    public String testApplication(HttpSession session)
    {
        // 获取我们当中最大的对象
        ServletContext servletContext = session.getServletContext();
        servletContext.setAttribute("testApplication","hello,application");
        return "success";
    }

    /**
     * thymeleaf中的视图转发，比较经常使用
     * @return
     */
    @RequestMapping("/test/view/thymeleaf")
    public String testThymeleafView()
    {
        return "success";
    }

    /***
     * 只是做简单的视图转发，但是不会给thymeleaf渲染
     * 所以不是很推荐使用
     * 转发：地址是不变的
     *
     * --一般来说，业务逻辑成功用重定向；失败用转发
     * @return
     */
    @RequestMapping("/test/view/forward")
    public String testInternalResourceView()
    {

        return "forward:/test/model";
    }

    /**
     * 做重定向视图
     * 重定向以后地址发生改变了
     * @return
     */
    @RequestMapping("/test/view/redirect")
    public String testViewThymeleaf()
    {
        /**
         * 在浏览器解析中去掉redirect:，然后在通过response.setRedirect来实现页面跳转
         * 在servlet中没有办法直接写一个绝对路径，绝对路径就会被浏览器所解析
         * 重定向到的路径就会被浏览器解析，浏览器会将这个/解析成是localhost,没有上下文路径
         * 在redirect中会自动会为我们的路径添加上下文路径
         */
        return "redirect:/test/model";
    }

}
