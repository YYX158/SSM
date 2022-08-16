package com.atyyx.SpringMVC.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 查询所有的用户信息--->/user--->get
 * 根据id查询所有的用户信息--->/user/1--->get
 * 添加用户信息--->/user---->post
 * 修改用户信息--->/user--->put
 * 删除用户信息----->/user/1--->delete
 *
 * 注意：浏览器目前只能发送get和post请求
 * 若要发送put和delete请求，需要在web.xml中配置一个过滤器HiddenHttpMethodFilter
 * 配置了过滤器之后，发送的请求需要满足两个条件，才能将请求方式转换成put或者delete
 * 1.当前请求必须是post
 * 2.当前请求必须传输请求参数_method  ，_method中的value值才是最终的请求方式
 *
 *
 * ------源码-----要实现put跟delete
 *
 *
 *
   //ConfigurableApplicationContext是ApplicationContext的子接口
 // 其中扩展了刷新和刷新和关闭容器的方法
   ConfigurableApplicationContext ioc=new ClassPathXmlApplicationContext("spring-lifecycle.xml");
 //ApplicationContext ioc=new ClassPathXmlApplicationContext("spring-lifecycle.xml");
 User user = ioc.getBean(User.class);
 System.out.println(user);
 ioc.close();




 //Default method parameter: {@code _method}.
 public static final String DEFAULT_METHOD_PARAM = "_method";

 private String methodParam = DEFAULT_METHOD_PARAM;
 *        @Override
 *    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
 * 			throws ServletException, IOException {
 *
 * 		HttpServletRequest requestToUse = request; // 将request存储起来
 *
 *      // 判断是否为post方法而且没有异常
 *      // request.getAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE) == null 请求域中没有异常
 * 		if ("POST".equals(request.getMethod()) && request.getAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE) == null)
 * 		{
 * 			String paramValue = request.getParameter(this.methodParam);// 获取请求参数，从_method中获取
 * 			if (StringUtils.hasLength(paramValue)) // 判断是否传入了_method这个参数
 * 			{
 * 				String method = paramValue.toUpperCase(Locale.ENGLISH);// 将_method中传过来的值转转成大写
 * 			 //判断	ALLOWED_METHODS这个集合中是否包含了我们从_method中所赋的值
 * 			 //   private static final List<String> ALLOWED_METHODS =
 * 			//        Collections.unmodifiableList(Arrays.asList(HttpMethod.PUT.name(),
 * 			//         HttpMethod.DELETE.name(), HttpMethod.PATCH.name()));
 * 				if (ALLOWED_METHODS.contains(method))
 * 				{
 * 			        // 替换之后的请求对象
 * 					requestToUse = new HttpMethodRequestWrapper(request, method);
 *                }
 *            }
 *        }
 *
 * 		filterChain.doFilter(requestToUse, response);
 *    }
 */
@Controller
public class testRestController {

    @RequestMapping(value = "/user",method = RequestMethod.GET)
    public String getAllUsers()
    {
        System.out.println("查询所有的用户信息---->/user--->get");
        return "success";
    }

    @RequestMapping(value = "/user/{id}",method = RequestMethod.GET)
    public String getUserById(@PathVariable("id") Integer id)
    {
        System.out.println("根据id查询所有的用户信息--->/user/"+id+"--->get");
        return "success";
    }

    @RequestMapping(value = "/user",method = RequestMethod.POST)
    public String insertUser()
    {
        System.out.println("添加用户信息--->/user--->put");
        return "success";
    }

    @RequestMapping(value = "/user",method = RequestMethod.PUT)
    public String updateUser()
    {
        System.out.println("修改用户信息--->/user---->put");
        return "success";
    }

    @RequestMapping(value = "/user/{id}",method = RequestMethod.DELETE)
    public String deletetUser(@PathVariable("id")Integer id)
    {
        System.out.println("删除用户信息----->/user/"+id+"--->delete");
        return "success";
    }

}
