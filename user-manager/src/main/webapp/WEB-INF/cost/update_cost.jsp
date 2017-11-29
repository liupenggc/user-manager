<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title></title>
        <link type="text/css" rel="stylesheet" media="all" href="../styles/global.css" />
        <link type="text/css" rel="stylesheet" media="all" href="../styles/global_color.css" />
        <script language="javascript" type="text/javascript">
            //保存结果的提示
            function showResult() {
                showResultDiv(true);
                window.setTimeout("showResultDiv(false);", 3000);
            }
            function showResultDiv(flag) {
                var divResult = document.getElementById("save_result_info");
                if (flag)
                    divResult.style.display = "block";
                else
                    divResult.style.display = "none";
            }
        </script>
    </head>
    <body>
        <!--主要区域开始-->
        <div id="main">            
            <div id="save_result_info" class="save_success">保存成功！</div>
            <form action="updateCost.do" method="post" class="main_form">
                <div class="text_info clearfix"><span>ID：</span></div>
                <div class="input_info">
                    <input type="text" name="id" value="${cost.id}" class="width100" />
                </div>
                <div class="text_info clearfix"><span>姓名:</span></div>
                <div class="input_info">
                    <input type="text" name="name" value="${cost.name}" class="width100" />
                </div>
                <div class="text_info clearfix"><span>年龄:</span></div>
                <div class="input_info">
                    <input type="text" name="age" value="${cost.age}" class="width100" />
                </div>
                <div class="text_info clearfix"><span>性别:</span></div>
                <div class="input_info">
                    <input type="text" name="sex" value="${cost.sex}" class="width100" />
                </div>   
                <div class="button_info clearfix">
                    <input type="submit" value="保存" class="btn_save" />
                    <input type="button" value="取消" class="btn_save" onclick="history.back();"/>
                </div>
            </form>
        </div>
        <div id="footer">
            <span>liupenggc@feinno.com</span>
            <br />
            <span>北京新媒传信科技有限公司 </span>
        </div>
    </body>
</html>