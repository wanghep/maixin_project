﻿<%@ page import="java.util.ArrayList" %>
<%@ page import="com.mx.domain.Garden" %>
<%@ page import="com.mx.LogUtil" %>
<%@ page import="sun.rmi.runtime.Log" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
  <title>麦信智能生态花园</title>

  <!-- Bootstrap -->
  <link href="${pageContext.request.contextPath}/views/css/bootstrap.min.css" rel="stylesheet">

  <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
  <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
  <!--[if lt IE 9]>
  <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
  <![endif]-->
</head>
<body>

<ul class="list-group">
  <%
    ArrayList list = (ArrayList)request.getAttribute("GardenList");
    Object userIdObjext = request.getAttribute("userId");

    long userId = 0;
    userId = ((Long)(userIdObjext) ).longValue();

    for  (int i=0;i<list.size();i++) {
  %>
  <li class="list-group-item">
    <a href="${pageContext.request.contextPath}/weiXinDevice/devices?gardenId=<%=((Garden)list.get(i)).getId() %>">
      <ul class="list-inline">
        <li><img src=<%= ((Garden)list.get(i)).getAvatarUrl() %> /></li>
        <li><p><%= ((Garden) list.get(i)).getName() %></p></li>
        <li><p><%= ((Garden) list.get(i)).getDeviceCount() %>台设备</p></li>
      </ul>
    </a>
  </li>
  <%
    }
  %>
</ul>

<script alert(<%=userIdObjext%>) ></script>

<div class="container">
  <a href="${pageContext.request.contextPath}/weiXinDevice/addGarden?userId=<%= userId %>">
    <img  src="${pageContext.request.contextPath}/views/img/img_btn_add.png" />
  </a>
</div>

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="${pageContext.request.contextPath}/views/js/jquery-3.1.1.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="${pageContext.request.contextPath}/views/js/bootstrap.min.js"></script>
</body>
</html>