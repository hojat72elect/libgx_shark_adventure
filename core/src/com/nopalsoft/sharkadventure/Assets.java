package com.nopalsoft.sharkadventure;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.nopalsoft.sharkadventure.parallax.ParallaxBackground;
import com.nopalsoft.sharkadventure.parallax.ParallaxLayer;

public class Assets {

    public static BitmapFont fontLarge;
    public static TextureRegionDrawable drawableTitle;
    public static TextureRegionDrawable drawableGameOver;

    public static Animation<TextureRegion> animationSharkSwim;
    public static Animation<TextureRegion> animationSharkMove;
    public static Animation<TextureRegion> animationSharkFire;

    public static AtlasRegion SharkDead;
    public static AtlasRegion TurboTail;
    public static AtlasRegion greenBarrel;
    public static AtlasRegion blackBarrel;
    public static AtlasRegion redBarrel;
    public static AtlasRegion yellowBarrel;

    public static Animation<TextureRegion> explosionAnimation;

    public static AtlasRegion redSubmarine;
    public static AtlasRegion yellowSubmarine;
    public static AtlasRegion grayMine;
    public static AtlasRegion oxideMine;
    public static AtlasRegion chain;
    public static AtlasRegion blast;
    public static AtlasRegion torpedo;
    public static AtlasRegion heart;
    public static AtlasRegion meat;

    public static Animation<TextureRegion> animationBlastHit;

    public static AtlasRegion redBar;
    public static AtlasRegion energyBar;
    public static AtlasRegion background;

    public static ParallaxBackground parallaxBack;
    public static ParallaxBackground parallaxFront;

    public static ParticleEffect particleEffectBubble;
    public static ParticleEffect particleEffectSharkBubble;
    public static ParticleEffect particleEffectTorpedoBubbleRightSide;
    public static ParticleEffect particleEffectTorpedoBubbleLeftSide;
    public static ParticleEffect particleEffectFish;
    public static ParticleEffect particleEffectMediumFish;

    public static TextureRegionDrawable buttonRight;
    public static TextureRegionDrawable buttonRightPress;
    public static TextureRegionDrawable buttonLeft;
    public static TextureRegionDrawable buttonLeftPress;
    public static TextureRegionDrawable buttonUp;
    public static TextureRegionDrawable buttonUpPress;
    public static TextureRegionDrawable buttonFire;
    public static TextureRegionDrawable buttonFirePress;
    public static TextureRegionDrawable buttonHome;
    public static TextureRegionDrawable buttonHomePress;
    public static TextureRegionDrawable buttonPause;
    public static TextureRegionDrawable buttonPausePress;
    public static TextureRegionDrawable buttonLeaderboard;
    public static TextureRegionDrawable buttonLeaderboardPress;
    public static TextureRegionDrawable buttonFacebook;
    public static TextureRegionDrawable buttonFacebookPress;
    public static TextureRegionDrawable buttonMusicOn;
    public static TextureRegionDrawable buttonMusicOff;
    public static TextureRegionDrawable buttonSoundOn;
    public static TextureRegionDrawable buttonSoundOff;
    public static TextureRegionDrawable buttonRefresh;
    public static TextureRegionDrawable buttonRefreshPress;
    public static TextureRegionDrawable buttonAchievements;
    public static TextureRegionDrawable buttonAchievementsPress;
    public static TextureRegionDrawable buttonTwitter;
    public static TextureRegionDrawable buttonTwitterPress;
    public static TextureRegionDrawable backgroundProgressBar;


    public static NinePatchDrawable backgroundMenu;
    public static NinePatchDrawable backgroundWindow;
    public static NinePatchDrawable backgroundTitle;
    public static LabelStyle labelStyle;
    public static Sound soundSwim;
    public static Sound soundSonar;
    public static Sound soundExplosion1;
    public static Sound soundExplosion2;
    public static Sound soundBlast;
    public static Music music;
    static TextureAtlas atlas;

