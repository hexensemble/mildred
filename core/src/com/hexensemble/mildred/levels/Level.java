package com.hexensemble.mildred.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Segment;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.hexensemble.mildred.entities.Boss;
import com.hexensemble.mildred.entities.Enemy;
import com.hexensemble.mildred.entities.EntityType;
import com.hexensemble.mildred.entities.Explosion;
import com.hexensemble.mildred.entities.Explosive;
import com.hexensemble.mildred.entities.Furniture;
import com.hexensemble.mildred.entities.Gib;
import com.hexensemble.mildred.entities.Item;
import com.hexensemble.mildred.entities.Player;
import com.hexensemble.mildred.entities.Projectile;
import com.hexensemble.mildred.entities.Sentry;
import com.hexensemble.mildred.entities.Spawner;
import com.hexensemble.mildred.system.CoreSettings;
import com.hexensemble.mildred.system.PlayList;

/**
 * Represents a game level.
 * 
 * @author HexEnsemble
 * @author www.hexensemble.com
 * @version 1.0.0
 * @since Alpha 1.0.0
 */
public class Level {

	/**
	 * Asset manager.
	 */
	public AssetManager assetManager;

	private Viewport viewport;
	private OrthographicCamera camera;

	private Vector3 mousePosition;

	private TiledMap map;
	private TiledMapRenderer mapRenderer;

	/**
	 * Tile layer in tiled map.
	 * 
	 */
	public TiledMapTileLayer tileLayer;

	/**
	 * Entity layer in tiled map.
	 * 
	 */
	public TiledMapTileLayer entityLayer;

	/**
	 * Overlay layer in tiled map.
	 * 
	 */
	public TiledMapTileLayer overlayLayer;

	/**
	 * Trigger layer in tiled map.
	 * 
	 */
	public TiledMapTileLayer triggerLayer;

	/**
	 * List of active players.
	 * 
	 */
	public Array<Player> players;

	/**
	 * List of active enemies.
	 * 
	 */
	public Array<Enemy> enemies;

	/**
	 * List of active bosses.
	 * 
	 */
	public Array<Boss> bosses;

	/**
	 * List of active items.
	 * 
	 */
	public Array<Item> items;

	/**
	 * List of active furniture.
	 * 
	 */
	public Array<Furniture> furnishings;

	/**
	 * List of active spawners.
	 * 
	 */
	public Array<Spawner> spawners;

	/**
	 * List of active explosives.
	 * 
	 */
	public Array<Explosive> explosives;

	/**
	 * List of active sentries.
	 * 
	 */
	public Array<Sentry> sentries;

	/**
	 * List of active projectiles.
	 * 
	 */
	public Array<Projectile> projectiles;

	/**
	 * List of active gibs.
	 * 
	 */
	public Array<Gib> gibs;

	/**
	 * List of active explosions.
	 * 
	 */
	public Array<Explosion> explosions;

	/**
	 * List of active doors.
	 * 
	 */
	public Array<Door> doors;

	/**
	 * List of active tile bounds on X axis.
	 * 
	 */
	public Array<Segment> tileBoundsX;

	/**
	 * List of active tile bounds on Y axis.
	 * 
	 */
	public Array<Segment> tileBoundsY;

	/**
	 * Pool of players.
	 * 
	 */
	public Pool<Player> playerPool;

	/**
	 * Pool of enemies.
	 * 
	 */
	public Pool<Enemy> enemyPool;

	/**
	 * Pool of bosses.
	 * 
	 */
	public Pool<Boss> bossPool;

	/**
	 * Pool of items.
	 * 
	 */
	public Pool<Item> itemPool;

	/**
	 * Pool of furniture.
	 * 
	 */
	public Pool<Furniture> furnishingsPool;

	/**
	 * Pool of spawners.
	 * 
	 */
	public Pool<Spawner> spawnerPool;

	/**
	 * Pool of explosives.
	 * 
	 */
	public Pool<Explosive> explosivePool;

	/**
	 * Pool of sentries.
	 * 
	 */
	public Pool<Sentry> sentryPool;

	/**
	 * Pool of projectiles.
	 * 
	 */
	public Pool<Projectile> projectilePool;

	/**
	 * Pool of gibs.
	 * 
	 */
	public Pool<Gib> gibPool;

	/**
	 * Pool of explosions.
	 * 
	 */
	public Pool<Explosion> explosionPool;

	private Sound spawnSound;

	private boolean levelComplete;

	private float totalSecrets;
	private int secrets;
	private int totalEnemies;
	private int kills;

	private long timeStart;
	private long timeCurrent;
	private Long timeSecond;
	private Long timeMinute;
	private Long timeHour;
	private String timeSecondString;
	private String timeMinuteString;
	private String timeHourString;
	private String time;

	private Music music;

