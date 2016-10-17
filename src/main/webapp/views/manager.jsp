﻿<%@ page import="com.mx.domain.Garden" %>
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
        String ids[] = new String[list.size()*4];
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
            ids[position] = dev.getId()+"01";
        %>
        <li class="list-group-item">
          <ul class="list-inline">
            <li><img id="indicator_01" src="img/img_indicator_happy.png" /></li>
            <li><p>温度</p></li>
            <li>
              <input type="button" id="b01" type="button" class="btn btn-default" onclick='start1();' value="通风降温" style="width:80px; font-size:13px;" />
            </li>
            <li>
              <progress id="p01" value="0" max="100" style="width:100px;height:18px;">
            </li>
            <li>
              <input type="button" class="btn btn-default" onclick='start1();' value="加温升温" style="width:80px; font-size:13px;"/>
            </li>
          </ul>
        </li>
        <%
        }
        %>

        <% 
          if((dev.getPropertyCombine()&0x02)==1) {
            position += 1;
            ids[position] = dev.getId()+"02";
        %>        
        <li class="list-group-item">
          <ul class="list-inline">
            <li><img id=<%= ids[position] %> src="img/img_indicator_happy.png" /></li>
            <li><p>湿度</p></li>
             <li>
              <input type="button" class="btn btn-default col-md-1" onclick='start2();' value="浇水" style="width:80px;font-size:13px;" />
            </li>
            <li>
              <progress id="p02" value="22" max="100" style="width:100px;height:18px;">
            </li>
          </ul>
        </li>
        <%
        }
        %>
        
        <% 
          if((dev.getPropertyCombine()&0x04)==1) {
            position += 1;
            ids[position] = dev.getId()+"03";
        %>          
        <li class="list-group-item">
          <ul class="list-inline">
            <li><img id=<%= ids[position] %> src="img/img_indicator_happy.png" /></li>
            <li><p>光照</p></li>
            <li>
              <input  type="button" class="btn btn-default" onclick='start3();' value="增加光照" style="width:80px;font-size:13px;" />
            </li>
            <li>
              <progress id="p03" value="22" max="100" style="width:100px;height:18px;">
            </li>
          </ul>
        </li>
        <%
        }
        %>
        
        <% 
          if((dev.getPropertyCombine()&0x08)==1) {
            position += 1;
            ids[position] = dev.getId()+"04";
        %>          
        <li class="list-group-item">
          <ul class="list-inline">
            <li><img id=<%= ids[position] %> src="img/img_indicator_sad.png" /></li>
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
    var testBool = 0;
    function getIndicatorImg(){
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
                document.getElementById("indicator_01").src="img/img_indicator_happy.png";
                testBool = 1;
              } else {
                document.getElementById("indicator_01").src="img/img_indicator_sad.png";
                testBool = 0;
              }
              
          },
          error: function(jqXHR, textStatus, errorThrown){
              alert('error ' + textStatus + " " + errorThrown);  
          }
      });

      setTimeout("getIndicatorImg()", 1000);
    }

		var c=0;
		var t;
		function timedCount1() {
			document.getElementById("p01").value = c;
			c = c+1;
			t = setTimeout("timedCount1()", 1000);
			if(c==100) {
				clearTimeout(t);
			}
		}
		function start1() {
			clearTimeout(t);
			c=0;
			timedCount1();
		}
		function timedCount2() {
			document.getElementById("p02").value = c;
			c = c+1;
			t = setTimeout("timedCount2()", 1000);
			if(c==100) {
				clearTimeout(t);
			}			
		}
		function start2() {
			clearTimeout(t);
			c=0;
			timedCount2();
		}
		function timedCount3() {
			document.getElementById("p03").value = c;
			c = c+1;
			t = setTimeout("timedCount3()", 1000);
			if(c==100) {
				clearTimeout(t);
			}			
		}	
		function start3() {
			clearTimeout(t);
			c=0;
			timedCount3();
		}	
      $("#myButton").on('click', function() {
        var btn = document.getElementById("myButton")
        if (btn.innerHTML == "手动") {
          btn.innerHTML="自动"
        } else {
          btn.innerHTML="手动"
        }
      })

    window.onload = function() {
      document.getElementById("myButton").innerHTML="手动"
      getIndicatorImg()
    }

    </script>
  </body>
</html>