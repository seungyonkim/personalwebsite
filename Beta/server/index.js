var app = require ('express')();
var server =require('http').Server(app);
var io = require('socket.io')(server);
var players= [];
var messages =[];


server.listen(8080,function(){
    console.log("Server is now running...");
});

io.on('connection',function(socket){

    console.log("Player connected");
    socket.emit('socketID',{ id: socket.id });
    socket.broadcast.emit('newPlayer',{ id: socket.id});
    socket.on('playerChose',function(data){
        data.id=socket.id;
        socket.emit('playerChose',data);
        socket.broadcast.emit('playerChose',data);

        console.log("Player chose : " + data.name);
        for(var i=0; i <players.length ; i++){
            if(players[i].id == data.id){
                players[i].name = data.name;
            }
        }
            //socket.emit('getPlayers', players);

    });
     socket.on('playerMoved',function(data){
            data.id=socket.id;
            socket.broadcast.emit('playerMoved',data);

            console.log("Player moved : " + data.id);
            for(var i=0; i <players.length ; i++){
                if(players[i].id == data.id){
                    players[i].x = data.x;
                    players[i].y = data.y;
                }
            }

        });

    socket.on('disconnect',function(){
        console.log("Player disconnected");
        socket.broadcast.emit("playerDisconnected",{id : socket.id});
        for(var i =0 ;i<players.length; i++){
            if(players[i].id == socket.id){
                players.splice(i,1);
            }
        }
    });
     socket.on('sendMessage',function(data){
            data.id=socket.id;

            console.log("Message Send : " + data.mess);
            messages.push(new message(data.id,data.mess));
            io.emit('sendMessage', messages);



        });
    players.push(new player(socket.id,"",0,0));
});

function player (id, name, x, y){
    this.id =id;
    this.name = name;
    this.x = x;
    this.y = y;

}

function message(id , mess){
    this.mess = mess;
    this.id = id;
}
