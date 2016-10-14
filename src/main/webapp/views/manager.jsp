﻿<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <title>设备管理</title>

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
  <%
    Garden garden = (Garden)request.getAttribute("Garden");
  %>    
  	<div class="container">
  	  <ul class="list-inline">
  		  <li>
          <a href="qr-code.jsp">
            <img src="${pageContext.request.contextPath}/views/img/img_btn_add_small.png" />
          </a>
        </li>
        <li>
          <a href="farm-avatar.html">
            <img src=<%= garden.getAvatarUrl() %> />
          </a>
        </li>
  		  <li>
          <button type="button" id="myButton" class="btn btn-primary" autocomplete="off">
            手动
          </button>
        </li>
      </ul>
    </div>

    <ul class="list-group">
  		<li class="list-group-item">
        <div class="container">
          <ul class="list-inline">
            <li>
              <a href="device.jsp">
                <img src="${pageContext.request.contextPath}/views/img/img_device_default.png" />
              </a>
            </li> 
            <li><p>设备1</p></li>
            <li><img src="${pageContext.request.contextPath}/views/img/img_indicator_green.png" /></li>
          </ul>
        </div>
    <ul class="list-group">
      <%
        ArrayList list = (ArrayList)request.getAttribute("devicesList");

        for (int i=0;i<list.size();i++) {
        Device dev = (Device)list.get(i);
      %>

      <li class="list-group-item">
        <div class="container">
          <ul class="list-inline">
            <li>
              <a href="device.html">
                <img src=<%= ((Device)list.get(i)).getAvatarUrl() %> />
              </a>
            </li> 
            <li><p>设备<%= i %></p></li>
            <li><img src="${pageContext.request.contextPath}/views/img/img_indicator_green.png" /></li>
          </ul>
        </div>

        <% 
          if((dev.getPropertyCombine()&0x01)==1) {
        %>
        <li class="list-group-item">
          <ul class="list-inline">
            <li><img src="img/img_indicator_happy.png" /></li>
            <li><p>温度</p></li>
            <li><button type="button" class="btn btn-default">通风降温</button></li>
            <li>
              <progress value="42" max="100" style="width:4.8em;height:18px;">
            </li>
            <li><button type="button" class="btn btn-default">加温升温</button></li>
          </ul>
        </li>
        <%
        }
        %>

        <% 
          if((dev.getPropertyCombine()&0x02)==1) {
        %>        
        <li class="list-group-item">
          <ul class="list-inline">
            <li><img src="img/img_indicator_happy.png" /></li>
            <li><p>湿度</p></li>
            <li><button type="button" class="btn btn-default">浇水</button></li>
            <li>
              <progress value="22" max="100">
            </li>
          </ul>
        </li>
        <%
        }
        %>
        
        <% 
          if((dev.getPropertyCombine()&0x04)==1) {
        %>          
        <li class="list-group-item">
          <ul class="list-inline">
            <li><img src="img/img_indicator_happy.png" /></li>
            <li><p>光照</p></li>
            <li><button type="button" class="btn btn-default">增加光照</button></li>
            <li>
              <progress value="22" max="100">
            </li>
          </ul>
        </li>
        <%
        }
        %>
        
        <% 
          if((dev.getPropertyCombine()&0x08)==1) {
        %>          
        <li class="list-group-item">
          <ul class="list-inline">
            <li><img src="img/img_indicator_sad.png" /></li>
            <li><p>水位</p></li>
            <li><p>请您亲自浇水</p></li>
          </ul>
        </li> 
        <%
        }
        %>                                  
      </ul>
      <%
        }
      %>
    </ul>

    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="${pageContext.request.contextPath}/views/js/bootstrap.min.js"></script>
    <script type="text/javascript">
      $("#myButton").on('click', function() {
        var btn = document.getElementById("myButton")
        if (btn.innerHTML == "手动") {
          btn.innerHTML="自动"
        } else {
          btn.innerHTML="手动"
        }
      })
    </script>
  </body>
</html>