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

/**
 * Represents the end of game state.
 * 
 * @author HexEnsemble
 * @author www.hexensemble.com
 * @version Beta 2.2.0
 * @since Alpha 1.0.0
 */
public class EndState extends State {

	private Texture background1;
	private Texture background2;
	private Texture background3;

	private BitmapFont mediumFont;
	private GlyphLayout layout;

	private String line1;
	private String line2;
	private String line3;
	private String line4;
	private String line5;
	private String line6;
	private String line7;
	private String line8;
	private String line9;

	private Music music;
	private boolean musicOn;

	private float timer;

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
	public EndState(AssetManager assetManager, Viewport viewport, OrthographicCamera camera) {
		super(assetManager, viewport, camera);

		viewport.setWorldSize(CoreSettings.P_WIDTH, CoreSettings.P_HEIGHT);
		viewport.apply();
		camera.setToOrtho(false, camera.viewportWidth, camera.viewportHeight);

		cursor = Gdx.graphics.newCursor(new Pixmap(Gdx.files.internal("graphics/mouse-blank.png")),
				(int) (CoreSettings.P_TILE_SIZE / 2), (int) (CoreSettings.P_TILE_SIZE / 2));

		background1 = assetManager.get("graphics/end-background01.png", Texture.class);
		background2 = assetManager.get("graphics/end-background02.png", Texture.class);
		background3 = assetManager.get("graphics/end-background03.png", Texture.class);

		mediumFont = assetManager.get("fonts/Track-20.fnt", BitmapFont.class);
		layout = new GlyphLayout();

		line1 = "You did it!";
		line2 = "The evil is defeated and Mildred is free.";
		line3 = "But wait...";
		line4 = "you hear voices...";
		line5 = "\"Mildred?\"";
		line6 = "\"Mildred, your soup's getting cold\"";
		line7 = "\"What's with that girl lately?\"";
		line8 = "\"Oh Ian, what are we going to do with her?\"";
		line9 = "\"Mildred!!!\"";

		music = assetManager.get("music/end.ogg", Music.class);
		music.setVolume(CoreSettings.musicVol);
		music.setLooping(false);
		musicOn = false;

		timer = 0;
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

		timer += delta;

		if (timer >= 2) {
			if (!musicOn) {
				music.play();
				musicOn = true;
			}
		}

		if (timer >= 46) {
			music.stop();

			stateFinished = true;
			nextStateID = StateManager.CREDITS_STATE;
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

		if (timer >= 2 && timer < 7) {
			batch.begin();
			batch.draw(background1, 0, 0);
			batch.end();

			batch.begin();
			layout.setText(mediumFont, line1);
			mediumFont.setColor(Color.valueOf("ffffff"));
			mediumFont.draw(batch, line1, (CoreSettings.P_WIDTH - layout.width) / 2,
					(CoreSettings.P_HEIGHT - layout.height) / 2 + (CoreSettings.P_TILE_SIZE * 11));
			batch.end();
		}
		if (timer >= 7 && timer < 11.5) {
			batch.begin();
			batch.draw(background1, 0, 0);
			batch.end();

			batch.begin();
			layout.setText(mediumFont, line2);
			mediumFont.setColor(Color.valueOf("ffffff"));
			mediumFont.draw(batch, line2, (CoreSettings.P_WIDTH - layout.width) / 2,
					(CoreSettings.P_HEIGHT - layout.height) / 2 + (CoreSettings.P_TILE_SIZE * 11));
			batch.end();
		}
		if (timer >= 11.5 && timer < 16.5) {
			batch.begin();
			batch.draw(background2, 0, 0);
			batch.end();

			batch.begin();
			layout.setText(mediumFont, line3);
			mediumFont.setColor(Color.valueOf("ffffff"));
			mediumFont.draw(batch, line3, (CoreSettings.P_WIDTH - layout.width) / 2,
					(CoreSettings.P_HEIGHT - layout.height) / 2 + (CoreSettings.P_TILE_SIZE * 11));
			batch.end();
		}
		if (timer >= 16.5 && timer < 21) {
			batch.begin();
			batch.draw(background2, 0, 0);
			batch.end();

			batch.begin();
			layout.setText(mediumFont, line4);
			mediumFont.setColor(Color.valueOf("ffffff"));
			mediumFont.draw(batch, line4, (CoreSettings.P_WIDTH - layout.width) / 2,
					(CoreSettings.P_HEIGHT - layout.height) / 2 + (CoreSettings.P_TILE_SIZE * 11));
			batch.end();
		}
		if (timer >= 21 && timer < 26) {
			batch.begin();
			batch.draw(background2, 0, 0);
			batch.end();

			batch.begin();
			layout.setText(mediumFont, line5);
			mediumFont.setColor(Color.valueOf("00ff00"));
			mediumFont.draw(batch, line5, (CoreSettings.P_WIDTH - layout.width) / 2 + (CoreSettings.P_TILE_SIZE * 4),
					(CoreSettings.P_HEIGHT - layout.height) / 2 + (CoreSettings.P_TILE_SIZE * 8));
			batch.end();
		}
		if (timer >= 26 && timer < 30.5) {
			batch.begin();
			batch.draw(background2, 0, 0);
			batch.end();

			batch.begin();
			layout.setText(mediumFont, line6);
			mediumFont.setColor(Color.valueOf("00ff00"));
			mediumFont.draw(batch, line6, (CoreSettings.P_WIDTH - layout.width) / 2 + (CoreSettings.P_TILE_SIZE * 4),
					(CoreSettings.P_HEIGHT - layout.height) / 2 + (CoreSettings.P_TILE_SIZE * 8));
			batch.end();
		}
		if (timer >= 30.5 && timer < 35) {
			batch.begin();
			batch.draw(background2, 0, 0);
			batch.end();

			batch.begin();
			layout.setText(mediumFont, line7);
			mediumFont.setColor(Color.valueOf("ffff00"));
			mediumFont.draw(batch, line7, (CoreSettings.P_WIDTH - layout.width) / 2 - (CoreSettings.P_TILE_SIZE * 4),
					(CoreSettings.P_HEIGHT - layout.height) / 2 + (CoreSettings.P_TILE_SIZE * 6));
			batch.end();
		}
		if (timer >= 35 && timer < 40) {
			batch.begin();
			batch.draw(background2, 0, 0);
			batch.end();

			batch.begin();
			layout.setText(mediumFont, line8);
			mediumFont.setColor(Color.valueOf("00ff00"));
			mediumFont.draw(batch, line8, (CoreSettings.P_WIDTH - layout.width) / 2 + (CoreSettings.P_TILE_SIZE * 4),
					(CoreSettings.P_HEIGHT - layout.height) / 2 + (CoreSettings.P_TILE_SIZE * 8));
			batch.end();
		}
		if (timer >= 40 && timer < 44) {
			batch.begin();
			batch.draw(background3, 0, 0);
			batch.end();

			batch.begin();
			layout.setText(mediumFont, line9);
			mediumFont.setColor(Color.valueOf("00ff00"));
			mediumFont.draw(batch, line9, (CoreSettings.P_WIDTH - layout.width) / 2 + (CoreSettings.P_TILE_SIZE * 4),
					(CoreSettings.P_HEIGHT - layout.height) / 2 + (CoreSettings.P_TILE_SIZE * 8));
			batch.end();

			batch.begin();
			layout.setText(mediumFont, line9);
			mediumFont.setColor(Color.valueOf("ffff00"));
			mediumFont.draw(batch, line9, (CoreSettings.P_WIDTH - layout.width) / 2 - (CoreSettings.P_TILE_SIZE * 4),
					(CoreSettings.P_HEIGHT - layout.height) / 2 + (CoreSettings.P_TILE_SIZE * 6));
			batch.end();
		}
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
		super.controls();

		if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
			music.stop();
			stateFinished = true;
			nextStateID = StateManager.CREDITS_STATE;
		}
	}

}
