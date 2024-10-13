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
        public void showRater() {

        }

        @Override
        public void showInterstitial() {

        }

        @Override
        public void showAdBanner() {

        }

        @Override
        public void showMoreGames() {
            Gdx.net.openURI("https://play.google.com/");
        }

        @Override
        public void shareOnTwitter(String mensaje) {
            String tweetUrl = "https://twitter.com/intent/tweet?text=" + mensaje + " Download it from &url=https://play.google.com/&hashtags=SharkAdventure";
            Gdx.net.openURI(tweetUrl);
        }

        @Override
        public void removeAds() {

        }

        @Override
        public void hideAdBanner() {

        }

        @Override
        public void buy5milCoins() {

        }

        @Override
        public void buy50milCoins() {

        }

        @Override
        public void buy30milCoins() {

        }

        @Override
        public void buy15milCoins() {

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
        public void signOut() {

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
        public void facebookSignOut() {

        }

        @Override
        public void facebookSignIn() {

        }

        @Override
        public void facebookShareFeed(String message) {

        }

        @Override
        public boolean facebookIsSignedIn() {
            return false;
        }

        @Override
        public void facebookInviteFriends(String message) {

        }
    };

    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 800;
        config.height = 480;
        new LwjglApplication(new MainShark(handler, gameServicesHandler, facebookHandler), config);
    }
}
