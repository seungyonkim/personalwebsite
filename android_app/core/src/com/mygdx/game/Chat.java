package com.mygdx.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
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
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class Chat implements Screen {
    MyGdxGame controller;
    TextArea areaMessage;
    TextField sendMessage;
    Table table;
    Stage stage;
    ImageButton sendButton;
    ScrollPane scrollMessage;
    Label label;
    Skin skin;

    Label title;
    TextButton Back;


    public Chat(MyGdxGame game){
        controller = game;


        skin = new Skin(Gdx.files.internal("res/default/uiskin.json"));
        title = new Label ("CHAT MENU", skin);
        areaMessage = new TextArea("", skin);
        sendMessage = new TextField("", skin);
        Back =new TextButton("Back to Game",controller.skin);

        sendButton = new ImageButton(controller.skin);

        table = new Table();

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        label = new Label("",skin, "avdira-30", "white");
        controller.setCurrentLabel(label);

        scrollMessage = new ScrollPane(label, skin);
        scrollMessage.layout();


    }

    @Override
    public void show(){
        stage.addActor(table);
        table.setFillParent(true);

        table.add(title).height(50).prefWidth(999).colspan(2);
        table.row().pad(40, 0, 40, 0);
        table.add(sendMessage).height(50).prefWidth(999);
        table.add(sendButton).size(50);
        table.row();
        table.add(scrollMessage).prefSize(999).colspan(2);
        table.row();
        table.add(Back);
        // areaMessage.setDisabled(true);
        stage.setKeyboardFocus(sendMessage);




        Back.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                controller.changeScreen(MyGdxGame.APPLICATION);

            }
        });


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
                    Gdx.input.setOnscreenKeyboardVisible(false);

                }
            }
        });

        // sendMessage.addCaptureListener(new TextField.TextFieldClickListener(){
        // 	@Override
        // 	public boolean keyDown (InputEvent event, int character) {
        // 		if (character == Input.Keys.ENTER){
        // 			String mess = sendMessage.getText();
        // 			if(!mess.equals("") && !mess.equals(" ")){
        // 				areaMessage.setText("Name\n   "+mess + "\n" +
        // 					areaMessage.getText());
        // 			}

        // 		}

        // 		return true;
        // 	}
        // });

    }

    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0, 0.2f, 0, 1);
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
        controller.changeScreen(MyGdxGame.MENU);
    }

    public void sendMess(){
        String mess = sendMessage.getText();
        if(!mess.equals("") && !mess.equals(" ")){
            sendMessage.setText("");

            controller.sendMessage(mess);
        }
    }


}
