package com.nopalsoft.sharkadventure.scene2d;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.nopalsoft.sharkadventure.Assets;
import com.nopalsoft.sharkadventure.MainShark;
import com.nopalsoft.sharkadventure.game.GameScreen;
import com.nopalsoft.sharkadventure.screens.Screens;

public class PauseWindowGroup extends Group {
    public static final float ANIMATION_DURATION = .3f;

    protected GameScreen screen;
    protected MainShark game;
    Button buttonPlay, buttonRefresh, buttonHome;
    private boolean isVisible = false;

    public PauseWindowGroup(GameScreen currentScreen) {
        setSize(300, 300);
        setPosition(Screens.SCREEN_WIDTH / 2f - getWidth() / 2f, 80);
        screen = currentScreen;
        game = currentScreen.game;
        setBackGround();

        Table tableTitle = new Table();
        tableTitle.setSize(getWidth() - 80, 50);
        tableTitle.setPosition(getWidth() / 2f - tableTitle.getWidth() / 2f, getHeight() - 30);
        tableTitle.setBackground(Assets.backgroundTitle);

        Label labelTitle = new Label("Paused", Assets.labelStyle);

        tableTitle.add(labelTitle).fill().padBottom(10);
        addActor(tableTitle);

        buttonPlay = new Button(Assets.buttonRight, Assets.buttonRightPress);
        buttonPlay.setSize(70, 70);
        buttonPlay.setPosition(getWidth() / 2f - buttonPlay.getWidth() / 2f, 170);
        buttonPlay.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                hide();
                screen.setRunning(false);

            }
        });

        buttonRefresh = new Button(Assets.buttonRefresh, Assets.buttonRefreshPress);
        buttonRefresh.setSize(70, 70);
        buttonRefresh.setPosition(getWidth() / 2f + 25, 80);
        buttonRefresh.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                hide();
                screen.game.setScreen(new GameScreen(game, false));
            }
        });

        buttonHome = new Button(Assets.buttonHome, Assets.buttonHomePress);
        buttonHome.setSize(70, 70);
        buttonHome.setPosition(getWidth() / 2f - buttonHome.getWidth() - 25, 80);
        buttonHome.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                hide();
                screen.game.setScreen(new GameScreen(game, true));
            }
        });

        addActor(buttonPlay);
        addActor(buttonRefresh);
        addActor(buttonHome);
    }

    private void setBackGround() {
        Image image = new Image(Assets.backgroundWindow);
        image.setSize(getWidth(), getHeight());
        addActor(image);

    }

    public void show(Stage stage) {

        setOrigin(getWidth() / 2f, getHeight() / 2f);
        setX(Screens.SCREEN_WIDTH / 2f - getWidth() / 2f);

        setScale(.5f);
        addAction(Actions.sequence(Actions.scaleTo(1, 1, ANIMATION_DURATION)));

        isVisible = true;
        stage.addActor(this);

        game.reqHandler.showAdBanner();

    }

    public boolean isVisible() {
        return isVisible;
    }

    public void hide() {
        isVisible = false;
        game.reqHandler.hideAdBanner();
        remove();
    }

}
