package com.nopalsoft.sharkadventure.handlers

interface RequestHandler {
    fun showInterstitial()

    fun shareOnTwitter(mensaje: String)

    fun showAdBanner()

    fun hideAdBanner()
}
