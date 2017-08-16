package com.hexensemble.mildred.entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.hexensemble.mildred.levels.Level;
import com.hexensemble.mildred.system.CoreSettings;
import com.hexensemble.mildred.system.Cutter;
import com.hexensemble.mildred.system.HUD;

/**
 * Represents an item.
 * 
 * @author HexEnsemble
 * @author www.hexensemble.com
 * @version Beta 2.2.0
 * @since Alpha 1.0.0
 */
public class Item extends AbstractEntity implements Poolable {

	private Cutter cutter;
	private Animation stationary;
	private TextureRegion currentFrame;

	/**
	 * @deprecated Use {@code init()}.
	 */
	@Deprecated
	public Item() {

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
		stationary = new Animation(0.4f, cutter.frames[0], cutter.frames[1], cutter.frames[2]);
	}

	@Override
	protected void deathCheck() {
		super.deathCheck();

		for (Player p : level.players) {
			if (distanceToPlayer(p) < 2) {
				if (p.getBounds().overlaps(bounds)) {
					if (type == EntityType.ITEM_HEALTH && p.getHealth() != p.getMaxHealth()) {
						p.setHealth(p.getHealth() + (int) actionValue);
						p.setXp(p.getXp() + xpDrop);
						sound.play(CoreSettings.sfxVol);
						dead = true;
					}
					if (type == EntityType.ITEM_ENERGY && p.getEnergy() != p.getMaxEnergy()) {
						p.setEnergy(p.getEnergy() + (int) actionValue);
						p.setXp(p.getXp() + xpDrop);
						sound.play(CoreSettings.sfxVol);
						dead = true;
					}
					if (type == EntityType.ITEM_XP) {
						p.setXp(p.getXp() + xpDrop);
						sound.play(CoreSettings.sfxVol);
						dead = true;
					}
					if (type == EntityType.ITEM_EXIT_KEY) {
						p.setExitKey(true);
						p.setXp(p.getXp() + xpDrop);
						HUD.showExitKey = true;
						sound.play(CoreSettings.sfxVol);
						dead = true;
					}
					if (type == EntityType.ITEM_QUAD_DAMAGE) {
						p.setQuadDamage(true);
						p.setXp(p.getXp() + xpDrop);
						HUD.quadDamage();
						sound.play(CoreSettings.sfxVol);
						dead = true;
					}
					if (type == EntityType.ITEM_INVINCIBLE) {
						p.setInvincible(true);
						p.setHealth(p.getMaxHealth());
						p.setXp(p.getXp() + xpDrop);
						HUD.invincible();
						sound.play(CoreSettings.sfxVol);
						dead = true;
					}
					if (type == EntityType.ITEM_SPRAY) {
						p.setSpray(true);
						p.setXp(p.getXp() + xpDrop);
						HUD.spray();
						sound.play(CoreSettings.sfxVol);
						dead = true;
					}
				}
			}
		}
	}

	private float distanceToPlayer(Player player) {
		float distance = 0;
		float x1 = player.getCenterX();
		float x2 = centerX;
		float y1 = player.getCenterY();
		float y2 = centerY;
		distance = (float) Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
		return distance;
	}

}
