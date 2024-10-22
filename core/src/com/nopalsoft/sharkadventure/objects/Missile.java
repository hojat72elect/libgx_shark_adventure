package com.nopalsoft.sharkadventure.objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.nopalsoft.sharkadventure.Assets;
import com.nopalsoft.sharkadventure.Settings;
import com.nopalsoft.sharkadventure.screens.Screens;

public class Missile implements Poolable {
    public final static int STATE_NORMAL = 0;
    public final static int STATE_EXPLODE = 1;
    public final static int STATE_REMOVE = 2;
    public int state;

    public final static float EXPLOSION_DURATION = .1f * 8f;

    public final static float SPEED_X = 1.7f;
    public final static float DRAW_WIDTH = .8f;
    public final static float DRAW_HEIGHT = .3f;

    final public Vector2 position;
    public float stateTime;
    public boolean isGoingLeft;

    public Missile() {
        position = new Vector2();
    }

    public void initialize(float x, float y, boolean isGoingLeft) {
        position.set(x, y);
        stateTime = 0;
        state = STATE_NORMAL;
        this.isGoingLeft = isGoingLeft;
    }

    public void update(Body body, float delta) {
        if (state == STATE_NORMAL) {
            position.x = body.getPosition().x;
            position.y = body.getPosition().y;

            if (position.y < -3 || position.x > Screens.WORLD_WIDTH + 3)
                hit();
        } else if (state == STATE_EXPLODE && stateTime >= EXPLOSION_DURATION) {
            state = STATE_REMOVE;
            stateTime = 0;
        }

        stateTime += delta;

    }

    public void hit() {
        if (state == STATE_NORMAL) {
            state = STATE_EXPLODE;
            stateTime = 0;
            if (Settings.isSoundOn) {
                Assets.playExplosionSound();
            }
        }
    }

    @Override
    public void reset() {
    }

}
