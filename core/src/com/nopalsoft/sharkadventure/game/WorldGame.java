package com.nopalsoft.sharkadventure.game;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pools;
import com.nopalsoft.sharkadventure.Achievements;
import com.nopalsoft.sharkadventure.Assets;
import com.nopalsoft.sharkadventure.Settings;
import com.nopalsoft.sharkadventure.objects.Blast;
import com.nopalsoft.sharkadventure.objects.Chain;
import com.nopalsoft.sharkadventure.objects.Items;
import com.nopalsoft.sharkadventure.screens.Screens;
import java.util.Iterator;
import com.nopalsoft.sharkadventure.objects.Submarine;
import com.nopalsoft.sharkadventure.objects.Missile;
import com.nopalsoft.sharkadventure.objects.Barrel;
import com.nopalsoft.sharkadventure.objects.Mine;
import com.nopalsoft.sharkadventure.objects.Shark;

public class WorldGame {

    static final int STATE_RUNNING = 0;
    static final int STATE_GAME_OVER = 1;

    static final float TIME_TO_SPAWN_BARREL = 5;
    static final float TIME_TO_SPAWN_MINE = 5;
    static final float TIME_TO_SPAWN_MINE_CHAIN = 7;
    static final float TIME_TO_SPAWN_SUBMARINE = 15;
    static final float TIME_TO_SPAWN_ITEMS = 10;

    public int state;
    float TIME_TO_GAME_OVER = 2f;
    float timeToGameOver;
    float timeToSpawnBarrel;
    float timeToSpawnMine;
    float timeToSpawnMineChain;
    float timeToSpawnSubmarine;
    float timeToSpawnItems;

    World world;
    Shark shark;

    Array<Barrel> arrayBarrels;
    Array<Body> arrayBodies;
    Array<Mine> arrayMines;
    Array<Chain> arrayChains;
    Array<Blast> arrayBlasts;
    Array<Missile> arrayMissiles;
    Array<Submarine> arraySubmarines;
    Array<Items> arrayItems;

    double score;

    int destroyedSubmarines;

    public WorldGame() {
        world = new World(new Vector2(0, -4f), true);
        world.setContactListener(new Colisiones());

        state = STATE_RUNNING;
        timeToGameOver = 0;
        score = 0;
        destroyedSubmarines = 0;

        arrayBodies = new Array<>();
        arrayBarrels = new Array<>();
        arrayMines = new Array<>();
        arrayChains = new Array<>();
        arrayBlasts = new Array<>();
        arrayMissiles = new Array<>();
        arraySubmarines = new Array<>();
        arrayItems = new Array<>();
        shark = new Shark(3.5f, 2f);

        timeToSpawnBarrel = 0;
        createMineChain();
        createWalls();
        createCharacter(false);

    }

    private void createWalls() {
        BodyDef bd = new BodyDef();
        bd.type = BodyType.StaticBody;

        Body body = world.createBody(bd);

        EdgeShape shape = new EdgeShape();

        // Below
        shape.set(0, 0, Screens.WORLD_WIDTH, 0);
        body.createFixture(shape, 0);

        // Right
        shape.set(Screens.WORLD_WIDTH + .5f, 0, Screens.WORLD_WIDTH + .5f, Screens.WORLD_HEIGHT);
        body.createFixture(shape, 0);

        // Above
        shape.set(0, Screens.WORLD_HEIGHT + .5f, Screens.WORLD_WIDTH, Screens.WORLD_HEIGHT + .5f);
        body.createFixture(shape, 0);

        // Left
        shape.set(-.5f, 0, -.5f, Screens.WORLD_HEIGHT);
        body.createFixture(shape, 0);

        for (Fixture fix : body.getFixtureList()) {
            fix.setFriction(0);
            Filter filterData = new Filter();
            filterData.groupIndex = -1;
            fix.setFilterData(filterData);
        }

        body.setUserData("piso");

        shape.dispose();

    }

