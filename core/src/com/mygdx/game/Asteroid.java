package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by a.shumilov on 14.09.2017.
 */
public class Asteroid {


    private final float scaley; // Масштаб асторойда относительно размеров текстуры по у
    private final float scalex; // Масштаб асторойда относительно размеров текстуры по х
    private float vrotation; // "ускорение" угла поворота



    private Texture texture; // Текстура астеройда
    private float posx;      // Позиция на игровом пространстве по х
    private float posy;      // Позиция на игровом пространстве по у
    private float vx;        // значение "ускорения" по х
    private float vy;        // значение "ускорения" по у
    private float rotation;  // значение начального угла поворота астеройда


    // Создаем астеройди с начальными параметрами
    public Asteroid(Texture tex, float posx, float posy, float rotation, float scale) {
        this.texture = tex;
        this.posx = posx;
        this.posy = posy;
        this.rotation = rotation;
        this.scalex = scale;
        this.scaley = scale;
        newVrotate(); // получение произвольного "ускорения" вращения
        newVx(); // получение произвольного "ускорения" по х
        newVy(); // получение произвольного "ускорения" по у
    }

    public float newVrotate(){
        return  vrotation = (float) (Math.random() * 20) - 10;
    }

    public float newVx(){
        return vx = (float) Math.random() * 101 - 50;
    }

    public float newVy(){
        return vy = (float) Math.random() * 101 - 50;
    }
    // Возвращает позицию по х
    public float getPosx() {
        return posx;
    }
    // Устанавливает позицию по х
    public void setPosx(float posx) {
        this.posx = posx;
    }
    // Возвращает позицию по у
    public float getPosy() {
        return posy;
    }
    // Устанавливает позицию по у
    public void setPosy(float posy) {
        this.posy = posy;
    }
    // возвращает значение "ускорения" по х
    public float getVx() {
        return vx;
    }
    // возвращает значение "ускорения" по у
    public float getVy() {
        return vy;
    }
    // Возвращает значение поворота астеройда
    public float getRotation() {
        return rotation;
    }
    // устанавливает значение поворота астеройда
    public void setRotation(float rotation) {
        this.rotation = rotation;
    }
    // возвращает значение "ускорения" вращения астеройда
    public float getVrotation() {
        return vrotation;
    }
    // возвращает текстуру астеройда
    public Texture getTexture() {
        return texture;
    }
    // возвращает значение масштаба астеройда по у
    public float getScaley() {
        return scaley;
    }
    // возвращает значение масштаба астеройда по х
    public float getScalex() {
        return scalex;
    }
}
