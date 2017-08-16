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
 * Represents a projectile.
 * 
 * @author HexEnsemble
 * @author www.hexensemble.com
 * @version Beta 2.2.0
 * @since Alpha 1.0.0
 */
public class Projectile extends AbstractEntity implements Poolable {

	private float lifeTimer;
	private int originID;

	private Sound hitPlayer;
	private Sound hitEnemy;
	private Sound hitBoss;
	private Sound hitFurniture;
	private Sound hitSpawner;
	private Sound hitExplosive;
	private Sound hitSentry;
	private Sound hitWall;

	private Cutter cutter;
	private Animation fire;
	private TextureRegion currentFrame;

	/**
	 * @deprecated Use {@code init()}.
	 */
	@Deprecated
	public Projectile() {

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
	 * @param angle
	 *            Initial angle.
	 * @param originID
	 *            Origin entity ID.
	 */
	public void init(EntityType type, Level level, float x, float y, double angle, int originID) {
		super.init(type, level, x, y);

		this.angle = angle;
		dX = (int) (Math.cos(Math.toRadians(angle)) * speed);
		dY = (int) (Math.sin(Math.toRadians(angle)) * speed);

		lifeTimer = 0;
		this.originID = originID;

		hitPlayer = level.assetManager.get("sounds/hit-player.wav", Sound.class);
		hitEnemy = level.assetManager.get("sounds/hit-enemy.wav", Sound.class);
		hitBoss = level.assetManager.get("sounds/hit-boss.wav", Sound.class);
		hitFurniture = level.assetManager.get("sounds/hit-furniture.wav", Sound.class);
		hitSpawner = level.assetManager.get("sounds/hit-spawner.wav", Sound.class);
		hitExplosive = level.assetManager.get("sounds/hit-explosive.wav", Sound.class);
		hitSentry = level.assetManager.get("sounds/hit-sentry.wav", Sound.class);
		hitWall = level.assetManager.get("sounds/hit-wall.wav", Sound.class);

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

		lifeTimer += delta;

		move(dX, dY);

		for (Player p : level.players) {
			if (originID == p.getEntityID() && p.isQuadDamage()) {
				damage += 4;
			}
		}

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

		currentFrame = fire.getKeyFrame(stateTime, true);

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
		fire = new Animation(0.1f, cutter.frames[0], cutter.frames[1], cutter.frames[2], cutter.frames[3],
				cutter.frames[4], cutter.frames[5], cutter.frames[6], cutter.frames[7]);
	}

	@Override
	protected void deathCheck() {
		super.deathCheck();

		if (lifeTimer > 0.5) {
			dead = true;
		}
		if (collisionTiles()) {
			hitWall.play(CoreSettings.sfxVol);
			dead = true;
		}
	}

	/**
	 * Moves the entity.
	 * 
	 * @param dX
	 *            direction X value.
	 * @param dY
	 *            direction Y value.
	 */
	@Override
	public void move(float dX, float dY) {
		if (dX != 0 && dY != 0) {
			move(dX, 0);
			move(0, dY);
			return;
		}

		if (!collisionTiles()) {
			x += dX * delta;
			y += dY * delta;
		}
	}

	private void entityHitCheck() {
		for (Player p : level.players) {
			if (originID != p.getEntityID() && bounds.overlaps(p.getBounds())) {
				hitPlayer.play(CoreSettings.sfxVol);
				p.setHealth(p.getHealth() - damage);
				dead = true;
			}
		}
		for (Enemy e : level.enemies) {
			if (originID != e.getEntityID() && bounds.overlaps(e.getBounds())) {
				hitEnemy.play(CoreSettings.sfxVol);
				e.setHealth(e.getHealth() - damage);
				dead = true;
			}
		}
		for (Boss b : level.bosses) {
			if (originID != b.getEntityID() && bounds.overlaps(b.getBounds())) {
				hitBoss.play(CoreSettings.sfxVol);
				b.setHealth(b.getHealth() - damage);
				dead = true;
			}
		}
		for (Furniture f : level.furnishings) {
			if (originID != f.getEntityID() && bounds.overlaps(f.getBounds())) {
				hitFurniture.play(CoreSettings.sfxVol);
				f.setHealth(f.getHealth() - damage);
				dead = true;
			}
		}
		for (Spawner s : level.spawners) {
			if (originID != s.getEntityID() && bounds.overlaps(s.getBounds())) {
				hitSpawner.play(CoreSettings.sfxVol);
				s.setHealth(s.getHealth() - damage);
				dead = true;
			}
		}
		for (Explosive ex : level.explosives) {
			if (originID != ex.getEntityID() && bounds.overlaps(ex.getBounds())) {
				hitExplosive.play(CoreSettings.sfxVol);
				ex.setHealth(ex.getHealth() - damage);
				dead = true;
			}
		}
		for (Sentry s : level.sentries) {
			if (originID != s.getEntityID() && bounds.overlaps(s.getBounds())) {
				hitSentry.play(CoreSettings.sfxVol);
				s.setHealth(s.getHealth() - damage);
				dead = true;
			}
		}
	}

}