    private void createCharacter(boolean isFacingLeft) {

        BodyDef bd = new BodyDef();
        bd.position.set(shark.position.x, shark.position.y);
        bd.type = BodyType.DynamicBody;

        Body body = world.createBody(bd);
        PolygonShape shape = new PolygonShape();

        if (isFacingLeft) {
            shape.set(new float[]{.05f, .34f, -.12f, .18f, .13f, .19f, .18f, .37f});
            body.createFixture(shape, 0);

            shape.set(new float[]{-.12f, .18f, -.40f, .09f, -.40f, -.18f, -.25f, -.37f, .29f, -.39f, .36f, -.19f, .27f, -.03f, .13f, .19f});
            body.createFixture(shape, 0);

            shape.set(new float[]{.59f, .12f, .43f, -.06f, .36f, -.19f, .52f, -.33f});
            body.createFixture(shape, 0);

            shape.set(new float[]{-.40f, -.18f, -.40f, .09f, -.58f, -.05f, -.59f, -.12f});
            body.createFixture(shape, 0);

            shape.set(new float[]{.36f, -.19f, .29f, -.39f, .33f, -.34f});
            body.createFixture(shape, 0);

            shape.set(new float[]{.36f, -.19f, .43f, -.06f, .27f, -.03f});
            body.createFixture(shape, 0);
        } else {
            shape.set(new float[]{-.13f, .19f, .12f, .18f, -.05f, .34f, -.18f, .37f});
            body.createFixture(shape, 0);

            shape.set(new float[]{-.27f, -.03f, -.36f, -.19f, -.29f, -.39f, .25f, -.37f, .40f, -.18f, .40f, .09f, .12f, .18f, -.13f, .19f});
            body.createFixture(shape, 0);

            shape.set(new float[]{-.36f, -.19f, -.43f, -.06f, -.59f, .12f, -.52f, -.33f});
            body.createFixture(shape, 0);

            shape.set(new float[]{.58f, -.05f, .40f, .09f, .40f, -.18f, .59f, -.12f});
            body.createFixture(shape, 0);

            shape.set(new float[]{-.29f, -.39f, -.36f, -.19f, -.33f, -.34f});
            body.createFixture(shape, 0);

            shape.set(new float[]{-.43f, -.06f, -.36f, -.19f, -.27f, -.03f});
            body.createFixture(shape, 0);
        }

        body.setUserData(shark);
        body.setFixedRotation(true);
        body.setGravityScale(.45f);

        shape.dispose();
    }

    private void crearBarril(float x, float y) {
        Barrel obj = Pools.obtain(Barrel.class);
        obj.initialize(x, y);

        BodyDef bd = new BodyDef();
        bd.position.set(obj.position.x, obj.position.y);
        bd.type = BodyType.DynamicBody;

        Body body = world.createBody(bd);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(Barrel.WIDTH / 2f, Barrel.HEIGHT / 2f);

        FixtureDef fixture = new FixtureDef();
        fixture.shape = shape;
        fixture.isSensor = true;

        body.createFixture(fixture);
        body.setUserData(obj);
        body.setFixedRotation(true);
        body.setGravityScale(.15f);
        body.setAngularVelocity(MathUtils.degRad * MathUtils.random(-50, 50));

        arrayBarrels.add(obj);
        shape.dispose();
    }

    private void createItem() {
        Items obj = Pools.obtain(Items.class);
        obj.initialize(Screens.WORLD_WIDTH + 1, MathUtils.random(Screens.WORLD_HEIGHT));

        BodyDef bd = new BodyDef();
        bd.position.set(obj.position.x, obj.position.y);
        bd.type = BodyType.KinematicBody;

        Body body = world.createBody(bd);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(Items.WIDTH / 2f, Items.HEIGHT / 2f);

        FixtureDef fixture = new FixtureDef();
        fixture.shape = shape;
        fixture.isSensor = true;

        body.createFixture(fixture);
        body.setUserData(obj);
        body.setFixedRotation(true);
        body.setLinearVelocity(Items.SPEED_X, 0);

        arrayItems.add(obj);
        shape.dispose();
    }

