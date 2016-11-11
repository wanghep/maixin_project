var http = require("http");
var url = require("url");
var express =   require("express");
var multer  =   require('multer');
var app         =   express();
app.use(express.static(__dirname + '/'));

app.get('/',function(req,res){
      res.sendFile(__dirname + "/farm-avatar.html");
});

var storage =   multer.diskStorage({
  destination: function (req, file, callback) {
    callback(null, './uploads');
  },
  filename: function (req, file, callback) {
    callback(null, file.fieldname + '-' + Date.now());
  }
});
var upload = multer({ storage : storage}).single('userPhoto');

app.get('/',function(req,res){
      res.sendFile(__dirname + "/farm-avatar.html");
});

app.post('/api/photo',function(req,res){
  console.log("api photo");
    upload(req,res,function(err) {
        if(err) {
          console.log("error: "+err);
            return res.end("Error uploading file.");
        }
        res.end("File is uploaded");
    });
});

app.listen(8888,function(){
    console.log("开始监听...");
});
