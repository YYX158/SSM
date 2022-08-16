package com.atyyx.SpringMVC.Controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.UUID;

@Controller
public class FileUpAndDownController {
    /**
     * ResponseEntity可以作为控制器方法的返回值，表示响应到浏览器的完整报文
     * @param session
     * @return
     * @throws IOException
     */
    @GetMapping("/test/download")
    public ResponseEntity<byte[]> testResponseEntity(HttpSession session) throws IOException {
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
    }

    /**
     * 文件上传的要求：
     * 1.form的请求方式必须是post
     * 2.form表单必须设置属性值
     *
     * SpringMVC将上传的文件封装成MultipartFile类
     */
    @PostMapping("/test/up")
    public String FileUp(MultipartFile photo,HttpSession session) throws IOException {
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
    }
}