    public static void load() {
        atlas = new TextureAtlas(Gdx.files.internal("data/atlasMap.txt"));

        fontLarge = new BitmapFont(Gdx.files.internal("data/FontGrande.fnt"), atlas.findRegion("FontGrande"));

        loadUI();

        SharkDead = atlas.findRegion("tiburonDead");

        AtlasRegion tiburon1 = atlas.findRegion("tiburon1");
        AtlasRegion tiburon2 = atlas.findRegion("tiburon2");
        AtlasRegion tiburon3 = atlas.findRegion("tiburon3");
        AtlasRegion tiburon4 = atlas.findRegion("tiburon4");
        AtlasRegion tiburon5 = atlas.findRegion("tiburon5");
        AtlasRegion tiburon6 = atlas.findRegion("tiburon6");
        AtlasRegion tiburon7 = atlas.findRegion("tiburon7");
        AtlasRegion tiburon8 = atlas.findRegion("tiburon8");

        animationSharkSwim = new Animation(.15f, tiburon1, tiburon2, tiburon3, tiburon4, tiburon5, tiburon6, tiburon7, tiburon8);
        animationSharkMove = new Animation(.04f, tiburon1, tiburon2, tiburon3, tiburon4, tiburon5, tiburon6, tiburon7, tiburon8);

        AtlasRegion tiburonFire1 = atlas.findRegion("tiburonFire1");
        AtlasRegion tiburonFire2 = atlas.findRegion("tiburonFire2");
        AtlasRegion tiburonFire3 = atlas.findRegion("tiburonFire3");
        AtlasRegion tiburonFire4 = atlas.findRegion("tiburonFire4");
        AtlasRegion tiburonFire5 = atlas.findRegion("tiburonFire5");

        animationSharkFire = new Animation(.075f, tiburonFire1, tiburonFire2, tiburonFire3, tiburonFire4, tiburonFire5);

        TurboTail = atlas.findRegion("turbo");

        greenBarrel = atlas.findRegion("barrilVerde");
        blackBarrel = atlas.findRegion("barrilNegro");
        redBarrel = atlas.findRegion("barrilRojo");
        yellowBarrel = atlas.findRegion("barrilAmarillo");

        AtlasRegion explosion1 = atlas.findRegion("explosion1");
        AtlasRegion explosion2 = atlas.findRegion("explosion2");
        AtlasRegion explosion3 = atlas.findRegion("explosion3");
        AtlasRegion explosion4 = atlas.findRegion("explosion4");
        AtlasRegion explosion5 = atlas.findRegion("explosion5");
        AtlasRegion explosion6 = atlas.findRegion("explosion6");
        AtlasRegion explosion7 = atlas.findRegion("explosion7");
        AtlasRegion explosion8 = atlas.findRegion("explosion8");

        explosionAnimation = new Animation(.1f, explosion1, explosion2, explosion3, explosion4, explosion5, explosion6, explosion7, explosion8);

        AtlasRegion blastHit1 = atlas.findRegion("blastHit1");
        AtlasRegion blastHit2 = atlas.findRegion("blastHit2");
        AtlasRegion blastHit3 = atlas.findRegion("blastHit3");
        AtlasRegion blastHit4 = atlas.findRegion("blastHit4");
        AtlasRegion blastHit5 = atlas.findRegion("blastHit5");
        AtlasRegion blastHit6 = atlas.findRegion("blastHit6");

        animationBlastHit = new Animation(.05f, blastHit1, blastHit2, blastHit3, blastHit4, blastHit5, blastHit6);

        yellowSubmarine = atlas.findRegion("submarinoAmarillo");
        redSubmarine = atlas.findRegion("submarinoRojo");

        grayMine = atlas.findRegion("minaGris");
        oxideMine = atlas.findRegion("minaOxido");
        chain = atlas.findRegion("chain");
        blast = atlas.findRegion("blast");
        torpedo = atlas.findRegion("torpedo");
        heart = atlas.findRegion("corazon");
        meat = atlas.findRegion("carne");

        reloadBackground();

        particleEffectBubble = new ParticleEffect();
        particleEffectBubble.load(Gdx.files.internal("particulas/burbujas"), atlas);

        particleEffectSharkBubble = new ParticleEffect();
        particleEffectSharkBubble.load(Gdx.files.internal("particulas/burbujasTiburon"), atlas);

        particleEffectTorpedoBubbleRightSide = new ParticleEffect();
        particleEffectTorpedoBubbleRightSide.load(Gdx.files.internal("particulas/burbujasTorpedoRightSide"), atlas);

        particleEffectTorpedoBubbleLeftSide = new ParticleEffect();
        particleEffectTorpedoBubbleLeftSide.load(Gdx.files.internal("particulas/burbujasTorpedoLeftSide"), atlas);

        particleEffectFish = new ParticleEffect();
        particleEffectFish.load(Gdx.files.internal("particulas/peces"), atlas);

        particleEffectMediumFish = new ParticleEffect();
        particleEffectMediumFish.load(Gdx.files.internal("particulas/pecesMediano"), atlas);

        soundSwim = Gdx.audio.newSound(Gdx.files.internal("sound/swim.mp3"));
        soundSonar = Gdx.audio.newSound(Gdx.files.internal("sound/sonar.mp3"));
        soundExplosion1 = Gdx.audio.newSound(Gdx.files.internal("sound/explosion1.mp3"));
        soundExplosion2 = Gdx.audio.newSound(Gdx.files.internal("sound/explosion2.mp3"));
        soundBlast = Gdx.audio.newSound(Gdx.files.internal("sound/blast1.mp3"));

        music = Gdx.audio.newMusic(Gdx.files.internal("sound/jungleHaze.mp3"));
        music.setLooping(true);

        if (Settings.isMusicOn)
            music.play();

    }

