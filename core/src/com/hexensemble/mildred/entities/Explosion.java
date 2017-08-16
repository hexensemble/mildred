package com.hexensemble.mildred.entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.hexensemble.mildred.levels.Level;
import com.hexensemble.mildred.system.CoreSettings;
import com.hexensemble.mildred.system.Cutter;

/**
 * Represents an explosion.
 * 
 * @author HexEnsemble
 * @author www.hexensemble.com
 * @version Beta 2.2.0
 * @since Alpha 1.0.0
 */
public class Explosion extends AbstractEntity implements Poolable {

	private float lifeTimer;

	private Cutter cutter;
	private Animation explosion;
	private TextureRegion currentFrame;

	/**
	 * @deprecated Use {@code init()}.
	 */
	@Deprecated
	public Explosion() {

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

		super.x -= width / 2;
		super.y -= height / 2;

		lifeTimer = 0;

		animationInit();

		sound.play(CoreSettings.sfxVol);

		for (int i = 0; i < 6; i++) {
			Gib g = level.gibPool.obtain();
			g.init(gibType, level, centerX, centerY);
			level.gibs.add(g);
		}
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

		lifeTimer += delta;

		entityHitCheck();
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

		currentFrame = explosion.getKeyFrame(stateTime, false);

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

		cutter = new Cutter(sprite, 8, 1);
		explosion = new Animation(0.1f, cutter.frames[0], cutter.frames[1], cutter.frames[2], cutter.frames[3],
				cutter.frames[4], cutter.frames[5], cutter.frames[6], cutter.frames[7]);
	}

	@Override
	protected void deathCheck() {
		super.deathCheck();

		if (lifeTimer > 0.7f) {
			dead = true;
		}
	}

	private void entityHitCheck() {
		for (Player p : level.players) {
			if (bounds.overlaps(p.getBounds())) {
				p.setHealth(p.getHealth() - damage);
			}
		}
		for (Enemy e : level.enemies) {
			if (bounds.overlaps(e.getBounds())) {
				e.setHealth(e.getHealth() - damage);
			}
		}
		for (Furniture f : level.furnishings) {
			if (bounds.overlaps(f.getBounds())) {
				f.setHealth(f.getHealth() - damage);
			}
		}
		for (Spawner s : level.spawners) {
			if (bounds.overlaps(s.getBounds())) {
				s.setHealth(s.getHealth() - damage);
			}
		}
		for (Explosive ex : level.explosives) {
			if (bounds.overlaps(ex.getBounds())) {
				ex.setHealth(ex.getHealth() - damage);
			}
		}
	}

}
