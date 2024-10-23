package com.nopalsoft.sharkadventure.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.nopalsoft.sharkadventure.Assets;
import com.nopalsoft.sharkadventure.objects.*;
import com.nopalsoft.sharkadventure.screens.Screens;

import java.util.Iterator;

public class WorldRenderer {

    SpriteBatch batcher;
    OrthographicCamera camera;

    WorldGame worldGame;

    Box2DDebugRenderer renderBox;

    public WorldRenderer(SpriteBatch batcher, WorldGame worldGame) {
        this.batcher = batcher;
        this.worldGame = worldGame;
        renderBox = new Box2DDebugRenderer();

        camera = new OrthographicCamera(Screens.WORLD_WIDTH, Screens.WORLD_HEIGHT);
        camera.position.set(Screens.WORLD_WIDTH / 2f, Screens.WORLD_HEIGHT / 2f, 0);

    }

    public void render(float delta) {

        camera.update();
        batcher.setProjectionMatrix(camera.combined);

        batcher.disableBlending();
        batcher.begin();
        drawBackground();
        batcher.end();

        drawSueloAtras(delta);

        batcher.enableBlending();
        batcher.begin();
        drawBackgroundParticles(delta);
        drawTiburon(delta);
        drawBarriles(delta);
        drawSubmarine();
        drawTorpedo(delta);
        drawMinas(delta);
        drawChains(delta);
        drawItems();
        drawBlast();

        batcher.end();
        drawSueloFrente(delta);

    }

    private void drawItems() {
        for (com.nopalsoft.sharkadventure.objects.GameItem obj : worldGame.arrayItems) {
            AtlasRegion keyFrame;
            if (obj.type == com.nopalsoft.sharkadventure.objects.GameItem.TYPE_MEAT)
                keyFrame = Assets.meat;
            else
                keyFrame = Assets.heart;

            batcher.draw(keyFrame, obj.position.x - com.nopalsoft.sharkadventure.objects.GameItem.DRAW_WIDTH / 2f, obj.position.y - com.nopalsoft.sharkadventure.objects.GameItem.DRAW_HEIGHT / 2f, com.nopalsoft.sharkadventure.objects.GameItem.DRAW_WIDTH,
                    com.nopalsoft.sharkadventure.objects.GameItem.DRAW_HEIGHT);
        }

    }

    private void drawSubmarine() {
        for (Submarine obj : worldGame.arraySubmarines) {
            com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion keyFrame;
            switch (obj.type) {
                case Submarine.TYPE_YELLOW:
                    keyFrame = Assets.yellowSubmarine;
                    break;

                default:
                case Submarine.TYPE_RED:
                    keyFrame = Assets.redSubmarine;
                    break;
            }

            if (obj.velocity.x > 0)
                batcher.draw(keyFrame, obj.position.x - Submarine.DRAW_WIDTH / 2f, obj.position.y - Submarine.DRAW_HEIGHT / 2f, Submarine.DRAW_WIDTH,
                        Submarine.DRAW_HEIGHT);
            else
                batcher.draw(keyFrame, obj.position.x + Submarine.DRAW_WIDTH / 2f, obj.position.y - Submarine.DRAW_HEIGHT / 2f,
                        -Submarine.DRAW_WIDTH, Submarine.DRAW_HEIGHT);

            if (obj.state == Submarine.STATE_EXPLODE) {
                drawExplosionSubmarine(obj.position.x - .4f, obj.position.y, obj.explosionStateTimes[0]);
                drawExplosionSubmarine(obj.position.x - .4f, obj.position.y - .4f, obj.explosionStateTimes[1]);
                drawExplosionSubmarine(obj.position.x, obj.position.y, obj.explosionStateTimes[2]);
                drawExplosionSubmarine(obj.position.x + .4f, obj.position.y, obj.explosionStateTimes[3]);
                drawExplosionSubmarine(obj.position.x + .4f, obj.position.y - .4f, obj.explosionStateTimes[4]);
            }
        }

    }

    private void drawExplosionSubmarine(float x, float y, float stateTime) {
        if (stateTime >= 0 && stateTime <= Submarine.EXPLOSION_DURATION) {
            batcher.draw(Assets.explosionAnimation.getKeyFrame(stateTime), x - .2f, y - .2f, .4f, .4f);
        }
    }

