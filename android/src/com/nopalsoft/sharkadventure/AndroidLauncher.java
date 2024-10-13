package com.nopalsoft.sharkadventure;

import android.os.Bundle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.nopalsoft.sharkadventure.handlers.FacebookHandler;
import com.nopalsoft.sharkadventure.handlers.GameServicesHandler;
import com.nopalsoft.sharkadventure.handlers.RequestHandler;

public class AndroidLauncher extends AndroidApplication implements RequestHandler, FacebookHandler, GameServicesHandler {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        initialize(new MainShark(this, this, this), config);
    }

    @Override
    public void facebookSignIn() {

    }

    @Override
    public void showFacebook() {
        Gdx.net.openURI("https://www.facebook.com");
    }

    @Override
    public void submitScore(long score) {

    }

    @Override
    public void unlockAchievement(String achievementId) {

    }

    @Override
    public void getLeaderboard() {

    }

    @Override
    public void getAchievements() {

    }

    @Override
    public boolean isSignedIn() {
        return false;
    }

    @Override
    public void signIn() {

    }

    @Override
    public void showInterstitial() {

    }

    @Override
    public void shareOnTwitter(String mensaje) {
        String tweetUrl = "https://twitter.com/intent/tweet?text=" + mensaje + " Download it from &url=https://play.google.com/&hashtags=SharkAdventure";
        Gdx.net.openURI(tweetUrl);
    }

    @Override
    public void showAdBanner() {

    }

    @Override
    public void hideAdBanner() {

    }

}
