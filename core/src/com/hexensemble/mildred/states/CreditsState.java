package com.hexensemble.mildred.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
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
 * Represents the credits state.
 * 
 * @author HexEnsemble
 * @author www.hexensemble.com
 * @version 1.0.1
 * @since Alpha 1.2.0
 */
public class CreditsState extends State {

	public static final String TITLE = CoreSettings.TITLE;
	public static final String AUTHOR = "by " + CoreSettings.AUTHOR;
	public static final String VERSION = CoreSettings.VERSION;
	public static final String DATE = CoreSettings.DATE;
	public static final String PROGRAMMING = "Programming";
	public static final String PROGRAMMER = "Jez Harding";
	public static final String ART = "Art";
	public static final String ARTIST = "DENZI";
	public static final String MUSIC = "Music & Sound";
	public static final String MUSICIAN = "JZTR";
	public static final String COPYRIGHT = CoreSettings.COPYRIGHT;
	public static final String ART_LICENCE_1 = "DENZI's artwork used under a Creative Commons Licence:";
	public static final String ART_LICENCE_2 = "Attribution-ShareAlike 3.0 Unported.";

	private Texture background;

	private BitmapFont largeFont;
	private BitmapFont mediumFont;
	private BitmapFont smallFont;
	private GlyphLayout layout;

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
	public CreditsState(AssetManager assetManager, Viewport viewport, OrthographicCamera camera) {
		super(assetManager, viewport, camera);

		viewport.setWorldSize(CoreSettings.P_WIDTH, CoreSettings.P_HEIGHT);
		viewport.apply();
		camera.setToOrtho(false, camera.viewportWidth, camera.viewportHeight);

		cursor = Gdx.graphics.newCursor(new Pixmap(Gdx.files.internal("graphics/mouse-blank.png")),
				(int) (CoreSettings.P_TILE_SIZE / 2), (int) (CoreSettings.P_TILE_SIZE / 2));

		background = assetManager.get("graphics/credits-background.png", Texture.class);

		largeFont = assetManager.get("fonts/Track-30.fnt", BitmapFont.class);
		mediumFont = assetManager.get("fonts/Track-20.fnt", BitmapFont.class);
		smallFont = assetManager.get("fonts/Track-14.fnt", BitmapFont.class);
		layout = new GlyphLayout();
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

		batch.begin();
		batch.draw(background, 0, 0);
		batch.end();

		batch.begin();
		layout.setText(largeFont, TITLE);
		largeFont.setColor(Color.valueOf("ff0000"));
		largeFont.draw(batch, TITLE, (CoreSettings.P_WIDTH - layout.width) / 2,
				(CoreSettings.P_HEIGHT - layout.height) / 2 + (CoreSettings.P_TILE_SIZE * 10));
		batch.end();

		batch.begin();
		layout.setText(mediumFont, AUTHOR);
		mediumFont.setColor(Color.valueOf("ffffff"));
		mediumFont.draw(batch, AUTHOR, (CoreSettings.P_WIDTH - layout.width) / 2,
				(CoreSettings.P_HEIGHT - layout.height) / 2 + (CoreSettings.P_TILE_SIZE * 8));
		batch.end();

		batch.begin();
		layout.setText(smallFont, VERSION);
		smallFont.setColor(Color.valueOf("ffffff"));
		smallFont.draw(batch, VERSION, (CoreSettings.P_WIDTH - layout.width) / 2,
				(CoreSettings.P_HEIGHT - layout.height) / 2 + (CoreSettings.P_TILE_SIZE * 6));
		batch.end();

		batch.begin();
		layout.setText(smallFont, DATE);
		smallFont.setColor(Color.valueOf("ffffff"));
		smallFont.draw(batch, DATE, (CoreSettings.P_WIDTH - layout.width) / 2,
				(CoreSettings.P_HEIGHT - layout.height) / 2 + (CoreSettings.P_TILE_SIZE * 5));
		batch.end();

		batch.begin();
		layout.setText(mediumFont, PROGRAMMING);
		mediumFont.setColor(Color.valueOf("ff0000"));
		mediumFont.draw(batch, PROGRAMMING, (CoreSettings.P_WIDTH - layout.width) / 2,
				(CoreSettings.P_HEIGHT - layout.height) / 2 + (CoreSettings.P_TILE_SIZE * 3));
		batch.end();

		batch.begin();
		layout.setText(mediumFont, PROGRAMMER);
		mediumFont.setColor(Color.valueOf("ffffff"));
		mediumFont.draw(batch, PROGRAMMER, (CoreSettings.P_WIDTH - layout.width) / 2,
				(CoreSettings.P_HEIGHT - layout.height) / 2 + (CoreSettings.P_TILE_SIZE * 2));
		batch.end();

		batch.begin();
		layout.setText(mediumFont, ART);
		mediumFont.setColor(Color.valueOf("ff0000"));
		mediumFont.draw(batch, ART, (CoreSettings.P_WIDTH - layout.width) / 2,
				(CoreSettings.P_HEIGHT - layout.height) / 2);
		batch.end();

		batch.begin();
		layout.setText(mediumFont, ARTIST);
		mediumFont.setColor(Color.valueOf("ffffff"));
		mediumFont.draw(batch, ARTIST, (CoreSettings.P_WIDTH - layout.width) / 2,
				(CoreSettings.P_HEIGHT - layout.height) / 2 - CoreSettings.P_TILE_SIZE);
		batch.end();

		batch.begin();
		layout.setText(mediumFont, MUSIC);
		mediumFont.setColor(Color.valueOf("ff0000"));
		mediumFont.draw(batch, MUSIC, (CoreSettings.P_WIDTH - layout.width) / 2,
				(CoreSettings.P_HEIGHT - layout.height) / 2 - (CoreSettings.P_TILE_SIZE * 3));
		batch.end();

		batch.begin();
		layout.setText(mediumFont, MUSICIAN);
		mediumFont.setColor(Color.valueOf("ffffff"));
		mediumFont.draw(batch, MUSICIAN, (CoreSettings.P_WIDTH - layout.width) / 2,
				(CoreSettings.P_HEIGHT - layout.height) / 2 - (CoreSettings.P_TILE_SIZE * 4));
		batch.end();

		batch.begin();
		layout.setText(smallFont, COPYRIGHT);
		smallFont.setColor(Color.valueOf("ffffff"));
		smallFont.draw(batch, COPYRIGHT, (CoreSettings.P_WIDTH - layout.width) / 2,
				(CoreSettings.P_HEIGHT - layout.height) / 2 - (CoreSettings.P_TILE_SIZE * 8));
		batch.end();

		batch.begin();
		layout.setText(smallFont, ART_LICENCE_1);
		smallFont.setColor(Color.valueOf("ffffff"));
		smallFont.draw(batch, ART_LICENCE_1, (CoreSettings.P_WIDTH - layout.width) / 2,
				(CoreSettings.P_HEIGHT - layout.height) / 2 - (CoreSettings.P_TILE_SIZE * 9));
		batch.end();

		batch.begin();
		layout.setText(smallFont, ART_LICENCE_2);
		smallFont.setColor(Color.valueOf("ffffff"));
		smallFont.draw(batch, ART_LICENCE_2, (CoreSettings.P_WIDTH - layout.width) / 2,
				(CoreSettings.P_HEIGHT - layout.height) / 2 - (CoreSettings.P_TILE_SIZE * 10));
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
		super.controls();

		if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER) || Gdx.input.isKeyJustPressed(Input.Keys.SPACE)
				|| Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
			stateFinished = true;
			nextStateID = StateManager.MENU_STATE;
		}
	}

}
