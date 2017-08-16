package com.hexensemble.mildred.system;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.hexensemble.mildred.levels.Level;

/**
 * Heads up display.
 * 
 * @author HexEnsemble
 * @author www.hexensemble.com
 * @version Beta 2.2.0
 * @since Alpha 1.0.0
 */
public class HUD {

	@SuppressWarnings("unused")
	private AssetManager assetManager;

	private Level level;

	private OrthographicCamera camera;
	private SpriteBatch batch;

	protected TextureAtlas textureAtlas;
	private AtlasRegion hudBackgroundRegion;
	private AtlasRegion hudPopUpRegion;
	private AtlasRegion hudMessageRegion;
	private AtlasRegion quadDamageBackgroundRegion;
	private AtlasRegion invincibleBackgroundRegion;
	private AtlasRegion sprayBackgroundRegion;
	private Sprite hudBackground;
	private Sprite hudPopUp;
	private Sprite hudMessage;
	private Sprite quadDamageBackground;
	private Sprite invincibleBackground;
	private Sprite sprayBackground;

	private BitmapFont smallFont;
	private GlyphLayout layout;

	private String health;
	private String energy;
	private String xp;
	private String xpLevel;
	private String fps;

	private Integer healthStats;
	private Integer energyStats;
	private Integer xpStats;
	private Integer xpLevelStats;
	private Integer fpsStats;

	private String healthString;
	private String energyString;
	private String xpString;
	private String xpLevelString;
	private String fpsString;

	private String levelUp;
	private String secret;
	private String exitKey;
	private String noExitKey;
	private String quit;
	private String quitConfirm;
	private String progressSaved;
	private String quadDamage;
	private String invincible;
	private String spray;

	/**
	 * Whether to show the leveled up message.
	 */
	public static boolean showLeveledUp;

	/**
	 * Whether to show the found a secret message.
	 */
	public static boolean showSecret;

	/**
	 * Whether to show the found exit key message.
	 */
	public static boolean showExitKey;

	/**
	 * Whether to show the need exit key message.
	 */
	public static boolean showNoExitKey;

	/**
	 * Whether to show the quit message.
	 */
	public static boolean showQuit;

	/**
	 * Whether to show the progress saved message.
	 */
	public static boolean showProgressSaved;

	private static boolean showQuadDamageBackground;
	private static boolean showQuadDamage;
	private static boolean quadDamageSeq;
	private static boolean showInvincibleBackground;
	private static boolean showInvincible;
	private static boolean invincibleSeq;
	private static boolean showSprayBackground;
	private static boolean showSpray;
	private static boolean spraySeq;

	private float messageTimer;
	private float quadDamageTimer;
	private float invincibleTimer;
	private float sprayTimer;

	private Sound leveledUpSound;
	private Sound secretSound;
	private Sound noExitSound;
	private Sound quadDamageSound;
	private Sound invincibleSound;
	private Sound spraySound;

