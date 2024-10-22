package com.nopalsoft.sharkadventure.handlers

interface GameServicesHandler {
    fun submitScore(score: Long)

    fun unlockAchievement(achievementId: String)

    val leaderboard: Unit

    val achievements: Unit

    val isSignedIn: Boolean

    fun signIn()
}
