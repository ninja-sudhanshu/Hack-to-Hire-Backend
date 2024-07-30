const stompClient = new StompJs.Client({
    brokerURL: 'ws://localhost:9001/flights-status'
});

stompClient.onConnect = (frame) => {
    console.log('Connected.');
    stompClient.publish({
        destination: "/app/departures",
        body: JSON.stringify({'name': "Client"})
    });
    stompClient.subscribe('/topic/departures', (message) => {
        console.log(JSON.parse(message.body));
        showGreeting(JSON.parse(message.body));
    });
};

stompClient.onWebSocketError = (error) => {
    console.error('Error with websocket', error);
};

stompClient.onStompError = (frame) => {
    console.error('Broker reported error: ' + frame.headers['message']);
    console.error('Additional details: ' + frame.body);
};

$(window).on('load', function() {
    setTimeout(function(){
        stompClient.activate()
    }, 1000)
})