	/**
	 * Initialize.
	 * 
	 * @param assetManager
	 *            Asset manager.
	 * @param level
	 *            Game level.
	 */
	public HUD(AssetManager assetManager, Level level) {
		this.assetManager = assetManager;
		this.level = level;

		camera = new OrthographicCamera();
		camera.setToOrtho(false, CoreSettings.P_WIDTH, CoreSettings.P_HEIGHT);
		batch = new SpriteBatch();

		textureAtlas = level.assetManager.get("graphics/hud.atlas", TextureAtlas.class);
		hudBackgroundRegion = textureAtlas.findRegion("hud-background");
		hudPopUpRegion = textureAtlas.findRegion("hud-popup");
		hudMessageRegion = textureAtlas.findRegion("hud-message");
		quadDamageBackgroundRegion = textureAtlas.findRegion("hud-quaddamage");
		invincibleBackgroundRegion = textureAtlas.findRegion("hud-invincible");
		sprayBackgroundRegion = textureAtlas.findRegion("hud-spray");
		hudBackground = new Sprite(hudBackgroundRegion);
		hudPopUp = new Sprite(hudPopUpRegion);
		hudMessage = new Sprite(hudMessageRegion);
		quadDamageBackground = new Sprite(quadDamageBackgroundRegion);
		invincibleBackground = new Sprite(invincibleBackgroundRegion);
		sprayBackground = new Sprite(sprayBackgroundRegion);

		smallFont = assetManager.get("fonts/Track-14.fnt", BitmapFont.class);
		layout = new GlyphLayout();

		health = "Health:";
		energy = "Energy:";
		xp = "XP:";
		xpLevel = "XP Level:";
		fps = "FPS:";

		healthStats = 0;
		energyStats = 0;
		xpStats = 0;
		xpLevelStats = 0;
		fpsStats = 0;

		healthString = "0";
		energyString = "0";
		xpString = "0";
		xpLevelString = "0";
		fpsString = "0";

		levelUp = "You leveled up!";
		secret = "You found a secret area!";
		exitKey = "You found the Exit Key!";
		noExitKey = "You need to find the Exit Key!";
		quit = "Are you sure you want to quit?";
		quitConfirm = "Y/N";
		progressSaved = "Progress saved!";
		quadDamage = "QUAD DAMAGE!";
		invincible = "INVINCIBLE!";
		spray = "SPRAY!";

		showLeveledUp = false;
		showSecret = false;
		showExitKey = false;
		showNoExitKey = false;
		showQuit = false;

		showQuadDamageBackground = false;
		showQuadDamage = false;
		quadDamageSeq = false;
		showInvincibleBackground = false;
		showInvincible = false;
		invincibleSeq = false;
		showSprayBackground = false;
		showSpray = false;
		spraySeq = false;

		messageTimer = 0;
		quadDamageTimer = 0;
		invincibleTimer = 0;
		sprayTimer = 0;

		leveledUpSound = assetManager.get("sounds/hud-leveledup.wav", Sound.class);
		secretSound = assetManager.get("sounds/hud-secret.wav", Sound.class);
		noExitSound = assetManager.get("sounds/hud-noexitkey.wav", Sound.class);
		quadDamageSound = assetManager.get("sounds/hud-quaddamage.wav", Sound.class);
		invincibleSound = assetManager.get("sounds/hud-invincible.wav", Sound.class);
		spraySound = assetManager.get("sounds/hud-spray.wav", Sound.class);
	}

	/**
	 * Update.
	 * 
	 * @param delta
	 *            Delta time.
	 */
	public void update(float delta) {
		stats(delta);
		messages(delta);
		quadDamageSeq(delta);
		invincibleSeq(delta);
		spraySeq(delta);
	}

