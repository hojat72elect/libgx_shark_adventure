package com.nopalsoft.sharkadventure.objects;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.nopalsoft.sharkadventure.Assets;
import com.nopalsoft.sharkadventure.Settings;

public class Shark implements Poolable {
    public final static int STATE_NORMAL = 0;
    public final static int STATE_DEAD = 1;
    public final static int MAX_LIFE = 5;
    public final static int MAX_ENERGY = 50;
    public final static float DURATION_TURBO = 3;
    public final static float DURATION_FIRING = .075f * 5;
    public final float TIME_TO_RECHARGE_ENERGY = 1.25f;
    final public Vector2 position;
    final float SPEED_X = 3.5f;
    final float SPEED_Y = 1.85f;
    public int state;
    public int life;
    public float energy;
    public Vector2 speed;
    public float angleDegree;
    public float stateTime;
    public boolean isTurbo;
    public boolean isFacingLeft;
    public boolean isFiring;
    public boolean didGetHurtOnce;

    public boolean didFlipX; // Indicates if an X flip has just been performed to recreate the body
    float durationTurbo;
    float timeToRechargeEnergy;
    boolean setSpeedDie;

    public Shark(float x, float y) {
        position = new Vector2(x, y);
        speed = new Vector2();

        stateTime = 0;
        state = STATE_NORMAL;

        life = MAX_LIFE;
        energy = MAX_ENERGY;

        setSpeedDie = true;
        didGetHurtOnce = false;

    }

    public void updateStateTime(float delta) {
        stateTime += delta;
    }

    public void update(Body body, float delta, float accelX, boolean didSwimUp) {
        position.x = body.getPosition().x;
        position.y = body.getPosition().y;

        speed = body.getLinearVelocity();

        if (state == STATE_NORMAL) {

            if (isTurbo) {
                durationTurbo += delta;
                if (durationTurbo >= DURATION_TURBO) {
                    durationTurbo = 0;
                    isTurbo = false;
                }
            }

            if (isFiring) {
                if (stateTime >= DURATION_FIRING) {
                    stateTime = 0;
                    isFiring = false;
                }
            }

            speed.x = accelX * SPEED_X;

            if (speed.x > 0 && isFacingLeft) {
                didFlipX = true;
                isFacingLeft = false;
            } else if (speed.x < 0 && !isFacingLeft) {
                didFlipX = true;
                isFacingLeft = true;
            }

            if (didSwimUp) {
                speed.y = SPEED_Y;

                if (Settings.isSoundOn)
                    Assets.soundSwim.play();
            }

            timeToRechargeEnergy += delta;
            if (timeToRechargeEnergy >= TIME_TO_RECHARGE_ENERGY) {
                timeToRechargeEnergy -= TIME_TO_RECHARGE_ENERGY;
                energy += 1.5f;
                if (energy > MAX_ENERGY)
                    energy = MAX_ENERGY;
            }

        } else {

            if (setSpeedDie) {
                speed.set(0, 0);
                setSpeedDie = false;
            }

            body.setGravityScale(-.15f);
            body.setAngularVelocity(MathUtils.degreesToRadians * 90);
            angleDegree = MathUtils.radDeg * body.getAngle();

            if (angleDegree >= 180) {
                body.setAngularVelocity(0);
                angleDegree = 180;

            }

        }

        body.setLinearVelocity(speed);
        stateTime += delta;

    }

    public void fire() {
        if (state == STATE_NORMAL) {
            isFiring = true;
            stateTime = 0;
            energy -= 1.25f;
            if (energy < 0)
                energy = 0;
        }
    }

    public void hit() {
        if (state == STATE_NORMAL) {
            life--;
            if (life == 0) {
                state = STATE_DEAD;
                stateTime = 0;

            }
            didGetHurtOnce = true;
        }
    }

    @Override
    public void reset() {
    }
}
