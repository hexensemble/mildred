package com.hexensemble.mildred.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Abstract class for all states.
 * 
 * @author HexEnsemble
 * @author www.hexensemble.com
 * @version Alpha 2.1.2
 * @since Alpha 1.0.0
 */
public abstract class State {

	protected AssetManager assetManager;
	protected Viewport viewport;
	protected OrthographicCamera camera;

	protected Cursor cursor;

	/**
	 * Is the state finished with?
	 */
	public static boolean stateFinished;

	/**
	 * State ID for next state.
	 */
	public static int nextStateID;

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
	public State(AssetManager assetManager, Viewport viewport, OrthographicCamera camera) {
		this.assetManager = assetManager;
		this.viewport = viewport;
		this.camera = camera;

		stateFinished = false;
		nextStateID = 0;
	}

	/**
	 * Update.
	 * 
	 * @param delta
	 *            Delta time.
	 */
	public void update(float delta) {
		controls();
	}

	/**
	 * Render.
	 * 
	 * @param batch
	 *            Sprite batch.
	 */
	public void render(SpriteBatch batch) {
		Gdx.graphics.setCursor(cursor);
	}

	/**
	 * Dispose.
	 */
	public void dispose() {
		cursor.dispose();
	}

	protected void controls() {

	}

}
