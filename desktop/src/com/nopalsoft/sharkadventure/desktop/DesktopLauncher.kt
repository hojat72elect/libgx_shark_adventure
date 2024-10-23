package com.nopalsoft.sharkadventure.desktop

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.nopalsoft.sharkadventure.MainShark
import com.nopalsoft.sharkadventure.handlers.FacebookHandler
import com.nopalsoft.sharkadventure.handlers.GameServicesHandler
import com.nopalsoft.sharkadventure.handlers.RequestHandler

fun main(args: Array<String>) {
    val config = LwjglApplicationConfiguration()
    config.width = 800
    config.height = 480

    val handler = object : RequestHandler {

        override fun showInterstitial() {}
        override fun showAdBanner() {}
        override fun shareOnTwitter(message: String) {
            Gdx.net.openURI("https://twitter.com/intent/tweet?text=$message Download it from &url=https://play.google.com/&hashtags=SharkAdventure")
        }

        override fun hideAdBanner() {}
    }
    val gameServicesHandler = object : GameServicesHandler {

        override fun submitScore(score: Long) {}
        override fun unlockAchievement(achievementId: String) {}
        override val leaderboard = Unit
        override val achievements = Unit
        override val isSignedIn = false
        override fun signIn() {}
    }
    val facebookHandler = object : FacebookHandler {

        override fun showFacebook() {
            Gdx.net.openURI("https://www.facebook.com")
        }

        override fun facebookSignIn() {}
    }

    LwjglApplication(MainShark(handler, gameServicesHandler, facebookHandler), config)
}

