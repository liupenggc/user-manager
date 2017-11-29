<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title></title>
        <link type="text/css" rel="stylesheet" media="all" href="../styles/global.css" />
        <link type="text/css" rel="stylesheet" media="all" href="../styles/global_color.css" />
        <script language="javascript" type="text/javascript">
            //排序按钮的点击事件
            function sort(btnObj) {
                if (btnObj.className == "sort_desc")
                    btnObj.className = "sort_asc";
                else
                    btnObj.className = "sort_desc";
            }
            //删除
            function deleteFee(id) {
                var r = window.confirm("确定要删除吗？");
                if(r){
                	window.location.href="deleteCost.do?id="+id;
                }
            }
        </script>        
    </head>
    <body>
        <!--主要区域开始-->
        <div id="main">
            <form action="" method="">
                <!--排序-->
                <div class="search_add">
                    <input type="button" value="增加" class="btn_add" onclick="location.href='toAddCost.do';" />
                </div> 
                <!--启用操作的操作提示-->
                <div id="operate_result_info" class="operate_success">
                    <img src="../images/close.png" onclick="this.parentNode.style.display='none';" />
                   	 删除成功！
                </div>    
                <!--数据区域：用表格展示数据-->     
                <div id="data">            
                    <table id="datalist">
                        <tr>
                            <th>ID</th>
                            <th class="width100">姓名</th>
                            <th>年龄</th>
                            <th>性别</th>
                            <th class="width200"></th>
                        </tr>                      
                      <c:forEach items="${costs}" var="c">
                        <tr>
                            <td>${c.id}</td>
                            <td><a href="fee_detail.html">${c.name}</a></td>
                            <td>${c.age}</td>
                            <td>${c.sex}</td>
                            <td>                                
                                <input type="button" value="修改" class="btn_modify" onclick="location.href='toUpdateCost.do?id=${c.id}';" />
                                <input type="button" value="删除" class="btn_delete" onclick="deleteFee('${c.id}');" />
                            </td>
                        </tr>
                       </c:forEach>
                    </table>
                </div>
            </form>
        </div>
        <!--主要区域结束-->
        <div id="footer">
            <span>liupenggc@feinno.com</span>
            <br />
            <span>北京新媒传信科技有限公司 </span>
        </div>
    </body>
</html>