<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <title>花园头像</title>

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
    Garden garden = (Device)request.getAttribute("Garden");
  %>
    <ul class="list-group">
		  <li class="list-group-item">
  			<ul class="list-inline">
          <li>
            <h2 style="color:blue;"><%= garden.getName() %></h2>
          </li>
          <li><p>长按修改花园名称</p></li>
  			</ul>
		  </li>
    </ul>

    <div class="container" align="center">
      <img src=<%= garden.getAvatarUrl() %> />
    </div>

    <form id="form" enctype="multipart/form-data" method="post">
      <div class="container" align="center">
        <input id="profile-image-upload" class="hidden" type="file">
        <div id="profile-image"><image src=<%= garden.getAvatarUrl() %> /></div>
        <h4>点击上传花园头像</h4>
      </div>
    </form>
    
  	<div class="container" align="center" style="margin-top:30px;">
      <button type="button" class="btn btn-primary" data-toggle="button" aria-pressed="false" autocomplete="off" style="color:red;">
      删除此花园
      </button>
  	</div>

    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="${pageContext.request.contextPath}/views/js/bootstrap.min.js"></script>
    <script type="text/javascript">
      $('#profile-image').on('click', function() {
        $('#profile-image-upload').click();
      });
    </script>       
  </body>
</html>