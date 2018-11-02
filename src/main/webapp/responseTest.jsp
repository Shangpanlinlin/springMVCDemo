<%--
  Created by IntelliJ IDEA.
  User: panlinlin
  Date: 18/11/1
  Time: 下午3:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script type="application/javascript">
        $(function(){
            $("#buttom").click(function(){
                alert("clicked");
                $.ajax({
                    url:"user/testJson",
                    type:"post",
                    contentType:"application/json;charset=UTF-8",
                    data:'{"username":"panlinlin", "password":"12351", "age":90}',
                    dataType:"json",
                    success: function(data){
                        alert(data);
                        alert(data.username);
                        alert(data.password);
                        alert(data.age);
                    }
                });
            });
        });

    </script>
</head>
<body>

<a href="user/testString">返回值是String类型</a>
<a href="user/testDomain">返回值类型是domain</a>
<a href="user/testVoid">返回值类型是void</a>
<a href="user/testModelAndView">返回值类型是ModelAndView</a>
<a href="user/testDispatcherAndRedirect">返回值类型是Dispatch and Redirect</a>
<input id="buttom"  type="button" value="异步请求发送数据是json返回值类型也是Json">
<<br><br>
传统方式上传文件
<form action="/user/uploadTradition" method="post" enctype="multipart/form-data">
    请选择文件<input type="file" name="upload">
    <input type="submit" value="submit" >
</form>
<br><br>
使用springmvc自带的解析器上传文件
<form action="/user/uploadSpringMVC" method="post" enctype="multipart/form-data">
    请选择文件<input type="file" name="upload">
    <input type="submit" value="submit" >
</form>


<br><br>
跨域上传
<form action="/user/uploadToAnotherServer" method="post" enctype="multipart/form-data">
    请选择文件<input type="file" name="upload">
    <input type="submit" value="submit" >
</form>
</body>
</html>
