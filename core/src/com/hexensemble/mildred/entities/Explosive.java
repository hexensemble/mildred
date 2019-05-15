package com.hexensemble.mildred.entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.hexensemble.mildred.levels.Level;
import com.hexensemble.mildred.system.Cutter;

/**
 * Represents an explosive.
 * 
 * @author HexEnsemble
 * @author www.hexensemble.com
 * @version 1.0.1
 * @since Alpha 1.0.0
 */
public class Explosive extends AbstractEntity implements Poolable {

	private boolean explode;
	private float explosionTimer;

	private Cutter cutter;
	private Animation<TextureRegion> stationary;
	private Animation<TextureRegion> explosion;
	private TextureRegion currentFrame;

	/**
	 * @deprecated Use {@code init()}.
	 */
	@Deprecated
	public Explosive() {

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

		explode = false;
		explosionTimer = 0;

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

		if (explode) {
			explosionTimer += delta;
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

		if (!explode) {
			currentFrame = stationary.getKeyFrame(stateTime, true);
		} else {
			currentFrame = explosion.getKeyFrame(stateTime, true);
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

		cutter = new Cutter(sprite, 7, 2);
		stationary = new Animation<TextureRegion>(0.2f, cutter.frames[0], cutter.frames[1], cutter.frames[2], cutter.frames[3],
				cutter.frames[4], cutter.frames[5], cutter.frames[6]);
		explosion = new Animation<TextureRegion>(0.1f, cutter.frames[7], cutter.frames[8], cutter.frames[9], cutter.frames[10],
				cutter.frames[11], cutter.frames[12], cutter.frames[13]);
	}

	@Override
	protected void deathCheck() {
		super.deathCheck();

		if (health <= 0) {
			explode = true;
		}
		if (explosionTimer > 0.4f) {
			Explosion explosion = level.explosionPool.obtain();
			explosion.init(gibType, level, centerX, centerY);
			level.explosions.add(explosion);
			for (Player p : level.players) {
				p.setXp(p.getXp() + xpDrop);
			}
			dead = true;
		}
	}

}
