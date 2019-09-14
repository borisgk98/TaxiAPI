var socket = require('socket.io-client')('http://192.168.2.166:3000');
socket.on('connect', function(){
    console.log("connect!");
    socket.emit("request.user.get.trips", "{\"userId\":\"1\", \"tripStatus\":\"ACTIVE\"}")
});
socket.on('response.user.get.trips', function(data){
    console.log(data);
});
socket.on('disconnect', function(){});