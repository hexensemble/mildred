package com.hexensemble.mildred.entities;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.hexensemble.mildred.levels.Level;
import com.hexensemble.mildred.system.CoreSettings;
import com.hexensemble.mildred.system.Cutter;

/**
 * Represents a boss.
 * 
 * @author HexEnsemble
 * @author www.hexensemble.com
 * @version 1.0.1
 * @since Beta 2.2.0
 */
public class Boss extends Enemy implements Poolable {

	private Sound deathSound;

	/**
	 * @deprecated Use {@code init()}.
	 */
	@Deprecated
	public Boss() {

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

		deathSound = level.assetManager.get("sounds/boss-death.wav", Sound.class);

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
		if (dX != 0 || dY != 0) {
			if (dY > 0) {
				currentFrame = up.getKeyFrame(stateTime, true);
			}
			if (dY < 0) {
				currentFrame = down.getKeyFrame(stateTime, true);
			}
			if (dX < 0) {
				currentFrame = left.getKeyFrame(stateTime, true);
			}
			if (dX > 0) {
				currentFrame = right.getKeyFrame(stateTime, true);
			}
		} else if (attacking) {
			currentFrame = attack.getKeyFrame(stateTime, true);
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

	}

	@Override
	protected void animationInit() {
		super.animationInit();

		cutter = new Cutter(sprite, 3, 6);
		stationary = new Animation<TextureRegion>(0.2f, cutter.frames[0], cutter.frames[1], cutter.frames[2]);
		up = new Animation<TextureRegion>(0.2f, cutter.frames[3], cutter.frames[4], cutter.frames[5]);
		down = new Animation<TextureRegion>(0.2f, cutter.frames[6], cutter.frames[7], cutter.frames[8]);
		left = new Animation<TextureRegion>(0.2f, cutter.frames[9], cutter.frames[10], cutter.frames[11]);
		right = new Animation<TextureRegion>(0.2f, cutter.frames[12], cutter.frames[13], cutter.frames[14]);
		attack = new Animation<TextureRegion>(0.1f, cutter.frames[15], cutter.frames[16], cutter.frames[17]);
	}

	@Override
	protected void deathCheck() {
		if (health <= 0) {
			deathSound.play(CoreSettings.sfxVol);

			for (int i = 0; i < 100; i++) {
				Gib g = level.gibPool.obtain();
				g.init(gibType, level, centerX, centerY);
				level.gibs.add(g);
			}

			Explosion explosion1 = level.explosionPool.obtain();
			explosion1.init(EntityType.EXPLOSION_LARGE, level, centerX, centerY);
			level.explosions.add(explosion1);
			Explosion explosion2 = level.explosionPool.obtain();
			explosion2.init(EntityType.EXPLOSION_MEDIUM, level, x, y + (height / 2));
			level.explosions.add(explosion2);
			Explosion explosion3 = level.explosionPool.obtain();
			explosion3.init(EntityType.EXPLOSION_MEDIUM, level, x + width, y + (height / 2));
			level.explosions.add(explosion3);
			Explosion explosion4 = level.explosionPool.obtain();
			explosion4.init(EntityType.EXPLOSION_SMALL, level, x + (width / 2), y + height);
			level.explosions.add(explosion4);
			Explosion explosion5 = level.explosionPool.obtain();
			explosion5.init(EntityType.EXPLOSION_SMALL, level, x, y);
			level.explosions.add(explosion5);
			Explosion explosion6 = level.explosionPool.obtain();
			explosion6.init(EntityType.EXPLOSION_SMALL, level, x + width, y);
			level.explosions.add(explosion6);

			for (Player p : level.players) {
				p.setXp(p.getXp() + xpDrop);
			}

			Item item = level.itemPool.obtain();
			item.init(EntityType.ITEM_EXIT_KEY, level, centerX - (EntityType.ITEM_EXIT_KEY.width / 2),
					centerY - (EntityType.ITEM_EXIT_KEY.height / 2));
			level.items.add(item);

			dead = true;
		}
	}

	@Override
	protected void fire() {
		if (fireTimer <= 0) {
			angle = directionToTarget(target);
			Projectile p = level.projectilePool.obtain();
			p.init(projectileType, level, centerX, centerY, angle, entityID);
			level.projectiles.add(p);
			for (int i = 0; i < 8; i++) {
				Projectile pSpray = level.projectilePool.obtain();
				pSpray.init(projectileType, level, centerX, centerY, 45 * i, entityID);
				level.projectiles.add(pSpray);
			}
			fireTimer = actionValue;
		}
	}

}