	/**
	 * Initialize.
	 * 
	 * @param assetManager
	 *            Asset manager.
	 * @param viewport
	 *            Viewport.
	 * @param camera
	 *            Camera.
	 * @param levelFile
	 *            TMX file.
	 */
	public Level(AssetManager assetManager, Viewport viewport, OrthographicCamera camera, String levelFile) {
		this.assetManager = assetManager;
		this.viewport = viewport;
		this.camera = camera;

		mousePosition = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
		camera.unproject(mousePosition, viewport.getScreenX(), viewport.getScreenY(), viewport.getScreenWidth(),
				viewport.getScreenHeight());

		map = new TmxMapLoader().load(levelFile);
		mapRenderer = new OrthogonalTiledMapRenderer(map, 1 / CoreSettings.P_TILE_SIZE);
		tileLayer = (TiledMapTileLayer) map.getLayers().get("Tile Layer");
		entityLayer = (TiledMapTileLayer) map.getLayers().get("Entity Layer");
		overlayLayer = (TiledMapTileLayer) map.getLayers().get("Overlay Layer");
		triggerLayer = (TiledMapTileLayer) map.getLayers().get("Trigger Layer");
		entityLayer.setVisible(false);
		triggerLayer.setVisible(false);

		players = new Array<Player>();
		enemies = new Array<Enemy>();
		bosses = new Array<Boss>();
		items = new Array<Item>();
		furnishings = new Array<Furniture>();
		spawners = new Array<Spawner>();
		explosives = new Array<Explosive>();
		sentries = new Array<Sentry>();
		projectiles = new Array<Projectile>();
		gibs = new Array<Gib>();
		explosions = new Array<Explosion>();

		doors = new Array<Door>();
		tileBoundsX = new Array<Segment>();
		tileBoundsY = new Array<Segment>();

		playerPool = Pools.get(Player.class);
		enemyPool = Pools.get(Enemy.class);
		bossPool = Pools.get(Boss.class);
		itemPool = Pools.get(Item.class);
		furnishingsPool = Pools.get(Furniture.class);
		spawnerPool = Pools.get(Spawner.class);
		explosivePool = Pools.get(Explosive.class);
		sentryPool = Pools.get(Sentry.class);
		projectilePool = Pools.get(Projectile.class);
		gibPool = Pools.get(Gib.class);
		explosionPool = Pools.get(Explosion.class);

		spawnSound = assetManager.get("sounds/enemy-spawn.wav", Sound.class);

		levelComplete = false;

		loadPlayers();
		loadEnemies();
		loadBosses();
		loadItems();
		loadFurnishings();
		loadSpawners();
		loadExplosives();
		loadSentries();
		loadDoors();
		loadTileBounds();

		loadSecrets();
		secrets = 0;
		totalEnemies = enemies.size + bosses.size;
		kills = 0;

		timeStart = TimeUtils.millis();
		timeCurrent = 0;
		timeSecond = null;
		timeMinute = null;
		timeHour = null;
		timeSecondString = null;
		timeMinuteString = null;
		timeHourString = null;
		time = null;

		music = assetManager.get(PlayList.getTrack(), Music.class);
		music.setVolume(CoreSettings.musicVol);
		music.setLooping(true);
		music.play();
	}

	/**
	 * Update.
	 * 
	 * @param delta
	 *            Delta time.
	 */
	public void update(float delta) {
		timeUpdate();

		cameraPositions();

		for (Player pl : players) {
			pl.update(delta);
		}
		for (Enemy e : enemies) {
			e.update(delta);
		}
		for (Boss b : bosses) {
			b.update(delta);
		}
		for (Item i : items) {
			i.update(delta);
		}
		for (Furniture f : furnishings) {
			f.update(delta);
		}
		for (Spawner s : spawners) {
			s.update(delta);
		}
		for (Explosive ex : explosives) {
			ex.update(delta);
		}
		for (Sentry s : sentries) {
			s.update(delta);
		}
		for (Projectile p : projectiles) {
			p.update(delta);
		}
		for (Gib g : gibs) {
			g.update(delta);
		}
		for (Explosion exp : explosions) {
			exp.update(delta);
		}
		for (Door d : doors) {
			d.update(delta);
		}

		deadEntities();
	}

	/**
	 * Render.
	 * 
	 * @param batch
	 *            Sprite batch.
	 */
	public void render(SpriteBatch batch) {
		batch.begin();
		mapRenderer.setView(camera);
		int[] backgroundLayers = { 0 };
		mapRenderer.render(backgroundLayers);
		batch.end();

		for (Gib g : gibs) {
			g.render(batch);
		}
		for (Item i : items) {
			i.render(batch);
		}
		for (Furniture f : furnishings) {
			f.render(batch);
		}
		for (Spawner s : spawners) {
			s.render(batch);
		}
		for (Explosive ex : explosives) {
			ex.render(batch);
		}
		for (Sentry s : sentries) {
			s.render(batch);
		}
		for (Projectile p : projectiles) {
			p.render(batch);
		}
		for (Enemy e : enemies) {
			e.render(batch);
		}
		for (Boss b : bosses) {
			b.render(batch);
		}
		for (Player pl : players) {
			pl.render(batch);
		}
		for (Explosion exp : explosions) {
			exp.render(batch);
		}

		batch.begin();
		mapRenderer.setView(camera);
		int[] foregroundLayers = { 2 };
		mapRenderer.render(foregroundLayers);
		batch.end();
	}

	/**
	 * Dispose.
	 */
	public void dispose() {
		map.dispose();

		for (Player pl : players) {
			pl.dispose();
		}
		for (Enemy e : enemies) {
			e.dispose();
		}
		for (Boss b : bosses) {
			b.dispose();
		}
		for (Item i : items) {
			i.dispose();
		}
		for (Furniture f : furnishings) {
			f.dispose();
		}
		for (Spawner s : spawners) {
			s.dispose();
		}
		for (Explosive ex : explosives) {
			ex.dispose();
		}
		for (Sentry s : sentries) {
			s.dispose();
		}
		for (Projectile p : projectiles) {
			p.dispose();
		}
		for (Gib g : gibs) {
			g.dispose();
		}
		for (Explosion exp : explosions) {
			exp.dispose();
		}
		for (Door d : doors) {
			d.dispose();
		}

		music.stop();
	}

	private void loadPlayers() {
		for (int y = 0; y < CoreSettings.V_TILE_GRID_HEIGHT; y++) {
			for (int x = 0; x < CoreSettings.V_TILE_GRID_WIDTH; x++) {
				Cell cell = entityLayer.getCell(x, y);
				if (cell != null) {
					if (cell.getTile().getProperties().containsKey("player-default")) {
						Player player = playerPool.obtain();
						player.init(EntityType.PLAYER_DEFAULT, this, x, y);
						players.add(player);
					}
				}
			}
		}
	}