	/**
	 * Render.
	 */
	public void render() {
		camera.update();

		batch.setProjectionMatrix(camera.combined);

		if (showQuadDamageBackground) {
			batch.begin();
			batch.draw(quadDamageBackground, 0, 0);
			batch.end();
		}
		if (showInvincibleBackground) {
			batch.begin();
			batch.draw(invincibleBackground, 0, 0);
			batch.end();
		}
		if (showSprayBackground) {
			batch.begin();
			batch.draw(sprayBackground, 0, 0);
			batch.end();
		}

		batch.begin();
		batch.draw(hudBackground, 0, CoreSettings.P_HEIGHT - CoreSettings.P_TILE_SIZE);
		batch.draw(hudBackground, 0, 0);
		batch.end();

		batch.begin();
		layout.setText(smallFont, health);
		smallFont.setColor(Color.valueOf("ff0000"));
		smallFont.draw(batch, health, CoreSettings.P_TILE_SIZE, layout.height * 1.75f);
		batch.end();

		batch.begin();
		layout.setText(smallFont, healthString);
		smallFont.setColor(Color.valueOf("ffffff"));
		smallFont.draw(batch, healthString, CoreSettings.P_TILE_SIZE * 4, layout.height * 1.75f);
		batch.end();

		batch.begin();
		layout.setText(smallFont, xpLevel);
		smallFont.setColor(Color.valueOf("ff0000"));
		smallFont.draw(batch, xpLevel, CoreSettings.P_WIDTH / 2 - layout.width, layout.height * 1.75f);
		batch.end();

		batch.begin();
		layout.setText(smallFont, xpLevelString);
		smallFont.setColor(Color.valueOf("ffffff"));
		smallFont.draw(batch, xpLevelString, CoreSettings.P_WIDTH / 2 + CoreSettings.P_TILE_SIZE,
				layout.height * 1.75f);
		batch.end();

		batch.begin();
		layout.setText(smallFont, energy);
		smallFont.setColor(Color.valueOf("ff0000"));
		smallFont.draw(batch, energy, CoreSettings.P_WIDTH - (CoreSettings.P_TILE_SIZE * 4) - layout.width,
				layout.height * 1.75f);
		batch.end();

		batch.begin();
		layout.setText(smallFont, energyString);
		smallFont.setColor(Color.valueOf("ffffff"));
		smallFont.draw(batch, energyString, CoreSettings.P_WIDTH - CoreSettings.P_TILE_SIZE - layout.width,
				layout.height * 1.75f);
		batch.end();

		if (CoreSettings.fps) {
			batch.begin();
			layout.setText(smallFont, fps);
			smallFont.setColor(Color.valueOf("ff0000"));
			smallFont.draw(batch, fps, CoreSettings.P_TILE_SIZE, CoreSettings.P_HEIGHT - (layout.height * 0.75f));
			batch.end();

			batch.begin();
			layout.setText(smallFont, fpsString);
			smallFont.setColor(Color.valueOf("ffffff"));
			smallFont.draw(batch, fpsString, CoreSettings.P_TILE_SIZE * 3,
					CoreSettings.P_HEIGHT - (layout.height * 0.75f));
			batch.end();
		}

		batch.begin();
		layout.setText(smallFont, xp);
		smallFont.setColor(Color.valueOf("ff0000"));
		smallFont.draw(batch, xp, CoreSettings.P_WIDTH / 2 - CoreSettings.P_TILE_SIZE,
				CoreSettings.P_HEIGHT - (layout.height * 0.75f));
		batch.end();

		batch.begin();
		layout.setText(smallFont, xpString);
		smallFont.setColor(Color.valueOf("ffffff"));
		smallFont.draw(batch, xpString, CoreSettings.P_WIDTH / 2 + (CoreSettings.P_TILE_SIZE / 2),
				CoreSettings.P_HEIGHT - (layout.height * 0.75f));
		batch.end();

		if (showLeveledUp) {
			batch.begin();
			batch.draw(hudPopUp, (CoreSettings.P_WIDTH - hudPopUp.getWidth()) / 2,
					(CoreSettings.P_HEIGHT - hudPopUp.getHeight()) / 2 + (CoreSettings.P_TILE_SIZE * 6));
			batch.end();

			batch.begin();
			layout.setText(smallFont, levelUp);
			smallFont.setColor(Color.valueOf("ff0000"));
			smallFont.draw(batch, levelUp, (CoreSettings.P_WIDTH - layout.width) / 2,
					(CoreSettings.P_HEIGHT - layout.height) / 2 + (CoreSettings.P_TILE_SIZE * 6.5f));
			batch.end();
		}
		if (showSecret) {
			batch.begin();
			batch.draw(hudPopUp, (CoreSettings.P_WIDTH - hudPopUp.getWidth()) / 2,
					(CoreSettings.P_HEIGHT - hudPopUp.getHeight()) / 2 + (CoreSettings.P_TILE_SIZE * 6));
			batch.end();

			batch.begin();
			layout.setText(smallFont, secret);
			smallFont.setColor(Color.valueOf("ff0000"));
			smallFont.draw(batch, secret, (CoreSettings.P_WIDTH - layout.width) / 2,
					(CoreSettings.P_HEIGHT - layout.height) / 2 + (CoreSettings.P_TILE_SIZE * 6.5f));
			batch.end();
		}
		if (showExitKey) {
			batch.begin();
			batch.draw(hudPopUp, (CoreSettings.P_WIDTH - hudPopUp.getWidth()) / 2,
					(CoreSettings.P_HEIGHT - hudPopUp.getHeight()) / 2 + (CoreSettings.P_TILE_SIZE * 6));
			batch.end();

			batch.begin();
			layout.setText(smallFont, exitKey);
			smallFont.setColor(Color.valueOf("ff0000"));
			smallFont.draw(batch, exitKey, (CoreSettings.P_WIDTH - layout.width) / 2,
					(CoreSettings.P_HEIGHT - layout.height) / 2 + (CoreSettings.P_TILE_SIZE * 6.5f));
			batch.end();
		}
		if (showNoExitKey) {
			batch.begin();
			batch.draw(hudPopUp, (CoreSettings.P_WIDTH - hudPopUp.getWidth()) / 2,
					(CoreSettings.P_HEIGHT - hudPopUp.getHeight()) / 2 + (CoreSettings.P_TILE_SIZE * 6));
			batch.end();

			batch.begin();
			layout.setText(smallFont, noExitKey);
			smallFont.setColor(Color.valueOf("ff0000"));
			smallFont.draw(batch, noExitKey, (CoreSettings.P_WIDTH - layout.width) / 2,
					(CoreSettings.P_HEIGHT - layout.height) / 2 + (CoreSettings.P_TILE_SIZE * 6.5f));
			batch.end();
		}
		if (showQuit) {
			batch.begin();
			batch.draw(hudMessage, (CoreSettings.P_WIDTH - hudMessage.getWidth()) / 2,
					(CoreSettings.P_HEIGHT - hudMessage.getHeight()) / 2);
			batch.end();

			batch.begin();
			layout.setText(smallFont, quit);
			smallFont.setColor(Color.valueOf("582000"));
			smallFont.draw(batch, quit, (CoreSettings.P_WIDTH - layout.width) / 2,
					(CoreSettings.P_HEIGHT - layout.height) / 2 + (CoreSettings.P_TILE_SIZE * 2));
			layout.setText(smallFont, quitConfirm);
			smallFont.setColor(Color.valueOf("ff0000"));
			smallFont.draw(batch, quitConfirm, (CoreSettings.P_WIDTH - layout.width) / 2,
					(CoreSettings.P_HEIGHT - layout.height) / 2);
			batch.end();
		}
		if (showProgressSaved) {
			batch.begin();
			batch.draw(hudPopUp, (CoreSettings.P_WIDTH - hudPopUp.getWidth()) / 2,
					(CoreSettings.P_HEIGHT - hudPopUp.getHeight()) / 2 + (CoreSettings.P_TILE_SIZE * 6));
			batch.end();

			batch.begin();
			layout.setText(smallFont, progressSaved);
			smallFont.setColor(Color.valueOf("ff0000"));
			smallFont.draw(batch, progressSaved, (CoreSettings.P_WIDTH - layout.width) / 2,
					(CoreSettings.P_HEIGHT - layout.height) / 2 + (CoreSettings.P_TILE_SIZE * 6.5f));
			batch.end();
		}
		if (showQuadDamage) {
			batch.begin();
			batch.draw(hudPopUp, (CoreSettings.P_WIDTH - hudPopUp.getWidth()) / 2,
					(CoreSettings.P_HEIGHT - hudPopUp.getHeight()) / 2 + (CoreSettings.P_TILE_SIZE * 6));
			batch.end();

			batch.begin();
			layout.setText(smallFont, quadDamage);
			smallFont.setColor(Color.valueOf("ff0000"));
			smallFont.draw(batch, quadDamage, (CoreSettings.P_WIDTH - layout.width) / 2,
					(CoreSettings.P_HEIGHT - layout.height) / 2 + (CoreSettings.P_TILE_SIZE * 6.5f));
			batch.end();
		}
		if (showInvincible) {
			batch.begin();
			batch.draw(hudPopUp, (CoreSettings.P_WIDTH - hudPopUp.getWidth()) / 2,
					(CoreSettings.P_HEIGHT - hudPopUp.getHeight()) / 2 + (CoreSettings.P_TILE_SIZE * 6));
			batch.end();

			batch.begin();
			layout.setText(smallFont, invincible);
			smallFont.setColor(Color.valueOf("ff0000"));
			smallFont.draw(batch, invincible, (CoreSettings.P_WIDTH - layout.width) / 2,
					(CoreSettings.P_HEIGHT - layout.height) / 2 + (CoreSettings.P_TILE_SIZE * 6.5f));
			batch.end();
		}
		if (showSpray) {
			batch.begin();
			batch.draw(hudPopUp, (CoreSettings.P_WIDTH - hudPopUp.getWidth()) / 2,
					(CoreSettings.P_HEIGHT - hudPopUp.getHeight()) / 2 + (CoreSettings.P_TILE_SIZE * 6));
			batch.end();

			batch.begin();
			layout.setText(smallFont, spray);
			smallFont.setColor(Color.valueOf("ff0000"));
			smallFont.draw(batch, spray, (CoreSettings.P_WIDTH - layout.width) / 2,
					(CoreSettings.P_HEIGHT - layout.height) / 2 + (CoreSettings.P_TILE_SIZE * 6.5f));
			batch.end();
		}
	}

