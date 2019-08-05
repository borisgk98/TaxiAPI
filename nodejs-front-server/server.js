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

const kafka = new Kafka({
    clientId: 'js-front-server',
    // brokers: [(process.env.KAFKA_SERVER == undefined ? 'localhost:9092' : process.env.KAFKA_SERVER)]
    // brokers: ['kafka-taxi:9092']
    brokers: ['localhost:9092', 'kafka-taxi:9092']
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

let socket;


async function startProducer(){

    await producer.connect();

    io
        .of('')
        .on('connection', s => {

            socket = s;

            endpoints.forEach(endpoint => {
                let name = "request." + endpoint;
                s.on(name, async data => {
                    await sendKafkaMess(name, data);
                });
            });
            // s.on('request.user.data', async data => {
            //     await sendKafkaMess("request.user.data", data);
            // });
            // s.on('request.trip.search', async data => {
            //     await sendKafkaMess("request.trip.search", data);
            // });
            // s.on('request.trip.create', async data => {
            //     await sendKafkaMess("request.trip.create", data);
            // });
        });
    server.listen(3000);
}

async function startConsumer() {
    // Consuming
    await consumer.connect();
    endpoints.forEach(async endpoint => {
        let name = "response." + endpoint;
        await consumer.subscribe({ topic: name, fromBeginning: false });
    });
    await consumer.subscribe({ topic: 'error', fromBeginning: false });
    await consumer.subscribe({ topic: 'response.test', fromBeginning: false });

    await consumer.run({
        eachMessage: async ({ topic, partition, message }) => {
            const ans = new String(message.value);
            socket.emit(topic, ans);
        },
    })
}

async function start() {
    await startProducer();
    await startConsumer();
}

start();
