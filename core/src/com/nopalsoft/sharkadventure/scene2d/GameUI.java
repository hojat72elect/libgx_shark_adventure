package com.nopalsoft.sharkadventure.scene2d;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.nopalsoft.sharkadventure.Assets;
import com.nopalsoft.sharkadventure.game.GameScreen;
import com.nopalsoft.sharkadventure.game.WorldGame;
import com.nopalsoft.sharkadventure.screens.Screens;

public class GameUI extends Group {
	public static final float ANIMATION_TIME = .35f;

	GameScreen gameScreen;
	WorldGame worldGame;

	public int speedX;
	public boolean didSwimUp;
	public boolean didFire;

	public ProgressBarUI lifeBar;
	public ProgressBarUI energyBar;

	Table tableHeader;
	Label labelScore;
	Button buttonLeft, buttonRight, buttonSwimUp, buttonFire, buttonPause;

	public GameUI(final GameScreen gameScreen, WorldGame worldGame) {
		setBounds(0, 0, Screens.SCREEN_WIDTH, Screens.SCREEN_HEIGHT);
		this.gameScreen = gameScreen;
		this.worldGame = worldGame;

		init();

		lifeBar = new ProgressBarUI(Assets.redBar, Assets.heart, com.nopalsoft.sharkadventure.objects.Shark.MAX_LIFE, -ProgressBarUI.WIDTH, 440);
		energyBar = new ProgressBarUI(Assets.energyBar, Assets.blast, com.nopalsoft.sharkadventure.objects.Shark.MAX_ENERGY, -ProgressBarUI.WIDTH, 395);

		addActor(lifeBar);
		addActor(energyBar);

	}

	private void init() {

		buttonSwimUp = new Button(Assets.buttonUp, Assets.buttonUpPress);
		buttonSwimUp.setSize(105, 105);
		buttonSwimUp.setPosition(692, -105);
		buttonSwimUp.getColor().a = .35f;

		buttonFire = new Button(Assets.buttonFire, Assets.buttonFirePress);
		buttonFire.setSize(105, 105);
		buttonFire.setPosition(579, -105);
		buttonFire.getColor().a = .35f;

		buttonRight = new Button(Assets.buttonRight, Assets.buttonRightPress, Assets.buttonRightPress);
		buttonRight.setSize(120, 120);
		buttonRight.setPosition(130, -120);
		buttonRight.getColor().a = .35f;

		buttonLeft = new Button(Assets.buttonLeft, Assets.buttonLeftPress, Assets.buttonLeftPress);
		buttonLeft.setSize(120, 120);
		buttonLeft.setPosition(5, -120);
		buttonLeft.getColor().a = .35f;

		buttonPause = new Button(Assets.buttonPause, Assets.buttonPausePress);
		buttonPause.setSize(45, 45);
		buttonPause.setPosition(845, 430);
		buttonPause.getColor().a = .5f;

		tableHeader = new Table();
		tableHeader.setSize(Screens.SCREEN_WIDTH, 50);
		tableHeader.setPosition(0, Screens.SCREEN_HEIGHT - tableHeader.getHeight());

		labelScore = new Label("0", Assets.labelStyle);
		tableHeader.add(labelScore).fill();

		addActor(tableHeader);
		buttonRight.addListener(new ClickListener() {
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				speedX = 1;
				buttonRight.setChecked(true);

			};

			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
				speedX = 0;
				buttonRight.setChecked(false);
			};
		});
		buttonLeft.addListener(new ClickListener() {
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				speedX = -1;
				buttonLeft.setChecked(true);
			};

			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
				speedX = 0;
				buttonLeft.setChecked(false);
			};
		});

		buttonSwimUp.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				didSwimUp = true;
			}
		});

		buttonFire.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				didFire = true;
			}
		});
		buttonPause.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				gameScreen.setPaused();
			}
		});

		if (Gdx.app.getType() == ApplicationType.Android || Gdx.app.getType() == ApplicationType.iOS) {
			addActor(buttonRight);
			addActor(buttonLeft);
			addActor(buttonSwimUp);
			addActor(buttonFire);
		}

		addActor(buttonPause);
	}

	@Override
	public void act(float delta) {
		super.act(delta);

		labelScore.setText(gameScreen.punctuation + " m");
	}

	private void addInActions() {
		buttonSwimUp.addAction(Actions.moveTo(692, 10, ANIMATION_TIME));
		buttonFire.addAction(Actions.moveTo(579, 10, ANIMATION_TIME));
		buttonRight.addAction(Actions.moveTo(130, 5, ANIMATION_TIME));
		buttonLeft.addAction(Actions.moveTo(5, 5, ANIMATION_TIME));
		buttonPause.addAction(Actions.moveTo(750, 430, ANIMATION_TIME));
		lifeBar.addAction(Actions.moveTo(20, 440, ANIMATION_TIME));
		energyBar.addAction(Actions.moveTo(20, 395, ANIMATION_TIME));

	}

	private void addOutActions() {
		buttonSwimUp.addAction(Actions.moveTo(692, -105, ANIMATION_TIME));
		buttonFire.addAction(Actions.moveTo(579, -105, ANIMATION_TIME));
		buttonRight.addAction(Actions.moveTo(130, -120, ANIMATION_TIME));
		buttonLeft.addAction(Actions.moveTo(5, -120, ANIMATION_TIME));
		buttonPause.addAction(Actions.moveTo(845, 430, ANIMATION_TIME));
		lifeBar.addAction(Actions.moveTo(-ProgressBarUI.WIDTH, 440, ANIMATION_TIME));
		energyBar.addAction(Actions.moveTo(-ProgressBarUI.WIDTH, 395, ANIMATION_TIME));
	}

	public void show(Stage stage) {
		addInActions();
		stage.addActor(this);
	}

	public void removeWithAnimations() {
		addOutActions();
		addAction(Actions.sequence(Actions.delay(ANIMATION_TIME), Actions.removeActor()));
	}

}
