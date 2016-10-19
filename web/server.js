var http=require("http");
var url = require("url");

var server=http.createServer(function(req,res){

    if(req.url!=="/favicon.ico"){
	    var m = url.parse(req.url, true);
	    console.log(m.query["mode"])
        //res.writeHead(200,{"Content-Type":"text/plain","Access-Control-Allow-Origin":"http://localhost:8080"});
		//res.write("hello,我是从服务器端接收的");
		res.writeHead(200, {"Content-Type":"application/json","Access-Control-Allow-Origin":"http://localhost:8080"});
		if(m.query["mode"]!= null) {
			if(m.query["mode"] == 0) {
				res.write(JSON.stringify({data:1}));
			} else {
				res.write(JSON.stringify({data:0}));
			}
		} else {
			res.write(JSON.stringify({data:"hello,我是从服务器端接收的"}));
		}
		
    }

    res.end();

});

server.listen(8888,"localhost",function(){

    console.log("开始监听...");

});
