package com.nopalsoft.sharkadventure.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.nopalsoft.sharkadventure.MainShark;
import com.nopalsoft.sharkadventure.handlers.FacebookHandler;
import com.nopalsoft.sharkadventure.handlers.GameServicesHandler;
import com.nopalsoft.sharkadventure.handlers.RequestHandler;

public class DesktopLauncher {
    static RequestHandler handler = new RequestHandler() {

        @Override
        public void showInterstitial() {

        }

        @Override
        public void showAdBanner() {

        }

        @Override
        public void shareOnTwitter(String message) {
            String tweetUrl = "https://twitter.com/intent/tweet?text=" + message + " Download it from &url=https://play.google.com/&hashtags=SharkAdventure";
            Gdx.net.openURI(tweetUrl);
        }

        @Override
        public void hideAdBanner() {

        }

    };
    static GameServicesHandler gameServicesHandler = new GameServicesHandler() {

        @Override
        public void unlockAchievement(String achievementId) {

        }

        @Override
        public void submitScore(long score) {

        }

        @Override
        public void signIn() {

        }

        @Override
        public boolean isSignedIn() {
            return false;
        }

        @Override
        public void getLeaderboard() {

        }

        @Override
        public void getAchievements() {

        }
    };
    static FacebookHandler facebookHandler = new FacebookHandler() {

        @Override
        public void showFacebook() {
            Gdx.net.openURI("https://www.facebook.com");
        }


        @Override
        public void facebookSignIn() {

        }

    };

    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 800;
        config.height = 480;
        new LwjglApplication(new MainShark(handler, gameServicesHandler, facebookHandler), config);
    }
}