    private void createBlast() {
        float velX = Blast.SPEED_X;
        float x = shark.position.x + .3f;

        if (shark.isFacingLeft) {
            velX = -Blast.SPEED_X;
            x = shark.position.x - .3f;
        }
        Blast obj = Pools.obtain(Blast.class);

        obj.initialize(x, shark.position.y - .15f);

        BodyDef bd = new BodyDef();
        bd.position.set(obj.position.x, obj.position.y);
        bd.type = BodyType.KinematicBody;

        Body body = world.createBody(bd);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(Blast.WIDTH / 2f, Blast.HEIGHT / 2f);

        FixtureDef fixture = new FixtureDef();
        fixture.shape = shape;
        fixture.isSensor = true;

        body.setBullet(true);
        body.createFixture(fixture);
        body.setUserData(obj);
        body.setFixedRotation(true);
        body.setLinearVelocity(velX, 0);

        arrayBlasts.add(obj);
        shape.dispose();
    }

    private void crearTorpedo(float x, float y, boolean goLeft) {
        float velX = com.nopalsoft.sharkadventure.objects.Missile.SPEED_X;
        if (goLeft) {
            velX = -com.nopalsoft.sharkadventure.objects.Missile.SPEED_X;
        }
        com.nopalsoft.sharkadventure.objects.Missile obj = Pools.obtain(com.nopalsoft.sharkadventure.objects.Missile.class);
        obj.initialize(x, y, goLeft);

        BodyDef bd = new BodyDef();
        bd.position.set(obj.position.x, obj.position.y);
        bd.type = BodyType.DynamicBody;

        Body body = world.createBody(bd);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(Blast.WIDTH / 2f, Blast.HEIGHT / 2f);

        FixtureDef fixutre = new FixtureDef();
        fixutre.shape = shape;
        fixutre.isSensor = true;

        body.createFixture(fixutre);
        body.setUserData(obj);
        body.setGravityScale(0);
        body.setFixedRotation(true);
        body.setLinearVelocity(velX, 0);

        arrayMissiles.add(obj);
        shape.dispose();
    }

    private void crearMina(float x, float y) {
        com.nopalsoft.sharkadventure.objects.Mine obj = Pools.obtain(com.nopalsoft.sharkadventure.objects.Mine.class);
        obj.initialize(x, y);

        BodyDef bd = new BodyDef();
        bd.position.set(obj.position.x, obj.position.y);
        bd.type = BodyType.DynamicBody;

        Body body = world.createBody(bd);

        CircleShape shape = new CircleShape();
        shape.setRadius(com.nopalsoft.sharkadventure.objects.Mine.WIDTH / 2f);

        FixtureDef fixutre = new FixtureDef();
        fixutre.shape = shape;
        fixutre.isSensor = true;

        body.createFixture(fixutre);
        body.setUserData(obj);
        body.setFixedRotation(true);
        body.setGravityScale(0);
        body.setLinearVelocity(com.nopalsoft.sharkadventure.objects.Mine.SPEED_X, 0);

        arrayMines.add(obj);
        shape.dispose();
    }

    private void createSubmarine() {
       Submarine obj = Pools.obtain(com.nopalsoft.sharkadventure.objects.Submarine.class);
        float x, y, xTarget, yTarget;
        switch (MathUtils.random(1, 4)) {
            case 1:
                x = -1;
                y = -1;
                xTarget = Screens.WORLD_WIDTH + 6;
                yTarget = Screens.WORLD_HEIGHT + 6;
                break;
            case 2:
                x = -1;
                y = Screens.WORLD_HEIGHT + 1;
                xTarget = Screens.WORLD_WIDTH + 6;
                yTarget = -6;
                break;
            case 3:
                x = Screens.WORLD_WIDTH + 1;
                y = Screens.WORLD_HEIGHT + 1;
                xTarget = -6;
                yTarget = -6;
                break;
            case 4:
            default:
                x = Screens.WORLD_WIDTH + 1;
                y = -1;
                xTarget = -6;
                yTarget = Screens.WORLD_HEIGHT + 6;
                break;
        }

        obj.initialize(x, y, xTarget, yTarget);

        BodyDef bd = new BodyDef();
        bd.position.set(obj.position.x, obj.position.y);
        bd.type = BodyType.DynamicBody;

        Body body = world.createBody(bd);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(com.nopalsoft.sharkadventure.objects.Submarine.WIDTH / 2f, com.nopalsoft.sharkadventure.objects.Submarine.HEIGHT / 2f);

        FixtureDef fixutre = new FixtureDef();
        fixutre.shape = shape;
        fixutre.isSensor = true;

        body.createFixture(fixutre);
        body.setUserData(obj);
        body.setFixedRotation(true);
        body.setGravityScale(0);
        arraySubmarines.add(obj);
        shape.dispose();
    }

