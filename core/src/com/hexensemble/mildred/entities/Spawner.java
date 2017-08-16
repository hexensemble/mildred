package com.hexensemble.mildred.entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.hexensemble.mildred.levels.Level;
import com.hexensemble.mildred.system.CoreSettings;
import com.hexensemble.mildred.system.Cutter;

/**
 * Represents a spawner.
 * 
 * @author HexEnsemble
 * @author www.hexensemble.com
 * @version Beta 2.2.0
 * @since Alpha 1.0.0
 */
public class Spawner extends AbstractEntity implements Poolable {

	private float spawnTimer;
	private boolean animateSpawn;
	private float animateSpawnTimer;

	private Cutter cutter;
	private Animation stationary;
	private Animation spawning;
	private TextureRegion currentFrame;

	/**
	 * @deprecated Use {@code init()}.
	 */
	@Deprecated
	public Spawner() {

	}

	/**
	 * Initialize.
	 * 
	 * @param type
	 *            Entity type.
	 * @param level
	 *            Game level.
	 * @param x
	 *            Initial X position.
	 * @param y
	 *            Initial Y position.
	 */
	@Override
	public void init(EntityType type, Level level, float x, float y) {
		super.init(type, level, x, y);

		spawnTimer = 0;
		animateSpawn = false;
		animateSpawnTimer = 0;

		animationInit();
	}

	/**
	 * Update.
	 * 
	 * @param delta
	 *            Delta time.
	 */
	@Override
	public void update(float delta) {
		super.update(delta);

		spawnTimer += delta;

		if (animateSpawn) {
			animateSpawnTimer += delta;
		}
		if (animateSpawnTimer > 3) {
			animateSpawn = false;
			animateSpawnTimer = 0;
		}

		if (spawnTimer > actionValue) {
			spawn();
		}
	}

	/**
	 * Render.
	 * 
	 * @param batch
	 *            Sprite batch.
	 */
	@Override
	public void render(SpriteBatch batch) {
		super.render(batch);

		if (animateSpawn) {
			currentFrame = spawning.getKeyFrame(stateTime, true);
		} else {
			currentFrame = stationary.getKeyFrame(stateTime, true);
		}

		batch.begin();
		batch.draw(currentFrame, x, y, width, height);
		batch.end();
	}

	/**
	 * Dispose.
	 */
	@Override
	public void dispose() {
		super.dispose();

		cutter.dispose();
	}

	@Override
	protected void animationInit() {
		super.animationInit();

		cutter = new Cutter(sprite, 3, 2);
		stationary = new Animation(0.1f, cutter.frames[0], cutter.frames[1], cutter.frames[2]);
		spawning = new Animation(0.1f, cutter.frames[3], cutter.frames[4], cutter.frames[5]);
	}

	@Override
	protected void deathCheck() {
		super.deathCheck();

		if (health <= 0) {
			for (int i = 0; i < 16; i++) {
				Gib g = level.gibPool.obtain();
				g.init(gibType, level, centerX, centerY);
				level.gibs.add(g);
			}
			for (Player p : level.players) {
				p.setXp(p.getXp() + xpDrop);
			}
			dead = true;
		}
	}

	private void spawn() {
		if (tileClear()) {
			animateSpawn = true;
			Enemy enemy = level.enemyPool.obtain();
			enemy.init(projectileType, level, x, y);
			level.enemies.add(enemy);
			level.setTotalEnemies(level.getTotalEnemies() + 1);
			sound.play(CoreSettings.sfxVol);
			spawnTimer = 0;
		} else {
			spawnTimer = 0;
		}
	}

	private boolean tileClear() {
		boolean clear = true;
		for (int i = 0; i < level.enemies.size; i++) {
			if (level.enemies.get(i).getPosition().equals(position)) {
				clear = false;
				return clear;
			}
		}
		return clear;
	}

}