	private void loadEnemies() {
		for (int y = 0; y < CoreSettings.V_TILE_GRID_HEIGHT; y++) {
			for (int x = 0; x < CoreSettings.V_TILE_GRID_WIDTH; x++) {
				Cell cell = entityLayer.getCell(x, y);
				if (cell != null) {
					if (cell.getTile().getProperties().containsKey("enemy-skeleton")) {
						Enemy enemy = enemyPool.obtain();
						enemy.init(EntityType.ENEMY_SKELETON, this, x, y);
						enemies.add(enemy);
					}
					if (cell.getTile().getProperties().containsKey("enemy-specter")) {
						Enemy enemy = enemyPool.obtain();
						enemy.init(EntityType.ENEMY_SPECTER, this, x, y);
						enemies.add(enemy);
					}
					if (cell.getTile().getProperties().containsKey("enemy-zombie")) {
						Enemy enemy = enemyPool.obtain();
						enemy.init(EntityType.ENEMY_ZOMBIE, this, x, y);
						enemies.add(enemy);
					}
					if (cell.getTile().getProperties().containsKey("enemy-ogre")) {
						Enemy enemy = enemyPool.obtain();
						enemy.init(EntityType.ENEMY_OGRE, this, x, y);
						enemies.add(enemy);
					}
					if (cell.getTile().getProperties().containsKey("enemy-worm")) {
						Enemy enemy = enemyPool.obtain();
						enemy.init(EntityType.ENEMY_WORM, this, x, y);
						enemies.add(enemy);
					}
					if (cell.getTile().getProperties().containsKey("enemy-imp")) {
						Enemy enemy = enemyPool.obtain();
						enemy.init(EntityType.ENEMY_IMP, this, x, y);
						enemies.add(enemy);
					}
					if (cell.getTile().getProperties().containsKey("enemy-ghost")) {
						Enemy enemy = enemyPool.obtain();
						enemy.init(EntityType.ENEMY_GHOST, this, x, y);
						enemies.add(enemy);
					}
					if (cell.getTile().getProperties().containsKey("enemy-wight")) {
						Enemy enemy = enemyPool.obtain();
						enemy.init(EntityType.ENEMY_WIGHT, this, x, y);
						enemies.add(enemy);
					}
					if (cell.getTile().getProperties().containsKey("enemy-scorpion")) {
						Enemy enemy = enemyPool.obtain();
						enemy.init(EntityType.ENEMY_SCORPION, this, x, y);
						enemies.add(enemy);
					}
					if (cell.getTile().getProperties().containsKey("enemy-giantspider")) {
						Enemy enemy = enemyPool.obtain();
						enemy.init(EntityType.ENEMY_GIANT_SPIDER, this, x, y);
						enemies.add(enemy);
					}
					if (cell.getTile().getProperties().containsKey("enemy-bee")) {
						Enemy enemy = enemyPool.obtain();
						enemy.init(EntityType.ENEMY_BEE, this, x, y);
						enemies.add(enemy);
					}
					if (cell.getTile().getProperties().containsKey("enemy-queenbee")) {
						Enemy enemy = enemyPool.obtain();
						enemy.init(EntityType.ENEMY_QUEEN_BEE, this, x, y);
						enemies.add(enemy);
					}
					if (cell.getTile().getProperties().containsKey("enemy-greaterfiend")) {
						Enemy enemy = enemyPool.obtain();
						enemy.init(EntityType.ENEMY_GREATER_FIEND, this, x, y);
						enemies.add(enemy);
					}
					if (cell.getTile().getProperties().containsKey("enemy-thing")) {
						Enemy enemy = enemyPool.obtain();
						enemy.init(EntityType.ENEMY_THING, this, x, y);
						enemies.add(enemy);
					}
				}
			}
		}
	}

	private void loadBosses() {
		for (int y = 0; y < CoreSettings.V_TILE_GRID_HEIGHT; y++) {
			for (int x = 0; x < CoreSettings.V_TILE_GRID_WIDTH; x++) {
				Cell cell = entityLayer.getCell(x, y);
				if (cell != null) {
					if (cell.getTile().getProperties().containsKey("boss-eye")) {
						Boss boss = bossPool.obtain();
						boss.init(EntityType.BOSS_EYE, this, x, y);
						bosses.add(boss);
					}

				}
			}
		}
	}

	private void loadItems() {
		for (int y = 0; y < CoreSettings.V_TILE_GRID_HEIGHT; y++) {
			for (int x = 0; x < CoreSettings.V_TILE_GRID_WIDTH; x++) {
				Cell cell = entityLayer.getCell(x, y);
				if (cell != null) {
					if (cell.getTile().getProperties().containsKey("item-health")) {
						Item item = itemPool.obtain();
						item.init(EntityType.ITEM_HEALTH, this, x, y);
						items.add(item);
					}
					if (cell.getTile().getProperties().containsKey("item-energy")) {
						Item item = itemPool.obtain();
						item.init(EntityType.ITEM_ENERGY, this, x, y);
						items.add(item);
					}
					if (cell.getTile().getProperties().containsKey("item-xp")) {
						Item item = itemPool.obtain();
						item.init(EntityType.ITEM_XP, this, x, y);
						items.add(item);
					}
					if (cell.getTile().getProperties().containsKey("item-exitkey")) {
						Item item = itemPool.obtain();
						item.init(EntityType.ITEM_EXIT_KEY, this, x, y);
						items.add(item);
					}
					if (cell.getTile().getProperties().containsKey("item-quaddamage")) {
						Item item = itemPool.obtain();
						item.init(EntityType.ITEM_QUAD_DAMAGE, this, x, y);
						items.add(item);
					}
					if (cell.getTile().getProperties().containsKey("item-invincible")) {
						Item item = itemPool.obtain();
						item.init(EntityType.ITEM_INVINCIBLE, this, x, y);
						items.add(item);
					}
					if (cell.getTile().getProperties().containsKey("item-spray")) {
						Item item = itemPool.obtain();
						item.init(EntityType.ITEM_SPRAY, this, x, y);
						items.add(item);
					}
				}
			}
		}
	}