    private void createMineChain() {
        float x = 10;
       Mine obj = Pools.obtain(Mine.class);
        obj.initialize(x, 1);
        obj.type = Mine.TYPE_GRAY;

        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(obj.position.x, obj.position.y);
        bodyDef.type = BodyType.DynamicBody;

        Body body = world.createBody(bodyDef);

        CircleShape shape = new CircleShape();
        shape.setRadius(Mine.WIDTH / 2f);

        FixtureDef fixture = new FixtureDef();
        fixture.shape = shape;
        fixture.isSensor = true;
        fixture.density = 1;

        body.createFixture(fixture);
        body.setUserData(obj);
        body.setFixedRotation(true);
        body.setGravityScale(-3.5f);

        arrayMines.add(obj);
        shape.dispose();

        PolygonShape chainShape = new PolygonShape();
        chainShape.setAsBox(Chain.WIDTH / 2f, Chain.HEIGHT / 2f);

        fixture.isSensor = false;
        fixture.shape = chainShape;
        fixture.filter.groupIndex = -1;

        int numLinks = MathUtils.random(5, 15);
        Body link = null;
        for (int i = 0; i < numLinks; i++) {
            Chain objChain = Pools.obtain(Chain.class);
            objChain.initialize(x, Chain.HEIGHT * i);
            bodyDef.position.set(objChain.position.x, objChain.position.y);
            if (i == 0) {
                objChain.initialize(x, -.12f);// It makes the kinematic body appear a little lower so as not to be colliding with it.
                bodyDef.position.set(objChain.position.x, objChain.position.y);
                bodyDef.type = BodyType.KinematicBody;
                link = world.createBody(bodyDef);
                link.createFixture(fixture);
                link.setLinearVelocity(Chain.SPEED_X, 0);

            } else {
                bodyDef.type = BodyType.DynamicBody;
                Body newLink = world.createBody(bodyDef);
                newLink.createFixture(fixture);
                createRotationJoint(link, newLink, -Chain.HEIGHT / 2f);
                link = newLink;
            }
            arrayChains.add(objChain);
            link.setUserData(objChain);
        }

        createRotationJoint(link, body, -com.nopalsoft.sharkadventure.objects.Mine.HEIGHT / 2f);

    }

    private void createRotationJoint(Body bodyA, Body bodyB, float anchorBY) {
        RevoluteJointDef jointDef = new RevoluteJointDef();
        jointDef.localAnchorA.set((float) 0, (float) 0.105);
        jointDef.localAnchorB.set((float) 0, anchorBY);
        jointDef.bodyA = bodyA;
        jointDef.bodyB = bodyB;
        world.createJoint(jointDef);
    }

