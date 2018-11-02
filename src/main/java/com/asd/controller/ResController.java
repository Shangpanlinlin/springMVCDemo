package com.asd.controller;


import com.asd.domain.User;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.tools.internal.ws.wsdl.document.soap.SOAPUse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/user")
public class ResController {

    @RequestMapping("/testDomain")
    public String testString(Model model){
        System.out.println("test String execute");
        User user = new User();
        user.setUsername("username1");
        user.setPassword("123456789");
        user.setAge(33);
        model.addAttribute("user", user);
        return "success";
    }

    @RequestMapping("/testVoid")
    public void testVoid(HttpServletRequest request , HttpServletResponse response) throws ServletException, IOException {
        System.out.println("return void execute");
        //request.getRequestDispatcher("/WEB-INF/pages/success.jsp").forward(request,response);
        response.sendRedirect(request.getContextPath()+"/index.jsp");
        return ;  //截断后续执行
    }

    @RequestMapping("/testModelAndView")
    public ModelAndView testModelAndView() throws ServletException, IOException {
        System.out.println("return ModelAndView");

        ModelAndView mv = new ModelAndView();
        User user = new User();
        user.setUsername("myname");
        user.setPassword("qwerewr");
        user.setAge(44);
        mv.setViewName("success");
        mv.addObject("user", user);
        return mv;
    }


    @RequestMapping("/testDispatcherAndRedirect")
    public String testDispatcherAndRedirect() throws ServletException, IOException {

        //return "forward:/WEB-INF/pages/success.jsp";
        return "redirect:/index.jsp";
    }
    @RequestMapping("/testJson")
    public @ResponseBody User testJson(@RequestBody User user) throws ServletException, IOException {
        System.out.println(user);
        user.setAge(18);
        return user;
    }



    @RequestMapping("/uploadTradition")
    public  String uploadTradition(HttpServletRequest request) throws Exception {
        //1.upload path make
        String path = request.getSession().getServletContext().getRealPath("/uploads/");
        File file = new File(path);
        System.out.println(path);
        if(!file.exists()){
            file.mkdir();
        }

        //2.get the DiskFileItemFactory
        DiskFileItemFactory factory = new DiskFileItemFactory();


        //3.get ServletFileUpload, and give the factory as the construction
        ServletFileUpload upload = new ServletFileUpload(factory);

        //4. parse the request
        List<FileItem> files  = upload.parseRequest(request);

        //5. check every item
        for (FileItem fileItem : files) {
            if(fileItem.isFormField()){

            }else{
                String name = fileItem.getName();
                fileItem.write(new File(path,name));
                fileItem.delete();
            }

        }

        return "success";
    }


    @RequestMapping("/uploadSpringMVC")
    public  String uploadSpringMVC(HttpServletRequest request, MultipartFile upload) throws Exception {
        //1.upload path make
        String path = request.getSession().getServletContext().getRealPath("/uploads/");
        File file = new File(path);
        System.out.println(path);
        if(!file.exists()){
            file.mkdir();
        }
        String name = upload.getOriginalFilename();
        upload.transferTo(new File(path,name));
        return "success";
    }



    @RequestMapping("/uploadToAnotherServer")
    public  String uploadToAnotherServer( MultipartFile upload) throws Exception {
        System.out.println("skip domain transfer file ");
        //1. get the path of another server
        String path = "http://localhost:9090/uploads/";

        String filename = upload.getOriginalFilename();
        String uuid = UUID.randomUUID().toString().replace("-","");
        filename = uuid + "_"+filename;
        Client client = Client.create();

        WebResource webResource  = client.resource(path + filename);
        webResource.put(upload.getBytes());

        return "success";
    }

}
