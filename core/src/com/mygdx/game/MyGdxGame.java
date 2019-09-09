package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture playerTexture, playerWithKeyTexture;
	Player player;
	Texture[] textures;
	Texture[] directions;
	@Override
	public void create () {
		batch = new SpriteBatch();

		playerTexture = new Texture("player.png");
		playerWithKeyTexture = new Texture("playerWithKey.png");
		player = new Player();
		textures = new Texture[6];
		textures[0] = new Texture("Unit.png");
		textures[1] = new Texture("Wall.png");
		textures[2] = new Texture("Key.png");
		textures[3] = new Texture("Door.png");
		textures[4] = new Texture("Elevator.png");
		textures[5] = new Texture("Spawn.png");
		directions = new Texture[4];
		directions[0] = new Texture("up.png");
		directions[1] = new Texture("right.png");
		directions[2] = new Texture("down.png");
		directions[3] = new Texture("left.png");
	}

	@Override
	public void render () {
		Level currentLevel = player.levels[player.getLevel()];
		Gdx.gl.glClearColor(currentLevel.red, currentLevel.green, currentLevel.blue, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();

		for(int i = 0; i<Level.MAP_SIZE; i++){
			for(int j = 0; j<Level.MAP_SIZE; j++){
				batch.draw(textures[currentLevel.map[i][j].getIndex()],j*Unit.SIZE, i*Unit.SIZE);
			}
		}
		batch.draw(player.isKey()?playerWithKeyTexture:playerTexture, player.getX(),player.getY(), player.SIZE, player.SIZE);
		batch.draw(directions[player.getDir()], 500,150);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		playerTexture.dispose();
	}
}
