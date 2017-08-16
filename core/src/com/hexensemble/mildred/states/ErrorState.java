package com.hexensemble.mildred.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.hexensemble.mildred.system.CoreSettings;

/**
 * Represents the error state.
 * 
 * @author HexEnsemble
 * @author www.hexensemble.com
 * @version Beta 2.0.0
 * @since Beta 2.0.0
 */
public class ErrorState extends State {

	private BitmapFont largeFont;
	private BitmapFont mediumFont;
	private GlyphLayout layout;

	private String title;

	private String error;

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
	public ErrorState(AssetManager assetManager, Viewport viewport, OrthographicCamera camera) {
		super(assetManager, viewport, camera);

		viewport.setWorldSize(CoreSettings.P_WIDTH, CoreSettings.P_HEIGHT);
		viewport.apply();
		camera.setToOrtho(false, camera.viewportWidth, camera.viewportHeight);

		cursor = Gdx.graphics.newCursor(new Pixmap(Gdx.files.internal("graphics/mouse-blank.png")),
				(int) (CoreSettings.P_TILE_SIZE / 2), (int) (CoreSettings.P_TILE_SIZE / 2));

		largeFont = assetManager.get("fonts/Track-30.fnt", BitmapFont.class);
		mediumFont = assetManager.get("fonts/Track-20.fnt", BitmapFont.class);
		layout = new GlyphLayout();

		title = "Error!";

		error = "Something broke. Did you you try and load a level that wasn't there?";
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
		layout.setText(largeFont, title);
		largeFont.setColor(Color.valueOf("ff0000"));
		largeFont.draw(batch, title, (CoreSettings.P_WIDTH - layout.width) / 2,
				(CoreSettings.P_HEIGHT - layout.height) / 2 + (CoreSettings.P_TILE_SIZE * 4));
		batch.end();

		batch.begin();
		layout.setText(mediumFont, error);
		mediumFont.setColor(Color.valueOf("ffffff"));
		mediumFont.draw(batch, error, (CoreSettings.P_WIDTH - layout.width) / 2,
				(CoreSettings.P_HEIGHT - layout.height) / 2);
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