	private void loadFurnishings() {
		for (int y = 0; y < CoreSettings.V_TILE_GRID_HEIGHT; y++) {
			for (int x = 0; x < CoreSettings.V_TILE_GRID_WIDTH; x++) {
				Cell cell = entityLayer.getCell(x, y);
				if (cell != null) {
					if (cell.getTile().getProperties().containsKey("furniture-bed01")) {
						Furniture furniture = furnishingsPool.obtain();
						furniture.init(EntityType.FURNITURE_BED01, this, x, y);
						furnishings.add(furniture);
					}
					if (cell.getTile().getProperties().containsKey("furniture-bed02")) {
						Furniture furniture = furnishingsPool.obtain();
						furniture.init(EntityType.FURNITURE_BED02, this, x, y);
						furnishings.add(furniture);
					}
					if (cell.getTile().getProperties().containsKey("furniture-table01")) {
						Furniture furniture = furnishingsPool.obtain();
						furniture.init(EntityType.FURNITURE_TABLE01, this, x, y);
						furnishings.add(furniture);
					}
					if (cell.getTile().getProperties().containsKey("furniture-table02")) {
						Furniture furniture = furnishingsPool.obtain();
						furniture.init(EntityType.FURNITURE_TABLE02, this, x, y);
						furnishings.add(furniture);
					}
					if (cell.getTile().getProperties().containsKey("furniture-table03")) {
						Furniture furniture = furnishingsPool.obtain();
						furniture.init(EntityType.FURNITURE_TABLE03, this, x, y);
						furnishings.add(furniture);
					}
					if (cell.getTile().getProperties().containsKey("furniture-chair01")) {
						Furniture furniture = furnishingsPool.obtain();
						furniture.init(EntityType.FURNITURE_CHAIR01, this, x, y);
						furnishings.add(furniture);
					}
					if (cell.getTile().getProperties().containsKey("furniture-chair02")) {
						Furniture furniture = furnishingsPool.obtain();
						furniture.init(EntityType.FURNITURE_CHAIR02, this, x, y);
						furnishings.add(furniture);
					}
					if (cell.getTile().getProperties().containsKey("furniture-chair03")) {
						Furniture furniture = furnishingsPool.obtain();
						furniture.init(EntityType.FURNITURE_CHAIR03, this, x, y);
						furnishings.add(furniture);
					}
					if (cell.getTile().getProperties().containsKey("furniture-chair04")) {
						Furniture furniture = furnishingsPool.obtain();
						furniture.init(EntityType.FURNITURE_CHAIR04, this, x, y);
						furnishings.add(furniture);
					}
					if (cell.getTile().getProperties().containsKey("furniture-chair05")) {
						Furniture furniture = furnishingsPool.obtain();
						furniture.init(EntityType.FURNITURE_CHAIR05, this, x, y);
						furnishings.add(furniture);
					}
					if (cell.getTile().getProperties().containsKey("furniture-chair06")) {
						Furniture furniture = furnishingsPool.obtain();
						furniture.init(EntityType.FURNITURE_CHAIR06, this, x, y);
						furnishings.add(furniture);
					}
					if (cell.getTile().getProperties().containsKey("furniture-mirror")) {
						Furniture furniture = furnishingsPool.obtain();
						furniture.init(EntityType.FURNITURE_MIRROR, this, x, y);
						furnishings.add(furniture);
					}
					if (cell.getTile().getProperties().containsKey("furniture-barrel01")) {
						Furniture furniture = furnishingsPool.obtain();
						furniture.init(EntityType.FURNITURE_BARREL01, this, x, y);
						furnishings.add(furniture);
					}
					if (cell.getTile().getProperties().containsKey("furniture-barrel02")) {
						Furniture furniture = furnishingsPool.obtain();
						furniture.init(EntityType.FURNITURE_BARREL02, this, x, y);
						furnishings.add(furniture);
					}
					if (cell.getTile().getProperties().containsKey("furniture-candle01")) {
						Furniture furniture = furnishingsPool.obtain();
						furniture.init(EntityType.FURNITURE_CANDLE01, this, x, y);
						furnishings.add(furniture);
					}
					if (cell.getTile().getProperties().containsKey("furniture-candle02")) {
						Furniture furniture = furnishingsPool.obtain();
						furniture.init(EntityType.FURNITURE_CANDLE02, this, x, y);
						furnishings.add(furniture);
					}
					if (cell.getTile().getProperties().containsKey("furniture-candle03")) {
						Furniture furniture = furnishingsPool.obtain();
						furniture.init(EntityType.FURNITURE_CANDLE03, this, x, y);
						furnishings.add(furniture);
					}
					if (cell.getTile().getProperties().containsKey("furniture-cauldron")) {
						Furniture furniture = furnishingsPool.obtain();
						furniture.init(EntityType.FURNITURE_CAULDRON, this, x, y);
						furnishings.add(furniture);
					}
					if (cell.getTile().getProperties().containsKey("furniture-fire01")) {
						Furniture furniture = furnishingsPool.obtain();
						furniture.init(EntityType.FURNITURE_FIRE01, this, x, y);
						furnishings.add(furniture);
					}
					if (cell.getTile().getProperties().containsKey("furniture-fire02")) {
						Furniture furniture = furnishingsPool.obtain();
						furniture.init(EntityType.FURNITURE_FIRE02, this, x, y);
						furnishings.add(furniture);
					}
					if (cell.getTile().getProperties().containsKey("furniture-fire03")) {
						Furniture furniture = furnishingsPool.obtain();
						furniture.init(EntityType.FURNITURE_FIRE03, this, x, y);
						furnishings.add(furniture);
					}
					if (cell.getTile().getProperties().containsKey("furniture-sack01")) {
						Furniture furniture = furnishingsPool.obtain();
						furniture.init(EntityType.FURNITURE_SACK01, this, x, y);
						furnishings.add(furniture);
					}
					if (cell.getTile().getProperties().containsKey("furniture-sack02")) {
						Furniture furniture = furnishingsPool.obtain();
						furniture.init(EntityType.FURNITURE_SACK02, this, x, y);
						furnishings.add(furniture);
					}
					if (cell.getTile().getProperties().containsKey("furniture-sack03")) {
						Furniture furniture = furnishingsPool.obtain();
						furniture.init(EntityType.FURNITURE_SACK03, this, x, y);
						furnishings.add(furniture);
					}
					if (cell.getTile().getProperties().containsKey("furniture-sack04")) {
						Furniture furniture = furnishingsPool.obtain();
						furniture.init(EntityType.FURNITURE_SACK04, this, x, y);
						furnishings.add(furniture);
					}
					if (cell.getTile().getProperties().containsKey("furniture-vase01")) {
						Furniture furniture = furnishingsPool.obtain();
						furniture.init(EntityType.FURNITURE_VASE01, this, x, y);
						furnishings.add(furniture);
					}
					if (cell.getTile().getProperties().containsKey("furniture-vase02")) {
						Furniture furniture = furnishingsPool.obtain();
						furniture.init(EntityType.FURNITURE_VASE02, this, x, y);
						furnishings.add(furniture);
					}
					if (cell.getTile().getProperties().containsKey("furniture-vase03")) {
						Furniture furniture = furnishingsPool.obtain();
						furniture.init(EntityType.FURNITURE_VASE03, this, x, y);
						furnishings.add(furniture);
					}
					if (cell.getTile().getProperties().containsKey("furniture-anvil")) {
						Furniture furniture = furnishingsPool.obtain();
						furniture.init(EntityType.FURNITURE_ANVIL, this, x, y);
						furnishings.add(furniture);
					}
					if (cell.getTile().getProperties().containsKey("furniture-rack01")) {
						Furniture furniture = furnishingsPool.obtain();
						furniture.init(EntityType.FURNITURE_RACK01, this, x, y);
						furnishings.add(furniture);
					}
					if (cell.getTile().getProperties().containsKey("furniture-rack02")) {
						Furniture furniture = furnishingsPool.obtain();
						furniture.init(EntityType.FURNITURE_RACK02, this, x, y);
						furnishings.add(furniture);
					}
					if (cell.getTile().getProperties().containsKey("furniture-noose")) {
						Furniture furniture = furnishingsPool.obtain();
						furniture.init(EntityType.FURNITURE_NOOSE, this, x, y);
						furnishings.add(furniture);
					}
					if (cell.getTile().getProperties().containsKey("furniture-tree01")) {
						Furniture furniture = furnishingsPool.obtain();
						furniture.init(EntityType.FURNITURE_TREE01, this, x, y);
						furnishings.add(furniture);
					}
					if (cell.getTile().getProperties().containsKey("furniture-tree02")) {
						Furniture furniture = furnishingsPool.obtain();
						furniture.init(EntityType.FURNITURE_TREE02, this, x, y);
						furnishings.add(furniture);
					}
					if (cell.getTile().getProperties().containsKey("furniture-tree03")) {
						Furniture furniture = furnishingsPool.obtain();
						furniture.init(EntityType.FURNITURE_TREE03, this, x, y);
						furnishings.add(furniture);
					}
					if (cell.getTile().getProperties().containsKey("furniture-tree04")) {
						Furniture furniture = furnishingsPool.obtain();
						furniture.init(EntityType.FURNITURE_TREE04, this, x, y);
						furnishings.add(furniture);
					}
					if (cell.getTile().getProperties().containsKey("furniture-tree05")) {
						Furniture furniture = furnishingsPool.obtain();
						furniture.init(EntityType.FURNITURE_TREE05, this, x, y);
						furnishings.add(furniture);
					}
					if (cell.getTile().getProperties().containsKey("furniture-rock01")) {
						Furniture furniture = furnishingsPool.obtain();
						furniture.init(EntityType.FURNITURE_ROCK01, this, x, y);
						furnishings.add(furniture);
					}
					if (cell.getTile().getProperties().containsKey("furniture-rock02")) {
						Furniture furniture = furnishingsPool.obtain();
						furniture.init(EntityType.FURNITURE_ROCK02, this, x, y);
						furnishings.add(furniture);
					}
					if (cell.getTile().getProperties().containsKey("furniture-rock03")) {
						Furniture furniture = furnishingsPool.obtain();
						furniture.init(EntityType.FURNITURE_ROCK03, this, x, y);
						furnishings.add(furniture);
					}
				}
			}
		}
	}

