package com.hexensemble.mildred.entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.hexensemble.mildred.levels.Level;
import com.hexensemble.mildred.system.Cutter;

/**
 * Represents a sentry.
 * 
 * @author HexEnsemble
 * @author www.hexensemble.com
 * @version Beta 2.2.0
 * @since Beta 2.2.0
 */
public class Sentry extends Enemy implements Poolable {

	/**
	 * @deprecated Use {@code init()}.
	 */
	@Deprecated
	public Sentry() {

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
		if (attacking) {
			currentFrame = attack.getKeyFrame(stateTime, true);
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

	}

	@Override
	protected void animationInit() {
		super.animationInit();

		cutter = new Cutter(sprite, 3, 2);
		stationary = new Animation(1, cutter.frames[0], cutter.frames[1], cutter.frames[2]);
		attack = new Animation(0.1f, cutter.frames[3], cutter.frames[4], cutter.frames[5]);
	}

	@Override
	protected void ai() {
		if (!targetIdentified) {
			for (Player p : level.players) {
				if (distanceToTarget(p) < 12) {
					if (lineOfSightCheck(p)) {
						target = p;
						targetIdentified = true;
						break;
					}
				}
			}
		}

		if (targetIdentified) {
			angle = directionToTarget(target);

			if (distanceToTarget(target) < 8 && lineOfSightCheck(target)) {
				attacking = true;
				fire();
			}

			if (targetTimer > 10 || target.isDead()) {
				targetIdentified = false;
				targetTimer = 0;
			}
		}
	}

}
