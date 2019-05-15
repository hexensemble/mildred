package com.hexensemble.mildred.entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.hexensemble.mildred.levels.Level;
import com.hexensemble.mildred.system.CoreSettings;
import com.hexensemble.mildred.system.Cutter;

/**
 * Represents a gib.
 * 
 * @author HexEnsemble
 * @author www.hexensemble.com
 * @version 1.0.1
 * @since Alpha 1.0.0
 */
public class Gib extends AbstractEntity implements Poolable {

	private float movingTimer;
	private float maxMovingTime;
	private float lifeTimer;

	private Cutter cutter;
	private Animation<TextureRegion> stationary;
	private Animation<TextureRegion> moving;
	private TextureRegion currentFrame;

	/**
	 * @deprecated Use {@code init()}.
	 */
	@Deprecated
	public Gib() {

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

		angle = Math.random() * 360;
		dX = (int) (Math.cos(Math.toRadians(angle)) * speed);
		dY = (int) (Math.sin(Math.toRadians(angle)) * speed);

		movingTimer = 0;
		maxMovingTime = (float) (Math.random() / 2);
		lifeTimer = 0;

		animationInit();

		sound.play(CoreSettings.sfxVol);
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

		movingTimer += delta;
		lifeTimer += delta;

		if (movingTimer <= maxMovingTime) {
			move(dX, dY);
		} else {
			dX = 0;
			dY = 0;
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

		if (dX != 0 || dY != 0) {
			currentFrame = moving.getKeyFrame(stateTime, true);
		} else {
			currentFrame = stationary.getKeyFrame(stateTime, false);
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
		stationary = new Animation<TextureRegion>(1, cutter.frames[0], cutter.frames[1], cutter.frames[2]);
		moving = new Animation<TextureRegion>(0.1f, cutter.frames[3], cutter.frames[4], cutter.frames[5]);
	}

	@Override
	protected void deathCheck() {
		super.deathCheck();

		if (lifeTimer > 20) {
			dead = true;
		}
	}

}
