<%@ page import="com.mx.domain.Garden" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.mx.domain.Devices" %>
<%@ page import="com.mx.util.ImageIds" %>
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
      .luck-ping-div2 {
        width:200px;
        padding-left: 10px;
    
      }                    
    </style>     
  </head>
  <body class="luck-ping-body">
  <script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>

  <%
    String appId  = (String) request.getAttribute("appid");
    long timestamp  = (long) request.getAttribute("timestamp");
    String nonceStr  = (String) request.getAttribute("nonceStr");
    String signature  = (String) request.getAttribute("signature");
  %>

  <script>
  wx.config({
              debug: true,
              appId: '<%=appId %>',
              timestamp:  <%=timestamp %>,
              nonceStr: '<%=nonceStr %>',
              signature: '<%=signature %>',
              jsApiList: [
              'checkJsApi',
              'onMenuShareTimeline',
              'onMenuShareAppMessage',
              'onMenuShareQQ',
              'onMenuShareWeibo',
              'onMenuShareQZone',
              'hideMenuItems',
              'showMenuItems',
              'hideAllNonBaseMenuItem',
              'showAllNonBaseMenuItem',
              'translateVoice',
              'startRecord',
              'stopRecord',
              'onVoiceRecordEnd',
              'playVoice',
              'onVoicePlayEnd',
              'pauseVoice',
              'stopVoice',
              'uploadVoice',
              'downloadVoice',
              'chooseImage',
              'previewImage',
              'uploadImage',
              'downloadImage',
              'getNetworkType',
              'openLocation',
              'getLocation',
              'hideOptionMenu',
              'showOptionMenu',
              'closeWindow',
              'scanQRCode',
              'chooseWXPay',
              'openProductSpecificView',
              'addCard',
              'chooseCard',
              'openCard'
  ]
  });
  </script>

  <%
    Garden garden = (Garden)request.getAttribute("garden");
  %>   
    <div class="container">
      <div class="btn-group btn-group-justified" role="group" aria-label="...">
        <div class="btn-group" role="group">
            <li>
                <input type="image" id="scanQRCode1" src="${pageContext.request.contextPath}/views/img/img_btn_add_small.png" />
            </li>
        </div>
        <div class="btn-group" role="group">
          <a href="farm-avatar.html">
            <img src=<%= garden.getAvatarUrl() %> class="img-circle"/>
          </a>
        </div>
        <div class="btn-group" role="group">
          <img src="${pageContext.request.contextPath}/views/img/img_control_switcher.png" id="myButton" class="img-responsive center-block" />
        </div>
      </div>
    </div>

    <ul class="list-group">
      <%
        ArrayList list = (ArrayList)request.getAttribute("devicesList");
        ArrayList<ImageIds> image_ids = new ArrayList<ImageIds>();
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
            <li><p><%= dev.getName() %></p></li>
            <li style="float:right;margin-top:30px;"><img src="${pageContext.request.contextPath}/views/img/img_indicator_green.png"  /></li>
          </ul>
        </div>

        <% 
          if((dev.getPropertyCombine()&0x01)==0x01) {
            ImageIds item = new ImageIds();
            item.idName = "image_"+dev.getId()+"_01";
            item.deviceId = dev.getId();
            item.typeId = 0x01;
            image_ids.add(item);
            prog_ids[position] = "prog_"+dev.getId()+"_01";
        %>
        <!-- FOR 演示
        <li class="list-group-item" style="background-color: #eee;">
          <ul class="list-inline">
            <li><img id="<%= item.idName %>" src="${pageContext.request.contextPath}/views/img/img_indicator_happy.png" /></li>
            <li><p>温度</p></li>
            <li>
              <input type="button" id="b01" type="button" class="btn btn-default" onclick='start("<%= prog_ids[position] %>", "<%= dev.getId() %>", 1);' value="通风降温" style="width:80px; font-size:13px;" />
            </li>
            <li>
              <progress id="<%= prog_ids[position] %>" value="0" max="100" style="width:100px;height:18px;">
            </li>
            <li>
              <input type="button" class="btn btn-default" onclick='start("<%= prog_ids[position] %>", "<%= dev.getId() %>", 2);' value="加湿升温" style="width:80px; font-size:13px;"/>
            </li>
          </ul>
        </li>
        -->
        <%
        }
        %>

        <% 
          if((dev.getPropertyCombine()&0x02)==0x02) {
            position += 1;
            ImageIds item = new ImageIds();
            item.idName = "image_"+dev.getId()+"_02";
            item.deviceId = dev.getId();
            item.typeId = 0x02;
            image_ids.add(item);
            prog_ids[position] = "prog_"+dev.getId()+"_02";
        %>        
        <li class="list-group-item" style="background-color: #eee;">
          <ul class="list-inline">
            <li><img id="<%= item.idName %>" src="${pageContext.request.contextPath}/views/img/img_indicator_happy.png" /></li>
            <li><p>湿度</p></li>
             <li>
              <input type="button" class="btn btn-default col-md-1" onclick='start("<%= prog_ids[position] %>", "<%= dev.getId() %>", 3);' value="浇水" style="width:80px;font-size:13px;" />
            </li>
            <li>
              <progress id="<%= prog_ids[position] %>" value="0" max="100" style="width:100px;height:18px;">
            </li>
          </ul>
        </li>
        <%
        }
        %>
        
        <% 
          if((dev.getPropertyCombine()&0x04)==0x04) {
            position += 1;
            ImageIds item = new ImageIds();
            item.idName = "image_"+dev.getId()+"_04";
            item.deviceId = dev.getId();
            item.typeId = 0x04;
            image_ids.add(item);
            prog_ids[position] = "prog_"+dev.getId()+"_03";
        %>          
        <li class="list-group-item" style="background-color: #eee;">
          <ul class="list-inline">
            <li><img id="<%= item.idName %>" src="${pageContext.request.contextPath}/views/img/img_indicator_happy.png" /></li>
            <li><p>光照</p></li>
            <li>
              <input  type="button" class="btn btn-default" onclick='start("<%= prog_ids[position] %>", "<%= dev.getId() %>", 4);' value="增加光照" style="width:80px;font-size:13px;" />
            </li>
            <li>
              <progress id="<%= prog_ids[position] %>" value="0" max="100" style="width:100px;height:18px;">
            </li>
          </ul>
        </li>
        <%
        }
        %>
        
        <% 
          if((dev.getPropertyCombine()&0x08)==0x08) {
            position += 1;
            ImageIds item = new ImageIds();
            item.idName = "image_"+dev.getId()+"_08";
            item.deviceId = dev.getId();
            item.typeId = 0x08;
            image_ids.add(item);
            prog_ids[position] = "prog_"+dev.getId()+"_04";
        %>
        <!-- FOR 演示
        <li class="list-group-item" style="background-color: #eee;">
          <ul class="list-inline">
            <li><img id="<%= item.idName %>" src="${pageContext.request.contextPath}/views/img/img_indicator_sad.png" /></li>
            <li><p>水位</p></li>
            <li><p>请您亲自浇水</p></li>
          </ul>
        </li>
        -->
        <%
        }
        %>                                  
      </ul>
      <%
        }
      %>
    </ul>

    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="${pageContext.request.contextPath}/views/js/jquery-3.1.1.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="${pageContext.request.contextPath}/views/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/views/js/jquery.mobile-1.4.5.js"></script>
    <script type="text/javascript">
    var testBool = 0;
    function getIndicatorImg(id , deviceId , typeId ){
      //alert("work on me");

      setTimeout(function() {
        getIndicatorImg(id , deviceId , typeId )
      }, 1000);

      $.ajax({
          url: '${pageContext.request.contextPath}/weiXinDevice/deviceStatusReq',
          type: 'get',
          dataType: 'json',
          data: {"deviceId":deviceId , "Type":typeId},
          cache: false,
          timeout: 5000,
          success: function(data){
              if(data.happy == true) {
                document.getElementById(id).src="${pageContext.request.contextPath}/views/img/img_indicator_happy.png";
                //testBool = 1;
              } else {
                document.getElementById(id).src="${pageContext.request.contextPath}/views/img/img_indicator_sad.png";
                //testBool = 0;
              }
              
          },
          error: function(jqXHR, textStatus, errorThrown){
              //alert('error ' + textStatus + " " + errorThrown);
          }
      });

    }

		var c=0;
		var t;
    function timedCount(id) {

      document.getElementById(id).value = c;
      c = c+1;
      t = setTimeout(function() {
        timedCount(id);
      }, 1000);
      if(c==100) {
        clearTimeout(t);
      }
    }
    function start(id, deviceId, controlType) {
      clearTimeout(t);
      c=0;
      $.ajax({
          url: '${pageContext.request.contextPath}/weiXinDevice/deviceCommand',
          type: 'get',
          data: {"deviceId":deviceId , "controlType":controlType},
          dataType: 'json',
          cache: false,
          timeout: 5000,
          success: function(data){
              
          },
          error: function(jqXHR, textStatus, errorThrown){
              //alert('error ' + textStatus + " " + errorThrown);
          }
      }); 

      timedCount(id);
    }

    var control_mode = <%= garden.getRunMode()%>
    $("#myButton").on('click', function() {
      var btn = document.getElementById("myButton")

        if( control_mode  == 0 )
        {
            control_mode = 1;
        }
        else
        {
            control_mode = 0;
        }
      $.ajax({
          url: '${pageContext.request.contextPath}/weiXinDevice/mode',
          type: 'get',
          data: {"mode":control_mode , "gardenId":<%=garden.getId()%>},
          dataType: 'json',
          cache: false,
          timeout: 5000,
          success: function(data){
            if( control_mode == 0) {
				document.getElementById("myButton").src="${pageContext.request.contextPath}/views/img/img_control_switcher_manual.png"
			} else {
			    document.getElementById("myButton").src="${pageContext.request.contextPath}/views/img/img_control_switcher.png"
			   }
          },
          error: function(jqXHR, textStatus, errorThrown){
              //alert('error ' + textStatus + " " + errorThrown);
          }
      }); 

    })

	window.onload = function() {

	  if( 0 == control_mode) {
        document.getElementById("myButton").src="${pageContext.request.contextPath}/views/img/img_control_switcher_manual.png"
      }
      else
      {
        document.getElementById("myButton").src="${pageContext.request.contextPath}/views/img/img_control_switcher.png"
      }

             var allIdNames = new Array();
             var allDeviceId = new Array();
             var allTypeId = new Array();
             <%
               for (int i=0; i<image_ids.size(); i++)
               {
             %>
        allIdNames[<%=i%>] = '<%=image_ids.get(i).idName%>';
        allDeviceId[<%=i%>] = '<%=image_ids.get(i).deviceId%>';
        allTypeId[<%=i%>] = '<%=image_ids.get(i).typeId%>';
      <%
        }
      %>
      for(var i=0;i<allIdNames.length;i++) {
        getIndicatorImg(allIdNames[i],allDeviceId[i],allTypeId[i] )
      }
    }

    // 9.1.2 扫描二维码并返回结果
    $("#scanQRCode1").on('click', function(){

      wx.scanQRCode({
        needResult: 1,
        desc: 'scanQRCode desc',
        success: function (res) {
            alert(JSON.stringify(res));
            $.ajax({
                url: '${pageContext.request.contextPath}/weiXinDevice/addDevice',
                type: 'get',
                dataType: 'json',
                data:{"gardenId":<%=garden.getId()%> , "scanQRCodeResult":JSON.stringify(res)},
                cache: false,
                timeout: 5000,
                success: function(data){
                   console.log( "scanQRCode result", JSON.stringify(res) );

                },
                error: function(jqXHR, textStatus, errorThrown){
                    //alert('error ' + textStatus + " " + errorThrown);
                }
            });
        }
      });
    });
    </script>


  </body>
</html>