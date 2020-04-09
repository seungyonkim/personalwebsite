var app = require ('express')();
var server =require('http').Server(app);
var io = require('socket.io')(server);
var players= [];
var messages =[];
var difficulty=0;


server.listen(8080,function(){
    console.log("Server is now running...");
});

io.on('connect',function(socket){

    console.log("Player connected");
    players.push(new player(socket.id,"Choosing....",0));
    io.emit('newPlayer',players);


    socket.on('playerChose',function(data){
        data.id=socket.id;


        console.log("Player chose : " + data.name);

        if(data.difficulty == "Easy" ){
            difficulty = -1;
        }
        if(data.difficulty == "Hard"){
            difficulty = 1;
        }
        for(var i=0; i <players.length ; i++){
            if(players[i].id == data.id){
                players[i].name = data.name;
            }
        }
        io.emit('playerChose', players );


    });
     socket.on('playerMoved',function(data){
            data.id=socket.id;


            console.log("Player moved to region " + data.x );
            for(var i=0; i <players.length ; i++){
                if(players[i].id == data.id){
                    players[i].x = data.x;
                }
            }

            socket.broadcast.emit('playerMoved',players);

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
            var name = "Not Found";
            for(var i =0;i<players.length;i++){
                if(players[i].id == data.id){
                         name = players[i].name;
                 }
             }
            console.log(name + " sent " + data.mess);
            messages.push(new message(name,data.mess));
            io.emit('sendMessage', messages);



        });

});

function player (id, name, x){
    this.id =id;
    this.name = name;
    this.x = x;

}

function message(id , mess){
    this.mess = mess;
    this.id = id;
}
