var app = require ('express')();
var server =require('http').Server(app);
var io = require('socket.io')(server);
var characters= [];
var player= [];


server.listen(8080,function(){
    console.log("Server is now running...");
});

io.on('connection',function(socket){

    console.log("Player connected");
    socket.emit('socketID',{ id: socket.id });
    socket.emit('getPlayers', characters);
    socket.broadcast.emit('newPlayer',{ id: socket.id});
    socket.on('playerChose',function(data){
        data.id=socket.id;
        socket.broadcast.emit('playerChose',data);

        console.log("Player chose : " + data.character);
        for(var i=0; i <characters.length ; i++){
            if(characters[i].id == data.id){
                characters[i].name = data.character;
            }
        }
    });

    socket.on('disconnect',function(){
        console.log("Player disconnected");
        socket.broadcast.emit("playerDisconnected",{id : socket.id});
        for(var i =0 ;i<characters.length; i++){
            if(characters[i].id == socket.id){
                characters.splice(i,1);
            }
        }
    });
    characters.push(new character(socket.id,""));
    players.push(new player(socket.id, 0, 0));
});

function character(id, name){
    this.id =id;
    this.name = name;
}

function player(id, x, y){
	this.id = id;
	this.x = x;
	this.y = y;
}