<%@ page import="com.mx.domain.Devices" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.mx.domain.Message" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <title>农场监测控制</title>

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
        background-color: #E3E3E3;
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
        width:128px;
        padding-left: 10px;
      }                    
    </style>       
  </head>
  <body class="luck-ping-body">
  <%
    Devices dev = (Devices)request.getAttribute("device");
    ArrayList tempture = (ArrayList)request.getAttribute("messageTemperaterList");
    ArrayList humidit = (ArrayList)request.getAttribute("MessageHumiditList");
    ArrayList illumination = (ArrayList)request.getAttribute("messageIlluminationList");
    ArrayList water = (ArrayList)request.getAttribute("messageWaterLevelList");

    int temptureSize = ((tempture==null)?0:tempture.size());
    String temptureX[] = new String[temptureSize];
    int temptureY[] = new int[temptureSize];
    for(int i=0;i<temptureSize;i++) {
      Message item = (Message)tempture.get(i);
      temptureX[i] = item.getTime().toString();
      int temp = 0;
      try {
        temp = Integer.parseInt(item.getContext());
      } catch(NumberFormatException e) {}
      temptureY[i] = temp;

    }

    int humiditSize = ((humidit==null)?0:humidit.size());
    String humiditX[] = new String[humiditSize];
    int humiditY[] = new int[humiditSize];
    for(int i=0;i<humiditSize;i++) {
      Message item = (Message)humidit.get(i);
      humiditX[i] = item.getTime().toString();
      int temp = 0;
      try {
        temp = Integer.parseInt(item.getContext());
      } catch(NumberFormatException e) {}
      humiditY[i] = temp;

    }

    int illuminationSize = ((illumination==null)?0:illumination.size());
    String illuminationX[] = new String[illuminationSize];
    int illuminationY[] = new int[illuminationSize];
    for(int i=0;i< illuminationSize;i++) {
      Message item = (Message)illumination.get(i);
      illuminationX[i] = item.getTime().toString();
      int temp = 0;
      try {
        temp = Integer.parseInt(item.getContext());
      } catch(NumberFormatException e) {}
      illuminationY[i] = temp;

    }

    int waterSize = ((water==null)?0:water.size());
    String waterX[] = new String[waterSize];
    int waterY[] = new int[waterSize];
    for(int i=0;i<waterSize;i++) {
      Message item = (Message)water.get(i);
      waterX[i] = item.getTime().toString();
      int temp = 0;
      try {
        temp = Integer.parseInt(item.getContext());
      } catch(NumberFormatException e) {}
      waterY[i] = temp;

    }            
  %>
    <div class="luck-ping-div">
      <ul class="list-group">
        <li class="list-group-item">
          <ul class="list-inline">
            <li><img src="${pageContext.request.contextPath}/views/img/img_device_default.png" /></li>
            <li><p><%= dev.getName() %></p></li>
            <li><p>设备<%= dev.getId() %></p></li>
          </ul>
        </li>
      </ul>
    </div>

    <div id="tempture" style="width: 400px;height:300px;"></div>

    <ul class="list-group">
      <li class="list-group-item">
        <ul class="list-inline">
          <li>
            <div class="luck-ping-div2">
              <p class="text-right" style="color:Aqua;">自动模式上限</p>
            </div>
          </li>
          <li>
            <div style="width:46px">
              <input type="text" id="0101" style="width:46px;" onfocusout='setParameter("0101", <%= dev.getId() %>, 1)'></input>
            </div>
          </li>
          <li><p style="color:Aqua;">°C</p></li>
        </ul>
      </li>
      <li class="list-group-item">
        <ul class="list-inline">
          <li>
            <div class="luck-ping-div2">
              <p class="text-right" style="color:Aqua;">自动模式下限</p>
            </div>
          </li>
          <li>
            <div style="width:46px">
              <input type="text" id="0102" style="width:46px;" onfocusout='setParameter("0102",<%= dev.getId() %>, 2)'></input>
            </div>
          </li>
          <li><p style="color:Aqua;">°C</p></li>
        </ul>
      </li>
      <li class="list-group-item">
        <ul class="list-inline">
          <li>
            <div class="luck-ping-div2">
              <p class="text-right" style="color:Aqua;">手动模式通风时间</p>
            </div>
          </li>
          <li>
            <div style="width:46px">
              <input type="text" id="0103" style="width:46px;" onfocusout='setParameter("0103",<%= dev.getId() %>, 3)'></input>
            </div>
          </li>
          <li><p style="color:Aqua;">分钟</p></li>
        </ul>
      </li>
      <li class="list-group-item">
        <ul class="list-inline">
          <li>
            <div class="luck-ping-div2">
              <p class="text-right" style="color:Aqua;">手动模式加热时间</p>
            </div>
          </li>
          <li>
            <div style="width:46px">
              <input type="text" id="0104" style="width:46px;" onfocusout='setParameter("0104",<%= dev.getId() %>, 4)'></input>
            </div>
          </li>
          <li><p style="color:Aqua;">分钟</p></li>
        </ul>
      </li>                           
    </ul>

    <div id="moisture" style="width: 400px;height:300px;"></div>

    <ul class="list-group">
      <li class="list-group-item">
        <ul class="list-inline">
          <li>
            <div class="luck-ping-div2">
              <p class="text-right" style="color:Aqua;">自动模式上限</p>
            </div>
          </li>
          <li>
            <div style="width:46px">
              <input type="text" id="0201" style="width:46px;" onfocusout='setParameter("0201",<%= dev.getId() %>, 5)'></input>
            </div>
          </li>
          <li><p style="color:Aqua;">%RH</p></li>
        </ul>
      </li>
      <li class="list-group-item">
        <ul class="list-inline">
          <li>
            <div class="luck-ping-div2">
              <p class="text-right" style="color:Aqua;">自动模式下限</p>
            </div>
          </li>
          <li>
            <div style="width:46px">
              <input type="text" id="0202" style="width:46px;" onfocusout='setParameter("0202",<%= dev.getId() %>, 6)'></input>
            </div>
          </li>
          <li><p style="color:Aqua;">%RH</p></li>
        </ul>
      </li>
      <li class="list-group-item">
        <ul class="list-inline">
          <li>
            <div class="luck-ping-div2">
              <p class="text-right" style="color:Aqua;">手动模式浇水时间</p>
            </div>
          </li>
          <li>
            <div style="width:46px">
              <input type="text" id="0203" style="width:46px;" onfocusout='setParameter("0203",<%= dev.getId() %>, 7)'></input>
            </div>
          </li>
          <li><p style="color:Aqua;">分钟</p></li>
        </ul>
      </li>                        
    </ul>

    <div id="light" style="width: 400px;height:300px;"></div>

    <ul class="list-group">
      <li class="list-group-item">
        <ul class="list-inline">
          <li>
            <div class="luck-ping-div2">
              <p class="text-right" style="color:Aqua;">自动模式上限</p>
            </div>
          </li>
          <li>
            <div style="width:46px">
              <input type="text" id="0301" style="width:46px;" onfocusout='setParameter("0301",<%= dev.getId() %>, 8)'></input>
            </div>
          </li>
          <li><p style="color:Aqua;">μmol</p></li>
        </ul>
      </li>
      <li class="list-group-item">
        <ul class="list-inline">
          <li>
            <div class="luck-ping-div2">
              <p class="text-right" style="color:Aqua;">自动模式下限</p>
            </div>
          </li>
          <li>
            <div style="width:46px">
              <input type="text" id="0302" style="width:46px;" onfocusout='setParameter("0302",<%= dev.getId() %>, 9)'></input>
            </div>
          </li>
          <li><p style="color:Aqua;">μmol</p></li>
        </ul>
      </li>                         
    </ul>    

    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="${pageContext.request.contextPath}/views/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/views/js/echarts.js"></script>
    <script>
        // 基于准备好的dom，初始化echarts实例
        var myChart1 = echarts.init(document.getElementById('tempture'));
        var myChart2 = echarts.init(document.getElementById('moisture'));
        var myChart3 = echarts.init(document.getElementById('light'));

        var tempX = new Array();
        <%
          for (int i=0; i<temptureX.length; i++)
          {
        %>
        tempX[<%=i%>] = '<%=temptureX[i]%>';
        <%
          }
        %>

        var tempY = new Array();
        <%
          for (int i=0; i<temptureY.length; i++)
          {
        %>
        tempY[<%=i%>] = '<%=temptureY[i]%>';
        <%
          }
        %>
        // 指定图表的配置项和数据
        var option1 = {
          title: {
            text: '温度历史数据表 单位°C'
          },
          tooltip: {},
          legend: {
            data:['温度']
          },
          xAxis: {
            data: tempX
          },
          yAxis: {
            type: 'value',
            axisLabel: {
              formatter: '{value} °C'
            }
          },
          series: [{
            name: '温度',
            type: 'line',
            data: tempY
          }]
        };

        var humiX = new Array();
        <%
          for (int i=0; i<humiditX.length; i++)
          {
        %>
        humiX[<%=i%>] = '<%=humiditX[i]%>';
        <%
          }
        %>

        var humiY = new Array();
        <%
          for (int i=0; i<humiditY.length; i++)
          {
        %>
        humiY[<%=i%>] = '<%=humiditY[i]%>';
        <%
          }
        %>
        var option2 = {
          title: {
            text: '湿度历史数据表 单位%RH'
          },
          tooltip: {},
          legend: {
            data:['湿度']
          },
          xAxis: {
            data: humiX
          },
          yAxis: {
            type: 'value',
            axisLabel: {
              formatter: '{value} %RH'
            }
          },
          series: [{
            name: '湿度',
            type: 'line',
            data: humiY
          }]
        };

        var illumX = new Array();
        <%
          for (int i=0; i<illuminationX.length; i++)
          {
        %>
        illumX[<%=i%>] = '<%=illuminationX[i]%>';
        <%
          }
        %>

        var illumY = new Array();
        <%
          for (int i=0; i<illuminationY.length; i++)
          {
        %>
        illumY[<%=i%>] = '<%=illuminationY[i]%>';
        <%
          }
        %>

        var option3 = {
          title: {
            text: '光照历史数据表 单位μmol'
          },
          tooltip: {},
          legend: {
            data:['光照']
          },
          xAxis: {
            data: illumX
          },
          yAxis: {
            type: 'value',
            axisLabel: {
              formatter: '{value} μmol'
            }
          },
          series: [{
            name: '光照',
            type: 'line',
            data: illumY
          }]
        };                

        // 使用刚指定的配置项和数据显示图表。
        myChart1.setOption(option1);
        myChart2.setOption(option2);
        myChart3.setOption(option3);

      function setParameter(id, devid, type) {
        var item = document.getElementById(id);
        //farm.value = farm.value.toUpperCase();
        $.ajax({
            url: '${pageContext.request.contextPath}/weiXinDevice/deviceRuleIndication',
            type: 'GET',
            data: {"value":item.value ,"deviceId":devid, "type" : type},
            dataType: 'json',
            cache: false,
            timeout: 5000,
            success: function(data){
              //TODO

            },
            error: function(jqXHR, textStatus, errorThrown){
                //alert('error ' + textStatus + " " + errorThrown);
            }
        });        

      }        
    </script>
  </body>
</html>