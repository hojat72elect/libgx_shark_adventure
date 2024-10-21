package com.nopalsoft.sharkadventure

import com.badlogic.gdx.Game
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.StretchViewport
import com.nopalsoft.sharkadventure.game.GameScreen
import com.nopalsoft.sharkadventure.handlers.FacebookHandler
import com.nopalsoft.sharkadventure.handlers.GameServicesHandler
import com.nopalsoft.sharkadventure.handlers.RequestHandler
import com.nopalsoft.sharkadventure.screens.Screens

class MainShark(
    @JvmField
    val reqHandler: RequestHandler,
    @JvmField
    val gameServiceHandler: GameServicesHandler,
    @JvmField
    val facebookHandler: FacebookHandler
) : Game() {

    lateinit var stage: Stage
    lateinit var batcher: SpriteBatch

    override fun create() {

        batcher = SpriteBatch()
        stage = Stage(StretchViewport(Screens.SCREEN_WIDTH, Screens.SCREEN_HEIGHT))

        Settings.load()
        Assets.load()
        Achievements.init(this)
        setScreen(GameScreen(this, true))

    }
}