	/**
	 * Dispose.
	 */
	public void dispose() {
		batch.dispose();
	}

	private void stats(float delta) {
		if (level.players.size > 0) {
			healthStats = level.players.get(0).getHealth();
			energyStats = level.players.get(0).getEnergy();
			xpStats = level.players.get(0).getXp();
			xpLevelStats = level.players.get(0).getXpLevel();

			healthString = healthStats.toString() + " / " + level.players.get(0).getMaxHealth();
			energyString = energyStats.toString() + " / " + level.players.get(0).getMaxEnergy();
			xpString = xpStats.toString();
			xpLevelString = xpLevelStats.toString();
		} else {
			healthString = "0";
			energyString = "0";
			xpString = "0";
			xpLevelString = "0";
		}
		if (CoreSettings.fps) {
			fpsStats = Gdx.graphics.getFramesPerSecond();
			fpsString = fpsStats.toString();
		}
	}

	private void messages(float delta) {
		if (showLeveledUp) {
			messageTimer += delta;
			if (messageTimer < 0.1f) {
				leveledUpSound.play(CoreSettings.sfxVol);
			}
			if (messageTimer > 2) {
				showLeveledUp = false;
				messageTimer = 0;
			}
		}
		if (showSecret) {
			messageTimer += delta;
			if (messageTimer < 0.1f) {
				secretSound.play(CoreSettings.sfxVol);
			}
			if (messageTimer > 2) {
				showSecret = false;
				messageTimer = 0;
			}
		}
		if (showExitKey) {
			messageTimer += delta;
			if (messageTimer > 2) {
				showExitKey = false;
				messageTimer = 0;
			}
		}
		if (showNoExitKey) {
			messageTimer += delta;
			if (messageTimer < 0.1f) {
				noExitSound.play(CoreSettings.sfxVol);
			}
			if (messageTimer > 2) {
				showNoExitKey = false;
				messageTimer = 0;
			}
		}
		if (showProgressSaved) {
			messageTimer += delta;
			if (messageTimer > 2) {
				showProgressSaved = false;
				messageTimer = 0;
			}
		}
		if (showQuadDamage) {
			messageTimer += delta;
			if (messageTimer > 2) {
				showQuadDamage = false;
				messageTimer = 0;
			}
		}
		if (showInvincible) {
			messageTimer += delta;
			if (messageTimer > 2) {
				showInvincible = false;
				messageTimer = 0;
			}
		}
		if (showSpray) {
			messageTimer += delta;
			if (messageTimer > 2) {
				showSpray = false;
				messageTimer = 0;
			}
		}
	}