    private static void loadUI() {
        drawableTitle = new TextureRegionDrawable(atlas.findRegion("UI/titulo"));
        drawableGameOver = new TextureRegionDrawable(atlas.findRegion("UI/gameOver2"));

        buttonRight = new TextureRegionDrawable(atlas.findRegion("UI/btDer"));
        buttonRightPress = new TextureRegionDrawable(atlas.findRegion("UI/btDerPress"));
        buttonLeft = new TextureRegionDrawable(atlas.findRegion("UI/btIzq"));
        buttonLeftPress = new TextureRegionDrawable(atlas.findRegion("UI/btIzqPress"));
        buttonUp = new TextureRegionDrawable(atlas.findRegion("UI/btUp"));
        buttonUpPress = new TextureRegionDrawable(atlas.findRegion("UI/btUpPress"));
        buttonFire = new TextureRegionDrawable(atlas.findRegion("UI/btFire"));
        buttonFirePress = new TextureRegionDrawable(atlas.findRegion("UI/btFirePress"));

        buttonRefresh = new TextureRegionDrawable(atlas.findRegion("UI/btRefresh"));
        buttonRefreshPress = new TextureRegionDrawable(atlas.findRegion("UI/btRefreshPress"));
        buttonHome = new TextureRegionDrawable(atlas.findRegion("UI/btHome"));
        buttonHomePress = new TextureRegionDrawable(atlas.findRegion("UI/btHomePress"));
        buttonPause = new TextureRegionDrawable(atlas.findRegion("UI/btPausa"));
        buttonPausePress = new TextureRegionDrawable(atlas.findRegion("UI/btPausaPress"));
        buttonLeaderboard = new TextureRegionDrawable(atlas.findRegion("UI/btLeaderboard"));
        buttonLeaderboardPress = new TextureRegionDrawable(atlas.findRegion("UI/btLeaderboardPress"));
        buttonAchievements = new TextureRegionDrawable(atlas.findRegion("UI/btAchievements"));
        buttonAchievementsPress = new TextureRegionDrawable(atlas.findRegion("UI/btAchievementsPress"));
        buttonFacebook = new TextureRegionDrawable(atlas.findRegion("UI/btFacebook"));
        buttonFacebookPress = new TextureRegionDrawable(atlas.findRegion("UI/btFacebookPress"));
        buttonTwitter = new TextureRegionDrawable(atlas.findRegion("UI/btTwitter"));
        buttonTwitterPress = new TextureRegionDrawable(atlas.findRegion("UI/btTwitterPress"));
        buttonSoundOn = new TextureRegionDrawable(atlas.findRegion("UI/btSonido"));
        buttonSoundOff = new TextureRegionDrawable(atlas.findRegion("UI/btSonidoOff"));
        buttonMusicOn = new TextureRegionDrawable(atlas.findRegion("UI/btMusic"));
        buttonMusicOff = new TextureRegionDrawable(atlas.findRegion("UI/btMusicOff"));

        redBar = atlas.findRegion("UI/redBar");
        energyBar = atlas.findRegion("UI/energyBar");

        backgroundProgressBar = new TextureRegionDrawable(atlas.findRegion("UI/backgroundProgressBar"));
        backgroundMenu = new NinePatchDrawable(new NinePatch(atlas.findRegion("UI/backgroundMenu"), 70, 70, 60, 60));
        backgroundWindow = new NinePatchDrawable(new NinePatch(atlas.findRegion("UI/backgroundVentana"), 25, 25, 25, 25));
        backgroundTitle = new NinePatchDrawable(new NinePatch(atlas.findRegion("UI/backgroundTitulo"), 30, 30, 0, 0));

        labelStyle = new LabelStyle(Assets.fontLarge, null);

    }

    public static void reloadBackground() {
        ParallaxLayer frontLayer;
        ParallaxLayer backLayer;

        if (MathUtils.randomBoolean()) {
            background = atlas.findRegion("fondo");
            backLayer = new ParallaxLayer(atlas.findRegion("sueloAtras"), new Vector2(5, 0), new Vector2(0, -50), new Vector2(-1, 480), 1024, 121);
            frontLayer = new ParallaxLayer(atlas.findRegion("suelo"), new Vector2(15, 0), new Vector2(0, -50), new Vector2(-1, 480), 1024, 121);

        } else {
            background = atlas.findRegion("fondo2");
            backLayer = new ParallaxLayer(atlas.findRegion("suelo2Atras"), new Vector2(5, 0), new Vector2(0, -50), new Vector2(-1, 480), 1024, 121);
            frontLayer = new ParallaxLayer(atlas.findRegion("suelo2"), new Vector2(15, 0), new Vector2(0, -50), new Vector2(-1, 480), 1024, 121);

        }
        parallaxBack = new ParallaxBackground(new ParallaxLayer[]{backLayer}, 800, 480, new Vector2(10, 0));
        parallaxFront = new ParallaxBackground(new ParallaxLayer[]{frontLayer}, 800, 480, new Vector2(10, 0));
    }

    public static void playExplosionSound() {
        int sound = MathUtils.random(1);
        Sound soundToBePlayed;
        if (sound == 0)
            soundToBePlayed = soundExplosion1;
        else
            soundToBePlayed = soundExplosion2;
        soundToBePlayed.play();
    }
}
