package com.hexensemble.mildred.entities;

/**
 * Entity types.
 * 
 * @author HexEnsemble
 * @author www.hexensemble.com
 * @version Beta 2.2.0
 * @since Alpha 2.0.0
 */
public enum EntityType {

	PROJECTILE_ENERGY_1(0.25f, 0.25f, 20, 666666, 5, 0, AttackType.NONE, null, null, 0, "projectile-energy1", "sounds/projectile-energy1.wav"),
	PROJECTILE_ENERGY_2(0.25f, 0.25f, 25, 666666, 10, 0, AttackType.NONE, null, null, 0, "projectile-energy2", "sounds/projectile-energy2.wav"),
	PROJECTILE_ENERGY_3(0.25f, 0.25f, 30, 666666, 15, 0, AttackType.NONE, null, null, 0, "projectile-energy3", "sounds/projectile-energy3.wav"),
	PROJECTILE_LIGHTNING(0.25f, 0.25f, 35, 666666, 20, 0, AttackType.NONE, null, null, 0, "projectile-lightning", "sounds/projectile-lightning.wav"),
	PROJECTILE_FLAME(0.25f, 0.25f, 40, 666666, 25, 0, AttackType.NONE, null, null, 0, "projectile-flame", "sounds/projectile-flame.wav"),
	PROJECTILE_SPARK(0.25f, 0.25f, 45, 666666, 30, 0, AttackType.NONE, null, null, 0, "projectile-spark", "sounds/projectile-spark.wav"),

	GIB_WHITE(0.5f, 0.5f, 10, 666666, 0, 0, AttackType.NONE, null, null, 0, "gib-white", "sounds/gib-white.wav"),
	GIB_RED(0.5f, 0.5f, 10, 666666,0, 0, AttackType.NONE, null, null, 0, "gib-red", "sounds/gib-red.wav"),
	GIB_GREEN(0.5f, 0.5f, 10, 666666, 0, 0, AttackType.NONE, null, null, 0, "gib-green", "sounds/gib-green.wav"),
	GIB_YELLOW(0.5f, 0.5f, 10, 666666, 0, 0, AttackType.NONE, null, null, 0, "gib-yellow", "sounds/gib-yellow.wav"),
	GIB_BLOOD(0.5f, 0.5f, 5, 666666, 0, 0, AttackType.NONE, null, null, 0, "gib-blood", "sounds/gib-blood.wav"),
	GIB_EVAPORATE(0.25f, 0.25f, 15, 666666, 0, 0, AttackType.NONE, null, null, 0, "gib-evaporate", "sounds/gib-evaporate.wav"),
	GIB_FURNITURE(0.5f, 0.5f, 5, 666666, 0, 0, AttackType.NONE, null, null, 0, "gib-furniture", "sounds/gib-furniture.wav"),
	GIB_SPAWNER(0.5f, 0.5f, 15, 666666, 0, 0, AttackType.NONE, null, null, 0, "gib-spawner", "sounds/gib-spawner.wav"),
	GIB_SENTRY(0.5f, 0.5f, 20, 666666, 0, 0, AttackType.NONE, null, null, 0, "gib-sentry", "sounds/gib-sentry.wav"),
	GIB_EXPLOSION(0.5f, 0.5f, 25, 666666, 0, 0, AttackType.NONE, null, null, 0, "gib-explosion", "sounds/gib-explosion.wav"),

	PLAYER_DEFAULT(1, 1, 1, 25, 10, 1, AttackType.RANGED, PROJECTILE_ENERGY_1, GIB_BLOOD, 0, "player-default", "sounds/player-melee.wav"),