	private void quadDamageSeq(float delta) {
		if (quadDamageSeq) {
			quadDamageTimer += delta;

			if (quadDamageTimer > 23) {
				showQuadDamageBackground = false;
			}
			if (quadDamageTimer > 23 && quadDamageTimer < 23.1) {
				quadDamageSound.play(CoreSettings.sfxVol);
			}
			if (quadDamageTimer > 23.5) {
				showQuadDamageBackground = true;
			}
			if (quadDamageTimer > 23.5 && quadDamageTimer < 23.6) {
				quadDamageSound.play(CoreSettings.sfxVol);
			}
			if (quadDamageTimer > 24) {
				showQuadDamageBackground = false;
			}
			if (quadDamageTimer > 24 && quadDamageTimer < 24.1) {
				quadDamageSound.play(CoreSettings.sfxVol);
			}
			if (quadDamageTimer > 24.5) {
				showQuadDamageBackground = true;
			}
			if (quadDamageTimer > 24.5 && quadDamageTimer < 24.6) {
				quadDamageSound.play(CoreSettings.sfxVol);
			}
			if (quadDamageTimer > 25) {
				showQuadDamageBackground = false;
				quadDamageSeq = false;
				quadDamageTimer = 0;
				quadDamageSound.play(CoreSettings.sfxVol);
			}
		}
	}

