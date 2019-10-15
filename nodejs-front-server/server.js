var log4js = require('log4js');
var logger = log4js.getLogger();

const server = require('http').createServer();
const io = require('socket.io')(server);

const endpoints = require('./endpoints.json');

// // const client = require('node-rest-client').Client;
// const javaServerUrl = 'localhost:8080';
// let request = require('async-request');
//
// async function doRequest(path, data) {
//     return (await request('http://' + javaServerUrl + path, data)).body;
// }

const { Kafka } = require('kafkajs');

const bootstrap_server = (process.env.KAFKA_SERVER == undefined ? 'localhost:9092' : process.env.KAFKA_SERVER);

const kafka = new Kafka({
    clientId: 'js-front-server',
    brokers: [bootstrap_server]
});

const producer = kafka.producer();
const consumer = kafka.consumer({ groupId: 'js-front-server' });

async function sendKafkaMess(topic, mess) {

    await producer.send({
        topic: topic,
        messages: [
            { value: mess }
        ]
    });
}

async function logInfo(socket, topic, message) {
    let date = new Date(Date.now());
    console.log("[" + socket.id + "]" +
        "[" + date.toLocaleString("ru-RU") + "]" +
        (topic != null ? "[" + topic + "]" : "") +
        message);
}

async function startProducer(){

    await producer.connect();

    io
        .of('')
        .on('connection', socket => {
            logInfo(socket, null, "New connection!");
            endpoints.forEach(endpoint => {
                let topic = "request." + endpoint;
                socket.on(topic, async data => {
                    logInfo(socket, topic, data);
                    let message = JSON.stringify({ socket: socket.id, payload: data });
                    await sendKafkaMess(topic, message);
                });
            });
        });
    server.listen(3000);
}

async function startConsumer() {
    // Consuming
    await consumer.connect();
    endpoints.forEach(async endpoint => {
        let name = "response." + endpoint;
        await consumer.subscribe({ topic: name, fromBeginning: false });gk.space
    });
    await consumer.subscribe({ topic: 'error', fromBeginning: false });
    await consumer.subscribe({ topic: 'response.test', fromBeginning: false });

    await consumer.run({
        eachMessage: async ({ topic, partition, message }) => {
            const ans = JSON.parse(new String(message.value));
            let socket = io.sockets.connected[ans.socket];
            if (socket != undefined && socket != null) {
                socket.emit(topic, ans.payload);
                logInfo(socket, topic, ans.payload);
            }
        },
    })
}

async function start() {
    await startProducer();
    await startConsumer();
}

start();