	ENEMY_SKELETON(1, 1, 3, 5, 5, 3, AttackType.MELEE, null, GIB_WHITE, 10, "enemy-skeleton", "sounds/enemy-melee.wav"),
	ENEMY_SPECTER(1, 1, 4, 10, 0, 3, AttackType.RANGED, PROJECTILE_ENERGY_1, GIB_WHITE, 15, "enemy-specter", "sounds/enemy-melee.wav"),	
	ENEMY_ZOMBIE(1, 1, 4, 10, 10, 2, AttackType.MELEE, null, GIB_GREEN, 10, "enemy-zombie", "sounds/enemy-melee.wav"),
	ENEMY_OGRE(1, 1, 5, 20, 0, 2, AttackType.RANGED, PROJECTILE_ENERGY_2, GIB_GREEN, 15, "enemy-ogre", "sounds/enemy-melee.wav"),	
	ENEMY_WORM(1, 1, 2, 15, 15, 2, AttackType.MELEE, null, GIB_YELLOW, 10, "enemy-worm", "sounds/enemy-melee.wav"),
	ENEMY_IMP(1, 1, 6, 15, 0, 2, AttackType.RANGED, PROJECTILE_ENERGY_2, GIB_YELLOW, 15, "enemy-imp", "sounds/enemy-melee.wav"),		
	ENEMY_GHOST(1, 1, 4, 10, 0, 3, AttackType.RANGED, PROJECTILE_ENERGY_1, GIB_EVAPORATE, 10, "enemy-ghost", "sounds/enemy-melee.wav"),
	ENEMY_WIGHT(1, 1, 5, 20, 0, 3, AttackType.RANGED, PROJECTILE_ENERGY_3, GIB_EVAPORATE, 15, "enemy-wight", "sounds/enemy-melee.wav"),		
	ENEMY_SCORPION(1, 1, 2, 25, 0, 1, AttackType.RANGED, PROJECTILE_ENERGY_3, GIB_GREEN, 20, "enemy-scorpion", "sounds/enemy-melee.wav"),
	ENEMY_GIANT_SPIDER(2, 2, 8, 100, 25, 0.5f, AttackType.MELEE, null, GIB_GREEN, 25, "enemy-giantspider", "sounds/enemy-melee.wav"),	
	ENEMY_BEE(0.5f, 0.5f, 10, 5, 2, 0.5f, AttackType.MELEE, null, GIB_YELLOW, 10, "enemy-bee", "sounds/enemy-melee.wav"),
	ENEMY_QUEEN_BEE(2, 2, 8, 100, 25, 0.5f, AttackType.MELEE, null, GIB_YELLOW, 20, "enemy-queenbee", "sounds/enemy-melee.wav"),	
	ENEMY_GREATER_FIEND(2, 2, 4, 35, 0, 1, AttackType.RANGED, PROJECTILE_LIGHTNING, GIB_RED, 20, "enemy-greaterfiend", "sounds/enemy-melee.wav"),
	ENEMY_THING(2, 2, 4, 100, 0, 1, AttackType.RANGED, PROJECTILE_FLAME, GIB_RED, 25, "enemy-thing", "sounds/enemy-melee.wav"),	

	BOSS_EYE(4, 4, 6, 1000, 0, 0.5f, AttackType.RANGED, PROJECTILE_SPARK, GIB_BLOOD, 500, "boss-eye", null),

	EXPLOSION_SMALL(1, 1, 0, 666666, 1, 0, AttackType.NONE, null, GIB_EXPLOSION, 0, "explosion-small", "sounds/explosion-default.wav"),
	EXPLOSION_MEDIUM(3, 3, 0, 666666, 1, 0, AttackType.NONE, null, GIB_EXPLOSION, 0, "explosion-medium", "sounds/explosion-default.wav"),
	EXPLOSION_LARGE(5, 5, 0, 666666, 1, 0, AttackType.NONE, null, GIB_EXPLOSION, 0, "explosion-large", "sounds/explosion-default.wav"),

