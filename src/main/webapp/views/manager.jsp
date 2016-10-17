<%@ page import="com.mx.domain.Garden" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.mx.domain.Devices" %>
<!DOCTYPE html>
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
    Garden garden = (Garden)request.getAttribute("garden");
  %>   
    <div class="container">
      <div class="btn-group btn-group-justified" role="group" aria-label="...">
        <div class="btn-group" role="group">
          <a href="qr-code.jsp">
            <img src="${pageContext.request.contextPath}/views/img/img_btn_add_small.png" />
          </a>
        </div>
        <div class="btn-group" role="group">
          <a href="farm-avatar.html">
            <img src=<%= garden.getAvatarUrl() %> />
          </a>
        </div>
        <div class="btn-group" role="group">
          <button type="button" id="myButton" class="btn btn-primary" autocomplete="off">
            手动
          </button>
        </div>
      </div>
    </div>

    <ul class="list-group">
      <%
        ArrayList list = (ArrayList)request.getAttribute("devicesList");
        String image_ids[] = new String[list.size()*4];
        String prog_ids[] = new String[list.size()*4];
        int position = 0;

        for (int i=0;i<list.size();i++) {
          Devices dev = (Devices)list.get(i);
      %>

      <li class="list-group-item">
        <div class="container">
          <ul class="list-inline">
            <li>
              <a href="${pageContext.request.contextPath}/weiXinDevice/deviceChart?deviceId=<%=dev.getId() %>">
                <img src=<%= ((Devices)list.get(i)).getAvatarUrl() %> />
              </a>
            </li> 
            <li><p>设备<%= i %></p></li>
            <li><img src="${pageContext.request.contextPath}/views/img/img_indicator_green.png" /></li>
          </ul>
        </div>

        <% 
          if((dev.getPropertyCombine()&0x01)==1) {
            image_ids[position] = "image_"+dev.getId()+"_01";
            prog_ids[position] = "prog_"+dev.getId()+"_01";
        %>
        <li class="list-group-item">
          <ul class="list-inline">
            <li><img id=<%= image_ids[position] %> src="img/img_indicator_happy.png" /></li>
            <li><p onload="getIndicatorImg(<%= image_ids[position] %>)">温度</p></li>
            <li>
              <input type="button" id="b01" type="button" class="btn btn-default" onclick='start(<%= image_ids[position] %>);' value="通风降温" style="width:80px; font-size:13px;" />
            </li>
            <li>
              <progress id=<%= prog_ids[position] %> value="0" max="100" style="width:100px;height:18px;">
            </li>
            <li>
              <input type="button" class="btn btn-default" onclick='start(<%= image_ids[position] %>);' value="加温升温" style="width:80px; font-size:13px;"/>
            </li>
          </ul>
        </li>
        <%
        }
        %>

        <% 
          if((dev.getPropertyCombine()&0x02)==1) {
            position += 1;
            image_ids[position] = "image_"+dev.getId()+"_02";
            prog_ids[position] = "prog_"+dev.getId()+"_02";
        %>        
        <li class="list-group-item">
          <ul class="list-inline">
            <li><img id=<%= image_ids[position] %> src="img/img_indicator_happy.png" /></li>
            <li><p onload="getIndicatorImg(<%= image_ids[position] %>)">湿度</p></li>
             <li>
              <input type="button" class="btn btn-default col-md-1" onclick='start(<%= image_ids[position] %>);' value="浇水" style="width:80px;font-size:13px;" />
            </li>
            <li>
              <progress id=<%= prog_ids[position] %> value="22" max="100" style="width:100px;height:18px;">
            </li>
          </ul>
        </li>
        <%
        }
        %>
        
        <% 
          if((dev.getPropertyCombine()&0x04)==1) {
            position += 1;
            image_ids[position] = "image_"+dev.getId()+"_03";
            prog_ids[position] = "prog_"+dev.getId()+"_03";
        %>          
        <li class="list-group-item">
          <ul class="list-inline">
            <li><img id=<%= image_ids[position] %> src="img/img_indicator_happy.png" /></li>
            <li><p onload="getIndicatorImg(<%= image_ids[position] %>)">光照</p></li>
            <li>
              <input  type="button" class="btn btn-default" onclick='start(<%= image_ids[position] %>);' value="增加光照" style="width:80px;font-size:13px;" />
            </li>
            <li>
              <progress id=<%= prog_ids[position] %> value="22" max="100" style="width:100px;height:18px;">
            </li>
          </ul>
        </li>
        <%
        }
        %>
        
        <% 
          if((dev.getPropertyCombine()&0x08)==1) {
            position += 1;
            image_ids[position] = "image_"+dev.getId()+"_04";
            prog_ids[position] = "prog_"+dev.getId()+"_04";
        %>          
        <li class="list-group-item">
          <ul class="list-inline">
            <li><img id=<%= image_ids[position] %> src="img/img_indicator_sad.png" /></li>
            <li><p onload="getIndicatorImg(<%= image_ids[position] %>)">水位</p></li>
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
    var testBool = 0;
    function getIndicatorImg(id){
      //alert("work on me");
      $.ajax({
          url: 'http://localhost:8888',
          type: 'get',
          dataType: 'json',
          cache: false,
          timeout: 5000,
          success: function(data){
              //alert(data);
              /*
              * iterator on data to get tempture,humidity,.. of device#
              * and update the image by get id
              */
              if(testBool == 0) {
                document.getElementById(id).src="img/img_indicator_happy.png";
                testBool = 1;
              } else {
                document.getElementById(id).src="img/img_indicator_sad.png";
                testBool = 0;
              }
              
          },
          error: function(jqXHR, textStatus, errorThrown){
              alert('error ' + textStatus + " " + errorThrown);  
          }
      });

      setTimeout(function() {
        getIndicatorImg(id)
      }, 1000);
    }

		var c=0;
		var t;
    function timedCount(id) {
      //alert(id);
      document.getElementById(id).value = c;
      c = c+1;
      t = setTimeout(function() {
        timedCount(id);
      }, 1000);
      if(c==100) {
        clearTimeout(t);
      }
    }
    function start(id) {
      clearTimeout(t);
      c=0;
      timedCount(id);
    }

    window.onload = function() {
      document.getElementById("myButton").innerHTML="手动"
    }

    </script>
  </body>
</html>