    private void drawTorpedo(float delta) {
        for (com.nopalsoft.sharkadventure.objects.Missile obj : worldGame.arrayMissiles) {
            if (obj.state == com.nopalsoft.sharkadventure.objects.Missile.STATE_EXPLODE) {
                batcher.draw(Assets.explosionAnimation.getKeyFrame(obj.stateTime), obj.position.x - .4f, obj.position.y - .4f, .8f, .8f);
            } else if (obj.state == com.nopalsoft.sharkadventure.objects.Missile.STATE_NORMAL) {

                if (obj.isGoingLeft) {
                    batcher.draw(Assets.torpedo, obj.position.x + com.nopalsoft.sharkadventure.objects.Missile.DRAW_WIDTH / 2f, obj.position.y - com.nopalsoft.sharkadventure.objects.Missile.DRAW_HEIGHT / 2f,
                            -com.nopalsoft.sharkadventure.objects.Missile.DRAW_WIDTH, com.nopalsoft.sharkadventure.objects.Missile.DRAW_HEIGHT);

                    Assets.particleEffectTorpedoBubbleRightSide.setPosition(obj.position.x + .34f, obj.position.y - .075f);
                    Assets.particleEffectTorpedoBubbleRightSide.draw(batcher, delta);
                } else {
                    batcher.draw(Assets.torpedo, obj.position.x - com.nopalsoft.sharkadventure.objects.Missile.DRAW_WIDTH / 2f, obj.position.y - com.nopalsoft.sharkadventure.objects.Missile.DRAW_HEIGHT / 2f,
                            com.nopalsoft.sharkadventure.objects.Missile.DRAW_WIDTH, com.nopalsoft.sharkadventure.objects.Missile.DRAW_HEIGHT);

                    Assets.particleEffectTorpedoBubbleLeftSide.setPosition(obj.position.x - .45f, obj.position.y - .075f);
                    Assets.particleEffectTorpedoBubbleLeftSide.draw(batcher, delta);

                }
            }
        }
    }

    private void drawBlast() {
        for (Blast obj : worldGame.arrayBlasts) {
            if (obj.state == Blast.STATE_HIT) {
                batcher.draw(Assets.animationBlastHit.getKeyFrame(obj.stateTime), obj.position.x - .25f, obj.position.y - .25f, .5f, .5f);
            } else {

                if (obj.velocity.x > 0)
                    batcher.draw(Assets.blast, obj.position.x - Blast.DRAW_WIDTH / 2f, obj.position.y - Blast.DRAW_HEIGHT / 2f, Blast.DRAW_WIDTH,
                            Blast.DRAW_HEIGHT);
                else
                    batcher.draw(Assets.blast, obj.position.x + Blast.DRAW_WIDTH / 2f, obj.position.y - Blast.DRAW_HEIGHT / 2f, -Blast.DRAW_WIDTH,
                            Blast.DRAW_HEIGHT);
            }
        }

    }

    private void drawBackgroundParticles(float delta) {
        Assets.particleEffectFish.setPosition(0, 0);
        Assets.particleEffectFish.draw(batcher, delta);

        Assets.particleEffectMediumFish.setPosition(Screens.WORLD_WIDTH, 0);
        Assets.particleEffectMediumFish.draw(batcher, delta);

    }

    private void drawSueloAtras(float delta) {
        Assets.parallaxBack.render(delta);
    }

    private void drawBackground() {
        batcher.draw(Assets.background, 0, 0, Screens.WORLD_WIDTH, Screens.WORLD_HEIGHT);
    }

    private void drawSueloFrente(float delta) {
        Assets.parallaxFront.render(delta);

    }

    private void drawBarriles(float delta) {

        Assets.particleEffectBubble.update(delta);

        Iterator<com.nopalsoft.sharkadventure.objects.Barrel> i = worldGame.arrayBarrels.iterator();
        while (i.hasNext()) {
            com.nopalsoft.sharkadventure.objects.Barrel obj = i.next();
            TextureRegion keyframe = null;

            if (obj.state == com.nopalsoft.sharkadventure.objects.Barrel.STATE_EXPLODE) {
                keyframe = Assets.explosionAnimation.getKeyFrame(obj.stateTime);
                batcher.draw(keyframe, obj.position.x - .4f, obj.position.y - .4f, .8f, .8f);
            } else if (obj.state == com.nopalsoft.sharkadventure.objects.Barrel.STATE_NORMAL) {

                switch (obj.type) {
                    case com.nopalsoft.sharkadventure.objects.Barrel.TYPE_YELLOW:
                        keyframe = Assets.yellowBarrel;
                        break;
                    case com.nopalsoft.sharkadventure.objects.Barrel.TYPE_BLACK:
                        keyframe = Assets.blackBarrel;
                        break;
                    case com.nopalsoft.sharkadventure.objects.Barrel.TYPE_RED:
                        keyframe = Assets.redBarrel;
                        break;
                    default:
                    case com.nopalsoft.sharkadventure.objects.Barrel.TYPE_GREEN:
                        keyframe = Assets.greenBarrel;
                        break;
                }

                batcher.draw(keyframe, obj.position.x - com.nopalsoft.sharkadventure.objects.Barrel.DRAW_WIDTH / 2f, obj.position.y - com.nopalsoft.sharkadventure.objects.Barrel.DRAW_HEIGHT / 2f, com.nopalsoft.sharkadventure.objects.Barrel.DRAW_WIDTH / 2f,
                        com.nopalsoft.sharkadventure.objects.Barrel.DRAW_HEIGHT / 2f, com.nopalsoft.sharkadventure.objects.Barrel.DRAW_WIDTH, com.nopalsoft.sharkadventure.objects.Barrel.DRAW_HEIGHT, 1, 1, obj.angleDegree);

                Assets.particleEffectBubble.setPosition(obj.position.x, obj.position.y);
                Assets.particleEffectBubble.draw(batcher);
            }
        }

    }

