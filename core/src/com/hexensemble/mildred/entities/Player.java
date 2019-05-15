package com.hexensemble.mildred.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.hexensemble.mildred.levels.Level;
import com.hexensemble.mildred.system.CoreSettings;
import com.hexensemble.mildred.system.Cutter;
import com.hexensemble.mildred.system.HUD;

/**
 * Represents a player.
 * 
 * @author HexEnsemble
 * @author www.hexensemble.com
 * @version 1.0.1
 * @since Alpha 1.0.0
 */
public class Player extends AbstractEntity implements Poolable {

	private int xp;
	private int xpLevel;
	private int xpLevelCheck;
	private int maxHealth;
	private int maxEnergy;
	private int energy;
	private float fireTimer;
	private boolean attacking;
	private boolean exitKey;
	private boolean quadDamage;
	private float quadDamageTimer;
	private boolean invincible;
	private float invincibleTimer;
	private boolean spray;
	private float sprayTimer;

	private Sound hitEnemy;

	private Cutter cutter;
	private Animation<TextureRegion> stationary;
	private Animation<TextureRegion> up;
	private Animation<TextureRegion> down;
	private Animation<TextureRegion> left;
	private Animation<TextureRegion> right;
	private Animation<TextureRegion> attack;
	private TextureRegion currentFrame;

	/**
	 * @deprecated Use {@code init()}.
	 */
	@Deprecated
	public Player() {

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

		xp = 0;
		xpLevel = xp / 1000 + 1;
		xpLevelCheck = xpLevel;
		maxHealth = xpLevel * 25;
		maxEnergy = xpLevel * 25;
		energy = 25;
		fireTimer = 0;
		attacking = false;
		exitKey = false;
		quadDamage = false;
		quadDamageTimer = 0;
		invincible = false;
		invincibleTimer = 0;
		spray = false;
		sprayTimer = 0;

		hitEnemy = level.assetManager.get("sounds/hit-enemy.wav", Sound.class);

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

		fireTimer -= delta;

		if (quadDamage) {
			quadDamageTimer += delta;
			if (quadDamageTimer > 25) {
				quadDamage = false;
				quadDamageTimer = 0;
			}
		}
		if (invincible) {
			invincibleTimer += delta;
			health = maxHealth;
			if (invincibleTimer > 25) {
				invincible = false;
				invincibleTimer = 0;
			}
		}
		if (spray) {
			sprayTimer += delta;
			if (sprayTimer > 25) {
				spray = false;
				sprayTimer = 0;
			}
		}

		dX = 0;
		dY = 0;

		xpLevelUpdate();

		if (health > maxHealth) {
			health = maxHealth;
		}
		if (energy > maxEnergy) {
			energy = maxEnergy;
		}

		attacking = false;

		controls();

		angle = directionToMouse();

		triggerCheck();
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

		cutter = new Cutter(sprite, 3, 6);
		stationary = new Animation<TextureRegion>(1, cutter.frames[0], cutter.frames[1], cutter.frames[2]);
		up = new Animation<TextureRegion>(0.2f, cutter.frames[3], cutter.frames[4], cutter.frames[5]);
		down = new Animation<TextureRegion>(0.2f, cutter.frames[6], cutter.frames[7], cutter.frames[8]);
		left = new Animation<TextureRegion>(0.2f, cutter.frames[9], cutter.frames[10], cutter.frames[11]);
		right = new Animation<TextureRegion>(0.2f, cutter.frames[12], cutter.frames[13], cutter.frames[14]);
		attack = new Animation<TextureRegion>(0.1f, cutter.frames[15], cutter.frames[16], cutter.frames[17]);
	}

	@Override
	protected void deathCheck() {
		super.deathCheck();

		if (health <= 0) {
			for (int i = 0; i < 8; i++) {
				Gib g = level.gibPool.obtain();
				g.init(gibType, level, centerX, centerY);
				level.gibs.add(g);
			}
			dead = true;
		}
	}

