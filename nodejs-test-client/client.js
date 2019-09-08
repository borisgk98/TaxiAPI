var socket = require('socket.io-client')('http://localhost:3000');
socket.on('connect', function(){
    console.log("connect!");
    socket.emit("request.user.data", "{\"avatarUrl\":\"https:\\/\\/sun9-55.userapi.com\\/c836723\\/v836723063\\/68018\\/s5ZhKqSenwE.jpg?ava=1\",\"firstName\":\"Александр\",\"lastName\":\"Скворцов\",\"id\":\"11\",\"authServicesData\":{\"VK\":{\"socialId\":\"153171206\",\"friendsHash\":775690970926602448}}}")
});
socket.on('response.user.data', function(data){
    console.log(data);
});
socket.on('disconnect', function(){});