	private void loadSpawners() {
		for (int y = 0; y < CoreSettings.V_TILE_GRID_HEIGHT; y++) {
			for (int x = 0; x < CoreSettings.V_TILE_GRID_WIDTH; x++) {
				Cell cell = entityLayer.getCell(x, y);
				if (cell != null) {
					if (cell.getTile().getProperties().containsKey("spawner-skeleton")) {
						Spawner spawner = spawnerPool.obtain();
						spawner.init(EntityType.SPAWNER_SKELETON, this, x, y);
						spawners.add(spawner);
					}
					if (cell.getTile().getProperties().containsKey("spawner-specter")) {
						Spawner spawner = spawnerPool.obtain();
						spawner.init(EntityType.SPAWNER_SPECTER, this, x, y);
						spawners.add(spawner);
					}
					if (cell.getTile().getProperties().containsKey("spawner-zombie")) {
						Spawner spawner = spawnerPool.obtain();
						spawner.init(EntityType.SPAWNER_ZOMBIE, this, x, y);
						spawners.add(spawner);
					}
					if (cell.getTile().getProperties().containsKey("spawner-ogre")) {
						Spawner spawner = spawnerPool.obtain();
						spawner.init(EntityType.SPAWNER_OGRE, this, x, y);
						spawners.add(spawner);
					}
					if (cell.getTile().getProperties().containsKey("spawner-worm")) {
						Spawner spawner = spawnerPool.obtain();
						spawner.init(EntityType.SPAWNER_WORM, this, x, y);
						spawners.add(spawner);
					}
					if (cell.getTile().getProperties().containsKey("spawner-imp")) {
						Spawner spawner = spawnerPool.obtain();
						spawner.init(EntityType.SPAWNER_IMP, this, x, y);
						spawners.add(spawner);
					}
					if (cell.getTile().getProperties().containsKey("spawner-ghost")) {
						Spawner spawner = spawnerPool.obtain();
						spawner.init(EntityType.SPAWNER_GHOST, this, x, y);
						spawners.add(spawner);
					}
					if (cell.getTile().getProperties().containsKey("spawner-wight")) {
						Spawner spawner = spawnerPool.obtain();
						spawner.init(EntityType.SPAWNER_WIGHT, this, x, y);
						spawners.add(spawner);
					}
					if (cell.getTile().getProperties().containsKey("spawner-scorpion")) {
						Spawner spawner = spawnerPool.obtain();
						spawner.init(EntityType.SPAWNER_SCORPION, this, x, y);
						spawners.add(spawner);
					}
					if (cell.getTile().getProperties().containsKey("spawner-bee")) {
						Spawner spawner = spawnerPool.obtain();
						spawner.init(EntityType.SPAWNER_BEE, this, x, y);
						spawners.add(spawner);
					}
				}
			}
		}
	}

