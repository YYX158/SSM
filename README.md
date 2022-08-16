# SpringMVC_helloworld
这是一个SpringMVC的比较基础的项目，在这个项目中利用tomcat作为服务器，通过部署项目到tomcat服务器上，以此完成功能。
在这个项目中数据库的话采用的是mysql，里面介绍了SpringMVC的核心组件，并且对文件功能上传进行了实现。
在DispatchServlet.java文件中有这么一段代码，是用来介绍SpringMVC中的九大核心组件。
/**
	 * Initialize the strategy objects that this servlet uses.
	 * <p>May be overridden in subclasses in order to initialize further strategy objects.
	 */
	protected void initStrategies(ApplicationContext context) 
  {
		initMultipartResolver(context);                 //MultipartResolver  主要用在解决文件上传问题
		initLocaleResolver(context);
		initThemeResolver(context);
		initHandlerMappings(context);                  // HandlerMappings 主要用于做视图映射，用来匹配对应的控制器方法
		initHandlerAdapters(context);                  // HandlerAdapters 视图适配器，匹配到的控制器方法还需要通过该处理器去调用
		initHandlerExceptionResolvers(context);        // HandlerExceptionResolvers  异常处理解析器
		initRequestToViewNameTranslator(context);      // RequestToViewNameTranslator  将请求的视图名称进行转换
		initViewResolvers(context);                    // ViewResolvers  视图解析器
		initFlashMapManager(context);
	}

利用response.getWriter().write("hello,axios")向着请求域中进行输入数据。

文件下载功能实现
 // 获取ServletContext对象
 ServletContext servletContext = session.getServletContext();
 // 获取服务器中文件的真实路径
 //getRealPath中如果是放空字符串，那么就会得到整个项目的一个真实路径
 String realPath = servletContext.getRealPath("img");
 realPath=realPath+ File.separator+"zwl.jpg";// 这样子分隔符就能适应不同的操作系统
 System.out.println("图片的路径"+realPath);
 // 创建输入流
 FileInputStream is = new FileInputStream(realPath);
 // 创建字节数组
 //is.available() 获取字节输入流对应的文件的字节数
 byte[] bytes = new byte[is.available()];
 // 将流读到字节数组
 is.read(bytes);
 // 创建HttpHeaders对象设置响应头信息
 MultiValueMap<String,String> headers = new HttpHeaders();
 // 设置要下载方式及下载文件的名字
 // 设置下载方式 Content-Disposition    不区分大小写，但是前面一定是固定的attachment;filename=
 // 这是以附件的形式进行下载，设置下载下来的文件名字
 headers.add("Content-Disposition","attachment;filename=zwl.jpg");
 // 设置响应状态码
 HttpStatus statusCode=HttpStatus.OK;
 //创建ResponseEntity对象   ---响应体，响应头，状态码
 //http报文主要由响应体、响应头以及状态码组成
 ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(bytes, headers, statusCode);
 // 关闭输入流
 is.close();
 return responseEntity;
 
 
 文件上传功能的实现
 * 文件上传的要求：
 * 1.form的请求方式必须是post
 * 2.form表单必须设置属性值
 *
 * SpringMVC将上传的文件封装成MultipartFile类
 // 获取要上传的文件名称
 String filename = photo.getOriginalFilename();
 System.out.println("要上传的文件名称:"+filename);
 // 获取上传文件的后缀名
 // substring是包前不包后的
 String hzName = filename.substring(filename.lastIndexOf("."));
 // 获取uuid
 String uuid = UUID.randomUUID().toString();
 filename=uuid+hzName;
 // 获取ServletContext对象
 ServletContext servletContext = session.getServletContext();
 // 获取当前工程下photo的真实路径
 String photoPath = servletContext.getRealPath("photo");
 System.out.println("真实路径"+photoPath);
 // 创建photoPath所对应的file对象
 File file = new File(photoPath);
 // 判断file所对应的目录是否存在
 if(!file.exists())
 {
     // 创建目录
     file.mkdir();
     System.out.println("目录不存在");
 }
 String finalPath=photoPath+File.separator+filename;
 // 默认是不追加，进行内容覆盖
 //new FileOutputStream();
 // 上传文件
 photo.transferTo(new File(finalPath));
 return "success";
 

在SpringMVC中上传文件和下载文件都是固定的模板，可以直接搬运！
    