	EXPLOSIVE_CRACKER(1, 1, 0, 5, 0, 0, AttackType.NONE, null, EXPLOSION_SMALL, 0, "explosive-cracker", null),
	EXPLOSIVE_HOPPER(1, 1, 0, 10, 0, 0, AttackType.NONE, null, EXPLOSION_MEDIUM, 0, "explosive-hopper", null),
	EXPLOSIVE_FEELER(1, 1, 0, 15, 0, 0, AttackType.NONE, null, EXPLOSION_LARGE, 0, "explosive-feeler", null),

	SENTRY_ORB(0.5f, 0.5f, 0, 20, 0, 0.2f, AttackType.RANGED, PROJECTILE_LIGHTNING, GIB_SENTRY, 10, "sentry-orb", null),

	SPAWNER_SKELETON(1, 1, 0, 30, 0, 10, AttackType.NONE, ENEMY_SKELETON, GIB_SPAWNER, 15, "spawner-skeleton", "sounds/spawner-default.wav"),
	SPAWNER_SPECTER(1, 1, 0, 30, 0, 10, AttackType.NONE, ENEMY_SPECTER, GIB_SPAWNER, 15, "spawner-specter", "sounds/spawner-default.wav"),
	SPAWNER_ZOMBIE(1, 1, 0, 30, 0, 10, AttackType.NONE, ENEMY_ZOMBIE, GIB_SPAWNER, 15, "spawner-zombie", "sounds/spawner-default.wav"),
	SPAWNER_OGRE(1, 1, 0, 30, 0, 10, AttackType.NONE, ENEMY_OGRE, GIB_SPAWNER, 15, "spawner-ogre", "sounds/spawner-default.wav"),
	SPAWNER_WORM(1, 1, 0, 30, 0, 10, AttackType.NONE, ENEMY_WORM, GIB_SPAWNER, 15, "spawner-worm", "sounds/spawner-default.wav"),
	SPAWNER_IMP(1, 1, 0, 30, 0, 10, AttackType.NONE, ENEMY_IMP, GIB_SPAWNER, 15, "spawner-imp", "sounds/spawner-default.wav"),
	SPAWNER_GHOST(1, 1, 0, 30, 0, 10, AttackType.NONE, ENEMY_GHOST, GIB_SPAWNER, 15, "spawner-ghost", "sounds/spawner-default.wav"),
	SPAWNER_WIGHT(1, 1, 0, 30, 0, 10, AttackType.NONE, ENEMY_WIGHT, GIB_SPAWNER, 15, "spawner-wight", "sounds/spawner-default.wav"),
	SPAWNER_SCORPION(1, 1, 0, 30, 0, 10, AttackType.NONE, ENEMY_SCORPION, GIB_SPAWNER, 15, "spawner-scorpion", "sounds/spawner-default.wav"),
	SPAWNER_BEE(1, 1, 0, 30, 0, 10, AttackType.NONE, ENEMY_BEE, GIB_SPAWNER, 15, "spawner-bee", "sounds/spawner-default.wav"),

	ITEM_HEALTH(1, 1, 0, 666666, 0, 25, AttackType.NONE, null, null, 0, "item-health", "sounds/item-health.wav"),
	ITEM_ENERGY(1, 1, 0, 666666, 0, 25, AttackType.NONE, null, null, 0, "item-energy", "sounds/item-energy.wav"),
	ITEM_XP(1, 1, 0, 666, 0, 0, AttackType.NONE, null, null, 100, "item-xp", "sounds/item-xp.wav"),
	ITEM_EXIT_KEY(1, 1, 0, 666666, 0, 0, AttackType.NONE, null, null, 50, "item-exitkey", "sounds/item-exitkey.wav"),
	ITEM_QUAD_DAMAGE(1, 1, 0, 666666, 0, 0, AttackType.NONE, null, null, 0, "item-quaddamage", "sounds/item-quaddamage.wav"),
	ITEM_INVINCIBLE(1, 1, 0, 666666, 0, 0, AttackType.NONE, null, null, 0, "item-invincible", "sounds/item-invincible.wav"),
	ITEM_SPRAY(1, 1, 0, 666666, 0, 0, AttackType.NONE, null, null, 0, "item-spray", "sounds/item-spray.wav"),