	private void loadExplosives() {
		for (int y = 0; y < CoreSettings.V_TILE_GRID_HEIGHT; y++) {
			for (int x = 0; x < CoreSettings.V_TILE_GRID_WIDTH; x++) {
				Cell cell = entityLayer.getCell(x, y);
				if (cell != null) {
					if (cell.getTile().getProperties().containsKey("explosive-cracker")) {
						Explosive explosive = explosivePool.obtain();
						explosive.init(EntityType.EXPLOSIVE_CRACKER, this, x, y);
						explosives.add(explosive);
					}
					if (cell.getTile().getProperties().containsKey("explosive-hopper")) {
						Explosive explosive = explosivePool.obtain();
						explosive.init(EntityType.EXPLOSIVE_HOPPER, this, x, y);
						explosives.add(explosive);
					}
					if (cell.getTile().getProperties().containsKey("explosive-feeler")) {
						Explosive explosive = explosivePool.obtain();
						explosive.init(EntityType.EXPLOSIVE_FEELER, this, x, y);
						explosives.add(explosive);
					}
				}
			}
		}
	}

	private void loadSentries() {
		for (int y = 0; y < CoreSettings.V_TILE_GRID_HEIGHT; y++) {
			for (int x = 0; x < CoreSettings.V_TILE_GRID_WIDTH; x++) {
				Cell cell = entityLayer.getCell(x, y);
				if (cell != null) {
					if (cell.getTile().getProperties().containsKey("sentry-orb")) {
						Sentry sentry = sentryPool.obtain();
						sentry.init(EntityType.SENTRY_ORB, this, x, y);
						sentries.add(sentry);
					}
				}
			}
		}
	}

	private void loadDoors() {
		for (int y = 0; y < CoreSettings.V_TILE_GRID_HEIGHT; y++) {
			for (int x = 0; x < CoreSettings.V_TILE_GRID_WIDTH; x++) {
				Cell cell = tileLayer.getCell(x, y);
				if (cell != null) {
					if (cell.getTile().getProperties().containsKey("door")) {
						doors.add(new Door(this, x, y));
					}
				}
			}
		}
	}

	private void loadTileBounds() {
		for (int y = 0; y < CoreSettings.V_TILE_GRID_HEIGHT; y++) {
			for (int x = 0; x < CoreSettings.V_TILE_GRID_WIDTH; x++) {
				Cell cell = tileLayer.getCell(x, y);
				if (cell != null) {
					if (cell.getTile().getProperties().containsKey("blocked")) {
						tileBoundsX.add(new Segment(x, y, 0, x + CoreSettings.V_TILE_SIZE, y, 0));
					}
				}
			}
		}
		for (int y = 0; y < CoreSettings.V_TILE_GRID_HEIGHT; y++) {
			for (int x = 0; x < CoreSettings.V_TILE_GRID_WIDTH; x++) {
				Cell cell = tileLayer.getCell(x, y);
				if (cell != null) {
					if (cell.getTile().getProperties().containsKey("blocked")) {
						tileBoundsY.add(new Segment(x, y, 0, x, y + CoreSettings.V_TILE_SIZE, 0));
					}
				}
			}
		}
	}

	private void loadSecrets() {
		for (int y = 0; y < CoreSettings.V_TILE_GRID_HEIGHT; y++) {
			for (int x = 0; x < CoreSettings.V_TILE_GRID_WIDTH; x++) {
				Cell cell = triggerLayer.getCell(x, y);
				if (cell != null) {
					if (cell.getTile().getProperties().containsKey("trigger-secret")) {
						totalSecrets += 1;
					}
				}
			}
		}
		totalSecrets /= 2;
	}

	private void cameraPositions() {
		if (players.size > 0) {
			camera.position.set(players.get(0).getCenterX(), players.get(0).getCenterY(), 0);
			camera.position.x = MathUtils.clamp(camera.position.x, CoreSettings.V_WIDTH / 2,
					CoreSettings.V_TILE_GRID_WIDTH - (CoreSettings.V_WIDTH / 2));
			camera.position.y = MathUtils.clamp(camera.position.y, CoreSettings.V_HEIGHT / 2,
					CoreSettings.V_TILE_GRID_HEIGHT - (CoreSettings.V_HEIGHT / 2));
		}

		mousePosition.set(Gdx.input.getX(), Gdx.input.getY(), 0);
		camera.unproject(mousePosition, viewport.getScreenX(), viewport.getScreenY(), viewport.getScreenWidth(),
				viewport.getScreenHeight());
	}

	/**
	 * Checks if a tile exists.
	 * 
	 * @param layer
	 *            Layer in tiled map.
	 * @param x
	 *            X position in level.
	 * @param y
	 *            Y position in level.
	 * @return {@code true} if a tile exists.
	 */
	public boolean tileNullCheck(TiledMapTileLayer layer, int x, int y) {
		Cell cell = layer.getCell(x, y);
		if (cell == null) {
			return true;
		}
		return false;
	}

