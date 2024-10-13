package com.nopalsoft.sharkadventure.scene2d;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
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
import com.nopalsoft.sharkadventure.Settings;
import com.nopalsoft.sharkadventure.game.GameScreen;
import com.nopalsoft.sharkadventure.game.WorldGame;
import com.nopalsoft.sharkadventure.screens.Screens;

public class MenuUI extends Group {
	public static final float ANIMATION_TIME = .35f;

	GameScreen gameScreen;
	WorldGame oWorld;
	Image imageTitle;
	Image imageGameOver;

	Table tableMenu;
	Table tableGameOver;

	Label labelBestScore;
	Label labelScore;

	Button buttonPlay, buttonLeaderboard, buttonAchievements, buttonFacebook, buttonTwitter;
	Button buttonMusic, buttonSound;

	boolean showMainMenu;

	public MenuUI(final GameScreen gameScreen, WorldGame worldGame) {
		setBounds(0, 0, Screens.SCREEN_WIDTH, Screens.SCREEN_HEIGHT);
		this.gameScreen = gameScreen;
		this.oWorld = worldGame;

		initialize();

		tableGameOver = new Table();
		tableGameOver.setSize(350, 200);
		tableGameOver.setBackground(Assets.backgroundVentana);
		tableGameOver.setPosition(getWidth() / 2f - tableGameOver.getWidth() / 2f, 110);

		labelBestScore = new Label("0", Assets.lblStyle);
		labelScore = new Label("0", Assets.lblStyle);

		labelScore.setFontScale(.8f);
		labelBestScore.setFontScale(.8f);

		tableGameOver.pad(15).padTop(30).padBottom(50);
		tableGameOver.defaults().expand();

		tableGameOver.add(new Label("Score", Assets.lblStyle)).left();
		tableGameOver.add(labelScore).expandX().right();

		tableGameOver.row();
		tableGameOver.add(new Label("Best score", Assets.lblStyle)).left();
		tableGameOver.add(labelBestScore).expandX().right();

	}