    public void update(float delta, float accelX, boolean didSwimUp, boolean didFire) {
        world.step(delta, 8, 4);

        eliminarObjetos();

        timeToSpawnBarrel += delta;
        if (timeToSpawnBarrel >= TIME_TO_SPAWN_BARREL) {
            timeToSpawnBarrel -= TIME_TO_SPAWN_BARREL;

            if (MathUtils.randomBoolean()) {
                for (int i = 0; i < 6; i++) {
                    crearBarril(MathUtils.random(Screens.WORLD_WIDTH), MathUtils.random(5.5f, 8.5f));
                }
            }
        }

        timeToSpawnMine += delta;
        if (timeToSpawnMine >= TIME_TO_SPAWN_MINE) {
            timeToSpawnMine -= TIME_TO_SPAWN_MINE;
            for (int i = 0; i < 5; i++) {
                if (MathUtils.randomBoolean())
                    crearMina(MathUtils.random(9, 10f), MathUtils.random(Screens.WORLD_HEIGHT));
            }
        }

        if (arrayMines.size == 0)
            crearMina(9, MathUtils.random(Screens.WORLD_HEIGHT));

        timeToSpawnMineChain += delta;
        if (timeToSpawnMineChain >= TIME_TO_SPAWN_MINE_CHAIN) {
            timeToSpawnMineChain -= TIME_TO_SPAWN_MINE_CHAIN;
            if (MathUtils.randomBoolean(.75f))
                createMineChain();
        }

        timeToSpawnSubmarine += delta;
        if (timeToSpawnSubmarine >= TIME_TO_SPAWN_SUBMARINE) {
            timeToSpawnSubmarine -= TIME_TO_SPAWN_SUBMARINE;
            if (MathUtils.randomBoolean(.65f)) {
                createSubmarine();
                if (Settings.isSoundOn)
                    Assets.soundSonar.play();
            }
        }

        timeToSpawnItems += delta;
        if (timeToSpawnItems >= TIME_TO_SPAWN_ITEMS) {
            timeToSpawnItems -= TIME_TO_SPAWN_ITEMS;
            if (MathUtils.randomBoolean()) {
                createItem();
            }
        }

        world.getBodies(arrayBodies);

        Iterator<Body> i = arrayBodies.iterator();
        while (i.hasNext()) {
            Body body = i.next();

            if (body.getUserData() instanceof Shark) {
                updatePersonaje(body, delta, accelX, didSwimUp, didFire);
            } else if (body.getUserData() instanceof Barrel) {
                updateBarrels(body, delta);
            } else if (body.getUserData() instanceof Mine) {
                updateMine(body, delta);
            } else if (body.getUserData() instanceof Chain) {
                updateChain(body);
            } else if (body.getUserData() instanceof Blast) {
                updateBlast(body, delta);
            } else if (body.getUserData() instanceof Missile) {
                updateMissile(body, delta);
            } else if (body.getUserData() instanceof Submarine) {
                updateSubmarine(body, delta);
            } else if (body.getUserData() instanceof Items) {
                updateItems(body);
            }

        }

        if (shark.state == Shark.STATE_DEAD) {
            timeToGameOver += delta;
            if (timeToGameOver >= TIME_TO_GAME_OVER) {
                state = STATE_GAME_OVER;
            }
        } else {
            score += delta * 15;
        }

        Achievements.distance((long) score, shark.didGetHurtOnce);

    }

    private void eliminarObjetos() {
        Iterator<Body> i = arrayBodies.iterator();
        while (i.hasNext()) {
            Body body = i.next();

            if (!world.isLocked()) {
                if (body.getUserData() instanceof Barrel) {
                    Barrel obj = (Barrel) body.getUserData();
                    if (obj.state == Barrel.STATE_REMOVE) {
                        arrayBarrels.removeValue(obj, true);
                        Pools.free(obj);
                        world.destroyBody(body);
                    }
                } else if (body.getUserData() instanceof Mine) {
                    Mine obj = (Mine) body.getUserData();
                    if (obj.state == Mine.STATE_REMOVE) {
                        arrayMines.removeValue(obj, true);
                        Pools.free(obj);
                        world.destroyBody(body);
                    }
                } else if (body.getUserData() instanceof Chain) {
                    Chain obj = (Chain) body.getUserData();
                    if (obj.state == Chain.STATE_REMOVE) {
                        arrayChains.removeValue(obj, true);
                        Pools.free(obj);
                        world.destroyBody(body);

                    }
                } else if (body.getUserData() instanceof Blast) {
                    Blast obj = (Blast) body.getUserData();
                    if (obj.state == Blast.STATE_REMOVE) {
                        arrayBlasts.removeValue(obj, true);
                        Pools.free(obj);
                        world.destroyBody(body);
                    }
                } else if (body.getUserData() instanceof Missile) {
                    Missile obj = (Missile) body.getUserData();
                    if (obj.state == Missile.STATE_REMOVE) {
                        arrayMissiles.removeValue(obj, true);
                        Pools.free(obj);
                        world.destroyBody(body);
                    }
                } else if (body.getUserData() instanceof Submarine) {
                    Submarine obj = (Submarine) body.getUserData();
                    if (obj.state == Submarine.STATE_REMOVE) {
                        arraySubmarines.removeValue(obj, true);
                        Pools.free(obj);
                        world.destroyBody(body);
                    }
                } else if (body.getUserData() instanceof Items) {
                    Items obj = (Items) body.getUserData();
                    if (obj.state == Items.STATE_REMOVE) {
                        arrayItems.removeValue(obj, true);
                        Pools.free(obj);
                        world.destroyBody(body);
                    }
                }

            }

        }
    }

