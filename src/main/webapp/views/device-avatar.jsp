<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <title>设备头像</title>

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

    <div class="container" align="center">
      <h4>原头像</h4>
      <img src="${pageContext.request.contextPath}/views/img/img_device_default.png" />
    </div>

    <form id="form" enctype="multipart/form-data" method="post">

      <div class="container" align="center">
        <h4>新头像</h4>
        <input id="profile-image-upload" class="hidden" type="file">
        <div id="profile-image"><image src="${pageContext.request.contextPath}/views/img/img_device_new.png" /></div>        
      </div> 

      <div class="container" align="center" style="margin-top:30px;">
        <button type="button" class="btn btn-primary" data-toggle="button" aria-pressed="false" autocomplete="off" style="background-color:white;">
        保存
        </button>
      </div>
    </form>

    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="${pageContext.request.contextPath}/views/js/jquery-3.1.1.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="${pageContext.request.contextPath}/views/js/bootstrap.min.js"></script>
    <script type="text/javascript">
      /*
      $('#profile-image').on('click', function() {
        $('#profile-image-upload').click();
      });
      */
    </script>       
  </body>
</html>