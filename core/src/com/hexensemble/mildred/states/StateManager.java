package com.hexensemble.mildred.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.hexensemble.mildred.system.CoreSettings;

/**
 * Loads assets and manages the application states.
 * 
 * @author HexEnsemble
 * @author www.hexensemble.com
 * @version 1.0.0
 * @since Alpha 1.0.0
 */
public class StateManager {

	/**
	 * State ID: Introduction state.
	 */
	public static final int INTRO_STATE = 0;

	/**
	 * State ID: Main menu state.
	 */
	public static final int MENU_STATE = 1;

	/**
	 * State ID: Prologue state.
	 */
	public static final int PROLOGUE_STATE = 2;

	/**
	 * State ID: Play state.
	 */
	public static final int PLAY_STATE = 3;

	/**
	 * State ID: Play state, continue from last save.
	 */
	public static final int CONTINUE_PLAY_STATE = 4;

	/**
	 * State ID: Progress state between levels.
	 */
	public static final int PROGRESS_STATE = 5;

	/**
	 * State ID: Play state, start the next level.
	 */
	public static final int NEXT_LEVEL_PLAY_STATE = 6;

	/**
	 * State ID: End of game state.
	 */
	public static final int END_STATE = 7;

	/**
	 * State ID: Help state.
	 */
	public static final int HELP_STATE = 8;

	/**
	 * State ID: Credits state.
	 */
	public static final int CREDITS_STATE = 9;

	/**
	 * State ID: Error state.
	 */
	public static final int ERROR_STATE = 10;

	/**
	 * Abstract class for all states.
	 */
	public State state;

	private AssetManager assetManager;
	private OrthographicCamera camera;
	private Viewport viewport;
	private SpriteBatch batch;

	private FileHandle fontFile;
	private BitmapFont mediumFont;
	private GlyphLayout layout;

	private float loadPercentage;
	private String loadProgress;
	private boolean loaded;
	private boolean running;

	/**
	 * Initialize.
	 * 
	 */
	public StateManager() {
		assetManager = new AssetManager();
		camera = new OrthographicCamera();
		viewport = new FitViewport(CoreSettings.width, CoreSettings.height, camera);
		viewport.setWorldSize(CoreSettings.P_WIDTH, CoreSettings.P_HEIGHT);
		viewport.apply();
		camera.setToOrtho(false, camera.viewportWidth, camera.viewportHeight);
		batch = new SpriteBatch();

		fontFile = Gdx.files.internal("fonts/Track-20.fnt");
		mediumFont = new BitmapFont(fontFile);
		layout = new GlyphLayout();

		loadPercentage = 0;
		loadProgress = null;
		loaded = false;
		running = false;

		loadAssets();
	}

	/**
	 * Update.
	 * 
	 * @param delta
	 *            Delta time.
	 */
	public void update(float delta) {
		if (!assetManager.update()) {
			assetManager.update();
			loadPercentage = assetManager.getProgress() * 100;
			loadProgress = "Loading: " + (int) loadPercentage + "%";
		} else {
			loaded = true;
		}

		if (loaded && !running) {
			setState(INTRO_STATE);
			running = true;
		}

		if (running) {
			state.update(delta);

			if (State.stateFinished) {
				setState(State.nextStateID);
			}
		}
	}

	/**
	 * Render.
	 */
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();

		batch.setProjectionMatrix(camera.combined);

		if (!assetManager.update()) {
			batch.begin();
			layout.setText(mediumFont, loadProgress);
			mediumFont.setColor(Color.valueOf("ff0000"));
			mediumFont.draw(batch, loadProgress, (CoreSettings.P_WIDTH - layout.width) / 2,
					(CoreSettings.P_HEIGHT - layout.height) / 2);
			batch.end();
		}