    private void drawMinas(float delta) {
        Iterator<com.nopalsoft.sharkadventure.objects.Mine> i = worldGame.arrayMines.iterator();
        while (i.hasNext()) {
            com.nopalsoft.sharkadventure.objects.Mine obj = i.next();
            TextureRegion keyframe = null;

            if (obj.state == com.nopalsoft.sharkadventure.objects.Mine.STATE_EXPLODE) {
                keyframe = Assets.explosionAnimation.getKeyFrame(obj.stateTime);
                batcher.draw(keyframe, obj.position.x - .3f, obj.position.y - .3f, .6f, .6f);
            } else if (obj.state == com.nopalsoft.sharkadventure.objects.Mine.STATE_NORMAL) {

                switch (obj.type) {
                    case com.nopalsoft.sharkadventure.objects.Mine.TYPE_GRAY:
                        keyframe = Assets.grayMine;
                        break;
                    case com.nopalsoft.sharkadventure.objects.Mine.TYPE_OXIDE:
                    default:
                        keyframe = Assets.oxideMine;
                        break;
                }

                batcher.draw(keyframe, obj.position.x - com.nopalsoft.sharkadventure.objects.Mine.DRAW_WIDTH / 2f, obj.position.y - com.nopalsoft.sharkadventure.objects.Mine.DRAW_HEIGHT / 2f, com.nopalsoft.sharkadventure.objects.Mine.DRAW_WIDTH / 2f,
                        com.nopalsoft.sharkadventure.objects.Mine.DRAW_HEIGHT / 2f, com.nopalsoft.sharkadventure.objects.Mine.DRAW_WIDTH, com.nopalsoft.sharkadventure.objects.Mine.DRAW_HEIGHT, 1, 1, 0);
            }
        }

    }

    private void drawChains(float delta) {
        Iterator<Chain> i = worldGame.arrayChains.iterator();
        while (i.hasNext()) {
            Chain obj = i.next();

            batcher.draw(Assets.chain, obj.position.x - Chain.DRAW_WIDTH / 2f, obj.position.y - Chain.DRAW_HEIGHT / 2f, Chain.DRAW_WIDTH / 2f,
                    Chain.DRAW_HEIGHT / 2f, Chain.DRAW_WIDTH, Chain.DRAW_HEIGHT, 1, 1, obj.angleDegree);

        }

    }

    private void drawTiburon(float delta) {
        com.nopalsoft.sharkadventure.objects.Shark obj = worldGame.shark;

        TextureRegion keyframe = null;

        if (obj.state == com.nopalsoft.sharkadventure.objects.Shark.STATE_DEAD) {
            keyframe = Assets.SharkDead;
        } else if (obj.isFiring) {// Disparar sobreescribe todo lo demas
            keyframe = Assets.animationSharkFire.getKeyFrame(obj.stateTime);
        } else if (obj.isTurbo) {
            keyframe = Assets.animationSharkMove.getKeyFrame(obj.stateTime, true);
        } else
            keyframe = Assets.animationSharkSwim.getKeyFrame(obj.stateTime, true);

        if (obj.isTurbo) {
            batcher.draw(Assets.TurboTail, obj.position.x - 1f, obj.position.y - .27f, .96f, .48f);
        }

        if (obj.isFacingLeft) {
            batcher.draw(keyframe, obj.position.x + .6f, obj.position.y - .39f, -.6f, .39f, -1.2f, .78f, 1, 1, obj.angleDegree);

        } else {
            batcher.draw(keyframe, obj.position.x - .6f, obj.position.y - .39f, .6f, .39f, 1.2f, .78f, 1, 1, obj.angleDegree);
        }

        Assets.particleEffectSharkBubble.setPosition(obj.position.x, obj.position.y);
        Assets.particleEffectSharkBubble.draw(batcher, delta);

    }
}
