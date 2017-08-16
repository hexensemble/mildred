package com.hexensemble.mildred.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.hexensemble.mildred.system.CoreSettings;
import com.hexensemble.mildred.system.GameData;

/**
 * Represents the progress state between levels.
 * 
 * @author HexEnsemble
 * @author www.hexensemble.com
 * @version Beta 2.0.0
 * @since Alpha 1.0.0
 */
public class ProgressState extends State {

	private Texture background;

	private BitmapFont largeFont;
	private BitmapFont mediumFont;
	private GlyphLayout layout;

	private String areaCleared;

	private int killStats;
	private int totalEnemies;
	private int secretStats;
	private int totalSecrets;
	private String timeStats;

	private String kills;
	private String secrets;
	private String time;
	private String killResult;
	private String secretResult;
	private String timeResult;

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
	 */
	public ProgressState(AssetManager assetManager, Viewport viewport, OrthographicCamera camera) {
		super(assetManager, viewport, camera);

		viewport.setWorldSize(CoreSettings.P_WIDTH, CoreSettings.P_HEIGHT);
		viewport.apply();
		camera.setToOrtho(false, camera.viewportWidth, camera.viewportHeight);

		cursor = Gdx.graphics.newCursor(new Pixmap(Gdx.files.internal("graphics/mouse-blank.png")),
				(int) (CoreSettings.P_TILE_SIZE / 2), (int) (CoreSettings.P_TILE_SIZE / 2));

		background = assetManager.get("graphics/progress-background.png", Texture.class);

		largeFont = assetManager.get("fonts/Track-30.fnt", BitmapFont.class);
		mediumFont = assetManager.get("fonts/Track-20.fnt", BitmapFont.class);
		layout = new GlyphLayout();

		areaCleared = "Area Cleared!";

		killStats = GameData.kills;
		totalEnemies = GameData.totalEnemies;
		secretStats = GameData.secrets;
		totalSecrets = (int) GameData.totalSecrets;
		timeStats = GameData.time;

		kills = "Kills:";
		secrets = "Secrets:";
		time = "Time:";
		killResult = killStats + "/" + totalEnemies;
		secretResult = secretStats + "/" + totalSecrets;
		timeResult = timeStats;

		music = assetManager.get("music/progress.ogg", Music.class);
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
	@Override
	public void update(float delta) {
		super.update(delta);

		if (stateFinished) {
			music.stop();
		}
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

		batch.begin();
		batch.draw(background, 0, 0);
		batch.end();

		batch.begin();
		layout.setText(largeFont, areaCleared);
		largeFont.setColor(Color.valueOf("ff0000"));
		largeFont.draw(batch, areaCleared, (CoreSettings.P_WIDTH - layout.width) / 2,
				(CoreSettings.P_HEIGHT - layout.height) / 2 + (CoreSettings.P_TILE_SIZE * 8));
		batch.end();

		batch.begin();
		layout.setText(mediumFont, kills);
		mediumFont.setColor(Color.valueOf("ff0000"));
		mediumFont.draw(batch, kills, (CoreSettings.P_WIDTH - layout.width) / 2 - (CoreSettings.P_TILE_SIZE * 2),
				(CoreSettings.P_HEIGHT - layout.height) / 2 + (CoreSettings.P_TILE_SIZE * 2));
		batch.end();

		batch.begin();
		layout.setText(mediumFont, killResult);
		mediumFont.setColor(Color.valueOf("ffffff"));
		mediumFont.draw(batch, killResult, (CoreSettings.P_WIDTH - layout.width) / 2 + (CoreSettings.P_TILE_SIZE * 2),
				(CoreSettings.P_HEIGHT - layout.height) / 2 + (CoreSettings.P_TILE_SIZE * 2));
		batch.end();

		batch.begin();
		layout.setText(mediumFont, secrets);
		mediumFont.setColor(Color.valueOf("ff0000"));
		mediumFont.draw(batch, secrets, (CoreSettings.P_WIDTH - layout.width) / 2 - (CoreSettings.P_TILE_SIZE * 2),
				((CoreSettings.P_HEIGHT - layout.height) / 2));
		batch.end();

		batch.begin();
		layout.setText(mediumFont, secretResult);
		mediumFont.setColor(Color.valueOf("ffffff"));
		mediumFont.draw(batch, secretResult, (CoreSettings.P_WIDTH - layout.width) / 2 + (CoreSettings.P_TILE_SIZE * 2),
				((CoreSettings.P_HEIGHT - layout.height) / 2));
		batch.end();

		batch.begin();
		layout.setText(mediumFont, time);
		mediumFont.setColor(Color.valueOf("ff0000"));
		mediumFont.draw(batch, time, (CoreSettings.P_WIDTH - layout.width) / 2 - (CoreSettings.P_TILE_SIZE * 2),
				((CoreSettings.P_HEIGHT - layout.height) / 2) - (CoreSettings.P_TILE_SIZE * 2));
		batch.end();

		batch.begin();
		layout.setText(mediumFont, timeResult);
		mediumFont.setColor(Color.valueOf("ffffff"));
		mediumFont.draw(batch, timeResult, (CoreSettings.P_WIDTH - layout.width) / 2 + (CoreSettings.P_TILE_SIZE * 2),
				((CoreSettings.P_HEIGHT - layout.height) / 2) - (CoreSettings.P_TILE_SIZE * 2));
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
	protected void controls() {
		if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER) || Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
			stateFinished = true;
			nextStateID = StateManager.NEXT_LEVEL_PLAY_STATE;
		}
	}

}
