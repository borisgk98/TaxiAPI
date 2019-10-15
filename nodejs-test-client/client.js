var socket = require('socket.io-client')('http://localhost:3000');
socket.on('connect', function(){
    console.log("connect!");
    // socket.emit("request.user.trips", "11");
    // socket.emit("request.user.report", "{\"reporterId\": 1, \"tripId\": 1, \"userId\": 1, \"date\":\"2019-10-13T18:54:06Z\"}");
    // socket.emit("request.user.isReported", "{\"reporterId\": 1, \"tripId\": 1, \"userId\": 1, \"date\":\"2019-10-13T18:54:06Z\"}");
    socket.emit("request.user.statistics", "12");
    // socket.emit("request.trip.create", "{\"date\":\"2019-10-13T18:54:06Z\",\"latTo\":55.777855702487898,\"addressTo\":\"улица Карима Тинчурина \",\"latFrom\":55.771881103515625,\"addressFrom\":\"Портовая улица 15\",\"longFrom\":49.097140008526949,\"users\":[{\"id\":\"11\",\"lastName\":\"Скворцов\",\"firstName\":\"Александр\",\"authServicesData\":{\"VK\":{\"friendsHash\":775690970926602448,\"socialId\":\"153171206\"}},\"avatarUrl\":\"https:\\/\\/sun9-23.userapi.com\\/c836723\\/v836723063\\/68018\\/s5ZhKqSenwE.jpg?ava=1\"}],\"longTo\":49.104469543751733}");
    // socket.emit("request.trip.search", "{\"userId\":\"11\",\"numberOfSeats\":1,\"longFrom\":49.097100403203015,\"latFrom\":55.7718505859375,\"date\":\"2019-10-13T18:54:06Z\"}");
});
socket.on('response.user.trips', function(data){
    console.log(data);
    // process.exit();
});
socket.on('response.user.isReported', function(data){
    console.log(data);
    // process.exit();
});
socket.on('response.user.statistics', function(data){
    console.log(data);
    // process.exit();
});
socket.on('response.user.report', function(data){
    console.log(data);
    // process.exit();
});
socket.on('response.trip.create', function(data){
    console.log(data);
    process.exit();
});
socket.on('response.trip.search', function(data){
    console.log(data);
    process.exit();
});
socket.on('disconnect', function(){});