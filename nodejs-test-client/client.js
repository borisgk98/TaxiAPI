var socket = require('socket.io-client')('http://localhost:3000');
socket.on('connect', function(){
    console.log("connect!");
    socket.emit("request.user.get.trips", "{\"userId\":\"1\", \"tripStatus\":\"ACTIVE\"}");
    socket.emit("request.trip.create", "{\"date\":1569224401,\"latTo\":55.777855702487898,\"addressTo\":\"улица Карима Тинчурина \",\"latFrom\":55.771881103515625,\"addressFrom\":\"Портовая улица 15\",\"longFrom\":49.097140008526949,\"users\":[{\"id\":\"11\",\"lastName\":\"Скворцов\",\"firstName\":\"Александр\",\"authServicesData\":{\"VK\":{\"friendsHash\":775690970926602448,\"socialId\":\"153171206\"}},\"avatarUrl\":\"https:\\/\\/sun9-23.userapi.com\\/c836723\\/v836723063\\/68018\\/s5ZhKqSenwE.jpg?ava=1\"}],\"longTo\":49.104469543751733}");
});
socket.on('response.user.get.trips', function(data){
    console.log(data);
});
socket.on('response.trip.create', function(data){
    console.log(data);
});
socket.on('disconnect', function(){});