    private void updatePersonaje(Body body, float delta, float accelX, boolean didSwimUp, boolean didFire) {
        // If I change position I have to do the body again.
        if (shark.didFlipX) {
            shark.didFlipX = false;
            world.destroyBody(body);
            createCharacter(shark.isFacingLeft);
        }

        if (didFire && shark.state == Shark.STATE_NORMAL) {
            if (shark.energy > 0) {
                createBlast();
                if (Settings.isSoundOn) {
                    Assets.soundBlast.play();
                }
            }
            shark.fire();

        }

        shark.update(body, delta, accelX, didSwimUp);

    }

    private void updateBarrels(Body body, float delta) {
        Barrel obj = (Barrel) body.getUserData();
        obj.update(body, delta);

    }

    private void updateMine(Body body, float delta) {
        Mine obj = (Mine) body.getUserData();
        obj.update(body, delta);

    }

    private void updateChain(Body body) {
        Chain obj = (Chain) body.getUserData();
        obj.update(body);

    }

    private void updateBlast(Body body, float delta) {
        Blast obj = (Blast) body.getUserData();
        obj.update(body, delta);

    }

    private void updateMissile(Body body, float delta) {
        com.nopalsoft.sharkadventure.objects.Missile obj = (com.nopalsoft.sharkadventure.objects.Missile) body.getUserData();
        obj.update(body, delta);
    }

    private void updateSubmarine(Body body, float delta) {
        com.nopalsoft.sharkadventure.objects.Submarine obj = (com.nopalsoft.sharkadventure.objects.Submarine) body.getUserData();
        obj.update(body, delta);

        if (obj.didFire) {
            obj.didFire = false;

            if (obj.velocity.x > 0)
                crearTorpedo(obj.position.x, obj.position.y, false);
            else
                crearTorpedo(obj.position.x, obj.position.y, true);
        }

    }

    private void updateItems(Body body) {
        Items obj = (Items) body.getUserData();
        obj.update(body);
    }

    class Colisiones implements ContactListener {

        @Override
        public void beginContact(Contact contact) {
            Fixture a = contact.getFixtureA();
            Fixture b = contact.getFixtureB();

            if (a.getBody().getUserData() instanceof com.nopalsoft.sharkadventure.objects.Shark) {
                beginContactTiburon(a, b);
            } else if (b.getBody().getUserData() instanceof com.nopalsoft.sharkadventure.objects.Shark) {
                beginContactTiburon(b, a);
            } else if (a.getBody().getUserData() instanceof Blast) {
                beginContactBlast(a, b);
            } else if (b.getBody().getUserData() instanceof Blast) {
                beginContactBlast(b, a);
            } else {
                beginContactOtrasCosas(a, b);
            }

        }