		if (running) {
			state.render(batch);
		}
	}

	/**
	 * Resizes the screen.
	 * 
	 * @param width
	 *            New screen width.
	 * @param height
	 *            New screen height.
	 */
	public void resize(int width, int height) {
		viewport.update(width, height);
		camera.setToOrtho(false, camera.viewportWidth, camera.viewportHeight);
	}

	/**
	 * Dispose.
	 */
	public void dispose() {
		assetManager.dispose();
		batch.dispose();
		mediumFont.dispose();
		state.dispose();
	}

	/**
	 * Sets the application state.
	 * 
	 * @param stateID
	 *            State ID.
	 */
	public void setState(int stateID) {
		if (state != null) {
			state.dispose();
		}

		switch (stateID) {
		case INTRO_STATE:
			state = new IntroState(assetManager, viewport, camera);
			break;
		case MENU_STATE:
			state = new MenuState(assetManager, viewport, camera);
			break;
		case PROLOGUE_STATE:
			state = new PrologueState(assetManager, viewport, camera);
			break;
		case PLAY_STATE:
			state = new PlayState(assetManager, viewport, camera, PlayState.NEW_GAME);
			break;
		case CONTINUE_PLAY_STATE:
			state = new PlayState(assetManager, viewport, camera, PlayState.CONTINUE_GAME);
			break;
		case PROGRESS_STATE:
			state = new ProgressState(assetManager, viewport, camera);
			break;
		case NEXT_LEVEL_PLAY_STATE:
			state = new PlayState(assetManager, viewport, camera, PlayState.NEXT_LEVEL);
			break;
		case END_STATE:
			state = new EndState(assetManager, viewport, camera);
			break;
		case HELP_STATE:
			state = new HelpState(assetManager, viewport, camera);
			break;
		case CREDITS_STATE:
			state = new CreditsState(assetManager, viewport, camera);
			break;
		case ERROR_STATE:
			state = new ErrorState(assetManager, viewport, camera);
			break;
		default:
			state = new IntroState(assetManager, viewport, camera);
			break;
		}
	}

	private void loadAssets() {
		// Fonts
		assetManager.load("fonts/Track-30.fnt", BitmapFont.class);
		assetManager.load("fonts/Track-20.fnt", BitmapFont.class);
		assetManager.load("fonts/Track-14.fnt", BitmapFont.class);

		// Textures
		assetManager.load("graphics/intro-hotbakedgoodslogo.png", Texture.class);
		assetManager.load("graphics/intro-hexensemblelogo.png", Texture.class);
		assetManager.load("graphics/intro-mildredlogo.png", Texture.class);
		assetManager.load("graphics/intro-mildred.png", Texture.class);
		assetManager.load("graphics/intro-start.png", Texture.class);
		assetManager.load("graphics/menu-background.png", Texture.class);
		assetManager.load("graphics/menu-menu.png", Texture.class);
		assetManager.load("graphics/menu-message.png", Texture.class);
		assetManager.load("graphics/menu-socket.png", Texture.class);
		assetManager.load("graphics/menu-slider.png", Texture.class);
		assetManager.load("graphics/help-background.png", Texture.class);
		assetManager.load("graphics/credits-background.png", Texture.class);
		assetManager.load("graphics/prologue-animation.png", Texture.class);
		assetManager.load("graphics/progress-background.png", Texture.class);
		assetManager.load("graphics/end-background01.png", Texture.class);
		assetManager.load("graphics/end-background02.png", Texture.class);
		assetManager.load("graphics/end-background03.png", Texture.class);

		// Texture Atlases
		assetManager.load("graphics/entities.atlas", TextureAtlas.class);
		assetManager.load("graphics/hud.atlas", TextureAtlas.class);

		// Sounds
		assetManager.load("sounds/intro-hotbakedgoods.wav", Sound.class);
		assetManager.load("sounds/intro-hexensemble.wav", Sound.class);
		assetManager.load("sounds/hud-leveledup.wav", Sound.class);
		assetManager.load("sounds/hud-secret.wav", Sound.class);
		assetManager.load("sounds/hud-noexitkey.wav", Sound.class);
		assetManager.load("sounds/hud-quaddamage.wav", Sound.class);
		assetManager.load("sounds/hud-invincible.wav", Sound.class);
		assetManager.load("sounds/hud-spray.wav", Sound.class);
		assetManager.load("sounds/player-melee.wav", Sound.class);
		assetManager.load("sounds/enemy-melee.wav", Sound.class);
		assetManager.load("sounds/enemy-spawn.wav", Sound.class);
		assetManager.load("sounds/boss-death.wav", Sound.class);
		assetManager.load("sounds/item-health.wav", Sound.class);
		assetManager.load("sounds/item-energy.wav", Sound.class);
		assetManager.load("sounds/item-xp.wav", Sound.class);
		assetManager.load("sounds/item-exitkey.wav", Sound.class);
		assetManager.load("sounds/item-quaddamage.wav", Sound.class);
		assetManager.load("sounds/item-invincible.wav", Sound.class);
		assetManager.load("sounds/item-spray.wav", Sound.class);
		assetManager.load("sounds/spawner-default.wav", Sound.class);
		assetManager.load("sounds/projectile-energy1.wav", Sound.class);
		assetManager.load("sounds/projectile-energy2.wav", Sound.class);
		assetManager.load("sounds/projectile-energy3.wav", Sound.class);
		assetManager.load("sounds/projectile-lightning.wav", Sound.class);
		assetManager.load("sounds/projectile-flame.wav", Sound.class);
		assetManager.load("sounds/projectile-spark.wav", Sound.class);
		assetManager.load("sounds/hit-player.wav", Sound.class);
		assetManager.load("sounds/hit-enemy.wav", Sound.class);
		assetManager.load("sounds/hit-boss.wav", Sound.class);
		assetManager.load("sounds/hit-furniture.wav", Sound.class);
		assetManager.load("sounds/hit-spawner.wav", Sound.class);
		assetManager.load("sounds/hit-explosive.wav", Sound.class);
		assetManager.load("sounds/hit-sentry.wav", Sound.class);
		assetManager.load("sounds/hit-wall.wav", Sound.class);
		assetManager.load("sounds/gib-white.wav", Sound.class);
		assetManager.load("sounds/gib-red.wav", Sound.class);
		assetManager.load("sounds/gib-green.wav", Sound.class);
		assetManager.load("sounds/gib-yellow.wav", Sound.class);
		assetManager.load("sounds/gib-blood.wav", Sound.class);
		assetManager.load("sounds/gib-evaporate.wav", Sound.class);
		assetManager.load("sounds/gib-furniture.wav", Sound.class);
		assetManager.load("sounds/gib-spawner.wav", Sound.class);
		assetManager.load("sounds/gib-sentry.wav", Sound.class);
		assetManager.load("sounds/gib-explosion.wav", Sound.class);
		assetManager.load("sounds/explosion-default.wav", Sound.class);
		assetManager.load("sounds/door-active.wav", Sound.class);

		// Music
		assetManager.load("music/intro.ogg", Music.class);
		assetManager.load("music/prologue.ogg", Music.class);
		assetManager.load("music/progress.ogg", Music.class);
		assetManager.load("music/end.ogg", Music.class);
		assetManager.load("music/01.ogg", Music.class);
		assetManager.load("music/02.ogg", Music.class);
		assetManager.load("music/03.ogg", Music.class);
		assetManager.load("music/04.ogg", Music.class);
		assetManager.load("music/05.ogg", Music.class);
	}

}
