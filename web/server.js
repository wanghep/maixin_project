var http=require("http");

var server=http.createServer(function(req,res){
	console.log(req.body);
    if(req.url!=="/favicon.ico"){

        //res.writeHead(200,{"Content-Type":"text/plain","Access-Control-Allow-Origin":"http://localhost:8080"});
		//res.write("hello,我是从服务器端接收的");
		res.writeHead(200, {"Content-Type":"application/json","Access-Control-Allow-Origin":"http://localhost:8080"});
		res.write(JSON.stringify({data:'hello,我是从服务器端接收的'}));
    }

    res.end();

});

server.listen(8888,"localhost",function(){

    console.log("开始监听...");

});
