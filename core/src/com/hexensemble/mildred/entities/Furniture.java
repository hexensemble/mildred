package com.hexensemble.mildred.entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.hexensemble.mildred.levels.Level;
import com.hexensemble.mildred.system.Cutter;

/**
 * Represents a piece of furniture.
 * 
 * @author HexEnsemble
 * @author www.hexensemble.com
 * @version 1.0.1
 * @since Alpha 1.1.0
 */
public class Furniture extends AbstractEntity implements Poolable {

	private Cutter cutter;
	private Animation<TextureRegion> stationary;
	private TextureRegion currentFrame;

	/**
	 * @deprecated Use {@code init()}.
	 */
	@Deprecated
	public Furniture() {
		super();

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

		currentFrame = stationary.getKeyFrame(stateTime, true);

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

		cutter = new Cutter(sprite, 3, 1);
		stationary = new Animation<TextureRegion>(0.2f, cutter.frames[0], cutter.frames[1], cutter.frames[2]);
	}

	@Override
	protected void deathCheck() {
		super.deathCheck();

		if (health <= 0) {
			for (int i = 0; i < 2; i++) {
				Gib g = level.gibPool.obtain();
				g.init(gibType, level, centerX, centerY);
				level.setTile(level.entityLayer, (int) x, (int) y, 383);
				level.gibs.add(g);
			}
			dead = true;
		}
	}

}