	private void invincibleSeq(float delta) {
		if (invincibleSeq) {
			invincibleTimer += delta;

			if (invincibleTimer > 23) {
				showInvincibleBackground = false;
			}
			if (invincibleTimer > 23 && invincibleTimer < 23.1) {
				invincibleSound.play(CoreSettings.sfxVol);
			}
			if (invincibleTimer > 23.5) {
				showInvincibleBackground = true;
			}
			if (invincibleTimer > 23.5 && invincibleTimer < 23.6) {
				invincibleSound.play(CoreSettings.sfxVol);
			}
			if (invincibleTimer > 24) {
				showInvincibleBackground = false;
			}
			if (invincibleTimer > 24 && invincibleTimer < 24.1) {
				invincibleSound.play(CoreSettings.sfxVol);
			}
			if (invincibleTimer > 24.5) {
				showInvincibleBackground = true;
			}
			if (invincibleTimer > 24.5 && invincibleTimer < 24.6) {
				invincibleSound.play(CoreSettings.sfxVol);
			}
			if (invincibleTimer > 25) {
				showInvincibleBackground = false;
				invincibleSeq = false;
				invincibleTimer = 0;
				invincibleSound.play(CoreSettings.sfxVol);
			}
		}
	}

	private void spraySeq(float delta) {
		if (spraySeq) {
			sprayTimer += delta;

			if (sprayTimer > 23) {
				showSprayBackground = false;
			}
			if (sprayTimer > 23 && sprayTimer < 23.1) {
				spraySound.play(CoreSettings.sfxVol);
			}
			if (sprayTimer > 23.5) {
				showSprayBackground = true;
			}
			if (sprayTimer > 23.5 && sprayTimer < 23.6) {
				spraySound.play(CoreSettings.sfxVol);
			}
			if (sprayTimer > 24) {
				showSprayBackground = false;
			}
			if (sprayTimer > 24 && sprayTimer < 24.1) {
				spraySound.play(CoreSettings.sfxVol);
			}
			if (sprayTimer > 24.5) {
				showSprayBackground = true;
			}
			if (sprayTimer > 24.5 && sprayTimer < 24.6) {
				spraySound.play(CoreSettings.sfxVol);
			}
			if (sprayTimer > 25) {
				showSprayBackground = false;
				spraySeq = false;
				sprayTimer = 0;
				spraySound.play(CoreSettings.sfxVol);
			}
		}
	}

	public static void quadDamage() {
		showQuadDamageBackground = true;
		showQuadDamage = true;
		quadDamageSeq = true;
	}

	public static void invincible() {
		showInvincibleBackground = true;
		showInvincible = true;
		invincibleSeq = true;
	}

	public static void spray() {
		showSprayBackground = true;
		showSpray = true;
		spraySeq = true;
	}

}
