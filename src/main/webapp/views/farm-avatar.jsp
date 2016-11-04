<%@ page import="com.mx.domain.Garden" %>
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
  <%
    Garden garden = (Garden)request.getAttribute("garden");
  %>
    <div class="luck-ping-div">
      <ul class="list-group">
        <li class="list-group-item">
          <ul class="list-inline">
            <li>
              <div class="container">
                <input id="name-farm" type="text" class="luck-ping-p1" value="<%=garden.getName()%>"></input>
              </div>
            </li>
            <li><p id="name-write" class="luck-ping-p2">点击修改花园名称</p></li>
          </ul>
        </li>
      </ul>
    </div>


    <form id="form" enctype="multipart/form-data" method="post">
      <div class="container" align="center">
        <input id="profile-image-upload" class="hidden" type="file">
        <div id="profile-image"><image src=<%= garden.getAvatarUrl() %> /></div>
        <h4>点击上传花园头像</h4>
      </div>
    </form>

  	<div class="container" align="center" style="margin-top:30px;">
        <a type="button" class="btn btn-primary btn-lg btn-block" href="${pageContext.request.contextPath}/weiXinDevice/deleteGarden?gardenId=<%=garden.getId() %>">
            删除此花园
        </a>
        <a type="button" class="btn btn-primary btn-lg btn-block" onclick="quit()" >
            退 出
        </a>
  	</div>

    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="${pageContext.request.contextPath}/views/js/jquery-3.1.1.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="${pageContext.request.contextPath}/views/js/bootstrap.min.js"></script>
    <script type="text/javascript">
      function deleteFarm() {
        var gardenId;
        gardenId = <%=garden.getId()%>;
        $.ajax({
            url: '${pageContext.request.contextPath}/weiXinDevice/deleteGarden',
            type: 'POST',
            data: {value:gardenId},
            dataType: 'json',
            cache: false,
            timeout: 5000,
            success: function(data){
              //TODO

            },
            error: function(jqXHR, textStatus, errorThrown){
                alert('error ' + textStatus + " " + errorThrown);  
            }
        });        
      }
      /*
      $('#profile-image').on('click', function() {
        $('#profile-image-upload').click();
      });
      */

      function quit() {
          var farm = document.getElementById("name-farm");

          var url = '${pageContext.request.contextPath}/weiXinDevice/gardenDetailQuit?newName='+farm.value+'&gardenId='+<%=garden.getId() %>;
          url = encodeURI(url);
          //open(url);
          window.location.href=url;
      }


    </script>       
  </body>
</html>