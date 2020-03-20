package com.mygdx.game.views;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Andor;
import com.mygdx.game.character.Hero;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class ChatScreen implements Screen{
    Andor parent;
    Hero CurrentHero;
    TextArea areaMessage;
    TextField sendMessage;
    Table table;
    Stage stage;
    TextButton sendButton;
    TextButton backButton;
    TextArea scrollMessage;
    Socket socket;



    public ChatScreen(Andor andor){
        parent = andor;
        socket = parent.getSocket();
        stage = new Stage(new ScreenViewport());




    }

    @Override
    public void show(){
        stage.clear();
        Gdx.input.setInputProcessor(stage);


        CurrentHero = parent.whoseTurn();
        areaMessage = new TextArea("", parent.skin);
        sendMessage = new TextField("", parent.skin);

        sendButton = new TextButton("Send",parent.skin);
        backButton = new TextButton("Back",parent.skin);


        table = new Table();





        scrollMessage = new TextArea("",parent.skin);
        scrollMessage.setDisabled(true);

        scrollMessage.layout();

        stage.addActor(table);
        table.setFillParent(true);
        connectSocket();
        configSocketEvents();


        table.add(sendMessage).height(50).prefWidth(999);
        table.add(sendButton).size(50);
        table.row();
        table.add(scrollMessage).prefSize(999).colspan(2);
        areaMessage.setDisabled(true);
        table.row();
        table.add(backButton);
        backButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                parent.changeScreen(Andor.MULTIGAME);
            }
        });

        stage.setKeyboardFocus(sendMessage);


        sendButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                sendMess();

                Gdx.input.setOnscreenKeyboardVisible(false);
            }
        });

        sendMessage.setTextFieldListener(new TextField.TextFieldListener(){
            @Override
            public void keyTyped(TextField textField, char c){
                // areaMessage.appendText(c+ " " + (int) c + "\n");
                if((int)c == 13 || (int)c == 10) {
                    sendMess();
                    Gdx.input.setOnscreenKeyboardVisible(true);

                }
            }
        });

         /*sendMessage.addCaptureListener(new TextField.TextFieldClickListener(){
         	@Override
         	public boolean keyDown (InputEvent event, int character) {
         		if (character == Input.Keys.ENTER){
        			String mess = sendMessage.getText();
        		if(!mess.equals("") && !mess.equals(" ")){
        		    areaMessage.setText("Name\n   "+mess + "\n" +
        				areaMessage.getText());
        		}

        		}

        		return true;
        	}
        });*/

    }

    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1/30f));

        stage.getBatch().begin();
        stage.getBatch().setColor(Color.WHITE);
        stage.getBatch().draw(parent.andorMenu, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.getBatch().end();
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }


    public void sendMess(){
        JSONObject data = new JSONObject();
        String mess = sendMessage.getText();
        scrollMessage.appendText("You : "+  mess+"\n");
        try{
            data.put("mess",mess);
            sendMessage.setText("");

            socket.emit("sendMessage", data);
        }catch (Exception e){

        }
    }

    public void configSocketEvents(){
        socket.on("sendMessage", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject data = (JSONObject)args[0];
                try {
                        String id = data.getString("id");
                        String mess = data.getString("mess");
                        scrollMessage.appendText(id+" : "+  mess+"\n");

                }catch(Exception e){
                    Gdx.app.log("SocketIO", "Error handling message getting");
                }
            }
        });

    }
    public void connectSocket(){

        try{
            socket =IO.socket("http://localhost:8080");
            socket.connect();
        }catch(Exception e){
            System.out.println(e);
        }
    }
}