	FURNITURE_BED01(1, 1, 0, 30, 0, 0, AttackType.NONE, null, GIB_FURNITURE, 0, "furniture-bed01", null),
	FURNITURE_BED02(1, 1, 0, 30, 0, 0, AttackType.NONE, null, GIB_FURNITURE, 0, "furniture-bed02", null),
	FURNITURE_TABLE01(1, 1, 0, 20, 0, 0, AttackType.NONE, null, GIB_FURNITURE, 0, "furniture-table01", null),
	FURNITURE_TABLE02(1, 1, 0, 20, 0, 0, AttackType.NONE, null, GIB_FURNITURE, 0, "furniture-table02", null),
	FURNITURE_TABLE03(1, 1, 0, 20, 0, 0, AttackType.NONE, null, GIB_FURNITURE, 0, "furniture-table03", null),
	FURNITURE_CHAIR01(1, 1, 0, 10, 0, 0, AttackType.NONE, null, GIB_FURNITURE, 0, "furniture-chair01", null),
	FURNITURE_CHAIR02(1, 1, 0, 10, 0, 0, AttackType.NONE, null, GIB_FURNITURE, 0, "furniture-chair02", null),
	FURNITURE_CHAIR03(1, 1, 0, 10, 0, 0, AttackType.NONE, null, GIB_FURNITURE, 0, "furniture-chair03", null),
	FURNITURE_CHAIR04(1, 1, 0, 10, 0, 0, AttackType.NONE, null, GIB_FURNITURE, 0, "furniture-chair04", null),
	FURNITURE_CHAIR05(1, 1, 0, 10, 0, 0, AttackType.NONE, null, GIB_FURNITURE, 0, "furniture-chair05", null),
	FURNITURE_CHAIR06(1, 1, 0, 10, 0, 0, AttackType.NONE, null, GIB_FURNITURE, 0, "furniture-chair06", null),
	FURNITURE_MIRROR(1, 1, 0, 20, 0, 0, AttackType.NONE, null, GIB_FURNITURE, 0, "furniture-mirror", null),
	FURNITURE_BARREL01(1, 1, 0, 10, 0, 0, AttackType.NONE, null, GIB_FURNITURE, 0, "furniture-barrel01", null),
	FURNITURE_BARREL02(1, 1, 0, 20, 0, 0, AttackType.NONE, null, GIB_FURNITURE, 0, "furniture-barrel02", null),
	FURNITURE_CANDLE01(1, 1, 0, 5, 0, 0, AttackType.NONE, null, GIB_FURNITURE, 0, "furniture-candle01", null),
	FURNITURE_CANDLE02(1, 1, 0, 5, 0, 0, AttackType.NONE, null, GIB_FURNITURE, 0, "furniture-candle02", null),
	FURNITURE_CANDLE03(1, 1, 0, 5, 0, 0, AttackType.NONE, null, GIB_FURNITURE, 0, "furniture-candle03", null),
	FURNITURE_CAULDRON(1, 1, 0, 100, 0, 0, AttackType.NONE, null, GIB_FURNITURE, 0, "furniture-cauldron", null),
	FURNITURE_FIRE01(1, 1, 0, 666666, 0, 0, AttackType.NONE, null, GIB_FURNITURE, 0, "furniture-fire01", null),
	FURNITURE_FIRE02(1, 1, 0, 666666, 0, 0, AttackType.NONE, null, GIB_FURNITURE, 0, "furniture-fire02", null),
	FURNITURE_FIRE03(1, 1, 0, 666666, 0, 0, AttackType.NONE, null, GIB_FURNITURE, 0, "furniture-fire03", null),
	FURNITURE_SACK01(1, 1, 0, 5, 0, 0, AttackType.NONE, null, GIB_FURNITURE, 0, "furniture-sack01", null),
	FURNITURE_SACK02(1, 1, 0, 5, 0, 0, AttackType.NONE, null, GIB_FURNITURE, 0, "furniture-sack02", null),
	FURNITURE_SACK03(1, 1, 0, 5, 0, 0, AttackType.NONE, null, GIB_FURNITURE, 0, "furniture-sack03", null),
	FURNITURE_SACK04(1, 1, 0, 5, 0, 0, AttackType.NONE, null, GIB_FURNITURE, 0, "furniture-sack04", null),
	FURNITURE_VASE01(1, 1, 0, 10, 0, 0, AttackType.NONE, null, GIB_FURNITURE, 0, "furniture-vase01", null),
	FURNITURE_VASE02(1, 1, 0, 10, 0, 0, AttackType.NONE, null, GIB_FURNITURE, 0, "furniture-vase02", null),
	FURNITURE_VASE03(1, 1, 0, 10, 0, 0, AttackType.NONE, null, GIB_FURNITURE, 0, "furniture-vase03", null),
	FURNITURE_ANVIL(1, 1, 0, 100, 0, 0, AttackType.NONE, null, GIB_FURNITURE, 0, "furniture-anvil", null),
	FURNITURE_RACK01(1, 1, 0, 30, 0, 0, AttackType.NONE, null, GIB_FURNITURE, 0, "furniture-rack01", null),
	FURNITURE_RACK02(1, 1, 0, 30, 0, 0, AttackType.NONE, null, GIB_FURNITURE, 0, "furniture-rack02", null),
	FURNITURE_NOOSE(1, 1, 0, 10, 0, 0, AttackType.NONE, null, GIB_FURNITURE, 0, "furniture-noose", null),
	FURNITURE_TREE01(4, 4, 0, 666666, 0, 0, AttackType.NONE, null, GIB_FURNITURE, 0, "furniture-tree01", null),
	FURNITURE_TREE02(4, 4, 0, 666666, 0, 0, AttackType.NONE, null, GIB_FURNITURE, 0, "furniture-tree02", null),
	FURNITURE_TREE03(4, 4, 0, 666666, 0, 0, AttackType.NONE, null, GIB_FURNITURE, 0, "furniture-tree03", null),
	FURNITURE_TREE04(4, 4, 0, 666666, 0, 0, AttackType.NONE, null, GIB_FURNITURE, 0, "furniture-tree04", null),
	FURNITURE_TREE05(4, 4, 0, 666666, 0, 0, AttackType.NONE, null, GIB_FURNITURE, 0, "furniture-tree05", null),
	FURNITURE_ROCK01(2, 2, 0, 666666, 0, 0, AttackType.NONE, null, GIB_FURNITURE, 0, "furniture-rock01", null),
	FURNITURE_ROCK02(2, 2, 0, 666666, 0, 0, AttackType.NONE, null, GIB_FURNITURE, 0, "furniture-rock02", null),
	FURNITURE_ROCK03(2, 2, 0, 666666, 0, 0, AttackType.NONE, null, GIB_FURNITURE, 0, "furniture-rock03", null);

	public float width;
	public float height;
	public float speed;
	public int health;
	public int damage;
	public float actionValue;
	public int attackType;
	public EntityType projectileType;
	public EntityType gibType;
	public int xpDrop;
	public String spriteName;
	public String soundFile;

	EntityType(float width, float height, float speed, int health, int damage, float actionValue, int attackType,
			EntityType projectileType, EntityType gibType, int xpDrop, String spriteName, String soundFile) {
		this.width = width;
		this.height = height;
		this.speed = speed;
		this.health = health;
		this.damage = damage;
		this.actionValue = actionValue;
		this.attackType = attackType;
		this.projectileType = projectileType;
		this.gibType = gibType;
		this.xpDrop = xpDrop;
		this.spriteName = spriteName;
		this.soundFile = soundFile;
	}

}