	private void initialize() {
		imageTitle = new Image(Assets.drawableTitle);
		imageTitle.setScale(1f);
		imageTitle.setPosition(getWidth() / 2f - imageTitle.getWidth() * imageTitle.getScaleX() / 2f, Screens.SCREEN_HEIGHT + imageTitle.getHeight());

		imageGameOver = new Image(Assets.drawableGameOver);
		imageGameOver.setScale(1.25f);
		imageGameOver.setPosition(getWidth() / 2f - imageGameOver.getWidth() * imageGameOver.getScaleX() / 2f, Screens.SCREEN_HEIGHT + imageGameOver.getHeight());

		buttonFacebook = new Button(Assets.btFacebook, Assets.btFacebookPress);
		buttonFacebook.setSize(60, 60);
		buttonFacebook.setPosition(Screens.SCREEN_WIDTH + buttonFacebook.getWidth(), 410);

		buttonTwitter = new Button(Assets.btTwitter, Assets.btTwitterPress);
		buttonTwitter.setSize(60, 60);
		buttonTwitter.setPosition(Screens.SCREEN_WIDTH + buttonTwitter.getWidth(), 410);

		buttonMusic = new Button(Assets.btMusicOff, Assets.btMusicOn, Assets.btMusicOn);
		buttonMusic.setSize(60, 60);
		buttonMusic.setPosition(-buttonMusic.getWidth(), 410);

		buttonSound = new Button(Assets.btSoundOff, Assets.btSoundOn, Assets.btSoundOn);
		buttonSound.setSize(60, 60);
		buttonSound.setPosition(-buttonSound.getWidth(), 325);

		tableMenu = new Table();
		tableMenu.setBackground(Assets.backgroundMenu);

		buttonPlay = new Button(Assets.btDer, Assets.btDerPress);
		buttonLeaderboard = new Button(Assets.btLeaderboard, Assets.btLeaderboardPress);
		buttonAchievements = new Button(Assets.btAchievements, Assets.btAchievementsPress);

		tableMenu.defaults().size(90).padBottom(20).padLeft(10).padRight(10);
		if (Gdx.app.getType() != ApplicationType.WebGL) {
			tableMenu.setSize(385, 85);
			tableMenu.add(buttonPlay);
			tableMenu.add(buttonLeaderboard);
			tableMenu.add(buttonAchievements);
		}
		else {
			tableMenu.setSize(120, 85);
			tableMenu.add(buttonPlay);
		}
		tableMenu.setPosition(Screens.SCREEN_WIDTH / 2f - tableMenu.getWidth() / 2f, -tableMenu.getHeight());

		buttonFacebook.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				gameScreen.game.facebookHandler.showFacebook();
			}
		});

		buttonTwitter.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				gameScreen.game.reqHandler.shareOnTwitter("");

			}
		});

		buttonLeaderboard.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (gameScreen.game.gameServiceHandler.isSignedIn()) {
					gameScreen.game.gameServiceHandler.getLeaderboard();
				}
				else {
					gameScreen.game.gameServiceHandler.signIn();
				}
			}
		});

		buttonAchievements.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (gameScreen.game.gameServiceHandler.isSignedIn()) {
					gameScreen.game.gameServiceHandler.getAchievements();
				}
				else {
					gameScreen.game.gameServiceHandler.signIn();
				}
			}
		});

		buttonPlay.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				gameScreen.game.reqHandler.hideAdBanner();
				if (showMainMenu)
					gameScreen.setRunning(true);
				else {
					gameScreen.game.setScreen(new GameScreen(gameScreen.game, false));
				}
			}
		});

		buttonMusic.setChecked(Settings.isMusicOn);
		buttonMusic.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Settings.isMusicOn = !Settings.isMusicOn;
				buttonMusic.setChecked(Settings.isMusicOn);
				if (Settings.isMusicOn)
					Assets.musica.play();
				else
					Assets.musica.pause();
			}
		});

		buttonSound.setChecked(Settings.isSoundOn);
		buttonSound.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Settings.isSoundOn = !Settings.isSoundOn;
				buttonSound.setChecked(Settings.isSoundOn);
			}
		});

		addActor(tableMenu);
		addActor(buttonFacebook);
		addActor(buttonTwitter);
		addActor(buttonMusic);
		addActor(buttonSound);

	}

	private void addInActions() {
		imageTitle.addAction(Actions.moveTo(getWidth() / 2f - imageTitle.getWidth() * imageTitle.getScaleX() / 2f, 300, ANIMATION_TIME));
		imageGameOver.addAction(Actions.moveTo(getWidth() / 2f - imageGameOver.getWidth() * imageGameOver.getScaleX() / 2f, 320, ANIMATION_TIME));

		tableMenu.addAction(Actions.moveTo(Screens.SCREEN_WIDTH / 2f - tableMenu.getWidth() / 2f, 0, ANIMATION_TIME));

		buttonFacebook.addAction(Actions.moveTo(735, 410, ANIMATION_TIME));
		buttonTwitter.addAction(Actions.moveTo(735, 325, ANIMATION_TIME));
		buttonMusic.addAction(Actions.moveTo(5, 410, ANIMATION_TIME));
		buttonSound.addAction(Actions.moveTo(5, 325, ANIMATION_TIME));

	}

	private void addOutActions() {
		imageTitle.addAction(Actions.moveTo(getWidth() / 2f - imageTitle.getWidth() * imageTitle.getScaleX() / 2f, Screens.SCREEN_HEIGHT + imageTitle.getHeight(),
				ANIMATION_TIME));
		imageGameOver.addAction(Actions.moveTo(getWidth() / 2f - imageGameOver.getWidth() * imageGameOver.getScaleX() / 2f,
				Screens.SCREEN_HEIGHT + imageGameOver.getHeight(), ANIMATION_TIME));

		tableMenu.addAction(Actions.moveTo(Screens.SCREEN_WIDTH / 2f - tableMenu.getWidth() / 2f, -tableMenu.getHeight(), ANIMATION_TIME));

		buttonFacebook.addAction(Actions.moveTo(Screens.SCREEN_WIDTH + buttonFacebook.getWidth(), 410, ANIMATION_TIME));
		buttonTwitter.addAction(Actions.moveTo(Screens.SCREEN_WIDTH + buttonTwitter.getWidth(), 325, ANIMATION_TIME));
		buttonMusic.addAction(Actions.moveTo(-buttonMusic.getWidth(), 410, ANIMATION_TIME));
		buttonSound.addAction(Actions.moveTo(-buttonSound.getWidth(), 325, ANIMATION_TIME));
	}

	public void show(Stage stage, final boolean showMainMenu) {
		addInActions();
		stage.addActor(this);

		imageTitle.remove();
		imageGameOver.remove();
		tableGameOver.remove();

		if (showMainMenu) {
			addActor(imageTitle);
		}
		else {
			labelBestScore.setText(Settings.bestScore + " m");
			labelScore.setText(gameScreen.puntuacion + " m");

			addActor(imageGameOver);
			addActor(tableGameOver);
		}

		this.showMainMenu = showMainMenu;

	}

	public void removeWithAnimations() {
		addOutActions();
		addAction(Actions.sequence(Actions.delay(ANIMATION_TIME), Actions.removeActor()));
	}

}
