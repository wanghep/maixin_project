﻿<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <title>麦信智能生态花园</title>

    <!-- Bootstrap -->
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
  </head>
  <body>

    <ul class="list-group">
		  <li class="list-group-item">
        <a href="manager.jsp">
    			<ul class="list-inline">
            <li><img src="${pageContext.request.contextPath}/img/img_default_garden.png" /></li>
            <li><p>我的花园</p></li>
            <li><p>3台设备</p></li>
    			</ul>
        </a>
		  </li>
		  <li class="list-group-item">
        <a href="manager.jsp">
    			<ul class="list-inline">
            <li><img src="${pageContext.request.contextPath}/img/img_grandfather_garden.png" /></li>
            <li><p>爷爷的养殖场</p></li>
            <li><p>0台设备</p></li>
    			</ul>
        </a>
		  </li>
    </ul>


  	<div class="container">
      <a href="farm-create.jsp">
        <img  src="${pageContext.request.contextPath}/img/img_btn_add.png" />
      </a>
  	</div>

    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
  </body>
</html>