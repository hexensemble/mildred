package com.hexensemble.mildred.entities;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.hexensemble.mildred.levels.Level;

/**
 * Abstract class for all entities.
 * 
 * @author HexEnsemble
 * @author www.hexensemble.com
 * @version Beta 2.2.0
 * @since Alpha 1.0.0
 */
public abstract class AbstractEntity {

	protected EntityType type;
	protected Level level;

	protected float width;
	protected float height;
	protected float speed;
	protected int health;
	protected int damage;
	protected float actionValue;
	protected int attackType;
	protected EntityType projectileType;
	protected EntityType gibType;
	protected int xpDrop;
	protected String spriteName;
	protected String soundFile;

	protected TextureAtlas textureAtlas;
	protected AtlasRegion atlasRegion;
	protected Sprite sprite;
	protected float stateTime;
	protected Sound sound;

	protected float x;
	protected float y;
	protected float centerX;
	protected float centerY;
	protected Vector2 position;
	protected Rectangle bounds;
	protected double angle;
	protected float dX;
	protected float dY;
	protected int entityID;
	protected boolean dead;

	protected float delta;

	/**
	 * @deprecated Use {@code init()}.
	 */
	@Deprecated
	public AbstractEntity() {

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
	public void init(EntityType type, Level level, float x, float y) {
		this.type = type;
		this.level = level;

		width = type.width;
		height = type.height;
		speed = type.speed;
		health = type.health;
		damage = type.damage;
		actionValue = type.actionValue;
		attackType = type.attackType;
		projectileType = type.projectileType;
		gibType = type.gibType;
		xpDrop = type.xpDrop;
		spriteName = type.spriteName;
		soundFile = type.soundFile;

		animationInit();
		if (soundFile != null) {
			sound = level.assetManager.get(soundFile, Sound.class);
		}

		this.x = x;
		this.y = y;
		centerX = x + (width / 2);
		centerY = y + (height / 2);
		position = new Vector2((int) centerX, (int) centerY);
		bounds = new Rectangle(x, y, width, height);
		angle = 0;
		dX = 0;
		dY = 0;
		entityID = MathUtils.random(Integer.MAX_VALUE - 1);
		dead = false;
	}

	/**
	 * Update.
	 * 
	 * @param delta
	 *            Delta time.
	 */
	public void update(float delta) {
		this.delta = delta;

		stateTime += delta;

		centerX = x + (width / 2);
		centerY = y + (height / 2);
		position.set((int) centerX, (int) centerY);
		bounds.set(x, y, width, height);

		deathCheck();
	}

	/**
	 * Render.
	 * 
	 * @param batch
	 *            Sprite batch.
	 */
	public void render(SpriteBatch batch) {

	}

	/**
	 * Dispose.
	 */
	public void dispose() {

	}

	/**
	 * Resets variables for {@code ObjectPool} reallocation.
	 */
	public void reset() {

	}

	protected void animationInit() {
		textureAtlas = level.assetManager.get("graphics/entities.atlas", TextureAtlas.class);
		atlasRegion = textureAtlas.findRegion(spriteName);
		sprite = new Sprite(atlasRegion);
		stateTime = 0;
	}

	protected void deathCheck() {

	}

	/**
	 * Moves the entity.
	 * 
	 * @param dX
	 *            direction X value.
	 * @param dY
	 *            direction Y value.
	 */
	public void move(float dX, float dY) {
		if (dX != 0 && dY != 0) {
			move(dX, 0);
			move(0, dY);
			return;
		}

		if (!collisionTiles() && !collisionEntities()) {
			x += dX * delta;
			y += dY * delta;
		}
	}

	/**
	 * Checks for tile collisions.
	 * 
	 * @return {@code true} if a tile collision occurs.
	 */
	public boolean collisionTiles() {
		boolean collision = false;

		float divisionValue = 0;
		if (width <= 1) {
			divisionValue = 32;
		}
		if (width == 2) {
			divisionValue = 16;
		}
		if (width >= 4) {
			divisionValue = 2;
		}

		if (level.tileBlockedCheck((int) (centerX + (width / 2) + (dX / divisionValue)), (int) centerY)) {
			collision = true;
		}
		if (level.tileBlockedCheck((int) (centerX - (width / 2) + (dX / divisionValue)), (int) centerY)) {
			collision = true;
		}
		if (level.tileBlockedCheck((int) centerX, (int) (centerY + (height / 2) + (dY / divisionValue)))) {
			collision = true;
		}
		if (level.tileBlockedCheck((int) centerX, (int) (centerY - (height / 2) + (dY / divisionValue)))) {
			collision = true;
		}
		if (level.tileBlockedCheck((int) (centerX + (width / 2) + (dX / divisionValue)),
				(int) (centerY + (height / 2) + (dY / divisionValue)))) {
			collision = true;
		}
		if (level.tileBlockedCheck((int) (centerX - (width / 2) + (dX / divisionValue)),
				(int) (centerY - (height / 2) + (dY / divisionValue)))) {
			collision = true;
		}
		if (level.tileBlockedCheck((int) (centerX + (width / 2) + (dX / divisionValue)),
				(int) (centerY - (height / 2) + (dY / divisionValue)))) {
			collision = true;
		}
		if (level.tileBlockedCheck((int) (centerX - (width / 2) + (dX / divisionValue)),
				(int) (centerY + (height / 2) + (dY / divisionValue)))) {
			collision = true;
		}

		return collision;
	}

	/**
	 * Checks for entity collisions.
	 * 
	 * @return {@code true} if an entity collision occurs.
	 */
	public boolean collisionEntities() {
		boolean collision = false;

		float divisionValue = 0;
		if (width <= 1) {
			divisionValue = 32;
		}
		if (width == 2) {
			divisionValue = 16;
		}
		if (width >= 4) {
			divisionValue = 2;
		}

		if (level.entityBlockedCheck((int) (centerX + (width / 2) + (dX / divisionValue)), (int) centerY)) {
			collision = true;
		}
		if (level.entityBlockedCheck((int) (centerX - (width / 2) + (dX / divisionValue)), (int) centerY)) {
			collision = true;
		}
		if (level.entityBlockedCheck((int) centerX, (int) (centerY + (height / 2) + (dY / divisionValue)))) {
			collision = true;
		}
		if (level.entityBlockedCheck((int) centerX, (int) (centerY - (height / 2) + (dY / divisionValue)))) {
			collision = true;
		}
		if (level.entityBlockedCheck((int) (centerX + (width / 2) + (dX / divisionValue)),
				(int) (centerY + (height / 2) + (dY / divisionValue)))) {
			collision = true;
		}
		if (level.entityBlockedCheck((int) (centerX - (width / 2) + (dX / divisionValue)),
				(int) (centerY - (height / 2) + (dY / divisionValue)))) {
			collision = true;
		}
		if (level.entityBlockedCheck((int) (centerX + (width / 2) + (dX / divisionValue)),
				(int) (centerY - (height / 2) + (dY / divisionValue)))) {
			collision = true;
		}
		if (level.entityBlockedCheck((int) (centerX - (width / 2) + (dX / divisionValue)),
				(int) (centerY + (height / 2) + (dY / divisionValue)))) {
			collision = true;
		}

		return collision;
	}

	/**
	 * Gets the width.
	 * 
	 * @return {@code width}
	 */
	public float getWidth() {
		return width;
	}

	/**
	 * Gets the height.
	 * 
	 * @return {@code height}
	 */
	public float getHeight() {
		return height;
	}

	/**
	 * Gets the speed.
	 * 
	 * @return {@code speed}
	 */
	public float getSpeed() {
		return speed;
	}

	/**
	 * Gets the health value.
	 * 
	 * @return {@code health}
	 */
	public int getHealth() {
		return health;
	}

	/**
	 * Gets the damage value.
	 * 
	 * @return {@code damage}
	 */
	public int getDamage() {
		return damage;
	}

	/**
	 * Gets the action value.
	 * 
	 * @return {@code actionValue}
	 */
	public float getActionValue() {
		return actionValue;
	}

	/**
	 * Gets the X position.
	 * 
	 * @return {@code x}
	 */
	public float getX() {
		return x;
	}

	/**
	 * Gets the Y position.
	 * 
	 * @return {@code y}
	 */
	public float getY() {
		return y;
	}

	/**
	 * Gets the center X position.
	 * 
	 * @return {@code centerX}
	 */
	public float getCenterX() {
		return centerX;
	}

	/**
	 * Gets the center Y position.
	 * 
	 * @return {@code centerY}
	 */
	public float getCenterY() {
		return centerY;
	}

	/**
	 * Gets a Vector2 containing X/Y positions.
	 * 
	 * @return {@code position}
	 */
	public Vector2 getPosition() {
		return position;
	}

	/**
	 * Gets the bounds.
	 * 
	 * @return {@code bounds}
	 */
	public Rectangle getBounds() {
		return bounds;
	}

	/**
	 * Gets the angle.
	 * 
	 * @return {@code angle}
	 */
	public double getAngle() {
		return angle;
	}

	/**
	 * Gets the direction X value.
	 * 
	 * @return {@code dX}
	 */
	public float getDX() {
		return dX;
	}

	/**
	 * Gets the direction Y value.
	 * 
	 * @return {@code dY}
	 */
	public float getDY() {
		return dY;
	}

	/**
	 * Gets the entity ID.
	 * 
	 * @return {@code entityID}
	 */
	public int getEntityID() {
		return entityID;
	}

	/**
	 * Is entity dead?
	 * 
	 * @return {@code true} if dead.
	 */
	public boolean isDead() {
		return dead;
	}

	/**
	 * Sets the health value.
	 * 
	 * @param health
	 *            Health value.
	 */
	public void setHealth(int health) {
		this.health = health;
	}

	/**
	 * Sets the damage value.
	 * 
	 * @param damage
	 *            Damage value.
	 */
	public void setDamage(int damage) {
		this.damage = damage;
	}

}
