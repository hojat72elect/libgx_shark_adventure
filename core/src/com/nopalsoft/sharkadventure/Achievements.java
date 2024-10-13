package com.nopalsoft.sharkadventure;

import com.badlogic.gdx.Gdx;
import com.nopalsoft.sharkadventure.handlers.GameServicesHandler;
import com.nopalsoft.sharkadventure.handlers.GoogleGameServicesHandler;

public class Achievements {

    static boolean didInit = false;
    static GameServicesHandler gameServicesHandler;

    static String firstStep, swimmer, submarineKiller, submarineHunter, submarineSlayer, youCanNotHitMe;
    private static boolean doneFirstStep, doneSwimmer, doneYouCanNotHitMe;

    public static void init(MainShark game) {
        gameServicesHandler = game.gameServiceHandler;

        if (gameServicesHandler instanceof GoogleGameServicesHandler) {
            firstStep = "CgkIwICJ-poIEAIQAw";
            swimmer = "CgkIwICJ-poIEAIQBA";
            submarineKiller = "CgkIwICJ-poIEAIQBQ";
            submarineHunter = "CgkIwICJ-poIEAIQBg";
            submarineSlayer = "CgkIwICJ-poIEAIQBw";
            youCanNotHitMe = "CgkIwICJ-poIEAIQCA";

        } else {
            firstStep = "FirstStepID";
            swimmer = "SwimmerID";
            submarineKiller = "SubmarinekillerID";
            submarineHunter = "SubmarinehunterID";
            submarineSlayer = "SubmarineslayerID";
            youCanNotHitMe = "YouCantHitMe";
        }
        didInit = true;
    }

    /**
     * Called when u start a new game so u can try to do achievements once more
     */
    public static void tryAgainAchievements() {
        doneFirstStep = false;
        doneYouCanNotHitMe = false;
        doneSwimmer = false;
    }

    private static void didInit() {
        if (didInit)
            return;
        Gdx.app.log("Achievements", "You must call first Achievements.init()");

    }

    public static void unlockKilledSubmarines(long num) {
        didInit();
        if (num == 3) {
            gameServicesHandler.unlockAchievement(submarineKiller);
        } else if (num == 6) {
            gameServicesHandler.unlockAchievement(submarineHunter);
        } else if (num == 10) {
            gameServicesHandler.unlockAchievement(submarineSlayer);
        }

    }

    public static void distance(long distance, boolean didGetHurt) {
        didInit();
        if (distance > 1 && !doneFirstStep) {
            doneFirstStep = true;
            gameServicesHandler.unlockAchievement(firstStep);
        }
        if (distance > 2500 && !doneSwimmer) {
            doneSwimmer = true;
            gameServicesHandler.unlockAchievement(swimmer);
        }
        if (distance > 850 && !doneYouCanNotHitMe && !didGetHurt) {
            doneYouCanNotHitMe = true;
            gameServicesHandler.unlockAchievement(youCanNotHitMe);
        }

    }
}