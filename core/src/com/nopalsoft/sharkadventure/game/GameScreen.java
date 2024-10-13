package com.nopalsoft.sharkadventure.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.nopalsoft.sharkadventure.Achievements;
import com.nopalsoft.sharkadventure.Assets;
import com.nopalsoft.sharkadventure.MainShark;
import com.nopalsoft.sharkadventure.Settings;
import com.nopalsoft.sharkadventure.scene2d.GameUI;
import com.nopalsoft.sharkadventure.scene2d.MenuUI;
import com.nopalsoft.sharkadventure.scene2d.PauseWindowGroup;
import com.nopalsoft.sharkadventure.screens.Screens;

public class GameScreen extends Screens {

    final int STATE_MENU = 0; // User is in the main menu
    final int STATE_RUNNING = 1; // The game is running
    final int STATE_PAUSED = 2; // The game is paused
    final int STATE_GAME_OVER = 3; // Game is over. Same as the main menu but without the title.


    public long punctuation;
    int state;
    WorldGame worldGame;
    WorldRenderer renderer;
    GameUI gameUI;
    MenuUI menuUI;
    PauseWindowGroup pauseWindow;

    /**
     * @param showMainMenu Show main menu otherwise start game immediately.
     */
    public GameScreen(MainShark game, boolean showMainMenu) {
        super(game);
        worldGame = new WorldGame();
        renderer = new WorldRenderer(batcher, worldGame);
        gameUI = new GameUI(this, worldGame);
        menuUI = new MenuUI(this, worldGame);
        pauseWindow = new PauseWindowGroup(this);

        Assets.reloadBackground();

        if (showMainMenu) {
            state = STATE_MENU;
            menuUI.show(stage, true);
        } else {
            setRunning(false);
        }

        Achievements.tryAgainAchievements();

    }

    @Override
    public void update(float delta) {
        switch (state) {
            case STATE_RUNNING:
                updateRunning(delta);
                break;
            case STATE_MENU:
                updateStateMenu(delta);
                break;

        }
    }

    private void updateRunning(float delta) {
        if (Gdx.input.isKeyPressed(Keys.A))
            gameUI.speedX = -1;

        else if (Gdx.input.isKeyPressed(Keys.D))
            gameUI.speedX = 1;

        if (Gdx.input.isKeyJustPressed(Keys.W) || Gdx.input.isKeyJustPressed(Keys.SPACE))
            gameUI.didSwimUp = true;

        if (Gdx.input.isKeyJustPressed(Keys.CONTROL_RIGHT) || Gdx.input.isKeyJustPressed(Keys.CONTROL_RIGHT) || Gdx.input.isKeyJustPressed(Keys.F))
            gameUI.didFire = true;

        worldGame.update(delta, gameUI.speedX, gameUI.didSwimUp, gameUI.didFire);

        punctuation = (long) worldGame.score;

        gameUI.lifeBar.updateActualNum(worldGame.shark.life);
        gameUI.energyBar.updateActualNum(worldGame.shark.energy);

        gameUI.didSwimUp = false;
        gameUI.didFire = false;

        if (worldGame.state == WorldGame.STATE_GAME_OVER) {
            setGameOver();
        }

    }

    private void updateStateMenu(float delta) {
        worldGame.shark.updateStateTime(delta);

    }

    public void setRunning(boolean removeMenu) {
        Runnable runAfterHideMenu = new Runnable() {
            @Override
            public void run() {
                Runnable run = new Runnable() {
                    @Override
                    public void run() {
                        state = STATE_RUNNING;
                    }
                };
                gameUI.addAction(Actions.sequence(Actions.delay(GameUI.ANIMATION_TIME), Actions.run(run)));
                gameUI.show(stage);
            }
        };

        if (removeMenu) {
            menuUI.addAction(Actions.sequence(Actions.delay(MenuUI.ANIMATION_TIME), Actions.run(runAfterHideMenu)));
            menuUI.removeWithAnimations();
        } else {
            stage.addAction(Actions.run(runAfterHideMenu));
        }

    }

    private void setGameOver() {
        if (state != STATE_GAME_OVER) {
            state = STATE_GAME_OVER;
            Runnable runAfterHideGameUI = new Runnable() {
                @Override
                public void run() {
                    menuUI.show(stage, false);
                }
            };

            Settings.setBestScore(punctuation);

            gameUI.addAction(Actions.sequence(Actions.delay(MenuUI.ANIMATION_TIME), Actions.run(runAfterHideGameUI)));
            gameUI.removeWithAnimations();
            game.gameServiceHandler.submitScore((long) worldGame.score);

            Settings.numberOfTimesPlayed++;
            if (Settings.numberOfTimesPlayed % 4f == 0) {
                game.reqHandler.showInterstitial();
            }
            game.reqHandler.showAdBanner();
        }
    }

    public void setPaused() {
        if (state == STATE_RUNNING) {
            state = STATE_PAUSED;
            gameUI.removeWithAnimations();
            pauseWindow.show(stage);
        }
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Keys.A || keycode == Keys.D)
            gameUI.speedX = 0;
        return super.keyUp(keycode);
    }

    @Override
    public void draw(float delta) {

        if (state == STATE_PAUSED || state == STATE_GAME_OVER)
            delta = 0;

        renderer.render(delta);

        camera.update();
        batcher.setProjectionMatrix(camera.combined);
        batcher.enableBlending();
        batcher.begin();

        batcher.end();

    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Keys.BACK | keycode == Keys.ESCAPE) {
            if (state == STATE_RUNNING) {
                setPaused();
            } else if (state == STATE_PAUSED) {
                pauseWindow.hide();
                setRunning(false);
            } else if (state == STATE_MENU) {
                Gdx.app.exit();
            }
            return true;
        }
        if (keycode == Keys.L) {
            game.facebookHandler.facebookSignIn();
            return true;
        }

        if (keycode == Keys.P) {
            game.setScreen(new GameScreen(game, false));
            return true;
        }
        return super.keyDown(keycode);
    }
}
