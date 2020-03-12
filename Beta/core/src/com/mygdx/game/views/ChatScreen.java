package com.mygdx.game.views;
import com.badlogic.gdx.Screen;
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
    Label label;



    public ChatScreen(Andor andor){
        parent = andor;


        CurrentHero = parent.whoseTurn();
        areaMessage = new TextArea("", parent.skin);
        sendMessage = new TextField("", parent.skin);

        sendButton = new TextButton("Send",parent.skin);
        backButton = new TextButton("Back",parent.skin);


        table = new Table();

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        label = new Label("",parent.skin);



        scrollMessage = new TextArea("",parent.skin);
        scrollMessage.setDisabled(true);

        scrollMessage.layout();


    }

    @Override
    public void show(){

        stage.addActor(table);
        table.setFillParent(true);



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
        stage.act(delta);
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


    public void backMenu(){
        parent.changeScreen(Andor.MULTIGAME);
    }

    public void sendMess(){
        String mess = sendMessage.getText();
        if(!mess.equals("") && !mess.equals(" ")){
            sendMessage.setText("");
            sendNewMessage(mess);
        }
    }
    public void sendNewMessage(String mess){
        scrollMessage.appendText(CurrentHero.getTypeOfHeroString()+" : "+  mess+"\n");
    }
}