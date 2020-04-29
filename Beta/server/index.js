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
    players.push(new player(socket.id,"Choosing...",0,"",0,0));
    io.emit('newPlayer',players);


    socket.on('playerChose',function(data){
        data.id=socket.id;

        console.log("Player chose : " + data.name);
        console.log("Game will be : " + data.difficulty);

        for(var i=0; i <players.length ; i++){
            if(players[i].id == data.id){
                players[i].level = data.difficulty;
                players[i].name = data.name;
            }
        }
        io.emit('playerChose', players );


    });

    socket.on('playerChose_Lose',function(data){
        data.id=socket.id;

        console.log("Player chose : " + data.name);

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

            io.emit('playerMoved',players);

        });


     socket.on("GoldWine",function(data){

            for(var i =0 ;i<players.length; i++){
                 if(players[i].name == "Archer" ){
                    players[i].gold = data.goldArcher;
                    players[i].wineskin = data.wineArcher;
                 }
                 if(players[i].name == "Warrior" ){
                    players[i].gold = data.goldWarrior;
                    players[i].wineskin = data.wineWarrior;
                 }
                 if(players[i].name == "Wizard" ){
                    players[i].gold = data.goldWizard;
                    players[i].wineskin = data.wineWizard;
                 }
                 if(players[i].name == "Dwarf" ){
                    players[i].gold = data.goldDwarf;
                    players[i].wineskin = data.wineDwarf;
                  }


             }

        socket.broadcast.emit("GoldWine",players);
     });


     socket.on("farmerPickedUp", function(data) {
//        data.id = socket.id;
        console.log("A farmer picked up by a hero.");
        var hero = data.hero;
        socket.broadcast.emit("farmerPickedUp", {hero : hero});
     });

     socket.on("farmerDroppedOff", function(data) {
        console.log("A farmer dropped off.");
        var hero = data.hero;
        socket.broadcast.emit("farmerDroppedOff", {hero : hero});
     });

     socket.on("goldDropped", function(data) {
        console.log("Gold dropped in a region.");
        var hero = data.hero;
        socket.broadcast.emit("goldDropped", {hero : hero});
     });

     socket.on("goldPickedUp", function(data) {
         console.log("Gold picked up by a hero.");
         var hero = data.hero;
         socket.broadcast.emit("goldPickedUp", {hero : hero});
      });

      socket.on("drankWell", function(data) {
        console.log("A hero drank from a well.");
        var hero = data.hero;
        socket.broadcast.emit("drankWell", {hero : hero});
      });

     socket.on("finishTurn",function(data){
        var choice = data.choice;
        var pastPlayer = data.pastPlayer;
        if(choice == 1 ){
            console.log(choice + ". player skip his turn" );
        }else if (choice ==2){
            console.log(choice + ". Next player turn" );
        }
        else{
            console.log(choice + ". End day" );
        }

         socket.broadcast.emit("finishTurn", {choice : choice , pastPlayer : pastPlayer});
     });

     socket.on("updateBattle",function(data){
        var wantToJoin = data.wantToJoin;
        socket.broadcast.emit("updateBattle", {wantToJoin : wantToJoin});

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

function player (id, name, x,level,gold,wineskin){
    this.id =id;
    this.name = name;
    this.x = x;
    this.level = level;
    this.gold =gold;
    this.wineskin = wineskin;


}

function message(id , mess){
    this.mess = mess;
    this.id = id;
}