﻿<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <title>创建花园</title>

    <!-- Bootstrap -->
    <link href="${pageContext.request.contextPath}/views/css/bootstrap.min.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
    <style type="text/css">
      #name-farm::-webkit-input-placeholder {
        color: blue;
        font-size: 18px;
      }
      .luck-ping-body {
          background-color: #eee;
      }
      .luck-ping-div {
          margin-top: 18px;
          margin-bottom: 10px;
          padding: 5px 10px 5px 10px; 
      }
      .luck-ping-p1 {
          margin: 10px;
          padding: 0px;
          font-size: 16px;
          border:none;
      }       
      .luck-ping-p2 {
          margin: 0px;
          padding: 0px;
          font-size: 11px;
      }                       
    </style>    
  </head>
  <body class="luck-ping-body">
    
    <div class="luck-ping-div">
      <ul class="list-group">
        <li class="list-group-item">
          <ul class="list-inline">
            <li>
              <div class="container">
                <input id="name-farm" onfocusout='nameFarm()' type="text" class="luck-ping-p1" placeholder="新添花园名称"></input>
              </div>
            </li>
            <li><p id="name-write" class="luck-ping-p2">长按修改花园名称</p></li>
          </ul>
        </li>
      </ul>
    </div>

    <form id="form" enctype="multipart/form-data" method="post">
      <div class="container" align="center">
        <input id="profile-image-upload" class="hidden" type="file">
        <div id="profile-image"><image src="${pageContext.request.contextPath}/views/img/img_default_garden.png" /></div>
        <h4>点击上传花园头像</h4>
      </div>
    </form>

    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="${pageContext.request.contextPath}/views/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/views/js/jquery.mobile-1.4.5.js"></script>
    <script type="text/javascript">
      function nameFarm() {
        var farm = document.getElementById("name-farm");
        //farm.value = farm.value.toUpperCase();
        $.ajax({
            url: '${pageContext.request.contextPath}/weiXinDevice/addA_GardenName',
            type: 'POST',
            data: {name:farm.value},
            dataType: 'json',
            cache: false,
            timeout: 5000,
            success: function(data){
                if(data == 0) {
                  farm.readOnly = true;
                } 
                
            },
            error: function(jqXHR, textStatus, errorThrown){
                alert('error ' + textStatus + " " + errorThrown);  
            }
        });        

      }

      $('#name-write').on("tap",function(){
        document.getElementById("name-farm").readOnly = false;
      });
      $('#profile-image').on('click', function() {
        $('#profile-image-upload').click();
      });
    </script>       
  </body>
</html>