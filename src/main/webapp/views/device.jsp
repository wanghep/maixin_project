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
  </head>
  <body>
  <%
    Device dev = (Device)request.getAttribute("device");
    ArrayList tempture = (ArrayList)request.getAttribute("messageTemperaterList");
    ArrayList humidit = (ArrayList)request.getAttribute("MessageHumiditList");
    ArrayList illumination = (ArrayList)request.getAttribute("messageIlluminationList");
    ArrayList water = (ArrayList)request.getAttribute("messageWaterLevelList");

    String temptureX[] = new String[tempture.size()];
    int temptureY[] = new int[tempture.size()];
    for(int i=0;i<tempture.size();i++) {
      Message item = (Message)tempture.get(i);
      temptureX[i] = item.getTime();
      int temp = 0;
      try {
        temp = Integer.parseInt(item.getContext());
      } catch(NumberFormatException e) {}
      temptureY[i] = temp;

    }

    String humiditX[] = new String[humidit.size()];
    int humiditY[] = new int[humidit.size()];
    for(int i=0;i<humidit.size();i++) {
      Message item = (Message)humidit.get(i);
      humiditX[i] = item.getTime();
      int temp = 0;
      try {
        temp = Integer.parseInt(item.getContext());
      } catch(NumberFormatException e) {}
      humiditY[i] = temp;

    }

    String illuminationX[] = new String[illumination.size()];
    int illuminationY[] = new int[illumination.size()];
    for(int i=0;i<illumination.size();i++) {
      Message item = (Message)illumination.get(i);
      illuminationX[i] = item.getTime();
      int temp = 0;
      try {
        temp = Integer.parseInt(item.getContext());
      } catch(NumberFormatException e) {}
      illuminationY[i] = temp;

    }

    String waterX[] = new String[water.size()];
    int waterY[] = new int[water.size()];
    for(int i=0;i<water.size();i++) {
      Message item = (Message)water.get(i);
      waterX[i] = item.getTime();
      int temp = 0;
      try {
        temp = Integer.parseInt(item.getContext());
      } catch(NumberFormatException e) {}
      waterY[i] = temp;

    }            
  %>
    <ul class="list-group">
      <li class="list-group-item">
        <ul class="list-inline">
          <li><img src="${pageContext.request.contextPath}/views/img/img_device_default.png" /></li>
          <li><p><%= dev.getName() %></p></li>
          <li><p>设备<%= dev.getId() %></p></li>
        </ul>
      </li>
    </ul>

    <div id="tempture" style="width: 400px;height:300px;"></div>

    <ul class="list-group">
      <li class="list-group-item">
        <ul class="list-inline">
          <li><p>自动模式上限</p></li>
          <li><input type="text"></input></li>
          <li><p>°C</p></li>
        </ul>
      </li>
      <li class="list-group-item">
        <ul class="list-inline">
          <li><p>自动模式下限</p></li>
          <li><input type="text"></input></li>
          <li><p>°C</p></li>
        </ul>
      </li>
      <li class="list-group-item">
        <ul class="list-inline">
          <li><p>手动模式通风时间</p></li>
          <li><input type="text"></input></li>
          <li><p>分钟</p></li>
        </ul>
      </li>
      <li class="list-group-item">
        <ul class="list-inline">
          <li><p>手动模式加热</p></li>
          <li><input type="text"></input></li>
          <li><p>分钟</p></li>
        </ul>
      </li>                           
    </ul>

    <div id="moisture" style="width: 400px;height:300px;"></div>

    <ul class="list-group">
      <li class="list-group-item">
        <ul class="list-inline">
          <li><p>自动模式上限</p></li>
          <li><input type="text"></input></li>
          <li><p>%RH</p></li>
        </ul>
      </li>
      <li class="list-group-item">
        <ul class="list-inline">
          <li><p>自动模式下限</p></li>
          <li><input type="text"></input></li>
          <li><p>%RH</p></li>
        </ul>
      </li>
      <li class="list-group-item">
        <ul class="list-inline">
          <li><p>手动模式浇水时间</p></li>
          <li><input type="text"></input></li>
          <li><p>分钟</p></li>
        </ul>
      </li>                        
    </ul>

    <div id="light" style="width: 400px;height:300px;"></div>

    <ul class="list-group">
      <li class="list-group-item">
        <ul class="list-inline">
          <li><p>自动模式上限</p></li>
          <li><input type="text"></input></li>
          <li><p>μmol</p></li>
        </ul>
      </li>
      <li class="list-group-item">
        <ul class="list-inline">
          <li><p>自动模式下限</p></li>
          <li><input type="text"></input></li>
          <li><p>μmol</p></li>
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
            data: <%= temptureX %>
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
            data: <%= temptureY %>
          }]
        };

        var option2 = {
          title: {
            text: '湿度历史数据表 单位%RH'
          },
          tooltip: {},
          legend: {
            data:['湿度']
          },
          xAxis: {
            data: <%= humiditX %>
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
            data: <%= humiditY %>
          }]
        };

        var option3 = {
          title: {
            text: '光照历史数据表 单位μmol'
          },
          tooltip: {},
          legend: {
            data:['光照']
          },
          xAxis: {
            data: <%= illuminationY %>
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
            data: <%= illuminationX %>
          }]
        };                

        // 使用刚指定的配置项和数据显示图表。
        myChart1.setOption(option1);
        myChart2.setOption(option2);
        myChart3.setOption(option3);
    </script>
  </body>
</html>