	private void controls() {
		if (Gdx.input.isButtonPressed(Input.Buttons.LEFT) && energy > 0
				|| Gdx.input.isButtonPressed(Input.Buttons.RIGHT) && energy > 0) {
			fire();
			attacking = true;
		}
		if (Gdx.input.isButtonPressed(Input.Buttons.LEFT) && energy == 0
				|| Gdx.input.isButtonPressed(Input.Buttons.RIGHT) && energy == 0) {
			melee();
			attacking = true;
		}

		if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)) {
			dX = 0;
			dY = speed;
			move(dX, dY);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			dX = 0;
			dY = -speed;
			move(dX, dY);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			dX = -speed;
			dY = 0;
			move(dX, dY);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			dX = speed;
			dY = 0;
			move(dX, dY);
		}

		if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
			openDoor();
		}
	}

	private float directionToMouse() {
		float angle = 0;
		float distanceX = level.getMousePosition().x - centerX;
		float distanceY = level.getMousePosition().y - centerY;
		angle = (float) (Math.toDegrees(Math.atan2(distanceY, distanceX)));
		return angle;
	}

	private void fire() {
		if (fireTimer <= 0) {
			if (spray) {
				Projectile p = level.projectilePool.obtain();
				p.init(projectileType, level, centerX, centerY, angle, entityID);
				level.projectiles.add(p);
				for (int i = 0; i < 8; i++) {
					Projectile pSpray = level.projectilePool.obtain();
					pSpray.init(projectileType, level, centerX, centerY, 45 * i, entityID);
					level.projectiles.add(pSpray);
				}
				fireTimer = actionValue;
				energy--;
			} else {
				Projectile p = level.projectilePool.obtain();
				p.init(projectileType, level, centerX, centerY, angle, entityID);
				level.projectiles.add(p);
				fireTimer = actionValue;
				energy--;
			}
		}
	}

	private void melee() {
		if (fireTimer <= 0) {
			sound.play(CoreSettings.sfxVol);
			for (int i = 0; i < 9; i++) {
				int xi = (i % 3) - 1;
				int yi = (i / 3) - 1;
				Vector2 iPosition = new Vector2((int) (centerX + xi), (int) (centerY + yi));
				for (int j = 0; j < level.enemies.size; j++) {
					if (level.enemies.get(j).getPosition().equals(iPosition)) {
						hitEnemy.play(CoreSettings.sfxVol);
						level.enemies.get(j).setHealth(level.enemies.get(j).getHealth() - damage);
					}
				}
			}
			fireTimer = actionValue;
		}
	}

	/**
	 * Opens a door if one exists.
	 */
	public void openDoor() {
		for (int i = 0; i < 9; i++) {
			int xi = (i % 3) - 1;
			int yi = (i / 3) - 1;
			if (level.tileDoorCheck((int) (centerX + xi), (int) (centerY + yi))) {
				Vector2 iPosition = new Vector2((int) (centerX + xi), (int) (centerY + yi));
				for (int j = 0; j < level.doors.size; j++) {
					if (level.doors.get(j).getPosition().equals(iPosition)) {
						level.doors.get(j).openDoor();
					}
				}
			}
		}
	}

	private void xpLevelUpdate() {
		if (xp < 1000) {
			xpLevel = 1;
		}
		if (xp >= 1000 && xp < 3000) {
			xpLevel = 2;
		}
		if (xp >= 3000 && xp < 6000) {
			xpLevel = 3;
		}
		if (xp >= 6000 && xp < 10000) {
			xpLevel = 4;
		}
		if (xp >= 10000 && xp < 15000) {
			xpLevel = 5;
		}
		if (xp >= 15000 && xp < 21000) {
			xpLevel = 6;
		}

		if (xpLevelCheck != xpLevel) {
			HUD.showLeveledUp = true;
			xpLevelCheck = xpLevel;
		}

		maxHealth = xpLevel * 25;
		maxEnergy = xpLevel * 25;

		speed = xpLevel + 3;

		actionValue = 1 - (xpLevel / 25);

		switch (xpLevel) {
		case 1:
			projectileType = EntityType.PROJECTILE_ENERGY_1;
			break;
		case 2:
			projectileType = EntityType.PROJECTILE_ENERGY_2;
			break;
		case 3:
			projectileType = EntityType.PROJECTILE_ENERGY_3;
			break;
		case 4:
			projectileType = EntityType.PROJECTILE_LIGHTNING;
			break;
		case 5:
			projectileType = EntityType.PROJECTILE_FLAME;
			break;
		case 6:
			projectileType = EntityType.PROJECTILE_SPARK;
			break;
		default:
			projectileType = EntityType.PROJECTILE_SPARK;
			break;
		}
	}

	private void triggerCheck() {
		if (level.tileActionCheck((int) centerX, (int) centerY)) {
			Cell cell = level.triggerLayer.getCell((int) centerX, (int) centerY);
			if (cell.getTile().getProperties().containsKey("trigger-action01")) {
				level.triggerResolve("trigger-resolve01");
			}
			if (cell.getTile().getProperties().containsKey("trigger-action02")) {
				level.triggerResolve("trigger-resolve02");
			}
			if (cell.getTile().getProperties().containsKey("trigger-action03")) {
				level.triggerResolve("trigger-resolve03");
			}

			for (int i = 0; i < 25; i++) {
				int xi = (i % 5) - 2;
				int yi = (i / 5) - 2;
				if (!level.tileNullCheck(level.triggerLayer, (int) (centerX + xi), (int) (centerY + yi))) {
					level.setTile(level.triggerLayer, (int) (centerX + xi), (int) (centerY + yi), 767);
				}
			}
		}

		if (level.tileSecretCheck((int) centerX, (int) centerY)) {
			level.setSecrets(level.getSecrets() + 1);
			HUD.showSecret = true;

			for (int i = 0; i < 9; i++) {
				int xi = (i % 3) - 1;
				int yi = (i / 3) - 1;
				if (!level.tileNullCheck(level.triggerLayer, (int) (centerX + xi), (int) (centerY + yi))) {
					level.setTile(level.triggerLayer, (int) (centerX + xi), (int) (centerY + yi), 767);
				}
			}
		}

		if (level.tileRevealCheck((int) centerX, (int) centerY)) {
			for (int i = 0; i < 49; i++) {
				int xi = (i % 7) - 3;
				int yi = (i / 7) - 3;
				if (!level.tileNullCheck(level.overlayLayer, (int) (centerX + xi), (int) (centerY + yi))) {
					level.setTile(level.overlayLayer, (int) (centerX + xi), (int) (centerY + yi), 767);
				}
			}
		}

		if (level.tileExitCheck((int) centerX, (int) centerY)) {
			if (!exitKey) {
				HUD.showNoExitKey = true;
			} else {
				level.setLevelComplete(true);
			}
		}
	}

	/**
	 * Gets the XP value.
	 * 
	 * @return {@code xp}
	 */
	public int getXp() {
		return xp;
	}

	/**
	 * Gets the XP level.
	 * 
	 * @return {@code xpLevel}
	 */
	public int getXpLevel() {
		return xpLevel;
	}

	/**
	 * Gets the XP level check value.
	 * 
	 * @return {@code xpLevelCheck}
	 */
	public int getXpLevelCheck() {
		return xpLevelCheck;
	}

	/**
	 * Gets the max health value.
	 * 
	 * @return {@code maxHealth}
	 */
	public int getMaxHealth() {
		return maxHealth;
	}

	/**
	 * Gets the max energy value.
	 * 
	 * @return {@code maxEnergy}
	 */
	public int getMaxEnergy() {
		return maxEnergy;
	}

	/**
	 * Gets the energy value.
	 * 
	 * @return {@code energy}
	 */
	public int getEnergy() {
		return energy;
	}

	/**
	 * Is Quad Damage on/off?
	 * 
	 * @return {@code quadDamage}
	 */
	public boolean isQuadDamage() {
		return quadDamage;
	}

	/**
	 * Sets the XP value.
	 * 
	 * @param xp
	 *            XP value.
	 */
	public void setXp(int xp) {
		this.xp = xp;
	}

	/**
	 * Sets the XP level.
	 * 
	 * @param xpLevel
	 *            XP level.
	 */
	public void setXpLevel(int xpLevel) {
		this.xpLevel = xpLevel;
	}

	/**
	 * Sets the XP level check value.
	 * 
	 * @param xpLevelCheck
	 *            XP level check value.
	 */
	public void setXpLevelCheck(int xpLevelCheck) {
		this.xpLevelCheck = xpLevelCheck;
	}

	/**
	 * Sets the energy value.
	 * 
	 * @param energy
	 *            Energy value.
	 */
	public void setEnergy(int energy) {
		this.energy = energy;
	}

	/**
	 * Sets player to have exit key.
	 * 
	 * @param exitKey
	 *            True/false.
	 */
	public void setExitKey(boolean exitKey) {
		this.exitKey = exitKey;
	}

	/**
	 * Sets Quad Damage on/off.
	 * 
	 * @param quadDamage
	 *            True/false.
	 */
	public void setQuadDamage(boolean quadDamage) {
		this.quadDamage = quadDamage;
	}

	/**
	 * Sets Invincible on/off.
	 * 
	 * @param invincible
	 *            True/false.
	 */
	public void setInvincible(boolean invincible) {
		this.invincible = invincible;
	}

	/**
	 * Sets Spray on/off.
	 * 
	 * @param spray
	 *            True/false.
	 */
	public void setSpray(boolean spray) {
		this.spray = spray;
	}

}
