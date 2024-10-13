package com.nopalsoft.sharkadventure.scene2d;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.nopalsoft.sharkadventure.Assets;

public class ProgressBarUI extends Table {
    public final static float WIDTH = 180;
    public final static float HEIGHT = 30;
    float BAR_WIDTH = 140;
    float BAR_HEIGHT = 20;

    public float maxNum;
    public float actualNum;
    AtlasRegion bar;

    public ProgressBarUI(AtlasRegion bar, AtlasRegion icon, float maxNum, float x, float y) {

        this.setBounds(x, y, WIDTH, HEIGHT);
        this.maxNum = maxNum;
        this.actualNum = maxNum;
        setBackground(Assets.backgroundProgressBar);
        setIcon(icon);
        this.bar = bar;

    }

    private void setIcon(AtlasRegion icon) {
        Image imageIcon = new Image(icon);
        // Both height because i want it to be a square
        imageIcon.scaleBy(-.3f);
        imageIcon.setPosition(-15, getHeight() / 2f - (imageIcon.getPrefHeight() * imageIcon.getScaleY() / 2f));
        addActor(imageIcon);

    }

    public void updateActualNum(float actualNum) {
        this.actualNum = actualNum;

        if (actualNum > maxNum)
            maxNum = actualNum;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if (actualNum > 0)
            batch.draw(bar, this.getX() + 34, this.getY() + 6, BAR_WIDTH * (actualNum / maxNum), BAR_HEIGHT);
    }
}
