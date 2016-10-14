<%@ page import="java.util.ArrayList" %>
<%@ page import="com.mx.domain.Garden" %>
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

    for  (int i=0;i<list.size();i++) {
  %>
  <li class="list-group-item">
    <a href="${pageContext.request.contextPath}/views/manager.jsp?gardenID=<%=((Garden)list.get(i)).getGardenId() %>">
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


<div class="container">
  <a href="farm-create.jsp">
    <img  src="${pageContext.request.contextPath}/views/img/img_btn_add.png" />
  </a>
</div>

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="${pageContext.request.contextPath}/views/js/bootstrap.min.js"></script>
</body>
</html>