        private void beginContactBlast(Fixture fixBlast, Fixture fixOtraCosa) {
            Object otraCosa = fixOtraCosa.getBody().getUserData();
            Blast oBlast = (Blast) fixBlast.getBody().getUserData();

            if (otraCosa instanceof com.nopalsoft.sharkadventure.objects.Barrel) {
                com.nopalsoft.sharkadventure.objects.Barrel obj = (com.nopalsoft.sharkadventure.objects.Barrel) otraCosa;
                if (obj.state == com.nopalsoft.sharkadventure.objects.Barrel.STATE_NORMAL) {
                    obj.hit();
                    oBlast.hit();

                }

            } else if (otraCosa instanceof com.nopalsoft.sharkadventure.objects.Mine) {
                com.nopalsoft.sharkadventure.objects.Mine obj = (com.nopalsoft.sharkadventure.objects.Mine) otraCosa;
                if (obj.state == com.nopalsoft.sharkadventure.objects.Mine.STATE_NORMAL) {
                    obj.hit();
                    oBlast.hit();

                }
            } else if (otraCosa instanceof Chain) {
                Chain obj = (Chain) otraCosa;
                if (obj.state == Chain.STATE_NORMAL) {
                    obj.hit();
                    oBlast.hit();
                }
            } else if (otraCosa instanceof com.nopalsoft.sharkadventure.objects.Submarine) {
                com.nopalsoft.sharkadventure.objects.Submarine obj = (com.nopalsoft.sharkadventure.objects.Submarine) otraCosa;
                if (obj.state == com.nopalsoft.sharkadventure.objects.Submarine.STATE_NORMAL) {
                    obj.hit();
                    oBlast.hit();

                    if (obj.state == com.nopalsoft.sharkadventure.objects.Submarine.STATE_EXPLODE) {
                        destroyedSubmarines++;
                        Achievements.unlockKilledSubmarines(destroyedSubmarines);
                    }

                }
            }

        }

        private void beginContactTiburon(Fixture fixTiburon, Fixture fixOtraCosa) {
            Object otraCosa = fixOtraCosa.getBody().getUserData();

            if (otraCosa instanceof com.nopalsoft.sharkadventure.objects.Barrel) {
                com.nopalsoft.sharkadventure.objects.Barrel obj = (com.nopalsoft.sharkadventure.objects.Barrel) otraCosa;
                if (obj.state == com.nopalsoft.sharkadventure.objects.Barrel.STATE_NORMAL) {
                    obj.hit();
                    shark.hit();
                }
            } else if (otraCosa instanceof com.nopalsoft.sharkadventure.objects.Mine) {
                com.nopalsoft.sharkadventure.objects.Mine obj = (com.nopalsoft.sharkadventure.objects.Mine) otraCosa;
                if (obj.state == com.nopalsoft.sharkadventure.objects.Mine.STATE_NORMAL) {
                    obj.hit();
                    shark.hit();
                }
            } else if (otraCosa instanceof com.nopalsoft.sharkadventure.objects.Missile) {
                com.nopalsoft.sharkadventure.objects.Missile obj = (com.nopalsoft.sharkadventure.objects.Missile) otraCosa;
                if (obj.state == com.nopalsoft.sharkadventure.objects.Missile.STATE_NORMAL) {
                    obj.hit();
                    shark.hit();
                    shark.hit();
                    shark.hit();
                }
            } else if (otraCosa instanceof Items) {
                Items obj = (Items) otraCosa;
                if (obj.state == Items.STATE_NORMAL) {
                    if (obj.type == Items.TYPE_MEAT) {
                        shark.energy += 15;
                    } else {
                        shark.life += 1;
                    }
                    obj.hit();
                }
            }

        }

        public void beginContactOtrasCosas(Fixture fixA, Fixture fixB) {
            Object objA = fixA.getBody().getUserData();
            Object objB = fixB.getBody().getUserData();

            if (objA instanceof com.nopalsoft.sharkadventure.objects.Barrel && objB instanceof com.nopalsoft.sharkadventure.objects.Mine) {
                ((com.nopalsoft.sharkadventure.objects.Barrel) objA).hit();
                ((com.nopalsoft.sharkadventure.objects.Mine) objB).hit();
            } else if (objA instanceof com.nopalsoft.sharkadventure.objects.Mine && objB instanceof com.nopalsoft.sharkadventure.objects.Barrel) {
                ((com.nopalsoft.sharkadventure.objects.Barrel) objB).hit();
                ((com.nopalsoft.sharkadventure.objects.Mine) objA).hit();
            }

        }

        @Override
        public void endContact(Contact contact) {

        }

        @Override
        public void preSolve(Contact contact, Manifold oldManifold) {

        }

        @Override
        public void postSolve(Contact contact, ContactImpulse impulse) {

        }

    }

}