	/**
	 * Checks if a door exists.
	 * 
	 * @param x
	 *            X position in level.
	 * @param y
	 *            Y position in level.
	 * @return {@code true} if a door exists.
	 */
	public boolean tileDoorCheck(int x, int y) {
		Cell cell = tileLayer.getCell(x, y);
		if (cell != null) {
			if (cell.getTile().getProperties().containsKey("door")) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks if a tile is blocked.
	 * 
	 * @param x
	 *            X position in level.
	 * @param y
	 *            Y position in level.
	 * @return {@code true} if a blocked tile exists.
	 */
	public boolean tileBlockedCheck(int x, int y) {
		Cell cell = tileLayer.getCell(x, y);
		if (cell != null) {
			if (cell.getTile().getProperties().containsKey("blocked")) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks if an entity is blocked.
	 * 
	 * @param x
	 *            X position in level.
	 * @param y
	 *            Y position in level.
	 * @return {@code true} if a blocked entity exists.
	 */
	public boolean entityBlockedCheck(int x, int y) {
		Cell cell = entityLayer.getCell(x, y);
		if (cell != null) {
			if (cell.getTile().getProperties().containsKey("blocked")) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks for a secret.
	 * 
	 * @param x
	 *            X position in level.
	 * @param y
	 *            Y position in level.
	 * @return {@code true} if a secret exists.
	 */
	public boolean tileSecretCheck(int x, int y) {
		Cell cell = triggerLayer.getCell(x, y);
		if (cell != null) {
			if (cell.getTile().getProperties().containsKey("trigger-secret")) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks if a reveal tile exists.
	 * 
	 * @param x
	 *            X position in level.
	 * @param y
	 *            Y position in level.
	 * @return {@code true} if a reveal tile exists.
	 */
	public boolean tileRevealCheck(int x, int y) {
		Cell cell = triggerLayer.getCell(x, y);
		if (cell != null) {
			if (cell.getTile().getProperties().containsKey("trigger-reveal")) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks for an action trigger.
	 * 
	 * @param x
	 *            X position in level.
	 * @param y
	 *            Y position in level.
	 * @return {@code true} if an action trigger exists.
	 */
	public boolean tileActionCheck(int x, int y) {
		Cell cell = triggerLayer.getCell(x, y);
		if (cell != null) {
			if (cell.getTile().getProperties().containsKey("trigger-action01")
					|| cell.getTile().getProperties().containsKey("trigger-action02")
					|| cell.getTile().getProperties().containsKey("trigger-action03")) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks if an exit exists.
	 * 
	 * @param x
	 *            X position in level.
	 * @param y
	 *            Y position in level.
	 * @return {@code true} if an exit exists.
	 */
	public boolean tileExitCheck(int x, int y) {
		Cell cell = triggerLayer.getCell(x, y);
		if (cell != null) {
			if (cell.getTile().getProperties().containsKey("trigger-exit")) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Gets a tile's ID.
	 * 
	 * @param layer
	 *            Layer in tiled map.
	 * @param x
	 *            X position in level.
	 * @param y
	 *            Y position in level.
	 * @return {@code int}.
	 */
	public int getTileID(TiledMapTileLayer layer, int x, int y) {
		Cell cell = layer.getCell(x, y);
		return cell.getTile().getId();
	}

	/**
	 * Sets a tile.
	 * 
	 * @param layer
	 *            Layer in tiled map.
	 * @param x
	 *            X position in level.
	 * @param y
	 *            Y position in level.
	 * @param tileID
	 *            Tile ID
	 */
	public void setTile(TiledMapTileLayer layer, int x, int y, int tileID) {
		Cell cell = layer.getCell(x, y);
		cell.setTile(map.getTileSets().getTile(tileID));
	}

	/**
	 * Resolves an action trigger.
	 * 
	 * @param property
	 *            Tile property.
	 */
	public void triggerResolve(String property) {
		for (int y = 0; y < CoreSettings.V_TILE_GRID_HEIGHT; y++) {
			for (int x = 0; x < CoreSettings.V_TILE_GRID_WIDTH; x++) {
				Cell cell = triggerLayer.getCell(x, y);
				if (cell != null) {
					if (cell.getTile().getProperties().containsKey(property)) {
						if (cell.getTile().getProperties().containsKey("enemy-skeleton")) {
							Enemy enemy = enemyPool.obtain();
							enemy.init(EntityType.ENEMY_SKELETON, this, x, y);
							enemies.add(enemy);
							spawnSound.play(CoreSettings.sfxVol);
							totalEnemies += 1;
						}
						if (cell.getTile().getProperties().containsKey("enemy-specter")) {
							Enemy enemy = enemyPool.obtain();
							enemy.init(EntityType.ENEMY_SPECTER, this, x, y);
							enemies.add(enemy);
							spawnSound.play(CoreSettings.sfxVol);
							totalEnemies += 1;
						}
						if (cell.getTile().getProperties().containsKey("enemy-zombie")) {
							Enemy enemy = enemyPool.obtain();
							enemy.init(EntityType.ENEMY_ZOMBIE, this, x, y);
							enemies.add(enemy);
							spawnSound.play(CoreSettings.sfxVol);
							totalEnemies += 1;
						}
						if (cell.getTile().getProperties().containsKey("enemy-ogre")) {
							Enemy enemy = enemyPool.obtain();
							enemy.init(EntityType.ENEMY_OGRE, this, x, y);
							enemies.add(enemy);
							spawnSound.play(CoreSettings.sfxVol);
							totalEnemies += 1;
						}
						if (cell.getTile().getProperties().containsKey("enemy-worm")) {
							Enemy enemy = enemyPool.obtain();
							enemy.init(EntityType.ENEMY_WORM, this, x, y);
							enemies.add(enemy);
							spawnSound.play(CoreSettings.sfxVol);
							totalEnemies += 1;
						}
						if (cell.getTile().getProperties().containsKey("enemy-imp")) {
							Enemy enemy = enemyPool.obtain();
							enemy.init(EntityType.ENEMY_IMP, this, x, y);
							enemies.add(enemy);
							spawnSound.play(CoreSettings.sfxVol);
							totalEnemies += 1;
						}
						if (cell.getTile().getProperties().containsKey("enemy-ghost")) {
							Enemy enemy = enemyPool.obtain();
							enemy.init(EntityType.ENEMY_GHOST, this, x, y);
							enemies.add(enemy);
							spawnSound.play(CoreSettings.sfxVol);
							totalEnemies += 1;
						}
						if (cell.getTile().getProperties().containsKey("enemy-wight")) {
							Enemy enemy = enemyPool.obtain();
							enemy.init(EntityType.ENEMY_WIGHT, this, x, y);
							enemies.add(enemy);
							spawnSound.play(CoreSettings.sfxVol);
							totalEnemies += 1;
						}
						if (cell.getTile().getProperties().containsKey("enemy-scorpion")) {
							Enemy enemy = enemyPool.obtain();
							enemy.init(EntityType.ENEMY_SCORPION, this, x, y);
							enemies.add(enemy);
							spawnSound.play(CoreSettings.sfxVol);
							totalEnemies += 1;
						}
						if (cell.getTile().getProperties().containsKey("enemy-giantspider")) {
							Enemy enemy = enemyPool.obtain();
							enemy.init(EntityType.ENEMY_GIANT_SPIDER, this, x, y);
							enemies.add(enemy);
							spawnSound.play(CoreSettings.sfxVol);
							totalEnemies += 1;
						}
						if (cell.getTile().getProperties().containsKey("enemy-bee")) {
							Enemy enemy = enemyPool.obtain();
							enemy.init(EntityType.ENEMY_BEE, this, x, y);
							enemies.add(enemy);
							spawnSound.play(CoreSettings.sfxVol);
							totalEnemies += 1;
						}
						if (cell.getTile().getProperties().containsKey("enemy-queenbee")) {
							Enemy enemy = enemyPool.obtain();
							enemy.init(EntityType.ENEMY_QUEEN_BEE, this, x, y);
							enemies.add(enemy);
							spawnSound.play(CoreSettings.sfxVol);
							totalEnemies += 1;
						}
						if (cell.getTile().getProperties().containsKey("enemy-greaterfiend")) {
							Enemy enemy = enemyPool.obtain();
							enemy.init(EntityType.ENEMY_GREATER_FIEND, this, x, y);
							enemies.add(enemy);
							spawnSound.play(CoreSettings.sfxVol);
							totalEnemies += 1;
						}
						if (cell.getTile().getProperties().containsKey("enemy-thing")) {
							Enemy enemy = enemyPool.obtain();
							enemy.init(EntityType.ENEMY_THING, this, x, y);
							enemies.add(enemy);
							spawnSound.play(CoreSettings.sfxVol);
							totalEnemies += 1;
						}
					}
				}
			}
		}
	}

	private void deadEntities() {
		for (int i = 0; i < players.size; i++) {
			Player pl = players.get(i);
			if (pl.isDead()) {
				players.removeIndex(i);
				playerPool.free(pl);
			}
		}
		for (int i = 0; i < enemies.size; i++) {
			Enemy e = enemies.get(i);
			if (e.isDead()) {
				enemies.removeIndex(i);
				enemyPool.free(e);
				kills += 1;
			}
		}
		for (int i = 0; i < bosses.size; i++) {
			Boss b = bosses.get(i);
			if (b.isDead()) {
				bosses.removeIndex(i);
				bossPool.free(b);
				kills += 1;
			}
		}
		for (int i = 0; i < items.size; i++) {
			Item item = items.get(i);
			if (item.isDead()) {
				items.removeIndex(i);
				itemPool.free(item);
			}
		}
		for (int i = 0; i < furnishings.size; i++) {
			Furniture furniture = furnishings.get(i);
			if (furniture.isDead()) {
				furnishings.removeIndex(i);
				furnishingsPool.free(furniture);
			}
		}
		for (int i = 0; i < spawners.size; i++) {
			Spawner s = spawners.get(i);
			if (s.isDead()) {
				spawners.removeIndex(i);
				spawnerPool.free(s);
			}
		}
		for (int i = 0; i < explosives.size; i++) {
			Explosive ex = explosives.get(i);
			if (ex.isDead()) {
				explosives.removeIndex(i);
				explosivePool.free(ex);
			}
		}
		for (int i = 0; i < sentries.size; i++) {
			Sentry s = sentries.get(i);
			if (s.isDead()) {
				sentries.removeIndex(i);
				sentryPool.free(s);
			}
		}
		for (int i = 0; i < projectiles.size; i++) {
			Projectile p = projectiles.get(i);
			if (p.isDead()) {
				projectiles.removeIndex(i);
				projectilePool.free(p);
			}
		}
		for (int i = 0; i < gibs.size; i++) {
			Gib g = gibs.get(i);
			if (g.isDead()) {
				gibs.removeIndex(i);
				gibPool.free(g);
			}
		}
		for (int i = 0; i < explosions.size; i++) {
			Explosion exp = explosions.get(i);
			if (exp.isDead()) {
				explosions.removeIndex(i);
				explosionPool.free(exp);
			}
		}
	}

	private void timeUpdate() {
		timeCurrent = TimeUtils.timeSinceMillis(timeStart);
		timeSecond = (timeCurrent / 1000) % 60;
		timeMinute = (timeCurrent / (1000 * 60)) % 60;
		timeHour = (timeCurrent / (1000 * 60 * 60)) % 24;

		if (timeSecond < 10) {
			timeSecondString = "0" + timeSecond.toString();
		} else {
			timeSecondString = timeSecond.toString();
		}
		if (timeMinute < 10) {
			timeMinuteString = "0" + timeMinute.toString();
		} else {
			timeMinuteString = timeMinute.toString();
		}
		if (timeHour < 10) {
			timeHourString = "0" + timeHour.toString();
		} else {
			timeHourString = timeHour.toString();
		}

		time = timeHourString + ":" + timeMinuteString + ":" + timeSecondString;
	}

	/**
	 * Gets a Vector3 containing X/Y/Z positions.
	 * 
	 * @return {@code mousePosition}
	 */
	public Vector3 getMousePosition() {
		return mousePosition;
	}

	/**
	 * Is the level complete?
	 * 
	 * @return {@code true} if level is complete.
	 */
	public boolean isLevelComplete() {
		return levelComplete;
	}

	/**
	 * Gets the total number of secrets.
	 * 
	 * @return {@code totalSecrets}
	 */
	public float getTotalSecrets() {
		return totalSecrets;
	}

	/**
	 * Gets the number of secrets found.
	 * 
	 * @return {@code secrets}
	 */
	public int getSecrets() {
		return secrets;
	}

	/**
	 * Gets the total number of enemies.
	 * 
	 * @return {@code totalEnemies}
	 */
	public int getTotalEnemies() {
		return totalEnemies;
	}

	/**
	 * Gets the number of enemies killed.
	 * 
	 * @return {@code kills}
	 */
	public int getKills() {
		return kills;
	}

	/**
	 * Gets the level completion time.
	 * 
	 * @return {@code time}
	 */
	public String getTime() {
		return time;
	}

	/**
	 * Sets whether the level is complete.
	 * 
	 * @param levelComplete
	 *            True/false.
	 */
	public void setLevelComplete(boolean levelComplete) {
		this.levelComplete = levelComplete;
	}

	/**
	 * Sets the number of secrets found.
	 * 
	 * @param secrets
	 *            Number of secrets found.
	 */
	public void setSecrets(int secrets) {
		this.secrets = secrets;
	}

	/**
	 * Sets the total number of enemies.
	 * 
	 * @param totalEnemies
	 *            Number of enemies.
	 */
	public void setTotalEnemies(int totalEnemies) {
		this.totalEnemies = totalEnemies;
	}

}
