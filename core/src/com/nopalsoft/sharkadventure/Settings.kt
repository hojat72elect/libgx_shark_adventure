package com.nopalsoft.sharkadventure

import com.badlogic.gdx.Gdx

object Settings {

    private val pref = Gdx.app.getPreferences("com.nopalsoft.sharkadventure")

    @JvmField
    var numberOfTimesPlayed = 0L

    @JvmField
    var bestScore = 0L

    @JvmField
    var isMusicOn = true

    @JvmField
    var isSoundOn = true
    private var didBuyNoAds = false
    private var didRate = false

    @JvmStatic
    fun save() {
        pref.putBoolean("isMusicOn", isMusicOn)
        pref.putBoolean("isSoundOn", isSoundOn)
        pref.putBoolean("didBuyNoAds", didBuyNoAds)
        pref.putBoolean("didRate", didRate)
        pref.putLong("numVecesJugadas", numberOfTimesPlayed)
        pref.putLong("bestScore", bestScore)
        pref.flush()
    }

    @JvmStatic
    fun load() {
        isMusicOn = pref.getBoolean("isMusicOn", true)
        isSoundOn = pref.getBoolean("isSoundOn", true)
        didBuyNoAds = pref.getBoolean("didBuyNoAds", false)
        didRate = pref.getBoolean("didRate", false)
        numberOfTimesPlayed = pref.getLong("numVecesJugadas", 0)
        bestScore = pref.getLong("bestScore", 0)
    }

    @JvmStatic
    fun setBestScore(score: Long) {
        if (score > bestScore) {
            bestScore = score
            save()
        }
    }
}