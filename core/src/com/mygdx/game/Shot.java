package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

public class Shot {
    private Texture tex;
    private float posx;
    private float posy;
    private float vx;
    private float angle;

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public Shot(Texture tex, float posx, float posy, float angle) {

        this.tex = tex;
        this.posx = posx;
        this.posy = posy;
        if ( angle == 0) {
            this.vx = -1000;
        } else {
            this.vx = 1000;
        }
    }


    public Texture getTex() {
        return tex;
    }

    public void setTex(Texture tex) {
        this.tex = tex;
    }

    public float getPosx() {
        return posx;
    }

    public void setPosx(float posx) {
        this.posx = posx;
    }

    public float getPosy() {
        return posy;
    }

    public void setPosy(float posy) {
        this.posy = posy;
    }

    public float getVx() {
        return vx;
    }
}
