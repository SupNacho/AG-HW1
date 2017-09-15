package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture mob;
	Texture player;
	Texture background;
	Texture shotLaser;

	float playerOrient = 180; // Начальная ориентация текстуры игрока


	float px;  // координата х игрока
	float py;  // координата y игрока
	float pvx; // "ускорение" игрока по х
	float pvy; // "ускорение" игрока по у
	Array<Asteroid> asteroids = null; // коллекция астеройдов
	Array<Shot> shots = new Array<Shot>();
	private static final float SSV = 5.0f; // константа изменения ускорения игрока

	
	@Override
	public void create () {
		batch = new SpriteBatch();
		mob = new Texture("asteroid.png");
		player = new Texture("spaceShip.png");
		background = new Texture("spaceBG.jpg");
		shotLaser = new Texture("LaserShot.png");
		asteroids = new Array<Asteroid>(); // Заполняем коллекцию астеройдов соответствующими объектами
		asteroids.add(new Asteroid(mob,10,10, 0, 1));
		asteroids.add(new Asteroid(mob,100,300, 5, 0.5f));
		asteroids.add(new Asteroid(mob,50,200, 10, 0.1f));
		asteroids.add(new Asteroid(mob,700,0, 200, 0.3f));
		asteroids.add(new Asteroid(mob,15,100, 45, 0.18f));
		asteroids.add(new Asteroid(mob,180,600, 5, 0.7f));
		asteroids.add(new Asteroid(mob,350,280, 10, 0.2f));
		asteroids.add(new Asteroid(mob,1200,15, 200, 0.8f));
		pvx = 0.0f; // начальное "ускорение" игрока по х и у
		pvy = 0.0f;
	}

	@Override
	public void render () {
		float dt = Gdx.graphics.getDeltaTime();
		update(dt);
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.setColor(1,1,1,0.3f);
		batch.draw(background, 0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.setColor(1,1,1,1);
		for (Asteroid asteroid : asteroids) {
			batch.draw(asteroid.getTexture(), asteroid.getPosx(), asteroid.getPosy(),
					asteroid.getTexture().getWidth() / 2 , asteroid.getTexture().getHeight() / 2 ,
					asteroid.getTexture().getWidth(), asteroid.getTexture().getHeight(), asteroid.getScalex(), asteroid.getScaley(), asteroid.getRotation(),
					0,0,asteroid.getTexture().getWidth(),asteroid.getTexture().getHeight(), false, false);
		}
		for (Shot shot : shots) {
			batch.draw(shot.getTex(), shot.getPosx(), shot.getPosy());
		}
		batch.draw(player, px, py, 64f, 32f, 128f, 61f, 1f, 1f, playerOrient,
				0,0,128,61, false, false);
		batch.end();
	}

	public void update(float dt){
		for (Asteroid asteroid : asteroids) {  // Перемещаем астеройды
			asteroid.setRotation(asteroid.getRotation() + asteroid.getVrotation() * dt);
			asteroid.setPosx(asteroid.getPosx() + asteroid.getVx() * dt);
			asteroid.setPosy(asteroid.getPosy() + asteroid.getVy() * dt);
			// При выходе астеройда за игровое поле возвращаем их в произвольные координаты внутри  игрового поля
			// с произвольным направлением движения
            if (asteroid.getPosx() > Gdx.graphics.getWidth() || asteroid.getPosx() < -256) {
                asteroid.setPosx((float) Math.random() * Gdx.graphics.getWidth());
                asteroid.newVx();
                asteroid.newVrotate();
            }
            if (asteroid.getPosy() > Gdx.graphics.getHeight() || asteroid.getPosy() < -256) {
                asteroid.setPosy((float) Math.random() * Gdx.graphics.getHeight());
                asteroid.newVy();
				asteroid.newVrotate();
            }
		}

		// Управление игроком с соответствующим поворотом модели(8 направлений),
		// модель усправления инерционная имитирует поведение космического коробля.

		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT )) {
			playerOrient = 180;
			pvx += SSV;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			playerOrient = 0;
			pvx -= SSV;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
//			playerOrient = 270;
			pvy += SSV;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
//			playerOrient = 90;
			pvy -= SSV;
		}
//		if (Gdx.input.isKeyPressed(Input.Keys.DOWN) && Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
//			playerOrient = 45;
//		}
//		if (Gdx.input.isKeyPressed(Input.Keys.UP) && Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
//			playerOrient = 315;
//		}
//		if (Gdx.input.isKeyPressed(Input.Keys.UP) && Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
//			playerOrient = 225;
//		}
//		if (Gdx.input.isKeyPressed(Input.Keys.DOWN) && Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
//			playerOrient = 135;
//		}

		if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
			System.out.println(playerOrient);
			shots.add(new Shot(shotLaser, px, py, playerOrient));
		}
        // Меняем направление движения игрока при достижении границы игрового поля
		if (px < 0 || px > Gdx.graphics.getWidth()) {
			pvx *= -1;
		}
		if (py < 0 || py > Gdx.graphics.getHeight()) {
			pvy *= -1;
		}
        px += pvx * dt;
        py += pvy * dt;

		Iterator<Shot> iter = shots.iterator();
		while (iter.hasNext()){
			Shot beam = iter.next();
			beam.setPosx(beam.getPosx() + beam.getVx() * dt);
			if (beam.getPosx() < 0 || beam.getPosx() > Gdx.graphics.getWidth()) iter.remove();
		}

	}
	
	@Override
	public void dispose () {
		batch.dispose();
		mob.dispose();
		player.dispose();
		background.dispose();
	}
}
